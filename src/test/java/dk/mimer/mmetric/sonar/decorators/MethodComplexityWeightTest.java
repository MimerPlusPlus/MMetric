package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.fest.util.Arrays;
import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class MethodComplexityWeightTest {

	private static final String[] ROW_HEADERS = new String[] {" 50 ≤	     ", " 20 - 50    ", " 10 - 20    ", " < 10       "};
	
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
	public void testCalculationPhi() {
		
		// 84,1%, 9,8% 6,0% 0.0
		assertEquals(MeasureWeight.ZERO, runCalculationWithMock("0=4071;10=476;20=291;50=0"));
	}
	
	
	
	
	@Test
	public void testIsVeryLowValue() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_VERY_LOW = new double[]{ 100,4,0,0};
		assertTrue(mlocw.isPlusPlus(ANY_VERY_LOW));
		assertEquals(MeasureWeight.PLUS_PLUS, mlocw.evaluateRisk(new double[]{ 100,0,0,0}));
		assertTrue(mlocw.isPlusPlus(new double[]{ 100,0,0,0}));
		assertTrue(mlocw.isPlusPlus(new double[]{ 0,0,0,0}));
		
		assertFalse(mlocw.isMinus(ANY_VERY_LOW));
		assertFalse(mlocw.isPlus(ANY_VERY_LOW));
		assertFalse(mlocw.isZero(ANY_VERY_LOW));
		assertFalse(mlocw.isMinusMinus(ANY_VERY_LOW));
	}
	
	@Test
	public void testIsLowValue() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_LOW = new double[]{ 74,26,0,0};
		assertTrue(mlocw.isPlus(ANY_LOW));
		assertTrue(mlocw.isPlus(new double[]{ 76,20,4,0}));
		assertTrue(mlocw.isPlus(new double[]{ 71,29,0,0}));
		assertFalse(mlocw.isPlus(new double[]{ 5.0,0.0,0.0,95.0}));
		
		assertFalse(mlocw.isMinus(ANY_LOW));
		assertFalse(mlocw.isMinusMinus(ANY_LOW));
		assertFalse(mlocw.isZero(ANY_LOW));
		assertFalse(mlocw.isPlusPlus(ANY_LOW));
	}
	
	@Test
	public void testIsMedium() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_MEDIUM = new double[]{0,39,9,0};
		assertTrue(mlocw.isZero(ANY_MEDIUM));
		assertTrue(mlocw.isZero(new double[]{64,31,5,0}));
		assertTrue(mlocw.isZero(new double[]{58,35,7,0}));
		
		assertFalse(mlocw.isMinus(ANY_MEDIUM));
		assertFalse(mlocw.isMinusMinus(ANY_MEDIUM));
		assertFalse(mlocw.isPlus(ANY_MEDIUM));
		assertFalse(mlocw.isPlusPlus(ANY_MEDIUM));
		
	}
	
	@Test
	public void testHighRisk() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		double[] ANY_HIGH = new double[]{ 60,41,0,0};
		assertTrue(mlocw.isMinus(ANY_HIGH));
		assertTrue(mlocw.isMinus(new double[]{ 51,49,3,0}));
		assertTrue(mlocw.isMinus(new double[]{ 71,31,7,2}));
		assertTrue(mlocw.isMinus(new double[]{ 71,31,7,4}));
		assertTrue(mlocw.isMinus(new double[]{ 96,0,0,4}));
		assertTrue(mlocw.isMinus(new double[]{ 51.0,45.0,0.0,0.0}));
		
		assertTrue(mlocw.isMinus(new double[]{ 90,0,11,0}));
		
		assertFalse(mlocw.isZero(ANY_HIGH));
		assertFalse(mlocw.isMinusMinus(ANY_HIGH));
		assertFalse(mlocw.isPlus(ANY_HIGH));
		assertFalse(mlocw.isPlusPlus(ANY_HIGH));
	}
	
	@Test
	public void testVeryHighRisk() {
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		assertTrue(mlocw.isMinusMinus(new double[]{ 50,51,0,0}));
		assertTrue(mlocw.isMinusMinus(new double[]{ 50,34,16,0}));
		assertTrue(mlocw.isMinusMinus(new double[]{ 71,40,40,0}));
		assertTrue(mlocw.isMinusMinus(new double[]{ 94,0,0,6}));
		assertTrue(mlocw.isMinusMinus(new double[]{ 0.0,0.0,5.0,95.0}));
	}
	
	@Test
	public void testBuildComplexityForAllProjects() {
		List<String[]> dataset = new ArrayList<String[]>();
		dataset.add(new String[] {"++", "Complexity", "MMetric initial", "0=499;10=34;20=0;50=0"});
		dataset.add(new String[] {"++", "Complexity", "MMetric latest", "0=480;10=48;20=0;50=0"});
		
		dataset.add(new String[] {"0", "Complexity", "Delta latest", "0=4071;10=476;20=291;50=0"});
		
		dataset.add(new String[] {"-", "Complexity", "Gamma initial", "0=40095;10=9943;20=3401;50=1470"});
		dataset.add(new String[] {"-", "Complexity", "Gamma latest", "0=47510;10=9918;20=3109;50=1464"});
		
		dataset.add(new String[] {"--", "Complexity", "Phi initial", "0=87442;10=17546;20=14729;50=8025"});
		dataset.add(new String[] {"--", "Complexity", "Phi latest", "0=93842;10=18676;20=15714;50=8615"});
		 
		for (int i = 0; i < dataset.size(); i++) {
			System.out.println("Testing : "+dataset.get(i)[2]);
			assertEquals(MeasureWeight.fromValue(dataset.get(i)[0]), runCalculationWithMock(dataset.get(i)[3]));
		}
		System.out.println("Print all datasets....");
		
		for (int i = 0; i < dataset.size(); i++) {
			TableUtil.printTable(dataset.get(i)[1], dataset.get(i)[2], ROW_HEADERS, dataset.get(i)[3], new MethodLinesOfCodeWeight());
		}
	}
	
	
	

	private MeasureWeight runCalculationWithMock(String data) {
		Measure ANY_MEASURE = new Measure().setData(data);
		MethodComplexityWeight mlocw = new MethodComplexityWeight();
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(mlocw.getDecoratedMetric())).thenReturn(ANY_MEASURE);
		return mlocw.calculateWeight(context);
	}
}
