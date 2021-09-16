package app.com.cms2.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.cms2.model.Machine;
import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.User;


public interface ReservationMachineRepository extends JpaRepository <ReservationMachine, Long> {

	
	
	List<ReservationMachine> findByUserId(Long id);
	List<ReservationMachine> findByMachineId(Long id);
	
	/*@Query(value="select new r from ReservationMachine where rm.Start_date <= :datetime1 and rm.Finish_date => :datetime2 ")	
	List<ReservationMachine> findReservationMachineBetween(@Param("datetime1")Date datetime1, @Param ("datetime2")Date datetime2);*/
	
	@Query(value="select r from ReservationMachine r where r.finish_date >= NOW() ")
	List<ReservationMachine> findActiveReservationMachine();
	
	@Query(value="select r from ReservationMachine r where r.user.id = :id and r.finish_date >= NOW() ")
	ReservationMachine findActiveReservationForUser(@Param("id")Long id);
	
	@Query(value="SELECT m FROM Machine m WHERE m.id not in (select r.machine.id from ReservationMachine r where r.finish_date > NOW())")
	List<Machine> findFreeMachines();
}
