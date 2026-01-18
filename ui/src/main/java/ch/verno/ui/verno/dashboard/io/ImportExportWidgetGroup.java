package ch.verno.ui.verno.dashboard.io;

import ch.verno.common.gate.VernoApplicationGate;
import ch.verno.ui.verno.dashboard.io.widgets.instructor.InstructorWidget;
import ch.verno.ui.verno.dashboard.io.widgets.participant.ParticipantWidget;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class ImportExportWidgetGroup extends VerticalLayout {

  @Nonnull private final VernoApplicationGate vernoApplicationGate;

  public ImportExportWidgetGroup(@Nonnull final VernoApplicationGate vernoApplicationGate) {
    this.vernoApplicationGate = vernoApplicationGate;

    setPadding(false);
    setMargin(false);
    setSpacing(false);
    setWidthFull();

    initUI();
  }


  private void initUI() {
    final var participant = new ParticipantWidget(vernoApplicationGate);
    final var instructorWidget = new InstructorWidget(vernoApplicationGate);

    add(participant, instructorWidget);
  }

}
