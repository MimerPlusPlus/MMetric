package dk.mimer.mmetric.sonar.decorators;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.value.MeasureWeight;
import dk.mimer.mmetric.sonar.value.Risk;

public class MethodComplexityWeight extends AbstractDistributedWeightDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.COMPLEXITY_WEIGHT;
	public static final Metric DECORATED_METRIC = MethodComplexityDistribution.METRIC;
	private double[][] riskBoundries = new double[][] {
			new double[] 	{	100, 100, 100, 100},
			new double[] 	{	 25, 30, 40, 50 },
			new double [] 	{ 	  0,  5, 10, 15 },
			new double[] 	{ 	  0,  0,  0,  5 }
	};
	
	@Override
	double[] getRiskBoundries(Risk risk) {
		return riskBoundries[risk.getValue()];
	}

	@DependsUpon
	public List<Metric> dependsUpon() {
		return Arrays.asList(getDecoratedMetric(), CoreMetrics.NCLOC);
	}
	
	double[] getDistribution(Measure measure) {
		return getValuesInPercentage(getDistributionValues(measure));
	}
	
	public Metric getDecoratedMetric() {
		return DECORATED_METRIC;
	}

	@Override
	Number[] getVolumeDistribution() {
		return MethodComplexityDistribution.VOLUME_DISTRIBUTION;
	}

	public Metric getMetric() {
		return METRIC;
	}

}
