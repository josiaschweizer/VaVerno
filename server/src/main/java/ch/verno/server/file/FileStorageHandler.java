package ch.verno.server.file;

import ch.verno.common.report.ReportDto;
import ch.verno.server.file.temp.TempFileStorageHandler;
import jakarta.annotation.Nonnull;

public class FileStorageHandler {

  private final TempFileStorageHandler tempFileStorageHandler;

  public FileStorageHandler() {
    tempFileStorageHandler = new TempFileStorageHandler();
  }

  @Nonnull
  public String storeFileTemporary(@Nonnull final String filename,
                                   @Nonnull final byte[] fileData) {
    return tempFileStorageHandler.store(filename, fileData);
  }

  @Nonnull
  public ReportDto loadTemporaryFile(@Nonnull final String token) {
    return tempFileStorageHandler.load(token);
  }

  public void delete(@Nonnull final String token) {
    tempFileStorageHandler.delete(token);
  }

}
