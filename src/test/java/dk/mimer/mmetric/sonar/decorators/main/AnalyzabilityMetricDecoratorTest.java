package dk.mimer.mmetric.sonar.decorators.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.MaintainabilityMetrics;

public class AnalyzabilityMetricDecoratorTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		AnalyzabilityMetricDecorator d = new AnalyzabilityMetricDecorator();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testMetric() {
		assertNotEquals(MaintainabilityMetrics.ANALYZABILITY, new AnalyzabilityMetricDecorator().getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new AnalyzabilityMetricDecorator().dependsUpon();
		assertNotNull(du);
		assertEquals(4, du.size());
		
		assertTrue(du.contains(MaintainabilityMetrics.VOLUME_WEIGHT));
		assertTrue(du.contains(MaintainabilityMetrics.DUPLICATION_WEIGHT));
		assertTrue(du.contains(MaintainabilityMetrics.UNIT_SIZE));
		assertTrue(du.contains(MaintainabilityMetrics.TEST_COVERAGE_WEIGHT));
	}
}
