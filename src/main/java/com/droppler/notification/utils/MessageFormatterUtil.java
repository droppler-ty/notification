package com.droppler.notification.utils;

import com.droppler.notification.dto.ChangeProductPriceDto;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageFormatterUtil {
  public final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag("en-EN"));

  public static String getNotificationMessage(ChangeProductPriceDto topic) {
    String notificationTitle = bundle.getString("notification_title");
    String notificationMessage = bundle.getString("notification_message");

    MessageFormat formatter = new MessageFormat(notificationMessage, Locale.forLanguageTag("en-EN"));
    notificationMessage = formatter.format(new Object[] {topic.getProductId(), topic.getNewPrice(), topic.getOldPrice()});

    StringBuilder message = new StringBuilder(notificationTitle);
    message.append(notificationMessage);

    return message.toString();
  }

  public static String getNotificationSubject(ChangeProductPriceDto topic) {
    return bundle.getString("notification_subject");
  }
}
