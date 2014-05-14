package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

public class MethodComplexityDistributionTest {

	
	@Test
	public void testDecoratedNotSameAsMetric() {
		MethodComplexityDistribution d = new MethodComplexityDistribution();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponMethodLinesOfCodeDistributionMetric() {
		List<Metric> du = new MethodComplexityDistribution().dependsUpon();
		assertNotNull(du);
		assertEquals(1, du.size());
		assertEquals(CoreMetrics.COMPLEXITY, du.get(0));
	}
	
	@Test
	public void testVolumeDistribution() {
		MethodComplexityDistribution d = new MethodComplexityDistribution();
		assertEquals(4, d.getVolumeDistribution().length);
		assertEquals(50, d.getVolumeDistribution()[0]);
		assertEquals(20, d.getVolumeDistribution()[1]);
		assertEquals(10, d.getVolumeDistribution()[2]);
		assertEquals(0, d.getVolumeDistribution()[3]);
	}
}
