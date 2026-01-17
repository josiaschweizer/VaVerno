package ch.verno.ui.verno.dashboard;

import ch.verno.common.db.enums.CourseScheduleStatus;
import ch.verno.common.db.service.*;
import ch.verno.common.file.FileServerGate;
import ch.verno.common.report.ReportServerGate;
import ch.verno.ui.base.Refreshable;
import ch.verno.ui.verno.dashboard.course.CourseWidgetGroup;
import ch.verno.ui.verno.dashboard.courseSchedules.CourseScheduleLifecycleWidgetGroup;
import ch.verno.ui.verno.dashboard.io.ImportExportWidgetGroup;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import jakarta.annotation.Nonnull;

public class Dashboard extends VerticalLayout {

  @Nonnull private final IInstructorService instructorService;
  @Nonnull private final IParticipantService participantService;
  @Nonnull private final ICourseService courseService;
  @Nonnull private final ICourseLevelService courseLevelService;
  @Nonnull private final ICourseScheduleService courseScheduleService;
  @Nonnull private final IMandantSettingService mandantSettingService;
  @Nonnull private final ReportServerGate reportServerGate;
  @Nonnull private final FileServerGate fileServerGate;

  public Dashboard(@Nonnull final ICourseService courseService,
                   @Nonnull final IInstructorService instructorService,
                   @Nonnull final IParticipantService participantService,
                   @Nonnull final ICourseLevelService courseLevelService,
                   @Nonnull final ICourseScheduleService courseScheduleService,
                   @Nonnull final IMandantSettingService mandantSettingService,
                   @Nonnull final ReportServerGate reportServerGate,
                   @Nonnull final FileServerGate fileServerGate) {
    this.instructorService = instructorService;
    this.participantService = participantService;
    this.courseService = courseService;
    this.courseLevelService = courseLevelService;
    this.courseScheduleService = courseScheduleService;
    this.mandantSettingService = mandantSettingService;
    this.reportServerGate = reportServerGate;
    this.fileServerGate = fileServerGate;

    setSizeFull();
    setPadding(false);
    setSpacing(false);
    add(createCourseTabView());
  }

  @Nonnull
  private TabSheet createCourseTabView() {
    final var tabSheet = new TabSheet();
    tabSheet.setWidthFull();

    final var plannedTab = new CourseWidgetGroup(
            CourseScheduleStatus.PLANNED,
            courseService,
            instructorService,
            participantService,
            courseLevelService,
            courseScheduleService,
            mandantSettingService,
            reportServerGate);
    final var activeTab = new CourseWidgetGroup(
            CourseScheduleStatus.ACTIVE,
            courseService,
            instructorService,
            participantService,
            courseLevelService,
            courseScheduleService,
            mandantSettingService,
            reportServerGate);
    final var lifecycleTab = new CourseScheduleLifecycleWidgetGroup(courseScheduleService);
    final var ioTab = new ImportExportWidgetGroup(
            participantService,
            courseLevelService,
            instructorService,
            reportServerGate,
            fileServerGate,
            courseService
    );

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
