package dk.mimer.mmetric.sonar.decorators;

import org.sonar.api.measures.Metric;

public interface MetricDecorator {

	/*
	 * The metric on which the calculation is based - the decorated metric.
	 */
	public Metric getDecoratedMetric();
	
	/**
	 * @return The metric of this Decorator.
	 */
	public Metric getMetric();
}