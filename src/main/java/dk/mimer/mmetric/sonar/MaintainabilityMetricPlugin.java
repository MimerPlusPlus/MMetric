package dk.mimer.mmetric.sonar;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import dk.mimer.mmetric.sonar.decorators.CodeDuplicationWeight;
import dk.mimer.mmetric.sonar.decorators.LinesOfCodeWeight;
import dk.mimer.mmetric.sonar.decorators.MethodComplexityDistribution;
import dk.mimer.mmetric.sonar.decorators.MethodComplexityWeight;
import dk.mimer.mmetric.sonar.decorators.MethodLinesOfCodeDistribution;
import dk.mimer.mmetric.sonar.decorators.MethodLinesOfCodeWeight;
import dk.mimer.mmetric.sonar.decorators.TestCoverageWeight;
import dk.mimer.mmetric.sonar.decorators.main.AnalyzabilityMetricDecorator;
import dk.mimer.mmetric.sonar.decorators.main.ChangeabilityMetricDecorator;
import dk.mimer.mmetric.sonar.decorators.main.MaintainabilityMetricDecorator;
import dk.mimer.mmetric.sonar.decorators.main.StabilityMetricDecorator;
import dk.mimer.mmetric.sonar.decorators.main.TestabilityMetricDecorator;
import dk.mimer.mmetric.sonar.ui.MaintainabilityRubyTab;
import dk.mimer.mmetric.sonar.ui.widget.AnalysabilityMaintainabilityRubyWidget;
import dk.mimer.mmetric.sonar.ui.widget.ChangeabilityMaintainabilityRubyWidget;
import dk.mimer.mmetric.sonar.ui.widget.MaintainabilityRubyWidget;
import dk.mimer.mmetric.sonar.ui.widget.StabilityMaintainabilityRubyWidget;
import dk.mimer.mmetric.sonar.ui.widget.TestabilityMaintainabilityRubyWidget;

/**
 * This class is the entry point for all extensions
 */
@Properties({ @Property(key = MaintainabilityMetricPlugin.MY_PROPERTY, name = "Maintainability Metric Plugin Property", description = "A property for the plugin", defaultValue = "Hello World!") })
public final class MaintainabilityMetricPlugin extends SonarPlugin {

	public static final String MY_PROPERTY = "dk.mimer.mmetric.sonar.property";

	// This is where you're going to declare all your Sonar extensions
	public List getExtensions() {
		return Arrays.asList(
				// Metrics
				MaintainabilityMetrics.class,

				// Decorators
				CodeDuplicationWeight.class, LinesOfCodeWeight.class, MethodComplexityDistribution.class, MethodComplexityWeight.class,
				MethodLinesOfCodeDistribution.class,TestCoverageWeight.class,MethodLinesOfCodeWeight.class,
				AnalyzabilityMetricDecorator.class,ChangeabilityMetricDecorator.class,StabilityMetricDecorator.class,TestabilityMetricDecorator.class,
				MaintainabilityMetricDecorator.class,
				
				// UI
				AnalysabilityMaintainabilityRubyWidget.class,ChangeabilityMaintainabilityRubyWidget.class,
				StabilityMaintainabilityRubyWidget.class,TestabilityMaintainabilityRubyWidget.class,
				MaintainabilityRubyWidget.class,
				MaintainabilityRubyTab.class);
	}
}
