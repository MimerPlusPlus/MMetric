package dk.mimer.mmetric.sonar.decorators;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class MethodComplexityDistribution extends AbstractDistributionDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.COMPLEXITY_DISTRIBUTION_METHOD;
	private static final Metric DECORATED_METRIC = CoreMetrics.COMPLEXITY;
	public static Number[] VOLUME_DISTRIBUTION = {50, 20, 10, 0};

	@DependsUpon
	public List<Metric> dependsUpon() {
		return Arrays.asList(getDecoratedMetric());
	}

	public Metric getMetric() {
		return METRIC;
	}

	public Metric getDecoratedMetric() {
		return DECORATED_METRIC;
	}

	Number[] getVolumeDistribution() {
		return VOLUME_DISTRIBUTION;
	}
}
