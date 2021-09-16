package app.com.cms2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.com.cms2.model.Line;


public interface LineRepository extends JpaRepository <Line, Long>{

	
	Optional<Line> findByName(String name);
    Boolean existsByName(String name);
    
}
