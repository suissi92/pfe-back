package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

//	@Query("select * from User u join Role r on u.id = r.id where r.id = :roleId")
@Query("select u from User u left join fetch u.roles where u.id = :roleId")
List<User> findMachinist(@Param("roleId") long roleId);

}