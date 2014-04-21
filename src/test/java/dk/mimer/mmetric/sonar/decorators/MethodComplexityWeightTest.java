package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fest.util.Arrays;
import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class MethodComplexityWeightTest {

	@Test
	public void testDecoratedNotSameAsMetric() {
		MethodComplexityWeight d = new MethodComplexityWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testCalculationPlusPlus() {
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("4=0;3=0;2=0;1=10"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("4=0;3=0;2=2;1=8"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("4=0;3=0;2=10;1=13213210"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("4=0;3=0;2=24;1=75"));
	}
	
	@Test
	public void testCalculationPlusPlusWithMeaures() {
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=10;10=0;40=0;80=0"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=8;10=2;40=0;80=0"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=13213210;10=0;40=0;80=0"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=75;10=25;40=0;80=0"));
	}
	
	
	@Test
	public void testCalculationPlus() {
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("3=0;2=0;1=29;0=71"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("3=0;2=4;1=0;0=95"));
		
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("3=0;2=0;1=26;0=74"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("3=0;2=1;1=0;0=99"));
		assertEquals(MeasureWeight.PLUS, runCalculationWithMock("3=0;2=1;1=26;0=73"));		
	}
	
	@Test
	public void testCalculationZero() {
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("5=0;3=9;1=0;0=90"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("5=0;3=0;1=39;0=60"));
		
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("3=0;2=0;1=31;0=69"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("3=0;2=6;1=0;0=94"));
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("3=0;2=6;1=31;0=65"));
	}
	
	@Test
	public void testCalculationMinus() {
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("1=100;2=40;3=20;4=0"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("4=0;3=14;2=0;1=86"));
		
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("4=0;3=110;2=0;1=890"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("4=0;3=0;2=41;1=59"));
		assertEquals(MeasureWeight.MINUS, runCalculationWithMock("4=0;3=200;2=4100;1=5700"));
	}
	
	@Test
	public void testCalculationMinusMinus() {
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("1=5;2=0;3=0;14=95"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=5;3=0;2=0;10=95"));
		
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=100;3=500;2=0;1=1000"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=100;3=0;2=0;1=0"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=0;3=0;2=51;1=49"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=0;3=150;2=0;1=840"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("4=6;3=0;2=0;1=95"));
	}
		
	@Test
	public void testCalculationGamma() {
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=110;10=2;20=0;50=0"));
		assertEquals(MeasureWeight.PLUS_PLUS, runCalculationWithMock("0=97;10=0;20=0;50=0"));
	}
	
	@Test
	public void testIsVeryLowValue() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_VERY_LOW = new double[]{ 100,4,0,0};
		assertTrue(mlocw.isVeryLowRisk(ANY_VERY_LOW));
		assertTrue(mlocw.isVeryLowRisk(new double[]{ 10000,0,0,0}));
		assertTrue(mlocw.isVeryLowRisk(new double[]{ 0,0,0,0}));
		
		assertFalse(mlocw.isHighRisk(ANY_VERY_LOW));
		assertFalse(mlocw.isLowRisk(ANY_VERY_LOW));
		assertFalse(mlocw.isMediumRisk(ANY_VERY_LOW));
		assertFalse(mlocw.isVeryHighRisk(ANY_VERY_LOW));
	}
	
	@Test
	public void testIsLowValue() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_LOW = new double[]{ 74,26,0,0};
		assertTrue(mlocw.isLowRisk(ANY_LOW));
		assertTrue(mlocw.isLowRisk(new double[]{ 76,20,4,0}));
		assertTrue(mlocw.isLowRisk(new double[]{ 71,29,0,0}));
		assertFalse(mlocw.isLowRisk(new double[]{ 5.0,0.0,0.0,95.0}));
		
		assertFalse(mlocw.isHighRisk(ANY_LOW));
		assertFalse(mlocw.isVeryHighRisk(ANY_LOW));
		assertFalse(mlocw.isMediumRisk(ANY_LOW));
		assertFalse(mlocw.isVeryLowRisk(ANY_LOW));
	}
	
	@Test
	public void testIsMedium() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_MEDIUM = new double[]{0,39,9,0};
		assertTrue(mlocw.isMediumRisk(ANY_MEDIUM));
		assertTrue(mlocw.isMediumRisk(new double[]{0,30,5,0}));
		assertTrue(mlocw.isMediumRisk(new double[]{0,35,7,0}));
		
		assertFalse(mlocw.isHighRisk(ANY_MEDIUM));
		assertFalse(mlocw.isVeryHighRisk(ANY_MEDIUM));
		assertFalse(mlocw.isLowRisk(ANY_MEDIUM));
		assertFalse(mlocw.isVeryLowRisk(ANY_MEDIUM));
		
	}
	
	@Test
	public void testHighRisk() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_HIGH = new double[]{ 60,41,0,0};
		assertTrue(mlocw.isHighRisk(ANY_HIGH));
		assertTrue(mlocw.isHighRisk(new double[]{ 51,49,3,0}));
		assertTrue(mlocw.isHighRisk(new double[]{ 71,31,7,2}));
		assertTrue(mlocw.isHighRisk(new double[]{ 71,31,7,4}));
		assertTrue(mlocw.isHighRisk(new double[]{ 96,0,0,4}));
		assertTrue(mlocw.isHighRisk(new double[]{ 51.0,45.0,0.0,0.0}));
		
		assertTrue(mlocw.isHighRisk(new double[]{ 90,0,11,0}));
		
		assertFalse(mlocw.isMediumRisk(ANY_HIGH));
		assertFalse(mlocw.isVeryHighRisk(ANY_HIGH));
		assertFalse(mlocw.isLowRisk(ANY_HIGH));
		assertFalse(mlocw.isVeryLowRisk(ANY_HIGH));
	}
	
	@Test
	public void testVeryHighRisk() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		assertTrue(mlocw.isVeryHighRisk(new double[]{ 50,50,0,0}));
		assertTrue(mlocw.isVeryHighRisk(new double[]{ 50,34,16,0}));
		assertTrue(mlocw.isVeryHighRisk(new double[]{ 71,40,40,0}));
		assertTrue(mlocw.isVeryHighRisk(new double[]{ 94,0,0,6}));
		assertTrue(mlocw.isVeryHighRisk(new double[]{ 0.0,0.0,5.0,95.0}));
	}
	

	private MeasureWeight runCalculationWithMock(String data) {
		Measure ANY_MEASURE = new Measure().setData(data);
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(mlocw.getDecoratedMetric())).thenReturn(ANY_MEASURE);
		return mlocw.calculateWeight(context);
	}
}
