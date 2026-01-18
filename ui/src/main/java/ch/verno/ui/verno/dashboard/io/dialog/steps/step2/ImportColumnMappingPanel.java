package ch.verno.ui.verno.dashboard.io.dialog.steps.step2;


import ch.verno.ui.base.components.mapping.VABaseColumnMappingPanel;
import jakarta.annotation.Nonnull;

import java.util.List;

public class ImportColumnMappingPanel extends VABaseColumnMappingPanel<ImportColumnMappingPanel.DbField> {

  public record DbField(@Nonnull String key, @Nonnull String label) {
  }

  public ImportColumnMappingPanel(@Nonnull final List<String> csvColumns,
                                  @Nonnull final List<DbField> dbFields) {
    super(
            csvColumns,
            dbFields,
            false,
            "Ignorieren"
    );
  }

  @Nonnull
  @Override
  protected String getTargetHeader() {
    return "Map to DB field";
  }

  @Nonnull
  @Override
  protected String getFieldKey(@Nonnull final DbField field) {
    return field.key();
  }

  @Nonnull
  @Override
  protected String getFieldLabel(@Nonnull final DbField field) {
    return field.label();
  }
}