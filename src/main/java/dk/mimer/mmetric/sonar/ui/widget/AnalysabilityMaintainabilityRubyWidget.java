
package dk.mimer.mmetric.sonar.ui.widget;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

@UserRole(UserRole.USER)
@Description("Measure of Maintainability charateristic Analysability")
@WidgetCategory("Design")
public class AnalysabilityMaintainabilityRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "MAMetric";
  }

  public String getTitle() {
    return "Analysability";
  }

  @Override
  protected String getTemplatePath() {
    return "/widget/maintainabilityAnalysability.html.erb";
  }
}
