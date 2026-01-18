package ch.verno.ui.verno.dashboard.io.widgets.instructor;

import ch.verno.common.gate.VernoApplicationGate;
import ch.verno.ui.verno.dashboard.io.dialog.steps.step2.ImportColumnMappingPanel;
import ch.verno.ui.verno.dashboard.io.widgets.ImportEntityConfig;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

/**
 * Import configuration for Instructor entities.
 */
public class InstructorImportConfig implements ImportEntityConfig {

  @Nonnull private final VernoApplicationGate vernoApplicationGate;

  public InstructorImportConfig(@Nonnull final VernoApplicationGate vernoApplicationGate) {
    this.vernoApplicationGate = vernoApplicationGate;
  }

  @Nonnull
  @Override
  public List<ImportColumnMappingPanel.DbField> getDbFields() {
    return List.of(
            new ImportColumnMappingPanel.DbField("firstname", "Vorname", true),
            new ImportColumnMappingPanel.DbField("lastname", "Nachname", true),
            new ImportColumnMappingPanel.DbField("email", "E-Mail", true),
            new ImportColumnMappingPanel.DbField("phone", "Telefon", false)
    );
  }

  @Override
  public boolean performImport(@Nonnull final String fileToken,
                               @Nonnull final Map<String, String> mapping) {
    // TODO: Implement actual instructor import logic
    // Example: vernoApplicationGate.getService(IInstructorService.class).importFromCsv(fileToken, mapping);
    return true;
  }

  @Nonnull
  @Override
  public String getEntityTypeName() {
    return "Instructor";
  }
}
