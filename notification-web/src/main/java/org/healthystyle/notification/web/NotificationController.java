package org.healthystyle.notification.web;

import org.healthystyle.notification.service.NotificationService;
import org.healthystyle.notification.service.dto.EventDto;
import org.healthystyle.notification.service.error.user.UserNotFoundException;
import org.healthystyle.notification.web.config.RabbitConfig;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

	@RabbitListener(queues = RabbitConfig.EVENT_CREATED_QUEUE)
	public void getEvent(EventDto event) throws ValidationException, UserNotFoundException {
		LOG.debug("Got notification: {}", event);
		notificationService.save(event);
	}
}
