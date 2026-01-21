package ch.verno.server;

import ch.verno.common.file.FileServerGate;
import ch.verno.common.file.dto.CsvMapDto;
import ch.verno.common.file.dto.FileDto;
import ch.verno.common.server.ServerGate;
import ch.verno.common.server.io.importing.CsvSchema;
import ch.verno.server.io.importing.SchemaResolver;
import jakarta.annotation.Nonnull;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerGateImpl implements ServerGate {

  @Nonnull private final FileServerGate fileServerGate;

  public ServerGateImpl(@Nonnull final FileServerGate fileServerGate) {
    this.fileServerGate = fileServerGate;
  }

  @Nonnull
  @Override
  public CsvSchema resolveCsvSchema(@Nonnull final String fileToken) {
    final var schemaResolver = new SchemaResolver(fileServerGate);
    return schemaResolver.resolveCsvSchema(fileToken);
  }

  @Nonnull
  @Override
  public FileDto generateFileFromCsv(@Nonnull final String filename,
                                     @NonNull final List<CsvMapDto> rows) {
    return fileServerGate.parseRows(rows, filename);
  }

}
