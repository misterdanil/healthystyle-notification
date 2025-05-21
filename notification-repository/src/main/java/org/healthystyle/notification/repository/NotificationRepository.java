package org.healthystyle.notification.repository;

import java.util.List;

import org.healthystyle.notification.Notification;
import org.healthystyle.notification.status.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	@Query("SELECT n FROM Notification n WHERE n.toUserId = :userId AND n.status.type = org.healthystyle.notification.status.Type.UNWATCHED ORDER BY n.createdOn DESC")
	Page<Notification> findByToUserId(Long userId, Pageable pageable);

	@Query("SELECT COUNT(n) FROM Notification n WHERE n.toUserId = :toUserId AND n.status.type = org.healthystyle.notification.status.Type.UNWATCHED GROUP BY n")
	Integer countUnwatched(Long toUserId);
	
	boolean existsByIdentifier(String identifier);
	
	@Query("UPDATE Notification n SET n.status = :status WHERE n.id IN :ids AND n.toUserId = :userId")
	@Modifying
	void updateStatus(List<Long> ids, Status status, Long userId);
}
