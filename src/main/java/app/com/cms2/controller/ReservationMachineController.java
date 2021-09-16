package app.com.cms2.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import app.com.cms2.message.request.AddLineForm;
import app.com.cms2.message.request.AddReservationMachineForm;
import app.com.cms2.message.request.UpdateLineForm;
import app.com.cms2.message.request.UpdateReservationMachineForm;
import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.ReservationLine;
import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.User;
import app.com.cms2.repository.MachineRepository;
import app.com.cms2.repository.ReservationMachineRepository;
import app.com.cms2.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservation-machine")
public class ReservationMachineController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private ReservationMachineRepository reservationMachineRepository;

	@GetMapping("/list-reservation")
	public ResponseEntity<List<ReservationMachine>> getAllReservationMachine() {

		return ResponseEntity.ok(reservationMachineRepository.findAll());
	}

	@GetMapping("/user-reservation/{id}")
	public ResponseEntity<List<ReservationMachine>> getReservationByUserId(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + userId));

		return ResponseEntity.ok(reservationMachineRepository.findByUserId(userId));
	}

	@GetMapping("/machine-reservation/{id}")
	public ResponseEntity<List<ReservationMachine>> getReservationByMachineId(
			@PathVariable(value = "id") Long machineId) throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(machineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + machineId));

		return ResponseEntity.ok(reservationMachineRepository.findByMachineId(machineId));
	}
	
	@GetMapping("/reservation/{id}")
	public ResponseEntity<ReservationMachine> getReservationById(
			@PathVariable(value = "id") Long ReservationMachineId) throws ResourceNotFoundException {
		ReservationMachine reservationMachine = reservationMachineRepository.findById(ReservationMachineId)
				.orElseThrow(() -> new ResourceNotFoundException(" reservation machine not found for this id :: " + ReservationMachineId));

		return ResponseEntity.ok().body(reservationMachine);
	}

	@PostMapping("/add-reservation")
    public ResponseEntity<String> addReservationMachine(@Valid @RequestBody AddReservationMachineForm addReservationMachineRequest) {
        
        // Creating reservation
	
		
		
				ReservationMachine activeReservation =reservationMachineRepository.findActiveReservationForUser(
						addReservationMachineRequest.getUser().getId());
				if (activeReservation == null ) {
				ReservationMachine reservationMachine = new ReservationMachine(
        		addReservationMachineRequest.getUser(),
        		addReservationMachineRequest.getMachine(),
        		addReservationMachineRequest.getStart_date(),
        		addReservationMachineRequest.getFinish_date());
      
        reservationMachineRepository.save(reservationMachine);
        return ResponseEntity.ok().body("reservation  registered successfully!");
				}
				else    return new ResponseEntity<String>("Fail ->user is already reserved",
	                    HttpStatus.BAD_REQUEST);
    }

	@GetMapping("/active-reservation")
	public ResponseEntity<List<ReservationMachine>> getActiveReservationMachine() {

		return ResponseEntity.ok(reservationMachineRepository.findActiveReservationMachine());
	}
	
	@GetMapping("/user-active-reservation")
	public ResponseEntity<ReservationMachine> getUserActiveReservationMachine(Long id) {

		return ResponseEntity.ok(reservationMachineRepository.findActiveReservationForUser(id));
	}
	
	
	
	
	
	
	
	
	
	
	
	@DeleteMapping("/delete_reservation/{id}")
    public Map<String, Boolean> deleteReservationMachine(@PathVariable(value = "id") Long reservationMachineId)
            throws ResourceNotFoundException {
        ReservationMachine reservationMachine = reservationMachineRepository.findById(reservationMachineId)
                .orElseThrow(() -> new ResourceNotFoundException("reservation machine not found for this id :: " + reservationMachineId));

        reservationMachineRepository.delete(reservationMachine);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@PutMapping("/update_reservation/{id}")
    public ResponseEntity<ReservationMachine> updateReservationMachine(@PathVariable(value = "id") Long reservationMachineId,
                                           @RequestBody UpdateReservationMachineForm updateReservationMachineForm) throws ResourceNotFoundException {
        ReservationMachine reservationMachine = reservationMachineRepository.findById(reservationMachineId)
                .orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + reservationMachineId));
        
        reservationMachine.setUser(updateReservationMachineForm.getUser());
        reservationMachine.setMachine(updateReservationMachineForm.getMachine());
        reservationMachine.setStart_date(updateReservationMachineForm.getStart_date());
        reservationMachine.setFinish_date(updateReservationMachineForm.getFinish_date());
        
   
        final ReservationMachine updatedReservationMachine = reservationMachineRepository.save(reservationMachine);
        return ResponseEntity.ok(updatedReservationMachine);
    }

}
