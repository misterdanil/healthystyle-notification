package org.healthystyle.notification.service.dto;

import java.util.List;

public class EventDto {
	private String title;
	private List<UserEventDto> users;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserEventDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserEventDto> users) {
		this.users = users;
	}

}
