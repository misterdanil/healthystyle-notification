package org.healthystyle.notification.service.status.impl;

import org.healthystyle.notification.repository.status.StatusRepository;
import org.healthystyle.notification.service.status.StatusService;
import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class StatusServiceImpl implements StatusService {
	@Autowired
	private StatusRepository repository;

	private static final Logger LOG = LoggerFactory.getLogger(StatusServiceImpl.class);

	@Override
	public Status findByType(Type type) {
		Assert.notNull(type, "Укажите тип статуса для поиска");

		Status status = repository.findByType(type);
		LOG.info("Got status successfully by type '{}'", type);
		return status;
	}

}
