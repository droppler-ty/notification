package com.droppler.notification.controller;

import com.droppler.notification.converter.NotificationConverter;
import com.droppler.notification.dto.NotificationDTO;
import com.droppler.notification.base.ApiResponse;
import com.droppler.notification.base.ApiResponse.ApiResponseBuilderWithData;
import com.droppler.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {

    private NotificationService notificationServiceImp;
    private NotificationConverter notificationConverter;

    public NotificationController(
            NotificationService notificationService,
            NotificationConverter notificationConverter){
        this.notificationServiceImp = notificationService;
        this.notificationConverter = notificationConverter;
    }

    @RequestMapping(value = "{customerId}/{pageNumber}/{pageSize}", method = RequestMethod.GET)
    public ApiResponse GetNotificationsByCustomerId(@PathVariable long customerId, @PathVariable int pageNumber, @PathVariable int pageSize){
        var notifications = notificationServiceImp.getNotificationsBy(customerId, pageNumber, pageSize);
        var notificationDTOS = notifications.stream()
                .map(notification -> notificationConverter.convert(notification))
                .toList();
        var response = ApiResponseBuilderWithData
                .<List<NotificationDTO>>builder()
                .setData(notificationDTOS)
                .setStatusCode(200)
                .setSuccess(true)
                .build();
        return response;
    }
}
