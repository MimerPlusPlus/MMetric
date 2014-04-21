package dk.mimer.mmetric.sonar.decorators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

public abstract class AbstractDecorator implements MetricDecorator {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@DependedUpon
	public Metric dependedUpon() {
		return getMetric();
	}
	
	public boolean shouldExecuteOnProject(Project project) {
		// For now - All languages...
		return true;
	}
	
	void saveMeasure(DecoratorContext context, Measure measure) {
		if (context.getMeasure(measure.getMetric()) == null) {
			logger.debug("Saving Measure: "+measure.getMetric()+" data: "+measure.getData());
			context.saveMeasure(measure);
		} else {
			logger.info("\nUps! Trying to save the same measure twice! ("+context.getResource().getLongName()+")");
			Measure oldMeasure = context.getMeasure(measure.getMetric());
			logger.debug(oldMeasure.getMetricKey() +" <-> "+measure.getMetric());
			logger.debug(oldMeasure.getData() +" <-> "+measure.getData());
			logger.debug(oldMeasure.getValue() +" <-> "+measure.getValue());
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	/*
	 * The metric on which the calculation is based - the decorated metric.
	 */
	public abstract Metric getDecoratedMetric();
	
	double getMetricValue(DecoratorContext context, Metric metric) {
		return MeasureUtils.getValue(context.getMeasure(metric), 0.0);
	}
	
	public boolean shouldPersistMeasures(DecoratorContext context) {
		return (!ResourceUtils.isFile(context.getResource()) && !ResourceUtils.isClass(context.getResource()));
	}
	
	
	protected void logDecoration(Resource resource, DecoratorContext context) {
		logger.debug("Decorating resource ["+resource.getQualifier()+"] ["+getClass().getName()+"] ["+resource.getName()+"]: "+getDecoratedMetric());
	}

}
