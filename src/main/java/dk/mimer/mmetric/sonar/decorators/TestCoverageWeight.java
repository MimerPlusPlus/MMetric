package dk.mimer.mmetric.sonar.decorators;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class TestCoverageWeight extends AbstractWeightDecorator implements Decorator {

	private static final Metric METRIC = MaintainabilityMetrics.TEST_COVERAGE_WEIGHT;
	private static final Metric DECORATED_METRIC = CoreMetrics.COVERAGE;
	public static Number[] VOLUME_DISTRIBUTION = {95, 80, 60, 20, 0};

	@DependsUpon
	public List<Metric> dependsUpon() {
		return Arrays.asList(getDecoratedMetric());
	}
	
	public Metric getDecoratedMetric() {
		return DECORATED_METRIC;
	}

	Number[] getVolumeDistribution() {
		return VOLUME_DISTRIBUTION;
	}

	public Metric getMetric() {
		return METRIC;
	}

	MeasureWeight calculateWeight(double metricValue) {
		Number[] volumeDistribution = getVolumeDistribution();
		MeasureWeight weight = null;
		for (int i = 0; i < volumeDistribution.length && weight == null; i++) {
			if (volumeDistribution[i].doubleValue() < metricValue) {
				weight = MeasureWeight.valueOf(volumeDistribution.length-(i+1));
			}
		}
		return (weight != null ? weight : MeasureWeight.MINUS_MINUS);
	}
}
