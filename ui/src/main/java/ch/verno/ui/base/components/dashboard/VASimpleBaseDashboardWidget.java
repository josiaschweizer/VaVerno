package ch.verno.ui.base.components.dashboard;

import com.vaadin.flow.component.html.Span;
import jakarta.annotation.Nonnull;

public class VASimpleBaseDashboardWidget extends VABaseDashboardWidget {

  public VASimpleBaseDashboardWidget(@Nonnull final String title,
                                     @Nonnull final String description) {
    setHeaderContent(new Span(title));
    setContent(new Span(description));
  }

}
