package dk.mimer.mmetric.sonar.value;

import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.MINUS_MINUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.PLUS_PLUS;
import static dk.mimer.mmetric.sonar.value.MeasureWeight.ZERO;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.sonar.api.resources.Qualifiers;

public class MeasureWeightTest {

	@Test
	public void testValueOfString() {
		assertEquals(PLUS_PLUS, MeasureWeight.fromValue("++"));
		assertEquals(PLUS, MeasureWeight.fromValue("+"));
		assertEquals(ZERO, MeasureWeight.fromValue("0"));
		assertEquals(MINUS, MeasureWeight.fromValue("-"));
		assertEquals(MINUS_MINUS, MeasureWeight.fromValue("--"));
	}
	
	@Test
	public void testValueOfInt() {
		assertEquals(PLUS_PLUS, MeasureWeight.valueOf(4));
		assertEquals(PLUS, MeasureWeight.valueOf(3));
		assertEquals(ZERO, MeasureWeight.valueOf(2));
		assertEquals(MINUS, MeasureWeight.valueOf(1));
		assertEquals(MINUS_MINUS, MeasureWeight.valueOf(0));
	}
}
