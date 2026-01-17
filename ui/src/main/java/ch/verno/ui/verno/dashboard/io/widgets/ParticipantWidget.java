package ch.verno.ui.verno.dashboard.io.widgets;

import ch.verno.common.db.service.ICourseLevelService;
import ch.verno.common.db.service.ICourseService;
import ch.verno.common.db.service.IParticipantService;
import ch.verno.common.report.ReportServerGate;
import ch.verno.ui.base.components.notification.NotificationFactory;
import ch.verno.ui.base.components.widget.VAAccordionWidgetBase;
import ch.verno.ui.verno.participant.ParticipantsGrid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;

public class ParticipantWidget extends VAAccordionWidgetBase {

  @Nonnull private final IParticipantService participantService;
  @Nonnull private final ICourseService courseService;
  @Nonnull private final ICourseLevelService courseLevelService;
  @Nonnull private final ReportServerGate reportServerGate;

  public ParticipantWidget(@Nonnull final IParticipantService participantService,
                           @Nonnull final ICourseService courseService,
                           @Nonnull final ICourseLevelService courseLevelService,
                           @Nonnull final ReportServerGate reportServerGate) {
    super();
    this.participantService = participantService;
    this.courseService = courseService;
    this.courseLevelService = courseLevelService;
    this.reportServerGate = reportServerGate;

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
            e -> NotificationFactory.showInfoNotification("This feature is not implemented yet."));
    final var exportButton = createHeaderButton(
            getTranslation("shared.export"),
            VaadinIcon.UPLOAD,
            e -> NotificationFactory.showInfoNotification("This feature is not implemented yet."));

    header.add(importButton, exportButton);
  }

  @Override
  protected void initContent() {
    final var participantsGrid = new ParticipantsGrid(
            participantService,
            courseService,
            courseLevelService,
            reportServerGate,
            false,
            false);
    participantsGrid.getGrid().setAllRowsVisible(true);
    participantsGrid.setWidthFull();
    add(participantsGrid);
  }
}
