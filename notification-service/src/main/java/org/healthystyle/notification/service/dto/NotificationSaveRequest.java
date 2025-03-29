package org.healthystyle.notification.service.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NotificationSaveRequest {
	@NotBlank(message = "Укажите текст уведомления")
	private String title;
	@NotBlank(message = "Укажите тип уведомления")
	private String type;
	@NotNull(message = "Укажите пользователя-источника уведомления")
	private Long fromUserId;
	@NotNull(message = "Укажите пользователя-цель уведомления")
	private Long toUserId;
	@NotEmpty(message = "Укажите опции")
	private List<OptionSaveRequest> options;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public List<OptionSaveRequest> getOptions() {
		return options;
	}

	public void setOptions(List<OptionSaveRequest> options) {
		this.options = options;
	}

}
