package ch.verno.ui.verno.dashboard.io.dialog.steps.step1;

import ch.verno.common.file.FileServerGate;
import ch.verno.ui.base.components.upload.VAFileUpload;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class StepImportFile extends VerticalLayout {

  private final VAFileUpload fileUpload;

  public StepImportFile(@Nonnull final FileServerGate fileServerGate) {
    setSizeFull();
    setPadding(false);
    setSpacing(false);

    fileUpload = new VAFileUpload(fileServerGate);
    fileUpload.setAcceptedFileTypes(".csv");

    add(fileUpload);
    expand(fileUpload);
  }

  public boolean hasFile() {
    return fileUpload.hasFile();
  }

  public String getTempToken() {
    return fileUpload.getTempToken();
  }

  public void cleanup() {
    fileUpload.cleanup();
  }
}