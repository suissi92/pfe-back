package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.MachineSession;
import app.com.cms2.model.UserSession;


@Repository
public interface MachineSessionRepository extends JpaRepository<MachineSession, Long>{
	
	@Query(value="select ms from MachineSession ms where ms.machine.id = :id and ms.status = true ")
	Optional<MachineSession> findSessionByMachineId(@Param("id") long machineId);
	
	
	
	
	@Query(value="select us from MachineSession us where us.status = true ")
	List<MachineSession> findOpenMachineSession();
	
	@Query(value="select us from MachineSession us where us.machine.id = :id ")
	List<MachineSession> findAllMachineSessions(@Param("id") long machineId);

}

