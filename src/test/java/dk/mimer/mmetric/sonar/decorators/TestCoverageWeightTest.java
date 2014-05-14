package dk.mimer.mmetric.sonar.decorators;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class TestCoverageWeightTest {
	
	@Test
	public void testDecoratedNotSameAsMetric() {
		TestCoverageWeight d = new TestCoverageWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}
	
	@Test
	public void testDependsUponCoverageMetric() {
		List<Metric> du = new TestCoverageWeight().dependsUpon();
		assertNotNull(du);
		assertEquals(1, du.size());
		assertEquals(CoreMetrics.COVERAGE, du.get(0));
	}
	
	
	@Test
	public void testDistributionHigh() {
		TestCoverageWeight locd = new TestCoverageWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(0));
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(20));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(20+1));
	}
	
	@Test
	public void testDistributionNotSoHigh() {
		int ANY_HIGH = 20;
		int ANY_LOW  = 60;
		TestCoverageWeight locd = new TestCoverageWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH+1));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW+1 ));
	}
	
	@Test
	public void testDistributionMedium() {
		int ANY_HIGH = 60;
		int ANY_LOW  = 80;
		TestCoverageWeight locd = new TestCoverageWeight();
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH+1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW-1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW+1 ));
	}
	
	@Test
	public void testDistributionLow() {
		int ANY_HIGH = 80;
		int ANY_LOW =  95;
		TestCoverageWeight locd = new TestCoverageWeight();
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_HIGH+1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW-1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW+1));
	}
	
	@Test
	public void testDistributionVeryLow() {
		int ANY_HIGH = 100;
		int ANY_LOW =   95;
		TestCoverageWeight locd = new TestCoverageWeight();
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_HIGH-1));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW+1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW));
	}
}
