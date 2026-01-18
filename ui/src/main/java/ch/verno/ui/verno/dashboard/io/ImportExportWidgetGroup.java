package ch.verno.ui.verno.dashboard.io;

import ch.verno.common.gate.VernoServerGate;
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

  @Nonnull private final VernoServerGate vernoServerGate;

  public ImportExportWidgetGroup(@Nonnull final VernoServerGate vernoServerGate) {
    this.vernoServerGate = vernoServerGate;

    setPadding(false);
    setMargin(false);
    setSpacing(false);
    setWidthFull();

    initUI();
  }


  private void initUI() {
    final var participant = new ParticipantWidget(vernoServerGate);
    final var instructorWidget = new InstructorWidget(vernoServerGate);

    add(participant, instructorWidget);
  }

}
