package dk.mimer.mmetric.sonar.decorators;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS_MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.doubleThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class AbstractCompositeWeightDecoratorTest {

	@Test
	public void testCalculateAverage() {
		AbstractCompositeWeightDecorator a = new AbstractCompositeWeightDecorator() {
			List<MeasureWeight> getMeasureWeights() {return null;}
			public Metric getMetric() {return null;}
			public Metric getDecoratedMetric() {return null;}
			protected List<Metric> getDecoratedMetrics() {return null;}
		};
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(ZERO,PLUS,PLUS_PLUS,MINUS,MINUS_MINUS)));
		assertEquals(PLUS, a.calculateWeightAverage(Arrays.asList(PLUS,PLUS,PLUS)));
		assertEquals(MINUS, a.calculateWeightAverage(Arrays.asList(MINUS,MINUS,MINUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(ZERO,ZERO,ZERO,ZERO,PLUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(ZERO,PLUS)));
		
		assertEquals(MINUS, a.calculateWeightAverage(Arrays.asList(MINUS_MINUS,MINUS_MINUS,PLUS_PLUS)));
	}
	
}
