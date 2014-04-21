package dk.mimer.mmetric.sonar.ui;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.NavigationSection;
import org.sonar.api.web.RubyRailsPage;
import org.sonar.api.web.UserRole;

@NavigationSection(NavigationSection.RESOURCE)
//@ResourceQualifier({ Qualifiers.FILE, Qualifiers.CLASS, Qualifiers.UNIT_TEST_FILE })
@UserRole(UserRole.CODEVIEWER)
public class MaintainabilityRubyTab extends AbstractRubyTemplate implements RubyRailsPage {

	/**
	 * Tab ID
	 */
	public final String getId() {
		return "maintainabilityMetricsTab";
	}

	/**
	 * Tab Title
	 */
	public final String getTitle() {
		return "Maintainability";
	}

	/**
	 * Tab Template path
	 */
	@Override
	protected final String getTemplatePath() {
		return "/tab/maintainabilityTab.html.erb";
	}

}
