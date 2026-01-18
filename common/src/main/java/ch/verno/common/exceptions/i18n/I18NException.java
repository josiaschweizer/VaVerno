package ch.verno.common.exceptions.i18n;

import jakarta.annotation.Nonnull;

public class I18NException extends IllegalArgumentException {

  public I18NException() {
    super();
  }

  public I18NException(@Nonnull final String message) {
    super(message);
  }

}
