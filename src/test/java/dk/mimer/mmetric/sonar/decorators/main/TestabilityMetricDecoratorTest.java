package dk.mimer.mmetric.sonar.decorators.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class TestabilityMetricDecoratorTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		TestabilityMetricDecorator d = new TestabilityMetricDecorator();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testMetric() {
		assertNotEquals(MaintainabilityMetrics.TESTABILITY, new TestabilityMetricDecorator().getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new TestabilityMetricDecorator().dependsUpon();
		assertNotNull(du);
		assertEquals(3, du.size());
		
		assertTrue(du.contains(MaintainabilityMetrics.COMPLEXITY_WEIGHT));
		assertTrue(du.contains(MaintainabilityMetrics.UNIT_SIZE));
		assertTrue(du.contains(MaintainabilityMetrics.TEST_COVERAGE_WEIGHT));
	}
}
