package com.droppler.notification.service;

import com.droppler.notification.domain.*;
import com.droppler.notification.dto.NotificationDTO;
import com.droppler.notification.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private NotificationsByCustomerIdRepository notificationsByCustomerIdRepository;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            NotificationsByCustomerIdRepository notificationsByCustomerIdRepository){
        this.notificationRepository = notificationRepository;
        this.notificationsByCustomerIdRepository = notificationsByCustomerIdRepository;
    }

    @Override
    public List<Notification> getNotificationsBy(long customerId, int pageNumber, int pageSize) {
        if(customerId == 0L){
            throw new BadRequestException("Customer id cannot be default");
        }
        var notificationsByCustomerId = notificationsByCustomerIdRepository.findById(customerId);
        if(!notificationsByCustomerId.isPresent()){
            return new ArrayList<>();
        }
        if(pageNumber <= 0){
            throw  new BadRequestException("Page number must be greater than 0");
        }
        if(pageSize <= 0){
            throw  new BadRequestException("Page size must be greater than 0");
        }
        var notifications = notificationsByCustomerId.get().getNotificationIds().stream()
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .map(id -> notificationRepository.findById(id))
                .toList();
        return notifications.stream()
                .filter(Optional::isPresent)
                .filter(optNotification -> optNotification.get().getType() == NotificationType.PushNotification)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Notification insert(NotificationDTO notificationDTO, long customerId) {
        if(notificationDTO == null){
            throw new BadRequestException("Notification cannot be null");
        }
        if(notificationDTO.getMessage() == null){
            throw new BadRequestException("Message cannot be null");
        }
        if(customerId == 0L){
            throw new BadRequestException("Customer id cannot be default");
        }
        if(notificationDTO.getType() == null){
            throw new BadRequestException("Notification type must be set");
        }
        var notification = new Notification();
        notification.setType(notificationDTO.getType());
        notification.setSubject(notificationDTO.getSubject());
        notification.setMessage(notificationDTO.getMessage());
        var insertedNotification = notificationRepository.insert(notification);
        var notificationsByCustomerId = notificationsByCustomerIdRepository.findById(customerId);
        if(notificationsByCustomerId.isPresent()){
            SetNotificationsByCustomerId(notificationsByCustomerId.get(), insertedNotification.getId());
        }
        else{
            CreateNotificationsByCustomerId(customerId, insertedNotification.getId());
        }
        return insertedNotification;
    }

    private void SetNotificationsByCustomerId(NotificationsByCustomerId notificationsByCustomerId, String id) {
        notificationsByCustomerId.getNotificationIds().add(id);
        notificationsByCustomerIdRepository.insert(notificationsByCustomerId);
    }

    private void CreateNotificationsByCustomerId(long customerId, String id) {
        var notificationByCustomerId = new NotificationsByCustomerId();
        notificationByCustomerId.setCustomerId(customerId);
        var notificationIds = new HashSet<String>();
        notificationIds.add(id);
        notificationByCustomerId.setNotificationIds(notificationIds);
        notificationsByCustomerIdRepository.insert(notificationByCustomerId);
    }

}
