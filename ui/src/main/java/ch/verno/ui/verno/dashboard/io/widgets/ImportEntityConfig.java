package ch.verno.ui.verno.dashboard.io.widgets;

import ch.verno.ui.verno.dashboard.io.dialog.steps.step2.ImportColumnMappingPanel;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

/**
 * Configuration interface for entity-specific import settings.
 * Implementations define which database fields are available for mapping
 * and how to perform the actual import.
 */
public interface ImportEntityConfig {

  /**
   * Returns the list of database fields available for mapping.
   * Each DbField represents a field in the target entity.
   *
   * @return List of database fields with their technical names and display labels
   */
  @Nonnull
  List<ImportColumnMappingPanel.DbField> getDbFields();

  /**
   * Performs the actual import using the provided file token and field mapping.
   *
   * @param fileToken The temporary file token from the uploaded CSV
   * @param mapping   Map of CSV column names to database field names
   * @return true if import was successful, false otherwise
   */
  boolean performImport(@Nonnull String fileToken, @Nonnull Map<String, String> mapping);

  /**
   * Returns a display name for this entity type (e.g., "Instructor", "Participant").
   *
   * @return The entity type display name
   */
  @Nonnull
  String getEntityTypeName();
}
