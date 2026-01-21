package ch.verno.common.lib.i18n;

import com.vaadin.flow.component.UI;
import jakarta.annotation.Nonnull;

public abstract class AbstractTranslationHelper {

  @Nonnull
  public String getTranslation(@Nonnull final String key) {
    return TranslationHelper.getTranslation(key, UI.getCurrent().getLocale());
  }

}
