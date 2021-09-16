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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import app.com.cms2.message.request.RegisterForm;
import app.com.cms2.message.request.UpdateForm;
import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;
import app.com.cms2.model.User;
import app.com.cms2.repository.ReservationMachineRepository;
import app.com.cms2.repository.RoleRepository;
import app.com.cms2.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ReservationMachineRepository  reservationMachineRepository;
	

	@PostMapping("/add")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterForm registerRequest) {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			return new ResponseEntity<String>("Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			return new ResponseEntity<String>("Fail -> Email is already in use!", HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(registerRequest.getName(), registerRequest.getUsername(), registerRequest.getEmail(),
				encoder.encode(registerRequest.getPassword()));

		Set<String> strRoles = registerRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "LigneManager":
				Role LigneManagerRole = roleRepository.findByName(RoleName.LigneManager)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(LigneManagerRole);

				break;

			default:
				Role userRole = roleRepository.findByName(RoleName.Machinist)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok().body("User registered successfully!");
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers() {

		return ResponseEntity.ok(userRepository.findAll());
	}

	@GetMapping("/list-free-users-by-roleId/{id}")
	public ResponseEntity<List<User>> getAllMachinist(@PathVariable("id")Long id) {
		List<User> users = new ArrayList<>();
		this.roleRepository.findById(id).get().getUsers().forEach(u->{
			if(reservationMachineRepository.findActiveReservationForUser(u.getId())==null) {
				users.add(u);
			}
		});
		return ResponseEntity.ok(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody UpdateForm updateForm)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("users not found for this id :: " + userId));

		user.setName(updateForm.getName());
		user.setEmail(updateForm.getEmail());
		user.setUsername(updateForm.getUsername());

		Set<String> strRoles = updateForm.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = roleRepository.findByName(RoleName.ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;

			default:
				Role userRole = roleRepository.findByName(RoleName.Machinist)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + userId));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
