package app.com.cms2.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.com.cms2.message.request.AddMachineForm;
import app.com.cms2.message.request.AffectMachinesToLineForm;
import app.com.cms2.message.request.UpdateMachineForm;
import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.MachineSession;
import app.com.cms2.model.Notification;
import app.com.cms2.model.ReservationLine;
import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.User;
import app.com.cms2.model.UserSession;
import app.com.cms2.repository.LineRepository;
import app.com.cms2.repository.MachineRepository;
import app.com.cms2.repository.MachineSessionRepository;
import app.com.cms2.repository.NotificationRepository;
import app.com.cms2.repository.ReservationLineRepository;
import app.com.cms2.repository.ReservationMachineRepository;
import app.com.cms2.repository.UserRepository;
import app.com.cms2.repository.UserSessionRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin/machine")
public class MachineController {

	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private MachineSessionRepository machineSessionRepository;

	@Autowired
	private ReservationMachineRepository reservationMachineRepository;
	
	@Autowired
	private ReservationLineRepository reservationLineRepository;
	
	@Autowired
	NotificationRepository notificationRepository;

	@PostMapping("/add")
	public ResponseEntity<Machine> addMachine(@Valid @RequestBody AddMachineForm addMachineRequest) {
		if (machineRepository.existsByName(addMachineRequest.getName())) {
			return ResponseEntity.badRequest().body(null);
		}
		// Creating machine
		Machine machine = new Machine(addMachineRequest.getName(), addMachineRequest.getDescription(),
				addMachineRequest.isStatus(), addMachineRequest.getMtype(), addMachineRequest.isFese());
		machineRepository.save(machine);
		return ResponseEntity.ok().body(machine);
	}

	@GetMapping("/list")
	public ResponseEntity<List<Machine>> getAllMachine() {

		return ResponseEntity.ok(machineRepository.findAll());
	}

	@GetMapping("/free-machine")
	public ResponseEntity<List<Machine>> getFreeMachine() {

		return ResponseEntity.ok(machineRepository.findByLineIsNull());
	}
	

	
	@GetMapping("/active-machines")
	public ResponseEntity<List<MachineSession>> getActiveMachines() {

		return ResponseEntity.ok(machineSessionRepository.findOpenMachineSession());
	}
	
	@GetMapping("/machine-sessions/{id}")
	public ResponseEntity<List<MachineSession>> getUserSessions(@PathVariable(value = "id") Long machineId) {

		return ResponseEntity.ok(machineSessionRepository.findAllMachineSessions(machineId));
	}

	@GetMapping("/unreserved-machine")
	public ResponseEntity<List<Machine>> getUnreservedMachines() {

		return ResponseEntity.ok(reservationMachineRepository.findFreeMachines());
	}

	@GetMapping("/machine/{id}")
	public ResponseEntity<Machine> getMachineById(@PathVariable(value = "id") Long machineId)
			throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(machineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + machineId));
		return ResponseEntity.ok().body(machine);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Machine> updateMachine(@PathVariable(value = "id") Long machineId,
			@RequestBody UpdateMachineForm updateMachineForm) throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(machineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + machineId));

		machine.setName(updateMachineForm.getName());
		machine.setDescription(updateMachineForm.getDescription());
		machine.setFese(updateMachineForm.isFese());
		machine.setMtype(updateMachineForm.getMtype());
		machine.setStatus(updateMachineForm.isStatus());
		machine.setStatus(updateMachineForm.isStatus());

		final Machine updatedMachine = machineRepository.save(machine);
		return ResponseEntity.ok(updatedMachine);
	}

	@GetMapping("/updateMachineStatus/{id}")
	public ResponseEntity<Machine> updateMachine(@PathVariable(value = "id") Long machineId) {
		Machine machine = machineRepository.findById(machineId).orElse(null);
		long lineId =  machine.getLine().getId();
	    ReservationLine rl = reservationLineRepository.findActiveReservationForLine(lineId).orElse(null);
	    
		    
		    ReservationMachine rm = reservationMachineRepository.findActiveReservationForMachine(machineId);
		    User machinist = rm.getUser();
		if (java.util.Objects.nonNull(machine)) {
			machine.setStatus(!machine.isStatus());
			machine = machineRepository.save(machine);
		}
		if (machine.isStatus()== true) {
			 MachineSession machineSession = new MachineSession();
			    machineSession.setMachine(machine);
			    machineSession.setStart_date(new Date());
			    machineSession.setStatus(true);
			    machineSession.setUser(machinist);
			    machineSessionRepository.save(machineSession);
			    //notifier le line manager de larret de la machine 
			   
			    if (rl != null) {
			    	User lm = rl.getUser();
			    Notification notification = new Notification();
		        notification.setConsulted(false);
		        notification.setUser(lm);
		        notification.setMessage("notification : machine "  + machine.getName()
		        		+ "has been started at"+ machineSession.getStart_date());
		        notification.setDateCreation(new Date());
		        
		        notificationRepository.save(notification);
			    }
		}
		if (machine.isStatus()==false) {
			
			MachineSession machineSession = machineSessionRepository.findSessionByMachineId(machineId).orElse(null);
			machineSession.setFinish_date(new Date());
			machineSession.setStatus(false);
			machineSessionRepository.save(machineSession);
			 if (rl != null) {
			    	User lm = rl.getUser();
			
			Notification notification = new Notification();
	        notification.setConsulted(false);
	        notification.setUser(lm);
	        notification.setMessage("notification : machine "  + machine.getName()
	        		+ "has been stopped at"+ machineSession.getFinish_date());
	        notification.setDateCreation(new Date());
	        
	        notificationRepository.save(notification);
			 }
		}
		 
		   

		return ResponseEntity.ok(machine);
	}

	/*
	 * @PutMapping("/affect/{id}") public ResponseEntity<Machine>
	 * affectMachine(@PathVariable(value = "id") Long machineId,
	 * 
	 * @RequestBody AffectMachineForm affectMachineForm) throws
	 * ResourceNotFoundException { Machine machine =
	 * machineRepository.findById(machineId) .orElseThrow(() -> new
	 * ResourceNotFoundException("machine not found for this id :: " + machineId));
	 * 
	 * 
	 * 
	 * 
	 * machine.setName(affectMachineForm.getName());
	 * machine.setDescription(affectMachineForm.getDescription());
	 * machine.setFese(affectMachineForm.isFese());
	 * machine.setMtype(affectMachineForm.getMtype());
	 * machine.setStatus(affectMachineForm.isStatus());
	 * 
	 * 
	 * final Machine updatedMachine = machineRepository.save(machine); return
	 * ResponseEntity.ok(updatedMachine); }
	 */

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteMachine(@PathVariable(value = "id") Long machineId)
			throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(machineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + machineId));

		machineRepository.delete(machine);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PostMapping("/affectMachinesToLine")
	public ResponseEntity<Line> affectMachinesToLine(

			@Valid @RequestBody AffectMachinesToLineForm affectMachinesToLineRequest) {
		Machine m = machineRepository.findById(affectMachinesToLineRequest.getMachine().getId()).orElseGet(null);

		if (m != null) {
			Line line = lineRepository.findById(affectMachinesToLineRequest.getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"line not found for this id :: " + affectMachinesToLineRequest.getId()));

			line.addMachine(affectMachinesToLineRequest.getMachine());

			return ResponseEntity.ok(lineRepository.save(line));
		}
		return ResponseEntity.ok(null);

	}

	@DeleteMapping("/delete-machine-from-line/{id}")
	public ResponseEntity<Machine> deleteMachineFromLine(@PathVariable(value = "id") Long machineId)
			throws ResourceNotFoundException {
		Machine machine = machineRepository.findById(machineId)
				.orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + machineId));

		machine.setLine(null);

		return ResponseEntity.ok(machineRepository.save(machine));
	}

}
