package dk.mimer.mmetric.sonar;

import junit.framework.Assert;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

public class SonarTest {

	
	@Test
	@SuppressWarnings("deprecation")
	public void testMetricCreation() {
		Metric m = new Metric("dummy-key", "Dummy name", "Dummy Description", Metric.ValueType.INT, 1, true, CoreMetrics.DOMAIN_GENERAL);
		Assert.assertNull(m.getFormula());
	}
}
