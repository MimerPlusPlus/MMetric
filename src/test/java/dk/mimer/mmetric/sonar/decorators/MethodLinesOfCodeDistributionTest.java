package dk.mimer.mmetric.sonar.decorators;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;

public class MethodLinesOfCodeDistributionTest {

	@Test
	public void testMethodsAreCalculated() {
		//Method method = mock(
		

		//MethodLinesOfCodeDistribution dist = new MethodLinesOfCodeDistribution();
		//dist.decorate(project, mockContext(10, 10));
	}

	private static DecoratorContext mockContext(int lines, int uncoveredLines) {
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(CoreMetrics.LINES_TO_COVER)).thenReturn(new Measure(CoreMetrics.LINES_TO_COVER, (double) lines));
		when(context.getMeasure(CoreMetrics.UNCOVERED_LINES)).thenReturn(new Measure(CoreMetrics.UNCOVERED_LINES, (double) uncoveredLines));
		return context;
	}
}
