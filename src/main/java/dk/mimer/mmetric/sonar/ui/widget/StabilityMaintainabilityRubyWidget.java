
package dk.mimer.mmetric.sonar.ui.widget;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

@UserRole(UserRole.USER)
@Description("Measure of Maintainability charateristic Stability")
@WidgetCategory("Design")
public class StabilityMaintainabilityRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "MSMetric";
  }

  public String getTitle() {
    return "Stability";
  }

  @Override
  protected String getTemplatePath() {
    return "/widget/maintainabilityStability.html.erb";
  }
}
