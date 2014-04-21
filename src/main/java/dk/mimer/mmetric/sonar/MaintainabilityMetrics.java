package dk.mimer.mmetric.sonar;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public final class MaintainabilityMetrics implements Metrics {
	public static final Metric MAINTAINABILITY = new Metric.Builder("mmetric_maintainability", "Maintainability", Metric.ValueType.DATA)
		.setDescription("Maintainability")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
		
	public static final Metric ANALYZABILITY = new Metric.Builder("mmetric_analysability", "Analysability", Metric.ValueType.DATA)
		.setDescription("Analysability")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
	public static final Metric CHANGEABILITY = new Metric.Builder("mmetric_changeability", "Changeability", Metric.ValueType.DATA)
		.setDescription("Changeability")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
	
	public static final Metric STABILITY = new Metric.Builder("mmetric_stability", "Stability", Metric.ValueType.DATA)
		.setDescription("Stability")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
	
	public static final Metric TESTABILITY = new Metric.Builder("mmetric_testablity", "Testability", Metric.ValueType.DATA)
		.setDescription("Testability")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();

	public static final Metric UNIT_SIZE_DISTRIBUTION = new Metric.Builder("mmetric_loc_distribution", "LoC method distribution", Metric.ValueType.DISTRIB)
      	.setDescription("Calculate LOC pr. method")
      	.setDirection(Metric.DIRECTION_WORST)
      	.setQualitative(true)
      	.setDomain(CoreMetrics.DOMAIN_GENERAL)
      	.create();

	public static final Metric COMPLEXITY_DISTRIBUTION_METHOD = new Metric.Builder("mmetric_cc_distribution", "Complexity pr. Method", Metric.ValueType.DISTRIB)
  		.setDescription("Calculate Complexity pr. method")
  		.setDirection(Metric.DIRECTION_WORST)
  		.setQualitative(true)
  		.setDomain(CoreMetrics.DOMAIN_GENERAL)
  		.create();
  
	public static final Metric VOLUME_WEIGHT = new Metric.Builder("mmetric_loc", "Volume", Metric.ValueType.DATA)
		.setDescription("Volume of code based on lines of code")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();

	public static final Metric DUPLICATION_WEIGHT = new Metric.Builder("mmetric_duplication", "Duplication", Metric.ValueType.DATA)
		.setDescription("Duplication of code based on distribution matrix")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();

	public static final Metric TEST_COVERAGE_WEIGHT  = new Metric.Builder("mmetric_coverage", "Test coverage", Metric.ValueType.DATA)
		.setDescription("Test coverage based on distribution matrix")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();

	public static final Metric COMPLEXITY_WEIGHT  = new Metric.Builder("mmetric_complexity_weight", "Complexity", Metric.ValueType.DATA)
		.setDescription("Complexity of methods based on distribution matrix")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
	
	public static final Metric UNIT_SIZE  = new Metric.Builder("mmetric_loc_weight", "Unit size", Metric.ValueType.DATA)
		.setDescription("Weight of LOC per methods based on distribution matrix")
		.setDirection(Metric.DIRECTION_WORST)
		.setQualitative(true)
		.setDomain(CoreMetrics.DOMAIN_GENERAL)
		.create();
  
  public List<Metric> getMetrics() {
    return Arrays.asList(UNIT_SIZE_DISTRIBUTION, COMPLEXITY_DISTRIBUTION_METHOD, 
    					VOLUME_WEIGHT, DUPLICATION_WEIGHT, TEST_COVERAGE_WEIGHT, COMPLEXITY_WEIGHT, UNIT_SIZE,
    					 ANALYZABILITY,CHANGEABILITY,STABILITY,TESTABILITY,MAINTAINABILITY);
  }
}
