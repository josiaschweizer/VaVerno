package ch.verno.common.file;

import jakarta.annotation.Nonnull;

public interface FileServerGate {

  @Nonnull
  String store(String filename, byte[] fileBytes);

  @Nonnull
  FileDto loadFile(@Nonnull String token);

  void delete(@Nonnull String token);
}
