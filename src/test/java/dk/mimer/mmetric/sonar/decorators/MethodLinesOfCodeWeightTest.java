package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class MethodLinesOfCodeWeightTest {

	@Test
	public void testDecoratedNotSameAsMetric() {
		MethodLinesOfCodeWeight d = new MethodLinesOfCodeWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	
	@Test
	public void testCalculationPlusPlus() {
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("80=0;40=0;10=0;0=10"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("80=0;40=0;10=2;0=8"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("80=0;40=0;10=10;0=13213210"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("80=0;40=0;10=25;0=75"));
	}
	
	
	@Test
	public void testCalculationPlus() {
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=0;40=0;10=30;0=75"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=0;40=5;10=0;0=100"));
		
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=0;40=0;10=26;0=74"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=0;40=1;10=0;0=99"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=0;40=1;10=26;0=73"));		
	}
	
	@Test
	public void testCalculationZero() {
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("80=0;40=10;10=0;0=100"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("80=0;40=0;10=40;0=61"));
		
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("80=0;40=0;10=31;0=69"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("80=0;40=6;10=0;0=94"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("80=0;40=6;10=31;0=65"));
	}
	
	@Test
	public void testCalculationMinus() {
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=5;40=0;10=0;0=96"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=0;40=0;10=50;0=51"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=0;40=15;10=0;0=86"));
		
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=0;40=11;10=0;0=89"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=0;40=0;10=41;0=59"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("80=0;40=2;10=41;0=57"));
	}
	
	@Test
	public void testCalculationMinusMinus() {
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("80=100;40=5;10=0;0=10"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("80=100;40=0;10=0;0=0"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("80=0;40=0;10=51;0=49"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("80=0;40=15;10=0;0=84"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("80=6;40=0;10=0;0=95"));
	}
	
	
	@Test
	public void testCalculationGamma() {
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("80=6614;10=1261;40=198;80=40"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("80=6614;10=1261;40=198;80=0"));
	}
	

	private MeasureWeight runCalculationWithMock(String data) {
		Measure ANY_MEASURE = new Measure().setData(data);
		MethodLinesOfCodeWeight mlocw = new MethodLinesOfCodeWeight();
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(mlocw.getDecoratedMetric())).thenReturn(ANY_MEASURE);
		return mlocw.calculateWeight(context);
	}
}
