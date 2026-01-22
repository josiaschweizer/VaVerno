package ch.verno.ui.base.components.notification;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.Nonnull;

@CssImport("./components/notification/va-notification.css")
public class NotificationFactory {

  public static final Notification.Position NOTIFICATION_POSITION = Notification.Position.BOTTOM_END;
  private static final int DEFAULT_DURATION = 4000;

  public static void showWarningNotification(@Nonnull final String message) {
    showNotification(message, VaadinIcon.WARNING, NotificationVariant.LUMO_WARNING, "warning");
  }

  public static void showSuccessNotification(@Nonnull final String message) {
    showNotification(message, VaadinIcon.CHECK_CIRCLE, NotificationVariant.LUMO_SUCCESS, "success");
  }

  public static void showErrorNotification(@Nonnull final String message) {
    showNotification(message, VaadinIcon.CLOSE_CIRCLE, NotificationVariant.LUMO_ERROR, "error");
  }

  public static void showInfoNotification(@Nonnull final String message) {
    showNotification(message, VaadinIcon.INFO_CIRCLE, NotificationVariant.LUMO_CONTRAST, "info");
  }

  private static void showNotification(@Nonnull final String message,
                                       @Nonnull final VaadinIcon iconType,
                                       @Nonnull final NotificationVariant variant,
                                       @Nonnull final String type) {
    final var notification = new Notification();
    notification.addThemeVariants(variant);
    notification.setDuration(DEFAULT_DURATION);
    notification.setPosition(NOTIFICATION_POSITION);

    final var iconContainer = new Div();
    iconContainer.addClassName("notification-icon-container");
    iconContainer.addClassName("notification-icon-" + type);

    final var icon = iconType.create();
    icon.setSize("24px");
    icon.addClassName("notification-icon");
    iconContainer.add(icon);

    final var messageSpan = new Span(message);
    messageSpan.addClassName("notification-message");

    final var closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_CONTRAST);
    closeButton.addClassName("notification-close-btn");
    closeButton.getElement().setAttribute("aria-label", "Close");
    closeButton.addClickListener(e -> {
      notification.close();
    });
    closeButton.getStyle()
            .set("color", "white")
            .set("cursor", "pointer");

    final var contentWrapper = new Div();
    contentWrapper.addClassName("notification-content-wrapper");
    contentWrapper.add(messageSpan);

    final var layout = new HorizontalLayout(iconContainer, contentWrapper, closeButton);
    layout.setSpacing(false);
    layout.setAlignItems(FlexComponent.Alignment.CENTER);
    layout.addClassName("notification-layout");
    layout.addClassName("notification-" + type);

    notification.add(layout);
    notification.open();
  }
}