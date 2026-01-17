package ch.verno.ui.base.components.upload;

import ch.verno.common.file.FileServerGate;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@CssImport("./components/upload/va-file-upload.css")
public class VAFileUpload extends VerticalLayout {

  @Nonnull
  private final FileServerGate fileServerGate;

  @Nullable private String tempToken;
  @Nullable private String originalFileName;
  private long sizeBytes;

  @Nonnull private final Div dropArea;
  @Nonnull private final Upload upload;

  public VAFileUpload(@Nonnull final FileServerGate fileServerGate) {
    this.fileServerGate = fileServerGate;

    setSizeFull();
    setPadding(false);
    setSpacing(false);

    dropArea = new Div();
    dropArea.setSizeFull();
    dropArea.addClassName("va-file-upload__drop");
    dropArea.add(
            new Span("Datei hierher ziehen"),
            new Span("oder klicken, um eine Datei auszuwählen")
    );

    upload = new Upload();
    upload.addClassName("va-file-upload");
    upload.setSizeFull();
    upload.setAutoUpload(true);
    upload.setDropAllowed(true);
    upload.setMaxFiles(1);
    upload.getElement().setProperty("nodrop", false);

    // Verstecke die Dateiliste komplett
    upload.getElement().executeJs(
            "this.addEventListener('upload-success', () => {" +
            "  const fileList = this.shadowRoot.querySelector('[part=\"file-list\"]');" +
            "  if (fileList) fileList.style.display = 'none';" +
            "});"
    );

    upload.setUploadButton(new Span());
    upload.setDropLabel(dropArea);
    upload.setDropLabelIcon(new Span());

    upload.setUploadHandler(event -> {
      deleteTempIfPresent();
      originalFileName = event.getFileName();
      final byte[] bytes = readAllBytes(event.getInputStream());
      sizeBytes = bytes.length;
      tempToken = fileServerGate.store(sanitizeFileName(originalFileName), bytes);
    });

    upload.addAllFinishedListener(e -> {
      if (tempToken != null && originalFileName != null) {
        dropArea.removeAll();
        dropArea.add(
                new Span("Datei ausgewählt"),
                new Div(new Text(originalFileName + " (" + sizeBytes + " bytes)")),
                new Span("Klicken oder neue Datei ziehen zum Ersetzen")
        );
      }
    });

    dropArea.getElement().executeJs(
            "const dropArea = this;" +
            "dropArea.addEventListener('click', (e) => {" +
            "  e.stopPropagation();" +
            "  const uploadEl = dropArea.closest('vaadin-upload');" +
            "  if (uploadEl && uploadEl.shadowRoot) {" +
            "    const fileInput = uploadEl.shadowRoot.querySelector('input[type=\"file\"]');" +
            "    if (fileInput) {" +
            "      fileInput.click();" +
            "    }" +
            "  }" +
            "});"
    );

    add(upload);
    expand(upload);
  }

  public void setAcceptedFileTypes(@Nonnull final String... acceptedFileTypes) {
    upload.setAcceptedFileTypes(acceptedFileTypes);
  }

  public void setMaxFiles(final int maxFiles) {
    upload.setMaxFiles(maxFiles);
  }

  @Nonnull
  public Upload getUpload() {
    return upload;
  }

  public boolean hasFile() {
    return tempToken != null && !tempToken.isBlank();
  }

  @Nullable
  public String getTempToken() {
    return tempToken;
  }

  @Nullable
  public String getOriginalFileName() {
    return originalFileName;
  }

  public long getSizeBytes() {
    return sizeBytes;
  }

  public void cleanup() {
    deleteTempIfPresent();
    resetUI();
  }

  private void resetUI() {
    tempToken = null;
    originalFileName = null;
    sizeBytes = 0;
    dropArea.removeAll();
    dropArea.add(
            new Span("Datei hierher ziehen"),
            new Span("oder klicken, um eine Datei auszuwählen")
    );
  }

  private void deleteTempIfPresent() {
    if (tempToken == null) {
      return;
    }
    try {
      fileServerGate.delete(tempToken);
    } catch (Exception ignored) {
    } finally {
      tempToken = null;
    }
  }

  private byte[] readAllBytes(@Nonnull final InputStream in) {
    try (in; var out = new ByteArrayOutputStream()) {
      in.transferTo(out);
      return out.toByteArray();
    } catch (Exception e) {
      throw new IllegalStateException("Upload failed", e);
    }
  }

  private String sanitizeFileName(@Nullable final String name) {
    if (name == null || name.isBlank()) {
      return "upload.bin";
    }
    return name.replaceAll("[^a-zA-Z0-9._-]", "_");
  }
}