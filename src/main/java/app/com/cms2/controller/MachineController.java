package app.com.cms2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import app.com.cms2.message.request.AddMachineForm;
import app.com.cms2.message.request.AffectMachinesToLineForm;
import app.com.cms2.message.request.UpdateMachineForm;
import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.Role;
import app.com.cms2.model.User;
import app.com.cms2.repository.LineRepository;
import app.com.cms2.repository.MachineRepository;
import app.com.cms2.repository.ReservationMachineRepository;
import app.com.cms2.repository.UserRepository;

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
	private ReservationMachineRepository reservationMachineRepository;

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

			/*
			 * Set<Machine> strMachines = affectMachinesToLineRequest.getMachines(); (
			 * strMachines).forEach(machine -> {
			 * 
			 * machine.setLine(line); machineRepository.save(machine); });
			 * 
			 * line.setName(affectMachinesToLineRequest.getName());
			 * line.setDescription(affectMachinesToLineRequest.getDescription());
			 * line.setMachines(affectMachinesToLineRequest.getMachines()); final Line
			 * affectMachinesToLine = lineRepository.save(line);
			 */

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
