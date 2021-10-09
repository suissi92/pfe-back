package app.com.cms2.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.cms2.ResourceNotFoundException;
import app.com.cms2.message.request.AddReservationLineForm;
import app.com.cms2.message.request.AddReservationMachineForm;
import app.com.cms2.message.request.UpdateReservationLineForm;
import app.com.cms2.message.request.UpdateReservationMachineForm;
import app.com.cms2.model.Machine;
import app.com.cms2.model.Notification;
import app.com.cms2.model.ReservationLine;
import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.User;
import app.com.cms2.repository.MachineRepository;
import app.com.cms2.repository.NotificationRepository;
import app.com.cms2.repository.ReservationLineRepository;
import app.com.cms2.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservation-line")
public class ReservationLineController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private ReservationLineRepository reservationLineRepository;

	
	@GetMapping("/list-reservation")
	public ResponseEntity<List<ReservationLine>> getAllReservationLine() {

		return ResponseEntity.ok(reservationLineRepository.findAll());
	}
	
	@GetMapping("/user-reservation/{id}")
	public ResponseEntity<List<ReservationLine>> getReservationByUserId(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + userId));

		return ResponseEntity.ok(reservationLineRepository.findByUserId(userId));
	}
	
	@GetMapping("/line-reservation/{id}")
	public ResponseEntity<List<ReservationLine>> getReservationByLineId(
			@PathVariable(value = "id") Long lineId) throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(lineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + lineId));

		return ResponseEntity.ok(reservationLineRepository.findByLineId(lineId));
	}
	
	@GetMapping("/reservation/{id}")
	public ResponseEntity<ReservationLine> getReservationById(
			@PathVariable(value = "id") Long ReservationLineId) throws ResourceNotFoundException {
		ReservationLine reservationLine = reservationLineRepository.findById(ReservationLineId)
				.orElseThrow(() -> new ResourceNotFoundException(" reservation machine not found for this id :: " + ReservationLineId));

		return ResponseEntity.ok().body(reservationLine);
	}
	
	@Autowired
	NotificationRepository notificationRepository;
	@PostMapping("/add-reservation")
    public ResponseEntity<String> addReservationMachine(@Valid @RequestBody AddReservationLineForm addReservationLineRequest) {
      
				ReservationLine activeReservation =reservationLineRepository.findActiveReservationForUser(
						addReservationLineRequest.getUser().getId());
				if (activeReservation == null ) {
				ReservationLine reservationLine = new ReservationLine(
						addReservationLineRequest.getUser(),
						addReservationLineRequest.getLine(),
						addReservationLineRequest.getStart_date(),
						addReservationLineRequest.getFinish_date());
      
        reservationLineRepository.save(reservationLine);
        Notification notification = new Notification();
        notification.setConsulted(false);
        notification.setUser(addReservationLineRequest.getUser());
        notification.setMessage("notification : user "  + addReservationLineRequest.getUser().getUsername()
        		+ "has been affected to line "+ addReservationLineRequest.getLine().getName());
        notification.setDateCreation(new Date());
        
        notificationRepository.save(notification);
        
        return ResponseEntity.ok().body("reservation Line  registered successfully!");
				}
				else    return new ResponseEntity<String>("Fail ->user is already reserved",
	                    HttpStatus.BAD_REQUEST);
    }
	
	@GetMapping("/active-reservation")
	public ResponseEntity<List<ReservationLine>> getActiveReservationLine() {

		return ResponseEntity.ok(reservationLineRepository.findActiveReservationLine());
	}
	
	@GetMapping("/user-active-reservation-line/{id}")
	public ResponseEntity<ReservationLine> getUserActiveReservationLine(@PathVariable(value = "id") Long userId) {

		
		ReservationLine rl =reservationLineRepository.findActiveReservationForUser(userId);
		if (rl != null) {
			return ResponseEntity.ok(rl);
		}
		return ResponseEntity.ok(null);
	}
	
	
	
	@DeleteMapping("/delete_reservation/{id}")
    public Map<String, Boolean> deleteReservationLine(@PathVariable(value = "id") Long reservationLineId)
            throws ResourceNotFoundException {
        ReservationLine reservationLine = reservationLineRepository.findById(reservationLineId)
                .orElseThrow(() -> new ResourceNotFoundException("reservation Line not found for this id :: " + reservationLineId));

        reservationLineRepository.delete(reservationLine);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@PutMapping("/update_reservation/{id}")
    public ResponseEntity<ReservationLine> updateReservationLine(@PathVariable(value = "id") Long reservationLineId,
                                           @RequestBody UpdateReservationLineForm updateReservationLineForm) throws ResourceNotFoundException {
        ReservationLine reservationLine = reservationLineRepository.findById(reservationLineId)
                .orElseThrow(() -> new ResourceNotFoundException("reservation not found for this id :: " + reservationLineId));
        
        reservationLine.setUser(updateReservationLineForm.getUser());
        reservationLine.setLine(updateReservationLineForm.getLine());
        reservationLine.setStart_date(updateReservationLineForm.getStart_date());
        reservationLine.setFinish_date(updateReservationLineForm.getFinish_date());
        
   
        final ReservationLine updatedReservationLine = reservationLineRepository.save(reservationLine);
        return ResponseEntity.ok(updatedReservationLine);
    }

}
