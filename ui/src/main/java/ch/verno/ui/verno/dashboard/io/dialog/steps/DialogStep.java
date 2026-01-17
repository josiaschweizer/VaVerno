package ch.verno.ui.verno.dashboard.io.dialog.steps;

import com.vaadin.flow.component.Component;
import jakarta.annotation.Nonnull;

public record DialogStep(int step,
                         @Nonnull Component content) {
}
