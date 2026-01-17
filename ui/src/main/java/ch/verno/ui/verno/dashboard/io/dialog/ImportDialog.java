package ch.verno.ui.verno.dashboard.io.dialog;

import ch.verno.common.file.FileServerGate;
import ch.verno.ui.verno.dashboard.io.dialog.steps.DialogStep;
import ch.verno.ui.verno.dashboard.io.dialog.steps.step1.StepImportFile;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImportDialog extends Dialog {

  @Nonnull private final List<DialogStep> steps;

  @Nullable private HorizontalLayout contentLayout;

  public ImportDialog(@Nonnull final FileServerGate fileServerGate) {
    steps = new ArrayList<>();
    steps.add(new DialogStep(1, new StepImportFile(fileServerGate)));

    initUI("Import");
  }

  private void initUI(@NonNull final String title) {
    setHeight("90vh");
    setWidth("min(1500px, 95vw)");
    setMaxWidth("1500px");
    setMinWidth("320px");

    updateHeaderTitle(title);
    add(createContent());
    createActionButtons().forEach(btn -> getFooter().add(btn));
  }

  @NonNull
  protected HorizontalLayout createContent() {
    contentLayout = new HorizontalLayout();
    contentLayout.setWidthFull();
    contentLayout.setHeightFull();

    updateContentByStep(1, contentLayout);

    return contentLayout;
  }


  @NonNull
  protected Collection<Button> createActionButtons() {
    final var closeButton = new Button("Close", e -> close());
    return List.of(closeButton);
  }

  protected void updateContentByStep(final int stepIndex, @Nonnull final HorizontalLayout contentLayout) {
    final var first = steps.stream()
            .filter(s -> s.step() == stepIndex)
            .findFirst();
    if (first.isEmpty()) {
      return;
    }

    final var content = first.get().content();

    contentLayout.removeAll();
    contentLayout.add(content);
  }

  private void updateHeaderTitle(@NonNull final String title) {
    setHeaderTitle(title);
  }
}
