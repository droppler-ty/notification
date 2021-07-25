package com.droppler.notification.converter;

import com.droppler.notification.domain.Notification;
import com.droppler.notification.dto.NotificationDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter implements Converter<Notification, NotificationDTO> {
    @Override
    public NotificationDTO convert(Notification notification) {
        var notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setSubject(notification.getSubject());
        notificationDTO.setType(notification.getType());
        return notificationDTO;
    }
}
