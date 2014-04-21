package dk.mimer.mmetric.sonar.decorators;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class CodeDuplicationWeight extends AbstractWeightDecorator implements Decorator {

	private static final Metric METRIC = MaintainabilityMetrics.DUPLICATION_WEIGHT;
	private static final Metric DECORATED_METRIC = CoreMetrics.DUPLICATED_LINES_DENSITY;
	public static Number[] VOLUME_DISTRIBUTION = {20, 10, 5, 3, 0};

	@DependsUpon
	public List<Metric> dependsUpon() {
		return Arrays.asList(getDecoratedMetric());
	}
	
	@Override
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
