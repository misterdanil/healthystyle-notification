package org.healthystyle.notification.service.dto;

import jakarta.validation.constraints.NotBlank;

public class OptionSaveRequest {
	@NotBlank(message = "Укажите название опции")
	private String title;
	@NotBlank(message = "Укажите ссылку")
	private String link;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
