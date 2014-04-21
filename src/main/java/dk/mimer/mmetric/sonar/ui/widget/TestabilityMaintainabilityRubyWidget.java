
package dk.mimer.mmetric.sonar.ui.widget;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

@UserRole(UserRole.USER)
@Description("Measure of Maintainability charateristic Testability")
@WidgetCategory("Design")
public class TestabilityMaintainabilityRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "MTMetric";
  }

  public String getTitle() {
    return "Testability";
  }

  @Override
  protected String getTemplatePath() {
    return "/widget/maintainabilityTestability.html.erb";
  }
}
