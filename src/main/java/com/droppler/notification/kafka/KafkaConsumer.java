package com.droppler.notification.kafka;

import com.droppler.notification.domain.NotificationType;
import com.droppler.notification.dto.ChangeProductPriceDto;
import com.droppler.notification.dto.NotificationDTO;
import com.droppler.notification.dto.UserDto;
import com.droppler.notification.service.EmailService;
import com.droppler.notification.service.NotificationService;
import com.droppler.notification.utils.MessageFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.logging.Logger;

@Component
public class KafkaConsumer {

  NotificationService notificationService;
  EmailService emailService;
  private final static Logger log = Logger.getLogger("Logger");


  @Autowired
  public KafkaConsumer(NotificationService notificationService, EmailService emailService) {
    this.notificationService = notificationService;
    this.emailService = emailService;
  }

  @KafkaListener(topics = "ChangeProductPrice", groupId = "droppler",
          containerFactory = "concurrentKafkaListenerContainerFactory")
  public void consume(@Payload ChangeProductPriceDto topic) {

    log.info(topic.toString());

    Consumer<UserDto> notify = user -> notificationService.insert(
            new NotificationDTO(
                    MessageFormatterUtil.getNotificationMessage(topic),
                    MessageFormatterUtil.getNotificationSubject(topic),
                    NotificationType.Email),
            user.getId());

    Consumer<UserDto> sendEmail = user -> emailService.Send(
            MessageFormatterUtil.getNotificationSubject(topic),
            MessageFormatterUtil.getNotificationMessage(topic),
            user.getEmail());

    topic.getUserList().forEach(notify.andThen(sendEmail));
  }
}
