package org.healthystyle.notification.service.error.user;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class UserNotFoundException extends AbstractException {
	private Long id;

	public UserNotFoundException(Long id, BindingResult result) {
		super(result, "Could not find user by id '%s'", id);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
