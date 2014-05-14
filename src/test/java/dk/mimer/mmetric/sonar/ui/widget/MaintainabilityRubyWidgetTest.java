package dk.mimer.mmetric.sonar.ui.widget;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaintainabilityRubyWidgetTest {

	@Test
	public void testId() {
		assertEquals("MaintainabilityMetric", new MaintainabilityRubyWidget().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Maintainability", new MaintainabilityRubyWidget().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/widget/maintainability.html.erb", new MaintainabilityRubyWidget().getTemplatePath());
	}
}
