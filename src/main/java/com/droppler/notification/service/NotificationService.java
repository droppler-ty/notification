package com.droppler.notification.service;

import com.droppler.notification.domain.Notification;
import com.droppler.notification.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    List<Notification> getNotificationsBy(long customerId, int pageNumber, int pageSize);

    Notification insert(NotificationDTO notificationDTO, long customerId);
}
