package org.healthystyle.notification.service.cache;

import java.util.List;
import java.util.Set;

import org.healthystyle.notification.service.cache.dto.User;
import org.healthystyle.util.error.ValidationException;

public interface UserService {
	List<User> findAllById(Set<Long> ids);

	User save(User user) throws ValidationException;

	boolean existsById(Long id);
}
