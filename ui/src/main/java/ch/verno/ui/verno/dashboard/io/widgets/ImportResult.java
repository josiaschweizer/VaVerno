package ch.verno.ui.verno.dashboard.io.widgets;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public record ImportResult(
        boolean completeSuccess,
        boolean partialSuccess,
        @Nullable String fileToken,
        @Nullable String fileName
) {

  @Nonnull
  public static ImportResult completeSuccessInstance() {
    return new ImportResult(true, false, null, null);
  }

  @Nonnull
  public static ImportResult partialSuccess(@Nullable String fileToken,
                                            @Nullable String fileName) {
    return new ImportResult(false, true, fileToken, fileName);
  }

  @Nonnull
  public static ImportResult completeFailure() {
    return new ImportResult(false, false, null, null);
  }

  public boolean hasDownload() {
    return fileToken != null && !fileToken.isBlank();
  }
}