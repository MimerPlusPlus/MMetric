package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class MethodLinesOfCodeWeightTest {

	private static final String[] ROW_HEADERS = new String[] {" 80 ≤	     ", " 40 - 80    ", " 10 - 40    ", " < 10       "};

	@Test
	public void testDecoratedNotSameAsMetric() {
		MethodLinesOfCodeWeight d = new MethodLinesOfCodeWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponMethodLinesOfCodeDistributionMetric() {
		assertEquals(2, new MethodLinesOfCodeWeight().dependsUpon().size());
		assertTrue(new MethodLinesOfCodeWeight().dependsUpon().contains(MethodLinesOfCodeDistribution.METRIC));
		assertTrue(new MethodLinesOfCodeWeight().dependsUpon().contains(CoreMetrics.NCLOC));
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
	
	@Test
	public void testBuildLOCForAllProjects() {
		List<String[]> dataset = new ArrayList<String[]>();
		dataset.add(new String[] {"0", "Unit size", "MMetric initial", "0=333;10=200;40=0;80=0"});
		dataset.add(new String[] {"0", "Unit size", "MMetric latest", "0=332;10=196;40=0;80=0"});
		
		dataset.add(new String[] {"-", "Unit size", "Delta latest", "0=1940;10=2321;40=345;80=232"});
		
		dataset.add(new String[] {"--", "Unit size", "Gamma initial", "0=19752;10=19096;40=10081;80=5980"});
		dataset.add(new String[] {"--", "Unit size", "Gamma latest", "0=24377;10=21930;40=10123;80=5571"});
		
		dataset.add(new String[] {"--", "Unit size", "Phi initial", "0=59262;10=37128;40=16707;80=14645"});
		dataset.add(new String[] {"--", "Unit size", "Phi latest", "0=62657;10=39641;40=17831;80=16718"});
		 
		for (int i = 0; i < dataset.size(); i++) {
			System.out.println("Testing : "+dataset.get(i)[2]);
			assertEquals(MeasureWeight.fromValue(dataset.get(i)[0]), runCalculationWithMock(dataset.get(i)[3]));
		}
		System.out.println("Print all datasets....");
		
		for (int i = 0; i < dataset.size(); i++) {
			TableUtil.printTable(dataset.get(i)[1], dataset.get(i)[2], ROW_HEADERS, dataset.get(i)[3], new MethodLinesOfCodeWeight());
		}
	}
	
	@Test
	public void testBuildLOCForOpenSourceProjects() {
		List<String[]> dataset = new ArrayList<String[]>();
		dataset.add(new String[] {"-", "Unit size", "Apache Commons IO", 		"0=3815;10=3612;40=492;80=0"});
		dataset.add(new String[] {"--", "Unit size", "Apache Commons Logging", 	"0=812;10=812;40=300;80=324"});
		dataset.add(new String[] {"--", "Unit size", "Arquillian", 				"0=4112;10=9121;40=1194;80=89"});
		dataset.add(new String[] {"0", "Unit size", "JUnit", 					"0=4521;10=2342;40=46;80=0"});
		dataset.add(new String[] {"-", "Unit size", "SonarQube", 				"0=36392;10=22521;40=1581;80=374"});
		
		for (int i = 0; i < dataset.size(); i++) {
			System.out.println("Testing : "+dataset.get(i)[2]);
			assertEquals(MeasureWeight.fromValue(dataset.get(i)[0]), runCalculationWithMock(dataset.get(i)[3]));
		}
		System.out.println("Print all datasets....");
		
		for (int i = 0; i < dataset.size(); i++) {
			TableUtil.printTablePct(dataset.get(i)[1], dataset.get(i)[2], ROW_HEADERS, dataset.get(i)[3], new MethodLinesOfCodeWeight(), (i == 0));
		}
	}
	
	
	
	@Test
	public void testCalculationGammaLOC() {
		//assertEquals(MeasureWeight.MINUS, runCalculationWithMock("0=19752;10=19096;40=10081;80=5980"));
		assertEquals(MeasureWeight.MINUS_MINUS, runCalculationWithMock("0=24377;10=21930;40=10123;80=5571"));
	}

	private MeasureWeight runCalculationWithMock(String data) {
		Measure ANY_MEASURE = new Measure().setData(data);
		MethodLinesOfCodeWeight mlocw = new MethodLinesOfCodeWeight();
		DecoratorContext context = mock(DecoratorContext.class);
		when(context.getMeasure(mlocw.getDecoratedMetric())).thenReturn(ANY_MEASURE);
		return mlocw.calculateWeight(context);
	}
}
