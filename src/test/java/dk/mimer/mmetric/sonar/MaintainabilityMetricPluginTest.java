package dk.mimer.mmetric.sonar;

import org.junit.Test;
import static org.junit.Assert.*;
import dk.mimer.mmetric.sonar.decorators.main.TestabilityMetricDecorator;

public class MaintainabilityMetricPluginTest {

	@Test
	public void testPluginsAreLoadable() {
		assertTrue(new MaintainabilityMetricPlugin().getExtensions().contains(TestabilityMetricDecorator.class));
	}
}
