package ch.verno.common.server;

import ch.verno.common.server.io.importing.CsvSchema;
import jakarta.annotation.Nonnull;

public interface ServerGate {

  @Nonnull
  CsvSchema resolveCsvSchema(@Nonnull String fileToken);

}
