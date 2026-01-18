package ch.verno.ui.verno.dashboard.course;

import ch.verno.common.db.enums.CourseScheduleStatus;
import ch.verno.common.db.service.ICourseService;
import ch.verno.common.gate.VernoServerGate;
import ch.verno.ui.base.Refreshable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class CourseWidgetGroup extends VerticalLayout implements Refreshable {

  @Nonnull private final VernoServerGate vernoServerGate;
  @Nonnull private final CourseScheduleStatus status;
  private final ICourseService courseService;

  public CourseWidgetGroup(@Nonnull final VernoServerGate vernoServerGate,
                           @Nonnull final CourseScheduleStatus status) {
    this.vernoServerGate = vernoServerGate;
    this.courseService = vernoServerGate.getService(ICourseService.class);
    this.status = status;

    setPadding(false);
    setMargin(false);
    setSpacing(false);
    setWidthFull();

    init();
  }

  private void init() {
    final var courses = courseService.getCoursesByCourseScheduleStatus(status);

    for (final var course : courses) {
      if (course.getId() != null) {
        add(new CourseWidget(course.getId(), vernoServerGate));
      }
    }
  }

  @Override
  public void refresh() {
    removeAll();
    init();
  }
}
