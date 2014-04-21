package dk.mimer.mmetric.sonar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

public class MaintainabilityMetricsTest {
	
	@Test
	public void testKeyUniqueness() {
		HashSet<String> keys = new HashSet<String>();
		List<Metric> metrics = new MaintainabilityMetrics().getMetrics();
		for (Metric metric : metrics) {
			assertTrue("Double key! "+metric.getKey(), keys.add(metric.getKey()));
		}
	}
	
	@Test
	public void testNameUniqueness() {
		HashSet<String> keys = new HashSet<String>();
		List<Metric> metrics = new MaintainabilityMetrics().getMetrics();
		for (Metric metric : metrics) {
			assertTrue("Double Name! "+metric.getName(), keys.add(metric.getName()));
		}
	}
	
	@Test
	public void testKeepHistory() {
		List<Metric> metrics = new MaintainabilityMetrics().getMetrics();
		for (Metric metric : metrics) {
			assertFalse("Delete! "+metric.getName(), metric.getDeleteHistoricalData());
		}
	}
	
	@Test
	public void testMetricsHasUniqueKey() {
		List<Metric> metrics = new MaintainabilityMetrics().getMetrics();
		HashSet keys = new HashSet<String>();
		for (Metric m : metrics) {
			assertTrue(keys.add(m.getKey()));
		}
	}
}
