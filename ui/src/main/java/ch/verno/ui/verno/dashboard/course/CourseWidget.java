package ch.verno.ui.verno.dashboard.course;

import ch.verno.common.db.dto.CourseDto;
import ch.verno.common.db.dto.ParticipantDto;
import ch.verno.common.db.filter.ParticipantFilter;
import ch.verno.common.db.service.*;
import ch.verno.common.report.ReportServerGate;
import ch.verno.publ.Publ;
import ch.verno.ui.base.components.widget.VAAccordionWidgetBase;
import ch.verno.ui.verno.dashboard.assignment.AssignToCourseDialog;
import ch.verno.ui.verno.dashboard.report.CourseReportDialog;
import ch.verno.ui.verno.participant.ParticipantsGrid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.Query;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class CourseWidget extends VAAccordionWidgetBase {

  @Nonnull private final CourseDto currentCourse;
  @Nonnull private final ICourseService courseService;
  @Nonnull private final IInstructorService instructorService;
  @Nonnull private final IParticipantService participantService;
  @Nonnull private final ICourseLevelService courseLevelService;
  @Nonnull private final IMandantSettingService mandantSettingService;
  @Nonnull private final ICourseScheduleService courseScheduleService;
  @Nonnull private final ReportServerGate reportServerGate;

  @Nullable private ParticipantsGrid participantsGrid;
  @Nullable private List<ParticipantDto> participantsInCurrentCourse;

  public CourseWidget(@Nonnull final Long currentCourseId,
                      @Nonnull final ICourseService courseService,
                      @Nonnull final IInstructorService instructorService,
                      @Nonnull final IParticipantService participantService,
                      @Nonnull final ICourseLevelService courseLevelService,
                      @Nonnull final IMandantSettingService mandantSettingService,
                      @Nonnull final ICourseScheduleService courseScheduleService,
                      @Nonnull final ReportServerGate reportServerGate) {
    this.courseService = courseService;
    this.instructorService = instructorService;
    this.participantService = participantService;
    this.courseLevelService = courseLevelService;
    this.mandantSettingService = mandantSettingService;
    this.courseScheduleService = courseScheduleService;
    this.reportServerGate = reportServerGate;

    this.currentCourse = courseService.getCourseById(currentCourseId);

    build();
  }

  @Nonnull
  @Override
  protected String getTitleText() {
    return currentCourse.getTitle();
  }

  @Override
  protected void buildHeaderActions(@Nonnull final HorizontalLayout header) {
    final var reportButton = createHeaderButton("Report", VaadinIcon.FILE_TEXT, e -> {
      final var dialog = new CourseReportDialog(
              reportServerGate,
              currentCourse,
              participantsInCurrentCourse != null ?
                      participantsInCurrentCourse :
                      List.of());
      dialog.open();
    });

    final var assignButton = createHeaderButton(getTranslation("participant.edit.participant"),
            VaadinIcon.COG, e -> {
              final var dialog = new AssignToCourseDialog(
                      courseService,
                      participantService,
                      mandantSettingService,
                      currentCourse.getId(),
                      participantsInCurrentCourse != null
                              ? participantsInCurrentCourse.stream().map(ParticipantDto::getId).toList()
                              : List.of()
              );

              dialog.addClosedListener(ev -> refresh());
              dialog.addDialogCloseActionListener(ev -> refresh());
              dialog.open();
            });

    final var detailButton = createHeaderButton(Publ.EMPTY_STRING,
            VaadinIcon.EXTERNAL_LINK, e -> {
              final var courseDetailDialog = new CourseDetailDialog(
                      courseService,
                      instructorService,
                      courseLevelService,
                      courseScheduleService,
                      participantService,
                      reportServerGate,
                      currentCourse
              );
              courseDetailDialog.open();
            });

    header.add(reportButton, assignButton, detailButton);
  }

  @Override
  protected void initContent() {
    this.participantsGrid = new ParticipantsGrid(
            participantService,
            courseService,
            courseLevelService,
            reportServerGate,
            false,
            false) {
      @Nonnull
      @Override
      protected Stream<ParticipantDto> fetch(@Nonnull final Query<ParticipantDto, ParticipantFilter> query,
                                             @Nonnull final ParticipantFilter filter) {
        if (currentCourse.getId() != null) {
          filter.setCourseIds(Set.of(currentCourse.getId()));
        }

        final var participants = super.fetch(query, filter).toList();
        CourseWidget.this.participantsInCurrentCourse = participants;
        return participants.stream();
      }
    };

    participantsGrid.getGrid().setAllRowsVisible(true);
    add(participantsGrid);
  }

  @Override
  protected void refresh() {
    if (participantsGrid == null) {
      return;
    }

    participantsGrid.setFilter(participantsGrid.getFilter());
    participantsInCurrentCourse = participantsGrid.getGrid()
            .getDataProvider()
            .fetch(new Query<>())
            .toList();
  }
}