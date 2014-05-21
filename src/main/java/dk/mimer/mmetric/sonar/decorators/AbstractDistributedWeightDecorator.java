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
	}
	
	private boolean holdsUpperBounds(MeasureWeight measureWeight, double[] values) {
		boolean hold = true;
		double[] boundries = getDistributionUpperBoundry(measureWeight);
		for (int i = 0; hold && i < boundries.length; i++) {
			hold = (boundries[i] < 0 || values[i]== 0 || values[i] <= boundries[i]); // Less that 0 is not good ;-)
			if (!hold) {
				System.out.println("Failed Upper Boundry test ["+i+"]: "+values[i]+ " <= "+boundries[i]);
			}
		}
		return hold;
	}
	
	/** All must be lower than their bounds! */
	private boolean allLowerThanBounds(MeasureWeight measureWeight, double[] values) {
		boolean hold = true;
		double[] boundries = getDistributionUpperBoundry(measureWeight);
		for (int i = 0; hold && i < boundries.length; i++) {
			hold = (boundries[i] < 0 || values[i]== 0 || values[i] < boundries[i]); // Less that 0 is not good ;-)
			if (!hold) {
				System.out.println("allLower: Failed Boundry test ["+i+"]: "+values[i]+ " < "+boundries[i]);
			}
		}
		return hold;
		/*
		boolean violates = false;
		double[] boundries = getDistributionUpperBoundry(measureWeight);
		for (int i = 0; !violates && i < boundries.length; i++) {
			//violates = (values[i] == 0 || (boundries[i] >= 0 && values[i] >= boundries[i]));
			violates = (boundries[i] >= 0 && (values[i] == 0 || values[i] > boundries[i]));
			if (violates) {
				System.out.println("Boundry violated ["+i+"]: "+values[i]+ " > "+boundries[i]);
			}
		}
		return violates;
		*/
	}
	
	abstract double[] getDistributionUpperBoundry(MeasureWeight measureWeight);

	boolean isVeryLowRisk(double[] values) {
		return (holdsUpperBounds(PLUS_PLUS, values));  // < 25
	}
	
	boolean isLowRisk(double[] values) {
		return (allLowerThanBounds(PLUS, values) && !allLowerThanBounds(PLUS_PLUS, values)); // 25 <= X < 30 
	}
	
	boolean isMediumRisk(double[] values) {
		return (allLowerThanBounds(ZERO, values) && !allLowerThanBounds(PLUS, values)); //  
	}

	boolean isHighRisk(double[] values) {
		return (allLowerThanBounds(MINUS, values) && !allLowerThanBounds(ZERO, values));
	}

	boolean isVeryHighRisk(double[] values) {
		return (!allLowerThanBounds(MeasureWeight.MINUS, values));
	}
	

}
