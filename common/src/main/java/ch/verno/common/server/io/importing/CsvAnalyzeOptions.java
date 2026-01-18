package ch.verno.common.server.io.importing;

import jakarta.annotation.Nonnull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public record CsvAnalyzeOptions(
        @Nonnull Charset charset,
        Character delimiter,
        char quoteChar,
        int sampleRows,
        int maxSamplesPerColumn,
        boolean collapseWhitespace,
        @Nonnull Set<String> trueTokens,
        @Nonnull Set<String> falseTokens,
        @Nonnull List<DateTimeFormatter> dateFormats,
        @Nonnull List<DateTimeFormatter> dateTimeFormats) {

  @Nonnull
  public static CsvAnalyzeOptions defaults() {
    return new CsvAnalyzeOptions(
            StandardCharsets.UTF_8,
            null,
            '"',
            25,
            5,
            true,
            Set.of("true", "1", "yes", "y", "ja"),
            Set.of("false", "0", "no", "n", "nein"),
            List.of(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                    DateTimeFormatter.ofPattern("d.M.yyyy"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ),
            List.of(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            )
    );
  }
}