package dk.mimer.mmetric.sonar.ui.widget;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnalysabilityMaintainabilityRubyWidgetTest {

	@Test
	public void testId() {
		assertEquals("MAMetric", new AnalysabilityMaintainabilityRubyWidget().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Analysability", new AnalysabilityMaintainabilityRubyWidget().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/widget/maintainabilityAnalysability.html.erb", new AnalysabilityMaintainabilityRubyWidget().getTemplatePath());
	}
}
