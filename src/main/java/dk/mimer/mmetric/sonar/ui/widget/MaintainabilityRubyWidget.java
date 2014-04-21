package dk.mimer.mmetric.sonar.ui.widget;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.Description;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.UserRole;
import org.sonar.api.web.WidgetCategory;

@UserRole(UserRole.USER)
@Description("Measure of Maintainability")
@WidgetCategory("Design")
public class MaintainabilityRubyWidget extends AbstractRubyTemplate implements RubyRailsWidget {

  public String getId() {
    return "MaintainabilityMetric";
  }

  public String getTitle() {
    return "Maintainability";
  }

  // ERB inspiration:  http://svn.sonar-plugins.codehaus.org/browse/sonar-plugins/tags/sonar-rulesmeter-plugin-0.1/src/main/resources/org/sonar/plugins/ral/rules_activation_widget.erb?hb=true
  
  @Override
  protected String getTemplatePath() {
    return "/widget/maintainability.html.erb";
  }
}