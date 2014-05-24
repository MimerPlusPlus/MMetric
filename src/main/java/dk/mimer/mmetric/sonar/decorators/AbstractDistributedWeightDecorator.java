package dk.mimer.mmetric.sonar.decorators;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS_MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;

import dk.mimer.mmetric.sonar.value.MeasureWeight;
import dk.mimer.mmetric.sonar.value.Risk;

public abstract class AbstractDistributedWeightDecorator extends AbstractWeightDecorator {

	MeasureWeight calculateWeight(DecoratorContext context) {
		MeasureWeight weight = null;
		Measure measure = context.getMeasure(getDecoratedMetric());
		logger.info("Calculating distributed weight : "+measure);
		if (measure != null) {
			double[] values = getDistributionValues(measure);
			weight = evaluateRisk(values);
		}
		return weight;
	}

	/**
	 * Calculate the distribution values, from SonarQube data field. Data fields are
	 * String arrays separated by semicolons.
	 * 
	 * @param measure The measure whose date to get.
	 * @return the values extracted form data field.
	 */
	double[] getDistributionValues(Measure measure) {
		String data = measure.getData();
		logger.info("Data for ["+measure.getMetricKey()+"]: "+data);
		double[] values = new double[getVolumeDistribution().length];
		String[] split = data.split(";");
		HashMap<Double, Double> map = new HashMap<Double, Double>();
		for (int i = 0; i < values.length; i++) {
			String[] value = split[i].split("=");
			map.put(Double.parseDouble(value[0]), Double.parseDouble(value[1]));
		}
		List<Double> keys = new ArrayList<Double>();
		keys.addAll(map.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			values[i] = map.get(keys.get(i));
		}
		logger.info("Values: "+values[0]+","+values[1]+","+values[2]+","+values[3]+",");
		return values;
	}
	
	/**
	 * Calculate percentage values from each cell in the distribution, from the total amount
	 * gotten by adding all values.
	 * 
	 * @param values values to calculate
	 * @return the values converted to percentage of the total amount.
	 */
	double[] getValuesInPercentage(double[] values) {
		double total = 0;
		double[] pct = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			total += values[i];
		}
		for (int i = 0; i < values.length; i++) {
			pct[i] = (values[i] / total) * 100;
		}
		return pct;
	}
	
	protected MeasureWeight evaluateRisk(double[] values) {
		int minimumWeight = PLUS_PLUS.getValue();
		for (Risk risk : Risk.values()) {
			int riskWeight = calculateRiskValue(values[risk.getValue()], getRiskBoundries(risk));
			System.out.println("Calculated Risk isolated to ["+risk.getTitle()+"] : "+MeasureWeight.valueOf(riskWeight));
			minimumWeight = Math.min(riskWeight, minimumWeight);
		}
		return MeasureWeight.valueOf(minimumWeight);
	}
	
	private int calculateRiskValue(double value, double[] riskBoundries) {
		int riskValue = MINUS_MINUS.getValue();
		for (int i = 0; i < riskBoundries.length && riskValue == MINUS_MINUS.getValue(); i++) {
			System.out.println("    "+ value +" <= "+riskBoundries[i]+"   = "+(value <= riskBoundries[i]));
			if (value <= riskBoundries[i]) {
				riskValue = (PLUS_PLUS.getValue() - i);
			}
		}
		System.out.println("Returning :"+riskValue);
		return riskValue;
	}

	abstract double[] getRiskBoundries(Risk risk);

	/*
	private MeasureWeight evaluateRisk(double[] values) {
		MeasureWeight weight = null;
		if (isVeryLowRisk(values)) {
			weight = PLUS_PLUS;
		} else if (isLowRisk(values)) {
			weight = PLUS;
		} else if (isMediumRisk(values)) {
			weight = MeasureWeight.ZERO;
		} else if (isHighRisk(values)) {
			weight = MINUS;
		} else {
			weight = MINUS_MINUS;
		}
		System.out.println("Evaluating Risk for : Low:["+values[0]+"] Medium:["+values[1]+"] High:["+values[2]+"] Very high:["+values[3]+"] = "+weight);
		return weight;
	}*/
	
	/*
	private boolean holdsUpperBounds(MeasureWeight measureWeight, double[] values) {
		boolean hold = true;
		double[] boundries = getRiskBoundries(measureWeight);
		for (int i = 0; hold && i < boundries.length; i++) {
			hold = (boundries[i] < 0 || values[i]== 0 || values[i] <= boundries[i]); // Less that 0 is not good ;-)
			if (!hold) {
				System.out.println("Failed Upper Boundry test ["+i+"]: "+values[i]+ " <= "+boundries[i]);
			}
		}
		return hold;
	}*/
	
	/** All must be lower than their bounds! */
	/*
	private boolean allLowerThanBounds(MeasureWeight measureWeight, double[] values) {
		boolean hold = true;
		double[] boundries = getRiskBoundries(measureWeight);
		for (int i = 0; hold && i < boundries.length; i++) {
			hold = (boundries[i] < 0 || values[i]== 0 || values[i] < boundries[i]); // Less that 0 is not good ;-)
			if (!hold) {
				System.out.println("allLower: Failed Boundry test ["+i+"]: "+values[i]+ " < "+boundries[i]);
			}
		}
		return hold;
	}*/
	

	boolean isPlusPlus(double[] values) {
		return PLUS_PLUS.equals(evaluateRisk(values));
	}
	
	boolean isPlus(double[] values) {
		return PLUS.equals(evaluateRisk(values)); 
	}
	
	boolean isZero(double[] values) {
		return ZERO.equals(evaluateRisk(values));  
	}

	boolean isMinus(double[] values) {
		return MINUS.equals(evaluateRisk(values));
	}

	boolean isMinusMinus(double[] values) {
		return MINUS_MINUS.equals(evaluateRisk(values));
	}

}
