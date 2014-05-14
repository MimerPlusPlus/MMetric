package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class CodeDuplicationWeightTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		CodeDuplicationWeight d = new CodeDuplicationWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new CodeDuplicationWeight().dependsUpon();
		assertNotNull(du);
		assertEquals(1, du.size());
		assertEquals(CoreMetrics.DUPLICATED_LINES_DENSITY, du.get(0));
	}
	
	@Test
	public void testDistributionHigh() {
		CodeDuplicationWeight locd = new CodeDuplicationWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(100));
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(20));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(19.999999));
	}
	
	@Test
	public void testDistributionNotSoHigh() {
		double ANY_HIGH = 20;
		double ANY_LOW  = 10;
		CodeDuplicationWeight locd = new CodeDuplicationWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH-1));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW -1 ));
	}
	
	@Test
	public void testDistributionMedium() {
		int ANY_HIGH = 10;
		int ANY_LOW  = 5;
		CodeDuplicationWeight locd = new CodeDuplicationWeight();
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH-1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW+1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW -1 ));
	}
	
	@Test
	public void testDistributionLow() {
		int ANY_HIGH = 5;
		int ANY_LOW =   3;
		CodeDuplicationWeight locd = new CodeDuplicationWeight();
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_HIGH-1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW+1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW -1 ));
	}
	
	@Test
	public void testDistributionVeryLow() {
		int ANY_HIGH = 3;
		int ANY_LOW =   0;
		CodeDuplicationWeight locd = new CodeDuplicationWeight();
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_HIGH-1));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW+1));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW));
	}
	
}
