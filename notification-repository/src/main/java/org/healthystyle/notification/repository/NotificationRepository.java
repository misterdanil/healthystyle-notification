package org.healthystyle.notification.repository;

import org.healthystyle.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	@Query("SELECT n FROM Notification n WHERE n.toUserId = :userId ORDER BY n.createdOn DESC")
	Page<Notification> findByToUserId(Long userId, Pageable pageable);
	
	@Query("SELECT COUNT(n) FROM Notification n WHERE n.toUserId = :toUserId AND n.type = org.healthystyle.notification.status.Type.UNWATCHED GROUP BY n")
	Integer countUnwatched(Long toUserId);
}
