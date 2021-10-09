package app.com.cms2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.cms2.model.Line;
import app.com.cms2.model.Notification;
import app.com.cms2.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

	@Query("select n from Notification n where n.user.id= :id order by dateCreation desc")
	List<Notification> getALLByUserId(@Param("id") Long id);
}
