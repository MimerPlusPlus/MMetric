package dk.mimer.mmetric.sonar.decorators;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class MethodLinesOfCodeDistribution extends AbstractDistributionDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.UNIT_SIZE_DISTRIBUTION;
	private static final Metric DECORATED_METRIC = CoreMetrics.NCLOC;
	public static Number[] VOLUME_DISTRIBUTION = { 80, 40, 10, 0 };

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
}
