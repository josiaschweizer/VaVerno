package ch.verno.ui.verno.dashboard.io.dto;

import jakarta.annotation.Nonnull;

/**
 * A unified representation of an import field for display purposes.
 * This combines regular fields, typed fields, and nested fields into a single structure.
 */
public record ImportField(
        @Nonnull String key,
        @Nonnull String label,
        boolean required
) {
}
