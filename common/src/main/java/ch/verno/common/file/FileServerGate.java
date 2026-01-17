package ch.verno.common.file;

import jakarta.annotation.Nonnull;

public interface FileServerGate {

  @Nonnull
  String store(String filename, byte[] fileBytes);

  void delete(@Nonnull String token);
}
