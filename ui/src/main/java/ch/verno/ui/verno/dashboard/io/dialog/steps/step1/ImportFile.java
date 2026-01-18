package ch.verno.ui.verno.dashboard.io.dialog.steps.step1;

import ch.verno.common.file.FileServerGate;
import ch.verno.common.gate.VernoServerGate;
import ch.verno.ui.base.components.upload.VAFileUpload;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public class ImportFile extends VerticalLayout {

  private final VAFileUpload fileUpload;

  public ImportFile(@Nonnull final VernoServerGate vernoServerGate) {
    setSizeFull();
    setPadding(false);
    setSpacing(false);

    fileUpload = new VAFileUpload(vernoServerGate.getService(FileServerGate.class));
    fileUpload.setAcceptedFileTypes(".csv");
    fileUpload.setMaxFileUpload(1);

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