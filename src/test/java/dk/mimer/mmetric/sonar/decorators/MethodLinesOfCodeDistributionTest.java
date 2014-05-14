package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;

public class MethodLinesOfCodeDistributionTest {

	@Test
	public void testMethodsAreCalculated() {
		//Method method = mock(
		

		//MethodLinesOfCodeDistribution dist = new MethodLinesOfCodeDistribution();
		//dist.decorate(project, mockContext(10, 10));
	}
	
	@Test
	public void testDependsUpon() {
		MethodLinesOfCodeDistribution d = new MethodLinesOfCodeDistribution();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testDecoratedMetric() {
		MethodLinesOfCodeDistribution d = new MethodLinesOfCodeDistribution();
		assertEquals(CoreMetrics.NCLOC, d.getDecoratedMetric());
	}
	
	@Test
	public void testVolumeDistribution() {
		MethodLinesOfCodeDistribution d = new MethodLinesOfCodeDistribution();
		assertEquals(4, d.getVolumeDistribution().length);
		assertEquals(80, d.getVolumeDistribution()[0]);
		assertEquals(40, d.getVolumeDistribution()[1]);
		assertEquals(10, d.getVolumeDistribution()[2]);
		assertEquals(0, d.getVolumeDistribution()[3]);
	}
	
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new MethodLinesOfCodeDistribution().dependsUpon();
		assertNotNull(du);
		assertEquals(1, du.size());
		assertEquals(CoreMetrics.NCLOC, du.get(0));
	}

	private static DecoratorContext mockContext(int lines, int uncoveredLines) {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(CoreMetrics.LINES_TO_COVER)).thenReturn(new Measure(CoreMetrics.LINES_TO_COVER, (double) lines));
		when(context.getMeasure(CoreMetrics.UNCOVERED_LINES)).thenReturn(new Measure(CoreMetrics.UNCOVERED_LINES, (double) uncoveredLines));
		return context;
	}
}
