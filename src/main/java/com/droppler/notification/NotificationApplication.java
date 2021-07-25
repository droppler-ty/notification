package com.droppler.notification;

import com.droppler.notification.domain.NotificationType;
import com.droppler.notification.dto.NotificationDTO;
import com.droppler.notification.kafka.KafkaConsumer;
import com.droppler.notification.service.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner firstRun(NotificationService notificationService){
//		return args -> {
//			for(var i=0; i<1000;i++){
//				var notification = new NotificationDTO();
//				notification.setMessage("heyo naber 1 2 ");
//				notification.setSubject("Konu başlığı");
//				var userId = i > 100 ? i/100 : i == 0 ? 1 : i;
//				notification.setType(NotificationType.PushNotification);
//				notificationService.insert(notification, userId);
//			}
//		};
//	}
}
