package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.JavaFile;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class AbstractWeightDecoratorTest {

	@Test
	public void testNotPersistedWhenItShouldnt() {
		final boolean[] methodCalled = {false};
		TestableWeightDecorator decorator = new TestableWeightDecorator() {
			@Override
			public boolean shouldPersistMeasures(DecoratorContext context) {
				methodCalled[0] = true;
				return false;
			}
			@Override
			double getDecoratedMetricValue(DecoratorContext context) {
				assertTrue(false);
				return -1d;
			}
			@Override
			public Metric getMetric() {
				return CoreMetrics.COMPLEXITY; // ANY Metric!
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		//when(context.getResource())
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(methodCalled[0]);
	}
	
	@Test
	public void testDecoratePersists() {
		final boolean[] methodCalled = {false,false};
		TestableWeightDecorator decorator = new TestableWeightDecorator() {
			@Override
			public boolean shouldPersistMeasures(DecoratorContext context) {
				methodCalled[0] = true;
				return true;
			}
			@Override
			double getDecoratedMetricValue(DecoratorContext context) {
				methodCalled[1] = true;
				return 1d;
			}
			@Override
			public Metric getMetric() {
				return CoreMetrics.COMPLEXITY; // ANY Metric!
			}
			@Override
			MeasureWeight calculateWeight(DecoratorContext context) {
				return MeasureWeight.PLUS_PLUS;
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(methodCalled[0] && methodCalled[1] );
	}
	
	@Test
	public void calculateWeightUsesContext() {
		final boolean[] methodCalled = {false};
		TestableWeightDecorator decorator = new TestableWeightDecorator() {
			MeasureWeight calculateWeight(double metricValue) {
				methodCalled[0] = true;
				return null;
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.calculateWeight(context);
		assertTrue(methodCalled[0]);
	}
	
	@Test
	public void testDecoratedMetricDefaultsToZero() {
		TestableWeightDecorator decorator = new TestableWeightDecorator();
		DecoratorContext context = mock(DecoratorContext.class);
		double value = decorator.getDecoratedMetricValue(context);
		assertEquals(0.0, value, 0);
	}
	
	@Test
	public void testDecoratedMetricValueUsed() {
		TestableWeightDecorator decorator = new TestableWeightDecorator() {
			@Override
			public Metric getDecoratedMetric() {
				return CoreMetrics.COMPLEXITY;
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(CoreMetrics.COMPLEXITY)).thenReturn(new Measure(CoreMetrics.COMPLEXITY, 45.0));
		double value = decorator.getDecoratedMetricValue(context);
		assertEquals(45.0, value, 0);
	}
}

class TestableWeightDecorator extends AbstractWeightDecorator {

	public Metric getMetric() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Number[] getVolumeDistribution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metric getDecoratedMetric() {
		// TODO Auto-generated method stub
		return null;
	}
	
}