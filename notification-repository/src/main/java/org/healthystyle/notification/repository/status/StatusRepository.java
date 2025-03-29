package org.healthystyle.notification.repository.status;

import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
	@Query("SELECT s FROM Status s WHERE s.type = :type")
	Status findByType(Type type);
}
