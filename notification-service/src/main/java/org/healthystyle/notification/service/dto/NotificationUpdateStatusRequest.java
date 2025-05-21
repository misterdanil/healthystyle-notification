package org.healthystyle.notification.service.dto;

import java.util.List;

import org.healthystyle.notification.status.Type;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NotificationUpdateStatusRequest {
	@NotEmpty(message = "Укажите идентификаторы уведомлений")
	private List<Long> ids;
	@NotNull(message = "Укажите тип")
	private Type type;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
