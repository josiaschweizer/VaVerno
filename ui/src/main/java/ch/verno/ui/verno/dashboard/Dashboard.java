package ch.verno.ui.verno.dashboard;

import ch.verno.common.db.enums.CourseScheduleStatus;
import ch.verno.common.gate.VernoApplicationGate;
import ch.verno.ui.base.Refreshable;
import ch.verno.ui.verno.dashboard.course.CourseWidgetGroup;
import ch.verno.ui.verno.dashboard.courseSchedules.CourseScheduleLifecycleWidgetGroup;
import ch.verno.ui.verno.dashboard.io.ImportExportWidgetGroup;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import jakarta.annotation.Nonnull;

public class Dashboard extends VerticalLayout {

  @Nonnull private final VernoApplicationGate vernoApplicationGate;

  public Dashboard(@Nonnull final VernoApplicationGate vernoApplicationGate) {
    this.vernoApplicationGate = vernoApplicationGate;

    setSizeFull();
    setPadding(false);
    setSpacing(false);
    add(createCourseTabView());
  }

  @Nonnull
  private TabSheet createCourseTabView() {
    final var tabSheet = new TabSheet();
    tabSheet.setWidthFull();

    final var plannedTab = new CourseWidgetGroup(vernoApplicationGate, CourseScheduleStatus.PLANNED);
    final var activeTab = new CourseWidgetGroup(vernoApplicationGate, CourseScheduleStatus.ACTIVE);
    final var lifecycleTab = new CourseScheduleLifecycleWidgetGroup(vernoApplicationGate);
    final var ioTab = new ImportExportWidgetGroup(vernoApplicationGate);

    tabSheet.add(getTranslation(CourseScheduleStatus.PLANNED.getDisplayNameKey()), plannedTab);
    tabSheet.add(getTranslation(CourseScheduleStatus.ACTIVE.getDisplayNameKey()), activeTab);
    tabSheet.add(getTranslation("courseSchedule.course.schedules.lifecycle"), lifecycleTab);
    tabSheet.add(getTranslation("shared.import.export"), ioTab);

    tabSheet.addSelectedChangeListener(event -> {
      final var selectedTab = event.getSelectedTab();

      if (selectedTab != null) {
        final var content = tabSheet.getComponent(selectedTab);

        if (content instanceof Refreshable refreshable) {
          refreshable.refresh();
        }
      }
    });

    return tabSheet;
  }

}
