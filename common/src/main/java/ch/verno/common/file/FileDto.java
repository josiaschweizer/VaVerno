package ch.verno.common.file;

import jakarta.annotation.Nonnull;

public record FileDto(@Nonnull String filename,
                      @Nonnull byte[] pdfBytes) {
}
