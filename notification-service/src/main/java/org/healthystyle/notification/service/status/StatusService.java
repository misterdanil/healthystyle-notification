package org.healthystyle.notification.service.status;

import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;

public interface StatusService {
	Status findByType(Type type);
}
