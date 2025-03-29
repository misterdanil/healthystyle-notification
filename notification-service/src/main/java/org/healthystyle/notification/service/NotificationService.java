package org.healthystyle.notification.service;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.service.dto.EventDto;
import org.healthystyle.notification.service.dto.NotificationSaveRequest;
import org.healthystyle.notification.service.error.user.UserNotFoundException;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.log.MethodNameHelper;
import org.springframework.data.domain.Page;

public interface NotificationService {
	static final String[] FIND_BY_TO_USER_ID_PARAM_NAMES = MethodNameHelper
			.getMethodParamNames(NotificationService.class, "findByToUserId", Long.class, int.class, int.class);

	Page<Notification> findByToUserId(Long userId, int page, int limit) throws ValidationException;

	Integer countUnwatched(Long toUserId) throws ValidationException;

	Notification save(NotificationSaveRequest saveRequest) throws ValidationException, UserNotFoundException;

	void save(EventDto event) throws ValidationException, UserNotFoundException;

}
