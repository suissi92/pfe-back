package app.com.cms2.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import app.com.cms2.message.request.AddLineForm;
import app.com.cms2.message.request.AddMachineForm;
import app.com.cms2.message.request.AffectMachinesToLineForm;
import app.com.cms2.message.request.UpdateLineForm;
import app.com.cms2.message.request.UpdateMachineForm;
import app.com.cms2.model.Line;
import app.com.cms2.model.Machine;
import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;
import app.com.cms2.model.User;
import app.com.cms2.repository.LineRepository;
import app.com.cms2.repository.MachineRepository;
import app.com.cms2.repository.ReservationLineRepository;
import app.com.cms2.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lm/line")
public class LineController {
	
	    @Autowired
	    private MachineRepository machineRepository;
	    
	    @Autowired
	    private UserRepository userRepository;
	    
	    @Autowired
	    private LineRepository lineRepository;
	    
		@Autowired
		private ReservationLineRepository reservationLineRepository;
	    
	    @PostMapping("/add")
	    public ResponseEntity<String> addLine(@Valid @RequestBody AddLineForm addLineRequest) {
	        if(lineRepository.existsByName(addLineRequest.getName())) {
	            return new ResponseEntity<String>("Fail -> name is already taken!",
	                    HttpStatus.BAD_REQUEST);
	        }
	        // Creating machine
	        Line line = new Line(addLineRequest.getName(), addLineRequest.getDescription());
	        lineRepository.save(line);
	        return ResponseEntity.ok().body("machine registered successfully!");
	    }
	    
	    
	    @GetMapping("/list")
	    public ResponseEntity<List<Line>> getAllLine() {

	        return ResponseEntity.ok(lineRepository.findAll());
	    }
	    
	    @GetMapping("/unreserved-lines")
		public ResponseEntity<List<Line>> getUnreservedLines() {

			return ResponseEntity.ok(reservationLineRepository.findFreeLines());
		}
	    

	    @GetMapping("/line/{id}")
	    public ResponseEntity<Line> getLineById(@PathVariable(value = "id") Long lineId)
	            throws ResourceNotFoundException {
	        Line line = lineRepository.findById(lineId)
	                .orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + lineId));
	        return ResponseEntity.ok().body(line);
	    }

	    @PutMapping("/update/{id}")
	    public ResponseEntity<Line> updateLine(@PathVariable(value = "id") Long lineId,
	                                           @RequestBody UpdateLineForm updateLineForm) throws ResourceNotFoundException {
	        Line line = lineRepository.findById(lineId)
	                .orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + lineId));

	        line.setName(updateLineForm.getName());
	        line.setDescription(updateLineForm.getDescription());
	   
	        final Line updatedLine = lineRepository.save(line);
	        return ResponseEntity.ok(updatedLine);
	    }
	    
	   

	    @DeleteMapping("/delete/{id}")
	    public Map<String, Boolean> deleteLine(@PathVariable(value = "id") Long lineId)
	            throws ResourceNotFoundException {
	        Line line = lineRepository.findById(lineId)
	                .orElseThrow(() -> new ResourceNotFoundException("machine not found for this id :: " + lineId));

	        lineRepository.delete(line);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }
	    
	    


}
