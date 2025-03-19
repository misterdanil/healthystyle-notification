package org.healthystyle.notification.repository;

import java.util.List;

import org.healthystyle.notification.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
	@Query("SELECT o FROM Option o WHERE o.notification.id = :notificationId")
	List<Option> findByNotification(Long notificationId);
}
