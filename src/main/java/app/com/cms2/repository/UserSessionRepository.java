package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.UserSession;


@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long>{
	
	@Query(value="select us from UserSession us where us.user.id = :id and us.status = true ")
	Optional<UserSession>  findActiveSessionByUserId(@Param("id") long userId);
	
	
	@Query(value="select us from UserSession us where us.status = true ")
	List<UserSession> findOpenSession();
	
	@Query(value="select us from UserSession us where us.user.id = :id ")
	List<UserSession> findAllUserSessions(@Param("id") long userId);


}
