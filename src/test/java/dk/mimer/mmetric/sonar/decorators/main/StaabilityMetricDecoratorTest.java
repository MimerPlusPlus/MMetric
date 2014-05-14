package dk.mimer.mmetric.sonar.decorators.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class StaabilityMetricDecoratorTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		StabilityMetricDecorator d = new StabilityMetricDecorator();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testMetric() {
		assertNotEquals(MaintainabilityMetrics.STABILITY, new StabilityMetricDecorator().getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new StabilityMetricDecorator().dependsUpon();
		assertNotNull(du);
		assertEquals(1, du.size());
		assertTrue(du.contains(MaintainabilityMetrics.TEST_COVERAGE_WEIGHT));

	}
}
