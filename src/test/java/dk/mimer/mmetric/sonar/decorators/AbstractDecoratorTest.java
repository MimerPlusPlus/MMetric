package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Method;
import org.sonar.api.resources.Project;
import org.sonar.java.api.JavaClass;

public class AbstractDecoratorTest {

	
	@Test
	public void testShouldExecuteOnProject() {
		assertTrue(new TestableDecorator().shouldExecuteOnProject(null));
		assertTrue(new TestableDecorator().shouldExecuteOnProject(new Project("ANY_PROJECT")));
	}
	
	@Test
	public void testDependsUponCallsGetMetric() {
		final boolean[] methodCalled = {false};
		AbstractDecorator d = new TestableDecorator() {
			public Metric getMetric() {
				methodCalled[0] = true;
				return null;
			};
		};
		d.dependedUpon();
		assertTrue(methodCalled[0]);
	}
	
	@Test
	public void testToString() {
		TestableDecorator td = new TestableDecorator();
		assertEquals("TestableDecorator", td.toString());
	}
	
	public void test() {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(new Project("ANY_PROJECT"));
		assertTrue(new TestableDecorator().shouldPersistMeasures(context));
	}
	
	@Test
	public void testFileIsNotPersisted() {
		DecoratorContext context = mock(DecoratorContext.class);
		double anyValue = 12.5d;
		Metric ANY_METRIC = CoreMetrics.NCLOC;
		Measure measure = new Measure(ANY_METRIC);
		measure.setValue(anyValue);
		when(context.getMeasure(ANY_METRIC)).thenReturn(measure);
		assertEquals(anyValue,new TestableDecorator().getMetricValue(context, ANY_METRIC), 0);
		verify(context).getMeasure(ANY_METRIC);
		
	}
	
	@Test
	public void testClassIsNotPersisted() {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(JavaClass.create("ANUY_NAME"));
		assertFalse(new TestableDecorator().shouldPersistMeasures(context));
	}
	
	@Test
	public void testMethodIsPersisted() {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(Method.createMethod("ANY_KEY", null));
		assertTrue(new TestableDecorator().shouldPersistMeasures(context));
	}
	
	@Test
	public void testProjectIsPersisted() {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(new Project("ANY_PROJECT"));
		assertTrue(new TestableDecorator().shouldPersistMeasures(context));
	}
	
	@Test
	public void saveMeasure() {
		Measure m = new Measure(CoreMetrics.COMPLEXITY);
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(new Project("ANY_PROJECT"));
		when(context.getMeasure(CoreMetrics.COMPLEXITY)).thenReturn(null);
		when(context.saveMeasure(m)).thenReturn(null);
		new TestableDecorator().saveMeasure(context, m);
	}
	
	@Test
	public void testMeasureOnlySavedOnce() {
		Measure m = new Measure(CoreMetrics.COMPLEXITY);
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getResource()).thenReturn(new Project("ANY_PROJECT"));
		when(context.getMeasure(CoreMetrics.COMPLEXITY)).thenReturn(m);
		when(context.saveMeasure(m)).thenThrow(new IllegalAccessError("Should not be called!"));
		new TestableDecorator().saveMeasure(context, m);
	}
}


class TestableDecorator extends AbstractDecorator {
	
	public Metric getMetric() {
		return null;
	}
	
	@Override
	public Metric getDecoratedMetric() {
		return null;
	}
}