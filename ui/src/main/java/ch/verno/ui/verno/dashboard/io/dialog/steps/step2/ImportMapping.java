package ch.verno.ui.verno.dashboard.io.dialog.steps.step2;

import ch.verno.common.gate.VernoServerGate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;

public class ImportMapping extends HorizontalLayout {

  @Nonnull private final VernoServerGate vernoServerGate;

  public ImportMapping(@Nonnull final VernoServerGate vernoServerGate) {
    this.vernoServerGate = vernoServerGate;
  }

}
