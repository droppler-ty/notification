package com.droppler.notification.dto;

import com.droppler.notification.domain.NotificationType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String id;
    private String message;
    private String subject;
    private NotificationType type;

  public NotificationDTO(String message, String subject, NotificationType type) {
    this.message = message;
    this.subject = subject;
    this.type = type;
  }
}
