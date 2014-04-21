
package dk.mimer.mmetric.sonar.ui.widget;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

@UserRole(UserRole.USER)
@Description("Measure of Maintainability charateristic Changeability")
@WidgetCategory("Design")
public class ChangeabilityMaintainabilityRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "MCMetric";
  }

  public String getTitle() {
    return "Changeability";
  }

  @Override
  protected String getTemplatePath() {
    return "/widget/maintainabilityChangeability.html.erb";
  }
}
