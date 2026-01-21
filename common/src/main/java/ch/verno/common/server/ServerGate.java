package ch.verno.common.server;

import ch.verno.common.file.dto.CsvMapDto;
import ch.verno.common.file.dto.FileDto;
import ch.verno.common.server.io.importing.CsvSchema;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface ServerGate {

  @Nonnull
  CsvSchema resolveCsvSchema(@Nonnull String fileToken);

  @Nonnull
  FileDto generateFileFromCsv(@Nonnull final String filename, @Nonnull final List<CsvMapDto> rows);
}
