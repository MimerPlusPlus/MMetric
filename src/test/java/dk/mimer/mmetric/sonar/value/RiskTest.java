package dk.mimer.mmetric.sonar.value;

import static dk.mimer.mmetric.sonar.value.Risk.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sonar.api.resources.Qualifiers;

public class RiskTest {

	@Test
	public void testGetTitle() {
		assertEquals("Very high risk", Risk.VERY_HIGH_RISK.getTitle());
		assertEquals("High risk", Risk.HIGH_RISK.getTitle());
		assertEquals("Moderate risk", Risk.MODERATE_RISK.getTitle());
		assertEquals("Low risk", Risk.LOW_RISK.getTitle());
	}

	@Test
	public void testValueOfInt() {
		assertEquals(VERY_HIGH_RISK, Risk.valueOf(3));
		assertEquals(HIGH_RISK, Risk.valueOf(2));
		assertEquals(MODERATE_RISK, Risk.valueOf(1));
		assertEquals(LOW_RISK, Risk.valueOf(0));
	}
	
	@Test
	public void testValueOfIntHandlesIllegalValue() {
		try {
			MeasureWeight.valueOf(6);
			assertTrue("Should have failed with exception", false);			
		}catch (IllegalArgumentException e) {
			//Expected
		}
	}
	
	@Test
	public void testValueOfIntHandlesIllegalValueLowerBoundry() {
		try {
			MeasureWeight.valueOf(-1);
			assertTrue("Should have failed with exception", false);			
		}catch (IllegalArgumentException e) {
			//Expected
		}
	}
}
