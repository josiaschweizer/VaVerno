package ch.verno.ui.verno.dashboard.io.dialog;

import ch.verno.common.gate.VernoServerGate;
import ch.verno.ui.verno.dashboard.io.dialog.steps.DialogStepDto;
import ch.verno.ui.verno.dashboard.io.dialog.steps.step1.ImportFile;
import ch.verno.ui.verno.dashboard.io.dialog.steps.step2.ImportMapping;
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

  @Nullable private HorizontalLayout contentLayout;

  @Nonnull private final List<DialogStepDto> steps;
  @Nullable private Button forwardButton;
  @Nullable private Button finishButton;

  private DialogStep currentStep;

  public ImportDialog(@Nonnull final VernoServerGate vernoServerGate,
                      @Nonnull final String dialogTitle) {
    steps = new ArrayList<>();
    currentStep = DialogStep.ZERO;

    steps.add(new DialogStepDto(DialogStep.ONE, new ImportFile(vernoServerGate)));
    steps.add(new DialogStepDto(DialogStep.TWO, new ImportMapping(vernoServerGate)));

    initUI(dialogTitle);
  }

  private void initUI(@NonNull final String dialogTitle) {
    setHeight("90vh");
    setWidth("min(1500px, 95vw)");
    setMaxWidth("1500px");
    setMinWidth("320px");

    updateHeaderTitle(dialogTitle);
    add(createContent());
    createActionButtons().forEach(btn -> getFooter().add(btn));
  }

  @NonNull
  protected HorizontalLayout createContent() {
    contentLayout = new HorizontalLayout();
    contentLayout.setSizeFull();
    contentLayout.setPadding(true);
    contentLayout.setSpacing(true);

    updateContentByStep(DialogStep.ONE, contentLayout);

    return contentLayout;
  }


  @NonNull
  protected Collection<Button> createActionButtons() {
    final var cancelButton = new Button(getTranslation("shared.cancel"), e -> close());
    forwardButton = new Button(getTranslation("shared.forward"), e -> {
      if (contentLayout == null) {
        return;
      }

      updateContentByStep(DialogStep.addSteps(currentStep, 1), contentLayout);
    });
    finishButton = new Button(getTranslation("shared.finish"), e -> close());
    updateButtonVisibility();

    return List.of(cancelButton, forwardButton, finishButton);
  }

  private void updateButtonVisibility() {
    if (forwardButton == null || finishButton == null) {
      return;
    }

    if (currentStep.getStepNumber() == steps.size()) {
      forwardButton.setVisible(false);
      finishButton.setVisible(true);
    } else {
      forwardButton.setVisible(true);
      finishButton.setVisible(false);
    }
  }

  protected void updateContentByStep(@Nonnull final DialogStep stepIndex,
                                     @Nonnull final HorizontalLayout contentLayout) {
    final var first = steps.stream()
            .filter(s -> s.step() == stepIndex)
            .findFirst();
    if (first.isEmpty()) {
      return;
    }

    currentStep = stepIndex;
    final var content = first.get().content();

    contentLayout.removeAll();
    contentLayout.add(content);
    contentLayout.expand(content);
  }

  private void updateHeaderTitle(@NonNull final String title) {
    setHeaderTitle(title);
  }
}
