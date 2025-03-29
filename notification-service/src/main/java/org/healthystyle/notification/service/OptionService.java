package org.healthystyle.notification.service;

import java.util.List;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.Option;
import org.healthystyle.notification.service.dto.OptionSaveRequest;
import org.healthystyle.util.error.ValidationException;

public interface OptionService {
	List<Option> findByNotification(Long notificationId) throws ValidationException;

	Option save(OptionSaveRequest saveRequest, Notification notification) throws ValidationException;
}
