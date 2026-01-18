package ch.verno.ui.verno.dashboard.io.widgets;

import ch.verno.common.gate.VernoServerGate;
import ch.verno.publ.Publ;
import ch.verno.ui.base.components.notification.NotificationFactory;
import ch.verno.ui.base.components.widget.VAAccordionWidgetBase;
import ch.verno.ui.verno.dashboard.io.dialog.ImportDialog;
import ch.verno.ui.verno.participant.ParticipantsGrid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;

public class ParticipantWidget extends VAAccordionWidgetBase {

  @Nonnull private final VernoServerGate vernoServerGate;

  public ParticipantWidget(@Nonnull final VernoServerGate vernoServerGate) {
    super();
    this.vernoServerGate = vernoServerGate;

    build();
  }

  @Nonnull
  @Override
  protected String getTitleText() {
    return getTranslation("participant.participant");
  }

  @Override
  protected void buildHeaderActions(@NonNull final HorizontalLayout header) {
    final var importButton = createHeaderButton(
            getTranslation("shared.import"),
            VaadinIcon.DOWNLOAD,
            e -> {
              final var importDialog = new ImportDialog(vernoServerGate, getTranslation("shared.import") + Publ.SPACE + getTranslation("participant.participant"));
              importDialog.open();
            });
    final var exportButton = createHeaderButton(
            getTranslation("shared.export"),
            VaadinIcon.UPLOAD,
            e -> NotificationFactory.showInfoNotification("This feature is not implemented yet."));

    header.add(importButton, exportButton);
  }

  @Override
  protected void initContent() {
    final var participantsGrid = new ParticipantsGrid(vernoServerGate,
            false,
            false);
    participantsGrid.getGrid().setAllRowsVisible(true);
    participantsGrid.setWidthFull();
    add(participantsGrid);
  }
}
