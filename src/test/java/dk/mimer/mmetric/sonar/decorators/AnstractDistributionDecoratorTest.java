package dk.mimer.mmetric.sonar.decorators;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import static org.mockito.Mockito.when;

import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.File;
import org.sonar.api.resources.JavaFile;
import org.sonar.api.resources.Method;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AnstractDistributionDecoratorTest {

	
	@Test
	public void testThatRootResourceIsCounted() {
		final boolean[] countCalled = {false};
		AbstractDistributionDecorator decorator = new TestableDistributionDecorator() {
			boolean isRootResource(org.sonar.api.resources.Resource resource) {
				return true;
			}
			Measure buildBlockMetricDistribution(DecoratorContext context, Metric metric, Metric resultMetric, Number[] bottomLimits) {
				countCalled[0] = true;
				return null;
			}
			@Override
			Measure buildCombinedMetricDistribution(DecoratorContext context, Metric metric, Number[] bottomLimits) {
				assertTrue("Should NOT have been called!", false);
				return null;
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(countCalled[0]);
	}
	
	
	@Test
	public void testShouldNotAreNotPersisted() {
		final boolean[] countCalled = {false};
		AbstractDistributionDecorator decorator = new TestableDistributionDecorator() {
			boolean isRootResource(org.sonar.api.resources.Resource resource) {
				return false;
			}
			@Override
			public boolean shouldPersistMeasures(DecoratorContext context) {
				return false;
			}
			Measure buildBlockMetricDistribution(DecoratorContext context, Metric metric, Metric resultMetric, Number[] bottomLimits) {
				assertTrue("Should NOT have been called!", false);
				return null;
			}
			@Override
			Measure buildCombinedMetricDistribution(DecoratorContext context, Metric metric, Number[] bottomLimits) {
				countCalled[0] = true;
				return new Measure();
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.saveMeasure(new Measure())).thenReturn(context);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(countCalled[0]);
	}
	
	@Test
	public void testThatUpperResourcesAreCombined() {
		final boolean[] countCalled = {false, false, false};
		AbstractDistributionDecorator decorator = new TestableDistributionDecorator() {
			boolean isRootResource(org.sonar.api.resources.Resource resource) {
				return false;
			}
			Measure buildBlockMetricDistribution(DecoratorContext context, Metric metric, Metric resultMetric, Number[] bottomLimits) {
				assertTrue("Should NOT have been called!", false);
				return null;
			}
			@Override
			Measure buildCombinedMetricDistribution(DecoratorContext context, Metric metric, Number[] bottomLimits) {
				countCalled[0] = true;
				return null;
			}
			@Override
			public boolean shouldPersistMeasures(DecoratorContext context) {
				countCalled[1] = true;
				return true;
			}
			@Override
			void saveMeasure(DecoratorContext context, Measure measure) {
				countCalled[2] = true;
			}
		};
		DecoratorContext context = mock(DecoratorContext.class);
		decorator.decorate(new JavaFile("ANY_RESOURCE"), context);
		assertTrue(countCalled[0] && countCalled[1] && countCalled[2]);
	}
	
	@Test
	public void testBuildCombinedUsesChildren() {
		Collection<Measure> children = new ArrayList<Measure>();
		children.add(new Measure().setValue(6.0));
		children.add(new Measure().setValue(5.0));
		children.add(new Measure().setValue(4.0));
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getChildrenMeasures(MaintainabilityMetrics.VOLUME_WEIGHT)).thenReturn(children);
		new TestableDistributionDecorator().buildCombinedMetricDistribution(context, MaintainabilityMetrics.VOLUME_WEIGHT, new Number[] {1,2,3,40});
		//assertEquals(15.0, combined.getValue(), 0);
		
	}
	
	@Test
	public void buildBlockMetricDistribution() {
		DecoratorContext context = mock(DecoratorContext.class);
		new TestableDistributionDecorator().buildBlockMetricDistribution(context, CoreMetrics.NCLOC, MaintainabilityMetrics.VOLUME_WEIGHT, new Number[] {1,2,3,40});
		//assertEquals(15.0, combined.getValue(), 0);
		
	}
	
	@Test
	public void testBlockIsRootResource() {
		assertTrue(new TestableDistributionDecorator().isRootResource(Method.createMethod("ANY_KEY", null)));
	}
	
	@Test
	public void testFileIsNotRootResource() {
		assertFalse(new TestableDistributionDecorator().isRootResource(new File("ANY_KEY")));
	}
	
	@Test
	public void testMethodIsRootResource() {
		
	}
}


class TestableDistributionDecorator extends AbstractDistributionDecorator {
	
	public Metric getMetric() {
		return null;
	}
	
	@Override
	public Metric getDecoratedMetric() {
		return null;
	}
	
	@Override
	Number[] getVolumeDistribution() {
		return null;
	}
}