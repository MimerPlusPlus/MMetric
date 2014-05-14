package dk.mimer.mmetric.sonar.ui.widget;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dk.mimer.mmetric.sonar.ui.MaintainabilityRubyTab;

public class TestabilityMaintainabilityRubyWidgetTest {

	@Test
	public void testId() {
		assertEquals("MTMetric", new TestabilityMaintainabilityRubyWidget().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Testability", new TestabilityMaintainabilityRubyWidget().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/widget/maintainabilityTestability.html.erb", new TestabilityMaintainabilityRubyWidget().getTemplatePath());
	}
}
