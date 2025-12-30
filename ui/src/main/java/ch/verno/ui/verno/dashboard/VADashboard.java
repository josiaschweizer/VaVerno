package ch.verno.ui.verno.dashboard;

import ch.verno.ui.base.components.dashboard.VABaseDashboard;
import ch.verno.ui.base.components.dashboard.VABaseDashboardWidget;
import ch.verno.ui.verno.dashboard.widgets.AssignParticipantDashboardWidget;
import com.vaadin.flow.component.dashboard.Dashboard;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;

import java.util.List;

public class VADashboard extends VABaseDashboard {

  public VADashboard() {
    super();
  }

  @Nonnull
  @Override
  protected Dashboard createDashboard() {
    final var dashboard = new Dashboard();
    dashboard.setMinimumColumnWidth("150px");
    dashboard.setMaximumColumnCount(4);
    dashboard.setGap(LumoUtility.Gap.SMALL);
    dashboard.setPadding(LumoUtility.Padding.SMALL);
    dashboard.setDenseLayout(true);
    return dashboard;
  }

  @Nonnull
  @Override
  protected List<VABaseDashboardWidget> getDashboardWidgets() {
    return List.of(new AssignParticipantDashboardWidget());
  }

}
