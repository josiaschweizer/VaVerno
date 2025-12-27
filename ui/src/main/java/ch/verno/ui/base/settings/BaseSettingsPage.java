package ch.verno.ui.base.settings;

import ch.verno.ui.base.components.toolbar.ViewToolbarFactory;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.Nonnull;

public abstract class BaseSettingsPage extends VerticalLayout {

  public BaseSettingsPage() {
  }

  protected void init() {
    setSizeFull();
    setPadding(false);
    setSpacing(false);

    add(ViewToolbarFactory.createSimpleToolbar(getSettingsPageName()));

    final var setting = createSetting();

    add(setting);
  }

  @Nonnull
  protected abstract BaseSetting createSetting();

  @Nonnull
  protected abstract String getSettingsPageName();
}
