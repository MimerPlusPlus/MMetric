package dk.mimer.mmetric.sonar.decorators;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public abstract class AbstractDistributionDecorator extends AbstractDecorator implements DistributionMetricDecorator {

	abstract Number[] getVolumeDistribution();
	
	public void decorate(Resource resource, DecoratorContext context) {
		// This method is executed on the whole tree of resources.
		// Bottom-up navigation : Java methods -> Java classes -> files -> packages -> modules -> project
		logDecoration(resource, context);
		
		if (Scopes.isBlockUnit(resource)) {
			logger.debug("We've stumbled into a method...");
			// Sonar API includes many libraries like commons-lang and google-collections
	
			Measure measure = buildBlockMetricDistribution(context, getDecoratedMetric(), getMetric(), getVolumeDistribution());
			logger.debug("Data from block measure: " + measure.getData());
			context.saveMeasure(measure);
		} else {
			Measure measure = buildCombinedMetricDistribution(context, getMetric(), getVolumeDistribution());
			if (shouldPersistMeasures(context)) {
				saveMeasure(context, measure);
			} else {
				logger.debug("Persisting to memory");
				context.saveMeasure(measure.setPersistenceMode(PersistenceMode.MEMORY));
			}
		}
	}

	/**
	 * Combine metrics from collected Methods (blocks).
	 * 
	 * @param context
	 * @param metric
	 * @param bottomLimits
	 * @return a combined measure...
	 */
	private Measure buildCombinedMetricDistribution(DecoratorContext context, Metric metric, Number[] bottomLimits) {
		RangeDistributionBuilder builder = new RangeDistributionBuilder(metric, bottomLimits);
		for (Measure childMeasure : context.getChildrenMeasures(metric)) {
			builder.add(childMeasure);
		}
		return builder.build();
	}

	private Measure buildBlockMetricDistribution(DecoratorContext context, Metric metric, Metric resultMetric, Number[] bottomLimits) {
		logger.debug("  - Building block? " + metric.getName()+" / "+resultMetric.getName());
		int measure = MeasureUtils.getValue(context.getMeasure(metric), 0.0).intValue();
		//int ncloc = MeasureUtils.getValue(context.getMeasure(CoreMetrics.NCLOC), 0.0).intValue();
		int value = 1; 
		RangeDistributionBuilder distribution = new RangeDistributionBuilder(resultMetric, bottomLimits);
		distribution.add(measure, value);
		return distribution.build();
	}


}
