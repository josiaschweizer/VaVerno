package ch.verno.ui.base.components.dashboard;

import com.vaadin.flow.component.dashboard.Dashboard;
import com.vaadin.flow.component.html.Div;
import jakarta.annotation.Nonnull;

import java.util.List;

public abstract class VABaseDashboard extends Div {

  @Nonnull
  protected Dashboard dashboard;

  public VABaseDashboard() {
    initUI();
  }

  private void initUI() {
    dashboard = createDashboard();
    getDashboardWidgets().forEach(d -> dashboard.add(d));

    add(dashboard);
  }

  @Nonnull
  protected abstract Dashboard createDashboard();

  @Nonnull
  protected abstract List<VABaseDashboardWidget> getDashboardWidgets();

}
