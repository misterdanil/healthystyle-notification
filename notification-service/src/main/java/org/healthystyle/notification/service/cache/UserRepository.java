package org.healthystyle.notification.service.cache;

import org.healthystyle.notification.service.cache.dto.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
