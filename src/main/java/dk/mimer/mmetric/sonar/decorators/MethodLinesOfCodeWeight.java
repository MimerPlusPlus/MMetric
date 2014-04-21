package dk.mimer.mmetric.sonar.decorators;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;

import java.util.HashMap;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class MethodLinesOfCodeWeight extends AbstractDistributedWeightDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.UNIT_SIZE;
	private static final Metric DECORATED_METRIC = MethodLinesOfCodeDistribution.METRIC;
	private HashMap<MeasureWeight, double[]> boundries = null;

	@DependsUpon
	public Metric dependsUpon() {
		return getDecoratedMetric();
	}
	
	@Override
	Number[] getVolumeDistribution() {
		return MethodLinesOfCodeDistribution.VOLUME_DISTRIBUTION;
	}
	
	@Override
	double[] getDistributionValues(Measure measure) {
		return getValuesInPercentage(super.getDistributionValues(measure));
	}
	
	protected double[] getDistributionUpperBoundry(MeasureWeight measureWeight) {
		init();
		return boundries.get(measureWeight); 
	}
	
	private void init() {
		boundries = new HashMap<MeasureWeight, double[]>();
		boundries.put(PLUS_PLUS,   	new double[] {-1,	25,	 0,	0});
		boundries.put(PLUS,        	new double[] {-1,	30,	 5,	0});
		boundries.put(ZERO,       	new double[] {-1,	40,	10,	0});
		boundries.put(MINUS, 		new double[] {-1,	50,	15,	5}
		);
	}
	
	public Metric getDecoratedMetric() {
		return DECORATED_METRIC;
	}

	public Metric getMetric() {
		return METRIC;
	}
}
