package dk.mimer.mmetric.sonar.decorators.main;

import static dk.mimer.mmetric.sonar.MaintainabilityMetrics.TEST_COVERAGE_WEIGHT;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.decorators.AbstractCompositeWeightDecorator;

public class StabilityMetricDecorator extends AbstractCompositeWeightDecorator implements Decorator {

	public Metric getMetric() {
		return MaintainabilityMetrics.STABILITY;
	}
	
	@DependsUpon
	public List<Metric> dependsUpon() {
		return getDecoratedMetrics();
	}

	public List<Metric> getDecoratedMetrics() {
		return Arrays.asList(TEST_COVERAGE_WEIGHT);
	}
}
