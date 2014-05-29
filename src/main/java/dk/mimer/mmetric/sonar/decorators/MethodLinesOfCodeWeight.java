package dk.mimer.mmetric.sonar.decorators;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.value.Risk;

public class MethodLinesOfCodeWeight extends AbstractDistributedWeightDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.UNIT_SIZE;
	private static final Metric DECORATED_METRIC = MethodLinesOfCodeDistribution.METRIC;
	private double[][] riskBoundries = new double[][] {
			new double[] 	{	100, 100, 100, 100},
			new double[] 	{	 25, 30, 40, 50 },
			new double [] 	{ 	  0,  5, 10, 15 },
			new double[] 	{ 	  0,  0,  0,  5 }
	};

	@DependsUpon
	public List<Metric> dependsUpon() {
		return Arrays.asList(getDecoratedMetric(), CoreMetrics.NCLOC);
	}
	
	@Override
	Number[] getVolumeDistribution() {
		return MethodLinesOfCodeDistribution.VOLUME_DISTRIBUTION;
	}
	
	@Override
	double[] getDistribution(Measure measure) {
		return getValuesInPercentage(getDistributionValues(measure));
	}
	
	@Override
	double[] getRiskBoundries(Risk risk) {
		return riskBoundries[risk.getValue()]; 
	}
	
	public Metric getDecoratedMetric() {
		return DECORATED_METRIC;
	}

	public Metric getMetric() {
		return METRIC;
	}
}
