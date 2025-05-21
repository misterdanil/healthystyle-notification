package org.healthystyle.notification.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.Option;
import org.healthystyle.notification.repository.NotificationRepository;
import org.healthystyle.notification.service.NotificationService;
import org.healthystyle.notification.service.OptionService;
import org.healthystyle.notification.service.cache.UserService;
import org.healthystyle.notification.service.dto.EventDto;
import org.healthystyle.notification.service.dto.NotificationSaveRequest;
import org.healthystyle.notification.service.dto.OptionSaveRequest;
import org.healthystyle.notification.service.dto.NotificationUpdateStatusRequest;
import org.healthystyle.notification.service.dto.UserEventDto;
import org.healthystyle.notification.service.error.IdentifierExistException;
import org.healthystyle.notification.service.error.user.UserNotFoundException;
import org.healthystyle.notification.service.status.StatusService;
import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.healthystyle.util.error.ValidationException;
import org.healthystyle.util.log.LogTemplate;
import org.healthystyle.util.user.User;
import org.healthystyle.util.user.UserAccessor;
import org.healthystyle.util.validation.ParamsChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationRepository repository;
	@Autowired
	private Validator validator;
//	@Autowired
//	private UserService userService;
	@Autowired
	private StatusService statusService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private UserAccessor userAccessor;

	private static final Integer MAX_SIZE = 50;

	private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public Page<Notification> findByAuthenticatedUser(int page, int limit) throws ValidationException {
		User user = userAccessor.getUser();

		return findByToUserId(user.getId(), page, limit);
	}

	@Override
	public Page<Notification> findByToUserId(Long userId, int page, int limit) throws ValidationException {
		String params = LogTemplate.getParamsTemplate(FIND_BY_TO_USER_ID_PARAM_NAMES, userId, page, limit);

		BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "notification");
		LOG.debug("Validating params: {}", params);
		if (userId == null) {
			result.reject("notification.find.to_user_id.not_null",
					"Укажите пользователя для отображения его уведомлений");
		}
		ParamsChecker.checkPageNumber(page, result);
		ParamsChecker.checkLimit(limit, MAX_SIZE, result);
		if (result.hasErrors()) {
			throw new ValidationException("The params are invalid: %s. Result: %s", result, params, result);
		}

		LOG.debug("The params are OK: {}", params);

		Page<Notification> notifications = repository.findByToUserId(userId, PageRequest.of(page, limit));
		LOG.info("Got notifications successfully by params: {}", params);

		return notifications;

	}

	@Override
	public Integer countUnwatched(Long toUserId) throws ValidationException {
		LOG.debug("Checking to user id for not null: {}", toUserId);
		if (toUserId == null) {
			BindingResult result = new MapBindingResult(new HashMap<>(), "notification");
			result.reject("notification.count.user_id.not_null", "Укажите идентификатор пользователя для подсчёта");
			throw new ValidationException("User id is null", result);
		}

		Integer count = repository.countUnwatched(toUserId);
		LOG.info("Got user's notifications count by his id '{}'", toUserId);

		return count;
	}

	@Override
	public Notification save(NotificationSaveRequest saveRequest)
			throws ValidationException, UserNotFoundException, IdentifierExistException {
		LOG.debug("Validating notification: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "notificaiton");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The notification is invalid: %s. Result: %s", result, saveRequest, result);
		}

		String type = saveRequest.getType();
		LOG.debug("Checking type for not blank: {}", type);
		if (type != null && type.isBlank()) {
			type = null;
		}

		Long fromUserId = saveRequest.getFromUserId();
		LOG.debug("Checking from user id existence: {}", fromUserId);
//		if (!userService.existsById(fromUserId)) {
//			result.reject("notification.save.from_user_id.not_exists", "Не существует пользователя отправителя");
//			throw new UserNotFoundException(fromUserId, result);
//		}

		Long toUserId = saveRequest.getToUserId();
		LOG.debug("Checking to user id existence: {}", toUserId);
//		if (!userService.existsById(toUserId)) {
//			result.reject("notification.save.to_user_id.not_exists", "Не существует пользователя получателя");
//			throw new UserNotFoundException(toUserId, result);
//		}
		String identifier = saveRequest.getIdentifier();
		if (repository.existsByIdentifier(identifier)) {
			throw new IdentifierExistException(result, identifier);
		}

		Status status = statusService.findByType(Type.UNWATCHED);

		Notification notification = new Notification(saveRequest.getTitle(), type, fromUserId, toUserId, status,
				saveRequest.getIdentifier());
		notification = repository.save(notification);
		LOG.info("The notification was saved successfully: {}", saveRequest);

		List<OptionSaveRequest> optionSaveRequests = saveRequest.getOptions();
		LOG.debug("Saving options: {}", optionSaveRequests);
		if (optionSaveRequests != null) {
			for (OptionSaveRequest optionSaveRequest : optionSaveRequests) {
				LOG.debug("Saving option: {}", optionSaveRequest);
				Option option = optionService.save(optionSaveRequest, notification);
				notification.addOption(option);
			}
		}

		return notification;
	}

	@Override
	@Transactional
	@Modifying
	public void updateStatus(NotificationUpdateStatusRequest updateStatusRequest) throws ValidationException {
		BindingResult result = new BeanPropertyBindingResult(updateStatusRequest, "notification");
		validator.validate(updateStatusRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("Notification update status request is invalid: %s. Result: %s", result,
					updateStatusRequest, result);
		}

		Type type = updateStatusRequest.getType();
		Status status = statusService.findByType(type);

		List<Long> ids = updateStatusRequest.getIds();

		repository.updateStatus(ids, status, userAccessor.getUser().getId());
	}

//	@Override
//	public void save(EventDto event) throws ValidationException, UserNotFoundException {
//		List<UserEventDto> users = event.getUsers();
//		for (UserEventDto user : users) {
//			NotificationSaveRequest saveRequest = new NotificationSaveRequest();
//			saveRequest.setTitle("Вас пригласили на мероприятие: " + event.getTitle());
//			saveRequest.setToUserId(user.getUserId());
//			saveRequest.setType("event");
//			save(saveRequest);
//		}
//	}

}
