package ch.verno.ui.base.file.pdf;

import ch.verno.ui.base.file.AbstractFilePreview;
import ch.verno.ui.base.file.FileType;
import jakarta.annotation.Nonnull;

public class PdfPreview extends AbstractFilePreview {

  public PdfPreview(@Nonnull final String src) {
    super(FileType.PDF);
    init();
    setSrc(src);
  }
}
