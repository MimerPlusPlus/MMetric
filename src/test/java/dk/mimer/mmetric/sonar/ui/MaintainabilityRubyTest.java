package dk.mimer.mmetric.sonar.ui;

import org.junit.Test;
import static org.junit.Assert.*;

public class MaintainabilityRubyTest {

	@Test
	public void testId() {
		assertEquals("maintainabilityMetricsTab", new MaintainabilityRubyTab().getId());
	}
	
	@Test
	public void testTitle() {
		assertEquals("Maintainability", new MaintainabilityRubyTab().getTitle());
	}
	
	@Test
	public void testTemplatePath() {
		assertEquals("/tab/maintainabilityTab.html.erb", new MaintainabilityRubyTab().getTemplatePath());
	}
}
