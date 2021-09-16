package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;
import app.com.cms2.model.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByName(RoleName roleName);
	 
	 
	 

}