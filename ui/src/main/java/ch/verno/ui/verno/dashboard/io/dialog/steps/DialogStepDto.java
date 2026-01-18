package ch.verno.ui.verno.dashboard.io.dialog.steps;

import ch.verno.ui.verno.dashboard.io.dialog.DialogStep;
import com.vaadin.flow.component.Component;
import jakarta.annotation.Nonnull;

public record DialogStepDto(@Nonnull DialogStep step,
                            @Nonnull Component content) {
}
