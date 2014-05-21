package dk.mimer.mmetric.sonar.decorators;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS_MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.JavaFile;
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
import dk.mimer.mmetric.sonar.value.MeasureWeightTest;

public class AbstractCompositeWeightDecoratorTest {

	
	@Test
	public void testThatRootResourceIsCounted() {
		final MeasureWeight AN_MEASURE_WEIGHT = MeasureWeight.MINUS_MINUS;
		final boolean[] methodCalled = {false,false,false,false};
		AbstractCompositeWeightDecorator decorator = new TestableCompositeDecorator() {
			public boolean shouldPersistMeasures(DecoratorContext context) {
				methodCalled[0] = true;
				return true;
			}
			@Override
			MeasureWeight calculateAverage(List<Measure> measures) {
				methodCalled[1] = true;
				return AN_MEASURE_WEIGHT;
			}
			@Override
			void saveMeasure(DecoratorContext context, Measure measure) {
				methodCalled[2] = true;
			}
			@Override
			List<Measure> getMeasures(DecoratorContext context) {
				methodCalled[3] = true;
				return null;
			}
			@Override
			public Metric getMetric() {
				return CoreMetrics.COMPLEXITY;
			}
		};
		
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(methodCalled[0] && methodCalled[1] && methodCalled[2] && methodCalled[3]);
	}
	
	@Test
	public void testNotPersistedOfShouldnt() {
		final MeasureWeight AN_MEASURE_WEIGHT = MeasureWeight.MINUS_MINUS;
		final boolean[] methodCalled = {false,false,false,false};
		AbstractCompositeWeightDecorator decorator = new TestableCompositeDecorator() {
			public boolean shouldPersistMeasures(DecoratorContext context) {
				methodCalled[0] = true;
				return false;
			}
			@Override
			MeasureWeight calculateAverage(List<Measure> measures) {
				methodCalled[1] = true;
				return AN_MEASURE_WEIGHT;
			}
			@Override
			void saveMeasure(DecoratorContext context, Measure measure) {
				methodCalled[2] = true;
			}
			@Override
			List<Measure> getMeasures(DecoratorContext context) {
				methodCalled[3] = true;
				return null;
			}
			@Override
			public Metric getMetric() {
				return CoreMetrics.COMPLEXITY;
			}
		};
		
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(methodCalled[0] && !(methodCalled[1] || methodCalled[2] || methodCalled[3]));
	}

	@Test
	public void getMeasures() {
		AbstractCompositeWeightDecorator decorator = new TestableCompositeDecorator() {
			protected java.util.List<Metric> getDecoratedMetrics() {
				return Arrays.asList(CoreMetrics.NCLOC);
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(CoreMetrics.NCLOC)).thenReturn(new Measure().setValue(123.0));
		List<Measure> measures = decorator.getMeasures(context);
		assertNotNull(measures);
		assertTrue(measures.size() == 1);
	}
	
	
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
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(PLUS_PLUS,PLUS_PLUS,ZERO,MINUS_MINUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(PLUS_PLUS,PLUS_PLUS,PLUS,MINUS_MINUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(PLUS_PLUS,MINUS_MINUS,ZERO)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(PLUS,MINUS_MINUS,PLUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(PLUS,MINUS_MINUS,PLUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(ZERO,PLUS_PLUS, MINUS_MINUS,ZERO)));
		assertEquals(MINUS, a.calculateWeightAverage(Arrays.asList(MINUS_MINUS,MINUS_MINUS,PLUS_PLUS)));
		
		assertEquals(PLUS, a.calculateWeightAverage(Arrays.asList(MINUS_MINUS,PLUS_PLUS,PLUS_PLUS,PLUS_PLUS)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(MINUS_MINUS,PLUS_PLUS,PLUS_PLUS)));
		
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(MINUS_MINUS,PLUS_PLUS,PLUS,ZERO)));
		
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(MINUS,PLUS_PLUS,PLUS_PLUS,ZERO)));
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(MINUS,PLUS_PLUS,PLUS)));
		
		assertEquals(ZERO, a.calculateWeightAverage(Arrays.asList(MINUS,PLUS_PLUS,PLUS,PLUS)));
	}
	
	@Test
	public void testCalculateAverages() {
		List<Measure> measures = new ArrayList<Measure>();
		measures.add(new Measure().setData("++"));
		measures.add(new Measure().setData("++"));
		measures.add(new Measure().setData("+"));
		measures.add(new Measure().setData("0"));
		MeasureWeight average = new TestableCompositeDecorator().calculateAverage(measures);
		assertNotNull(average);
		assertEquals(MeasureWeight.PLUS, average);
	}
	
	@Test
	public void testCalculateAveragesZero() {
		List<Measure> measures = new ArrayList<Measure>();
		measures.add(new Measure().setData("+"));
		measures.add(new Measure().setData("+"));
		measures.add(new Measure().setData("+"));
		measures.add(new Measure().setData("0"));
		measures.add(new Measure().setData("0"));
		MeasureWeight average = new TestableCompositeDecorator().calculateAverage(measures);
		assertNotNull(average);
		assertEquals(MeasureWeight.ZERO, average);
	}
	
	@Test
	public void testCalculateAveragesAllMinuses() {
		List<Measure> measures = new ArrayList<Measure>();
		measures.add(new Measure().setData("--"));
		measures.add(new Measure().setData("--"));
		measures.add(new Measure().setData("--"));
		MeasureWeight average = new TestableCompositeDecorator().calculateAverage(measures);
		assertNotNull(average);
		assertEquals(MeasureWeight.MINUS_MINUS, average);
	}
	
}

class TestableCompositeDecorator extends AbstractCompositeWeightDecorator {

	public Metric getMetric() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Metric> getDecoratedMetrics() {
		// TODO Auto-generated method stub
		return null;
	}
	
}