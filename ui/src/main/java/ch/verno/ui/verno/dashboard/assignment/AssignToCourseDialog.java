package ch.verno.ui.verno.dashboard.assignment;

import ch.verno.common.db.dto.CourseDto;
import ch.verno.common.db.dto.ParticipantDto;
import ch.verno.common.db.dto.base.BaseDto;
import ch.verno.common.util.Publ;
import ch.verno.server.service.CourseService;
import ch.verno.server.service.ParticipantService;
import ch.verno.ui.base.components.entry.combobox.VAComboBox;
import ch.verno.ui.base.components.filter.VASearchFilter;
import ch.verno.ui.base.factory.EntryFactory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.stream.Collectors;

@CssImport("/components/assignment/assignment.css")
public class AssignToCourseDialog extends Dialog {

  @Nonnull
  private final Binder<AssignParticipantsToCourseDto> binder;
  @Nonnull
  private final EntryFactory<AssignParticipantsToCourseDto> entryFactory;
  @Nonnull
  private final CourseService courseService;
  @Nonnull
  private final ParticipantService participantService;

  public AssignToCourseDialog(@Nonnull final CourseService courseService,
                              @Nonnull final ParticipantService participantService) {
    this.courseService = courseService;
    this.participantService = participantService;
    this.binder = new Binder<>(AssignParticipantsToCourseDto.class);
    this.entryFactory = new EntryFactory<AssignParticipantsToCourseDto>();

    setHeight("60vh");

    setHeaderTitle("Assign Participants to Course");
    add(createContent());
    getFooter().add(createCancelButton());
    getFooter().add(createSaveButton());
  }

  @Nonnull
  private HorizontalLayout createContent() {
    final var left = createCourseLayout();
    final var right = createParticipantLayout();

    final var layout = new HorizontalLayout(left, right);
    layout.setWidthFull();
    layout.setHeightFull();
    layout.setAlignItems(FlexComponent.Alignment.STRETCH);
    layout.addClassNames(LumoUtility.Gap.XLARGE);

    right.setHeightFull();
    layout.setFlexGrow(1, left, right);

    return layout;
  }

  @Nonnull
  private VerticalLayout createCourseLayout() {
    final var title = createTitleSpan("Course");
    final var courseCombobox = createCourseComboBox();

    return createLayoutFromComponents(title, courseCombobox);
  }

  @Nonnull
  private VerticalLayout createParticipantLayout() {
    final var title = createTitleSpan("Participants");
    final var searchBar = new VASearchFilter("Search participants…");
    final var participants = createParticipantsCheckboxGroup();

    final var layout = createLayoutFromComponents(title, searchBar, participants);
    layout.setFlexGrow(1, participants);
    return layout;
  }

  @Nonnull
  private Span createTitleSpan(@Nonnull final String titleLabel) {
    final var title = new Span(titleLabel);
    title.addClassNames("va-required");
    title.addClassNames(LumoUtility.FontSize.MEDIUM, LumoUtility.FontWeight.SEMIBOLD);
    return title;
  }

  private void searchChanged(@Nullable final String searchTerm) {
    // Implement search logic here
  }

  @Nonnull
  private Button createSaveButton() {
    final var button = new Button("Save");
    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    button.addClickListener(event -> save());
    return button;
  }

  private void save() {
    // Implement save logic here
  }

  @Nonnull
  private Button createCancelButton() {
    final var button = new Button("Cancel");
    button.addClickListener(event -> close());
    return button;
  }

  @Nonnull
  private VerticalLayout createLayoutFromComponents(@Nonnull final Component... components) {
    final var layout = new VerticalLayout(components);
    layout.setPadding(false);
    layout.setSpacing(false);
    return layout;
  }

  @Nonnull
  private VAComboBox<Long> createCourseComboBox() {
    return entryFactory.createComboBoxEntry(
            dto -> dto.getCourse().getId(),
            (dto, value) -> dto.setCourse(value != null ?
                    courseService.getCourseById(value) :
                    CourseDto.empty()),
            binder,
            Optional.of("Select Course"),
            Publ.EMPTY_STRING,
            courseService.getAllCourses().stream()
                    .collect(Collectors.toMap(CourseDto::getId, CourseDto::getTitle))
    );
  }

  @Nonnull
  private Scroller createParticipantsCheckboxGroup() {
    final var checkboxGroup = entryFactory.createCheckboxGroupEntry(
            dto -> dto.getParticipants().stream().map(BaseDto::getId).collect(Collectors.toSet()),
            (dto, value) -> {
              final var selectedParticipants = value.stream()
                      .map(participantService::getParticipantById)
                      .collect(Collectors.toSet());
              dto.setParticipants(selectedParticipants);
            },
            binder,
            Optional.of("Select Participants"),
            Publ.EMPTY_STRING,
            participantService.getAllParticipants().stream()
                    .collect(Collectors.toMap(ParticipantDto::getId, ParticipantDto::getFullName))
    );
    checkboxGroup.addClassName("participants-group");
    checkboxGroup.setWidthFull();

    final var scroller = new Scroller();
    scroller.setWidthFull();
    scroller.setHeightFull();
    scroller.setContent(checkboxGroup);
    return scroller;
  }
}
