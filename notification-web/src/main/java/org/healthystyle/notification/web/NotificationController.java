package org.healthystyle.notification.web;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.service.NotificationService;
import org.healthystyle.notification.service.dto.NotificationSaveRequest;
import org.healthystyle.notification.service.dto.NotificationUpdateStatusRequest;
import org.healthystyle.notification.service.error.IdentifierExistException;
import org.healthystyle.notification.service.error.user.UserNotFoundException;
import org.healthystyle.notification.status.Type;
import org.healthystyle.notification.web.config.RabbitConfig;
import org.healthystyle.notification.web.dto.mapper.NotificationMapper;
import org.healthystyle.util.error.ErrorResponse;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.user.UserAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private NotificationMapper mapper;
	@Autowired
	private UserAccessor userAccessor;

	private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

	@RabbitListener(queues = RabbitConfig.NOTIFICATION_SAVE_QUEUE)
	public void add(NotificationSaveRequest saveRequest) throws ValidationException, UserNotFoundException {
		LOG.debug("Got notification: {}", saveRequest);
		try {
			notificationService.save(saveRequest);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IdentifierExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/notifications")
	public ResponseEntity<?> getNotifications(@RequestParam int page, @RequestParam int limit) {
		Page<Notification> notifications;
		try {
			notifications = notificationService.findByAuthenticatedUser(page, limit);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(notifications.map(mapper::toDto));
	}

	@GetMapping(value = "/notifications/count", params = "status")
	public ResponseEntity<?> getCountNotifications(Type type) {
		Integer count;
		try {
			count = notificationService.countUnwatched(userAccessor.getUser().getId());
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.ok(count);
	}

	@PutMapping("/notifications")
	public ResponseEntity<?> setStatus(@RequestBody NotificationUpdateStatusRequest updateStatusRequest) {
		try {
			notificationService.updateStatus(updateStatusRequest);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse("1000", e.getGlobalErrors(), e.getFieldErrors()));
		}

		return ResponseEntity.noContent().build();
	}
}
