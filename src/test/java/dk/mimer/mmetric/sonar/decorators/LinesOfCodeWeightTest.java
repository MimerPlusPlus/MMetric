package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

public class LinesOfCodeWeightTest {

	@Test
	public void testDependsUpon() {
		List<Metric> dependsUpon = new LinesOfCodeWeight().dependsUpon();
		assertEquals(1, dependsUpon.size());
		assertTrue(dependsUpon.contains(CoreMetrics.NCLOC));
	}
}
