package org.healthystyle.notification.service.error;

import org.healthystyle.util.error.AbstractException;
import org.springframework.validation.BindingResult;

public class IdentifierExistException extends AbstractException {
	private String identifier;

	public IdentifierExistException(BindingResult result, String identifier) {
		super(result, "A notification with identifier '%s' has already existed", identifier);
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}
