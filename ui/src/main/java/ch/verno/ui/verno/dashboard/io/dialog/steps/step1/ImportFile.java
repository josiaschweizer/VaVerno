package ch.verno.ui.verno.dashboard.io.dialog.steps.step1;

import ch.verno.common.file.FileServerGate;
import ch.verno.common.gate.VernoApplicationGate;
import ch.verno.ui.base.components.upload.VAFileUpload;
import ch.verno.ui.base.dialog.stepdialog.BaseDialogStep;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class ImportFile extends BaseDialogStep {

  private final VAFileUpload fileUpload;

  @Nullable
  private Runnable onFileUploadedListener;

  public ImportFile(@Nonnull final VernoApplicationGate vernoApplicationGate) {
    setSizeFull();
    setPadding(false);
    setSpacing(false);

    fileUpload = new VAFileUpload(vernoApplicationGate.getService(FileServerGate.class));
    fileUpload.setAcceptedFileTypes(".csv");
    fileUpload.setMaxFileUpload(1);

    fileUpload.getUpload().addAllFinishedListener(event -> {
      if (hasFile() && onFileUploadedListener != null) {
        onFileUploadedListener.run();
      }
    });

    add(fileUpload);
    expand(fileUpload);
  }

  public void setOnFileUploadedListener(@Nullable final Runnable listener) {
    this.onFileUploadedListener = listener;
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

  @Override
  public boolean isValid() {
    return hasFile();
  }

  @Override
  public void onBecomeVisible() {
    fileUpload.refreshUI();
  }
}