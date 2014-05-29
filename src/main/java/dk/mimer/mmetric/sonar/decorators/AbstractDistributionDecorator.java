package dk.mimer.mmetric.sonar.decorators;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public abstract class AbstractDistributionDecorator extends AbstractDecorator implements DistributionMetricDecorator {

	abstract Number[] getVolumeDistribution();
	
	/**
	 * The method is executed on the whole tree of resources.
	 * Bottom-up navigation : Java methods -> Java classes -> files -> packages -> modules -> project
	 *
	 * @param resource
	 * @param context
	 */
	public void decorate(Resource resource, DecoratorContext context) {
		logDecoration(resource, context);
		
		if (isRootResource(resource)) {
			logger.debug("We've stumbled into a method...");
			// Sonar API includes many libraries like commons-lang and google-collections
	
			Measure measure = buildBlockMetricDistribution(context, getDecoratedMetric(), getMetric(), getVolumeDistribution());
			logger.debug("Data from block measure: " + (measure != null ? measure.getData() : null));
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
	 * @param resource The resource currently being investigated.
	 * @return true if the parameter resource, is a "root" resource, that is to be counted using the Metric.
	 */
	boolean isRootResource(Resource resource) {
		return Scopes.isBlockUnit(resource);
	}

	/**
	 * Combine metrics from collected Methods (blocks).
	 * 
	 * @param context
	 * @param metric
	 * @param bottomLimits
	 * @return a combined measure...
	 */
	Measure buildCombinedMetricDistribution(DecoratorContext context, Metric metric, Number[] bottomLimits) {
		RangeDistributionBuilder builder = new RangeDistributionBuilder(metric, bottomLimits);
		for (Measure childMeasure : context.getChildrenMeasures(metric)) {
			builder.add(childMeasure);
		}
		return builder.build();
	}

	Measure buildBlockMetricDistribution(DecoratorContext context, Metric metric, Metric resultMetric, Number[] bottomLimits) {
		logger.debug("  - Building block? " + metric.getName()+" / "+resultMetric.getName());
		int measure = MeasureUtils.getValue(context.getMeasure(metric), 0.0).intValue();
		int value = MeasureUtils.getValue(context.getMeasure(CoreMetrics.NCLOC), 0.0).intValue();
		RangeDistributionBuilder distribution = new RangeDistributionBuilder(resultMetric, bottomLimits);
		distribution.add(measure, value);
		if (measure > bottomLimits[0].intValue() || measure > bottomLimits[1].intValue()) {
			logger.warn("MinusMinus grade given for ["+getMetric().getKey()+"] with value ["+measure+"] and nloc ["+value+"] to: "+context.getResource().getLongName());
		}
		if (measure > bottomLimits[1].intValue()) {
			logger.warn("Minus grade given for ["+getMetric().getKey()+"] with value ["+measure+"] and nloc ["+value+"] to: "+context.getResource().getLongName());
		}
		if (measure > bottomLimits[2].intValue()) {
			logger.warn("Zero grade given for ["+getMetric().getKey()+"] with value ["+measure+"] and nloc ["+value+"] to: "+context.getResource().getLongName());
		}
		return distribution.build();
	}


}
