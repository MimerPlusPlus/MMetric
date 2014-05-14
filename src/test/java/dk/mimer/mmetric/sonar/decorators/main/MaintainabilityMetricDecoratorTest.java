package dk.mimer.mmetric.sonar.decorators.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class MaintainabilityMetricDecoratorTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		MaintainabilityMetricDecorator d = new MaintainabilityMetricDecorator();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testMetric() {
		assertNotEquals(MaintainabilityMetrics.ANALYZABILITY, new MaintainabilityMetricDecorator().getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new MaintainabilityMetricDecorator().dependsUpon();
		assertNotNull(du);
		assertEquals(4, du.size());
		assertTrue(du.contains(MaintainabilityMetrics.TESTABILITY));
		assertTrue(du.contains(MaintainabilityMetrics.STABILITY));
		assertTrue(du.contains(MaintainabilityMetrics.CHANGEABILITY));
		assertTrue(du.contains(MaintainabilityMetrics.ANALYZABILITY));
	}
}
