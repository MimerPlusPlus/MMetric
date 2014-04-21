package dk.mimer.mmetric.sonar.decorators.main;

import static dk.mimer.mmetric.sonar.MaintainabilityMetrics.ANALYZABILITY;
import static dk.mimer.mmetric.sonar.MaintainabilityMetrics.CHANGEABILITY;
import static dk.mimer.mmetric.sonar.MaintainabilityMetrics.STABILITY;
import static dk.mimer.mmetric.sonar.MaintainabilityMetrics.TESTABILITY;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import dk.mimer.mmetric.sonar.decorators.AbstractCompositeWeightDecorator;

public class MaintainabilityMetricDecorator extends AbstractCompositeWeightDecorator implements Decorator {

	public static final Metric METRIC = MaintainabilityMetrics.MAINTAINABILITY;
	
	public Metric getMetric() {
		return METRIC;
	}
	
	@DependsUpon
	public List<Metric> dependsUpon() {
		return getDecoratedMetrics();
	}

	public List<Metric> getDecoratedMetrics() {
		return Arrays.asList(ANALYZABILITY,CHANGEABILITY,STABILITY,TESTABILITY);
	}
}
