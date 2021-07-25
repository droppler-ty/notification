package com.droppler.notification;

import com.droppler.notification.domain.*;
import com.droppler.notification.dto.NotificationDTO;
import com.droppler.notification.exception.BadRequestException;
import com.droppler.notification.service.NotificationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationsByCustomerIdRepository notificationsByCustomerIdRepository;

    @Test
    public void whenNotificationDTOisNulForInsert_ThenThrowBadRequestException(){
        Assertions.assertThrows(BadRequestException.class, () -> {
           notificationService.insert(null, 15L);
        });
    }

    @Test
    public void whenMessageIsNull_ThenThrowBadRequestException(){
        Assertions.assertThrows(BadRequestException.class, () -> {
            var notificationDTO = new NotificationDTO();
            notificationDTO.setType(NotificationType.PushNotification);
            notificationDTO.setSubject("Hello world!");
            notificationService.insert(notificationDTO, 15L);
        });
    }

    @Test
    public void whenCustomerIdIsDefault_ThenThrowBadRequestException(){
        Assertions.assertThrows(BadRequestException.class, () -> {
            var notificationDTO = new NotificationDTO();
            notificationDTO.setType(NotificationType.PushNotification);
            notificationDTO.setSubject("Hello world!");
            notificationDTO.setMessage("Hello");
            notificationService.insert(notificationDTO, 0L);
        });
    }

    @Test
    public void whenTypeIsNull_ThenThrowBadRequestException(){
        Assertions.assertThrows(BadRequestException.class, () -> {
            var notificationDTO = new NotificationDTO();
            notificationDTO.setSubject("Hello world!");
            notificationDTO.setMessage("Hello");
            notificationService.insert(notificationDTO, 15L);
        });
    }

    @Test
    public void testInsert(){
        var notificationDTO = new NotificationDTO();
        notificationDTO.setMessage("Hello world!");
        notificationDTO.setSubject("Hello");
        notificationDTO.setType(NotificationType.PushNotification);
        var id = UUID.randomUUID().toString();
        Notification notificationMock = Mockito.mock(Notification.class);
        Mockito.when(notificationMock.getId()).thenReturn(id);
        Mockito.when(notificationMock.getMessage()).thenReturn(notificationDTO.getMessage());
        Mockito.when(notificationMock.getType()).thenReturn(notificationDTO.getType());
        Mockito.when(notificationMock.getSubject()).thenReturn(notificationDTO.getSubject());
        Mockito.when(notificationRepository.insert(ArgumentMatchers.any(Notification.class))).thenReturn(notificationMock);
        var notification = notificationService.insert(notificationDTO, 15L);
        Assertions.assertEquals(notification.getId(), id);
        Assertions.assertEquals(notification.getMessage(), notificationDTO.getMessage());
        Assertions.assertEquals(notification.getType(), notificationDTO.getType());
        Assertions.assertEquals(notification.getSubject(), notificationDTO.getSubject());
    }

    @Test
    public void whenCustomerIdIsDefaultForGetNotifications_ThenThrowBadRequestException(){
        Assertions.assertThrows(BadRequestException.class, () -> {
            notificationService.getNotificationsBy(0L,1,10);
        });
    }

    @Test
    public void whenNotFoundAnyNotification_ThenReturnEmptyList(){
        Mockito.when(notificationsByCustomerIdRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());
        var result = notificationService.getNotificationsBy(12L, 1, 10);
        Assertions.assertEquals(result.size(), 0);
    }

    @Test
    public void whenPageNumberLessThanZero_ThenThrowBadRequestException(){
        Mockito.when(notificationsByCustomerIdRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(new NotificationsByCustomerId()));
        Assertions.assertThrows(BadRequestException.class, () -> {
            notificationService.getNotificationsBy(15L,-1,10);
        });
    }

    @Test
    public void whenPageNumberEqualZero_ThenThrowBadRequestException(){
        Mockito.when(notificationsByCustomerIdRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(new NotificationsByCustomerId()));
        Assertions.assertThrows(BadRequestException.class, () -> {
            notificationService.getNotificationsBy(15L,0,10);
        });
    }

    @Test
    public void whenPageSizeLessThanZero_ThenThrowBadRequestException(){
        Mockito.when(notificationsByCustomerIdRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(new NotificationsByCustomerId()));
        Assertions.assertThrows(BadRequestException.class, () -> {
            notificationService.getNotificationsBy(15L,1,-6);
        });
    }

    @Test
    public void whenPageSizeEqualZero_ThenThrowBadRequestException(){
        Mockito.when(notificationsByCustomerIdRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(new NotificationsByCustomerId()));
        Assertions.assertThrows(BadRequestException.class, () -> {
            notificationService.getNotificationsBy(15L,1,0);
        });
    }
}
