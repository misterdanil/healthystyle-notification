package org.healthystyle.notification.web.dto.mapper;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.healthystyle.notification.web.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class NotificationMapper {

	@Mapping(source = "status", target = "status", qualifiedByName = "getStatusType")
	public abstract NotificationDto toDto(Notification notification);

	@Named("getStatusType")
	Type getStatusType(Status status) {
		return status.getType();
	}
}
