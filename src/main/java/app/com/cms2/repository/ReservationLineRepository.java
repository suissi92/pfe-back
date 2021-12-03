package app.com.cms2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.ReservationLine;




public interface ReservationLineRepository extends JpaRepository <ReservationLine, Long>{
	
	List<ReservationLine> findByUserId(Long id);
	List<ReservationLine> findByLineId(Long id);
	
	
	@Query(value="select r from ReservationLine r where r.finish_date >= NOW() ")
	List<ReservationLine> findActiveReservationLine();
	
	@Query(value="select r from ReservationLine r where r.user.id = :id and r.finish_date >= NOW() ")
	ReservationLine findActiveReservationForUser(@Param("id")Long id);
	
	@Query(value="select r from ReservationLine r where r.line.id = :id and r.finish_date >= NOW() ")
	Optional<ReservationLine>  findActiveReservationForLine(@Param("id")Long lineId);
	
	@Query(value="SELECT l FROM Line l WHERE l.id not in (select r.line.id from ReservationLine r where r.finish_date > NOW())")
	List<Line> findFreeLines();

}
