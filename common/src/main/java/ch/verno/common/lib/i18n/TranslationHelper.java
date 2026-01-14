package ch.verno.common.lib.i18n;

import ch.verno.common.exceptions.i18n.I18NException;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.server.VaadinService;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public class TranslationHelper {

  @Nonnull
  public static String getTranslation(@Nonnull final String key,
                                      @Nonnull final Locale locale) {
    return getI18NProvider().getTranslation(key, locale);
  }

  @Nonnull
  public static I18NProvider getI18NProvider() {
    final var service = VaadinService.getCurrent();
    if (service != null && service.getInstantiator() != null) {
      return service.getInstantiator().getI18NProvider();
    }

    throw new I18NException("No I18NProvider available in the current VaadinService.");
  }

}
