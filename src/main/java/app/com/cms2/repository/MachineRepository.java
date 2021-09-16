package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.Machine;


@Repository
public interface MachineRepository extends JpaRepository <Machine, Long>{
	
	Optional<Machine> findByName(String name);
    Boolean existsByName(String name);
    
    List<Machine> findByLineIsNull();

}
