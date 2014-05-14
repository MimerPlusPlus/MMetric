package dk.mimer.mmetric.sonar.ui.widget;

import org.junit.Test;
import static org.junit.Assert.*;

public class StabilityMaintainabilityRubyWidgetTest {

	@Test
	public void testId() {
		assertEquals("MSMetric", new StabilityMaintainabilityRubyWidget().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Stability", new StabilityMaintainabilityRubyWidget().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/widget/maintainabilityStability.html.erb", new StabilityMaintainabilityRubyWidget().getTemplatePath());
	}
}
