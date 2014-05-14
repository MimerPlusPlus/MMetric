package dk.mimer.mmetric.sonar.ui.widget;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChangeabilityMaintainabilityRubyWidgetTest {

	@Test
	public void testId() {
		assertEquals("MCMetric", new ChangeabilityMaintainabilityRubyWidget().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Changeability", new ChangeabilityMaintainabilityRubyWidget().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/widget/maintainabilityChangeability.html.erb", new ChangeabilityMaintainabilityRubyWidget().getTemplatePath());
	}
}
