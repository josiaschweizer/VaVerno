package ch.verno.ui.base.dialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

import java.util.Collection;

public abstract class VADialog extends Dialog {

  protected void initUI(@Nonnull final String title) {
    initUI(title, DialogSize.BIG);
  }

  protected void initUI(@Nonnull final String title,
                        @Nonnull final DialogSize dialogSize) {
    setHeight(dialogSize.getHeight());
    setWidth(dialogSize.getWidth());
    setMaxWidth(dialogSize.getMaxWidth());
    setMinWidth(dialogSize.getMinWidth());

    setHeaderTitle(title);
    add(createContent());
    createActionButtons().forEach(btn -> getFooter().add(btn));
  }

  @Nonnull
  protected abstract HorizontalLayout createContent();

  @Nonnull
  protected abstract Collection<Button> createActionButtons();

  @Nonnull
  protected VerticalLayout createLayoutFromComponents(@Nonnull final Component... components) {
    final var layout = new VerticalLayout(components);
    layout.setPadding(false);
    layout.setSpacing(false);
    return layout;
  }
}
