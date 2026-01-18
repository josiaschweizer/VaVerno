package ch.verno.common.server.io.importing;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;

public record CsvColumn(int index,
                        @Nonnull String name,
                        @Nonnull CsvDetectedType detectedType,
                        @Nonnull List<String> samples) {
  public CsvColumn {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(detectedType, "detectedType");
    Objects.requireNonNull(samples, "samples");
  }
}