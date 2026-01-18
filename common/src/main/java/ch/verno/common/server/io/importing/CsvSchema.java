package ch.verno.common.server.io.importing;

import jakarta.annotation.Nonnull;

import java.util.List;

public record CsvSchema(@Nonnull List<CsvColumn> columns) {
}