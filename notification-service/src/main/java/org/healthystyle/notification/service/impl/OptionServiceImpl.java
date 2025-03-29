package org.healthystyle.notification.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.Option;
import org.healthystyle.notification.repository.OptionRepository;
import org.healthystyle.notification.service.OptionService;
import org.healthystyle.notification.service.dto.OptionSaveRequest;
import org.healthystyle.util.error.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

@Service
public class OptionServiceImpl implements OptionService {
	@Autowired
	private OptionRepository repository;
	@Autowired
	private Validator validator;

	private static final Logger LOG = LoggerFactory.getLogger(OptionServiceImpl.class);

	@Override
	public List<Option> findByNotification(Long notificationId) throws ValidationException {
		LOG.debug("Checking notification id for not null: {}", notificationId);
		if (notificationId == null) {
			BindingResult result = new MapBindingResult(new LinkedHashMap<>(), "option");
			result.reject("option.find.notification_id.not_null",
					"Укажите идентификатор уведомления для поиска его опций");
			throw new ValidationException("The notification id is null", result);
		}

		List<Option> options = repository.findByNotification(notificationId);
		LOG.info("Got options successfully by notification id '{}'", notificationId);

		return options;
	}

	@Override
	public Option save(OptionSaveRequest saveRequest, Notification notification) throws ValidationException {
		Assert.notNull(notification, "Укажите уведомление для сохранения опции");

		LOG.debug("Validating option: {}", saveRequest);
		BindingResult result = new BeanPropertyBindingResult(saveRequest, "option");
		validator.validate(saveRequest, result);
		if (result.hasErrors()) {
			throw new ValidationException("The option is invalid: %s. Result: %s", result, saveRequest, result);
		}

		String title = saveRequest.getTitle();
		String link = saveRequest.getLink();

		Option option = new Option(title, link, notification);
		option = repository.save(option);
		LOG.info("Option was saved successfully: {}", option);

		return option;
	}

}
