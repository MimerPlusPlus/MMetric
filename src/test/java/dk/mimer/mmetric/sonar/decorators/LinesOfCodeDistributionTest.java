package dk.mimer.mmetric.sonar.decorators;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.JavaFile;
import org.sonar.api.resources.JavaPackage;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.Method;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import dk.mimer.mmetric.sonar.value.MeasureWeight;

public class LinesOfCodeDistributionTest {

	@Test
	public void testDecoratedNotSameAsMetric() {
		LinesOfCodeWeight d = new LinesOfCodeWeight();
		assertNotEquals(d.getMetric(), d.getDecoratedMetric());
	}

	@Test
	public void testDistributionHigh() {
		LinesOfCodeWeight locd = new LinesOfCodeWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(131000231));
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(1310001));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(1310000 - 1));
	}

	@Test
	public void testDistributionNotSoHigh() {
		int ANY_HIGH = 1310000;
		int ANY_LOW = 655000;
		LinesOfCodeWeight locd = new LinesOfCodeWeight();
		assertEquals(MeasureWeight.MINUS_MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH - 1));
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW - 1));
	}

	@Test
	public void testDistributionMedium() {
		int ANY_HIGH = 655000;
		int ANY_LOW = 246000;
		LinesOfCodeWeight locd = new LinesOfCodeWeight();
		assertEquals(MeasureWeight.MINUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH - 1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW + 1));
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW - 1));
	}

	@Test
	public void testDistributionLow() {
		int ANY_HIGH = 246000;
		int ANY_LOW = 66000;
		LinesOfCodeWeight locd = new LinesOfCodeWeight();
		assertEquals(MeasureWeight.ZERO, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_HIGH - 1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW + 1));
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_LOW));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW - 1));
	}

	@Test
	public void testDistributionVeryLow() {
		int ANY_HIGH = 66000;
		int ANY_LOW = 0;
		LinesOfCodeWeight locd = new LinesOfCodeWeight();
		assertEquals(MeasureWeight.PLUS, locd.calculateWeight(ANY_HIGH));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_HIGH - 1));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW + 1));
		assertEquals(MeasureWeight.PLUS_PLUS, locd.calculateWeight(ANY_LOW));
	}

	public void testBlocksAreNotCounted() {
		LinesOfCodeWeight locd = new LinesOfCodeWeight();

		Method m = Method.createMethod("XXX", new Java());
		DecoratorContext context = mock(DecoratorContext.class);
		locd.decorate(m, context);

	}

	public void testClassesAreNotCounted() {
		LinesOfCodeWeight locd = new LinesOfCodeWeight();

		JavaFile m = JavaFile.fromRelativePath("", false);
		DecoratorContext context = mock(DecoratorContext.class);
		locd.decorate(m, context);

	}

	public void testPackagesAreCounted() {
		LinesOfCodeWeight locd = new LinesOfCodeWeight();

		JavaPackage m = new JavaPackage("dk.fisk");
		DecoratorContext context = mock(DecoratorContext.class);
		locd.decorate(m, context);

	}
}
