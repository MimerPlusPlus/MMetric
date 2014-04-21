package dk.mimer.mmetric.sonar.decorators;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.resources.Resource;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public abstract class AbstractWeightDecorator extends AbstractDecorator {

	abstract Number[] getVolumeDistribution();
	
	public void decorate(Resource resource, DecoratorContext context) {
		// Bottom-up navigation : Java methods -> Java classes -> files -> packages -> modules -> project
		logDecoration(resource, context);
		if (shouldPersistMeasures(context)) {
			double value = getDecoratedMetricValue(context);
			logger.debug("Decorated value of metric ["+getMetric().getName()+"] for ["+resource.getName()+"] : "+value);
			MeasureWeight weight = calculateWeight(context);
			logger.debug("Weight: "+weight);
			if (weight != null) {
				Measure measure = new Measure(getMetric(), value, weight.getTitle());
				saveMeasure(context, measure);
			}
		} else {
			logger.debug(" - Ignoreing scope below package ["+resource.getScope()+"] : "+resource.getName()+" \n\n");
		}
	}
/*
	public boolean shouldPersistMeasures(DecoratorContext context) {
		return (ResourceUtils.isPackage(context.getResource()) ||
				ResourceUtils.isProject(context.getResource()));
	}
*/
	MeasureWeight calculateWeight(DecoratorContext context) {
		return calculateWeight(getDecoratedMetricValue(context));
	}
	
	double getDecoratedMetricValue(DecoratorContext context) {
		return MeasureUtils.getValue(context.getMeasure(getDecoratedMetric()), 0.0);
	}

	MeasureWeight calculateWeight(double metricValue) {
		logger.debug("Calculating weight for: "+metricValue);
		Number[] volumeDistribution = getVolumeDistribution();
		MeasureWeight weight = null;
		for (int i = 0; i < volumeDistribution.length && weight == null; i++) {
			if (volumeDistribution[i].doubleValue() <= metricValue) {
				weight = MeasureWeight.valueOf(i);
			}
		}
		return (weight != null ? weight : MeasureWeight.MINUS_MINUS);
	}
}
