package ch.verno.server.file;

import ch.verno.common.file.FileServerGate;
import ch.verno.common.file.FileDto;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class FileServerGateImpl implements FileServerGate {

  @Nonnull private final FileStorageHandler fileStorageHandler;

  public FileServerGateImpl() {
    fileStorageHandler = new FileStorageHandler();
  }

  @Nonnull
  @Override
  public String store(final String filename, final byte[] fileBytes) {
    return fileStorageHandler.storeFileTemporary(filename, fileBytes);
  }

  @Nonnull
  @Override
  public FileDto loadFile(@Nonnull final String token) {
    return fileStorageHandler.getFileByToken(token);
  }

  @Override
  public void delete(@Nonnull final String token) {
    fileStorageHandler.delete(token);
  }
}
