package dk.mimer.mmetric.sonar.decorators.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class ChnageabilityMetricDecoratorTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		ChangeabilityMetricDecorator d = new ChangeabilityMetricDecorator();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testMetric() {
		assertNotEquals(MaintainabilityMetrics.CHANGEABILITY, new ChangeabilityMetricDecorator().getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new ChangeabilityMetricDecorator().dependsUpon();
		assertNotNull(du);
		assertEquals(2, du.size());
		
		assertTrue(du.contains(MaintainabilityMetrics.COMPLEXITY_WEIGHT));
		assertTrue(du.contains(MaintainabilityMetrics.DUPLICATION_WEIGHT));
	}
}
