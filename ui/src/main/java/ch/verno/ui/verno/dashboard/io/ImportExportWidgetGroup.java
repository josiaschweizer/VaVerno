package ch.verno.ui.verno.dashboard.io;

import ch.verno.common.db.service.ICourseLevelService;
import ch.verno.common.db.service.ICourseService;
import ch.verno.common.db.service.IInstructorService;
import ch.verno.common.db.service.IParticipantService;
import ch.verno.common.file.FileServerGate;
import ch.verno.common.report.ReportServerGate;
import ch.verno.ui.verno.dashboard.io.widgets.InstructorWidget;
import ch.verno.ui.verno.dashboard.io.widgets.ParticipantWidget;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class ImportExportWidgetGroup extends VerticalLayout {

  @Nonnull private final IParticipantService participantService;
  @Nonnull private final ICourseLevelService courseLevelService;
  @Nonnull private final IInstructorService instructorService;
  @Nonnull private final ICourseService courseService;
  @Nonnull private final FileServerGate fileServerGate;
  @Nonnull private final ReportServerGate reportServerGate;

  public ImportExportWidgetGroup(@Nonnull final IParticipantService participantService,
                                 @Nonnull final ICourseLevelService courseLevelService,
                                 @Nonnull final IInstructorService instructorService,
                                 @Nonnull final ReportServerGate reportServerGate,
                                 @Nonnull final FileServerGate fileServerGate,
                                 @Nonnull final ICourseService courseService) {
    this.participantService = participantService;
    this.courseLevelService = courseLevelService;
    this.instructorService = instructorService;
    this.reportServerGate = reportServerGate;
    this.fileServerGate = fileServerGate;
    this.courseService = courseService;

    setPadding(false);
    setMargin(false);
    setSpacing(false);
    setWidthFull();

    initUI();
  }


  private void initUI() {
    final var participant = new ParticipantWidget(
            participantService,
            courseService,
            courseLevelService,
            reportServerGate
    );
    final var instructorWidget = new InstructorWidget(fileServerGate, instructorService);

    add(participant, instructorWidget);
  }

}
