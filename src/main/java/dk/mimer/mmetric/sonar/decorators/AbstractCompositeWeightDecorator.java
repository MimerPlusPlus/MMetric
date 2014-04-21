package dk.mimer.mmetric.sonar.decorators;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Resource;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public abstract class AbstractCompositeWeightDecorator extends AbstractDecorator {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void decorate(Resource resource, DecoratorContext context) {
		logDecoration(resource, context);
		if (shouldPersistMeasures(context)) {
			MeasureWeight weight = calculateAverage(getMeasures(context));
			Measure measure = new Measure(getMetric(), (double)weight.getValue(), weight.getTitle());
			saveMeasure(context, measure);
		}
	}
	
	MeasureWeight calculateWeightAverage(List<MeasureWeight> measureWeights) {
		int total = 0;
		for (MeasureWeight measureWeight : measureWeights) {
			total += measureWeight.getValue();
		}
		return (measureWeights.size() > 0 ? MeasureWeight.valueOf(total/measureWeights.size()) : MeasureWeight.ZERO);
	}

	MeasureWeight calculateAverage(List<Measure> measures) {
		List<MeasureWeight> weights = new ArrayList<MeasureWeight>();
		for (Measure measure : measures) {
			if (measure != null) {
				weights.add(MeasureWeight.fromValue(measure.getData()));
			} else {
				logger.warn("Measure is null! "+this.getClass().getName());
			}
		}
		return calculateWeightAverage(weights);
	}

	List<Measure> getMeasures(DecoratorContext context) {
		List<Measure> measures = new ArrayList<Measure>();
		for (Metric metric : getDecoratedMetrics()) {
			measures.add(context.getMeasure(metric));
		}
		return measures;
	}

	abstract protected List<Metric> getDecoratedMetrics();
	

	@Override
	public Metric getDecoratedMetric() {
		Log.error("Should NOT have been called! ");
		return null;
	}
}
