package app.com.cms2.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.cms2.repository.RoleRepository;
import app.com.cms2.repository.UserRepository;
import app.com.cms2.repository.UserSessionRepository;
import app.com.cms2.security.jwt.JwtProvider;
import app.com.cms2.ResourceNotFoundException;
import app.com.cms2.message.request.LoginForm;
import app.com.cms2.message.request.RegisterForm;
import app.com.cms2.message.response.JwtResponse;
import app.com.cms2.model.ReservationMachine;
import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;
import app.com.cms2.model.User;
import app.com.cms2.model.UserSession;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
 
  @Autowired
  AuthenticationManager authenticationManager;
 
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  UserSessionRepository userSessionRepository;
 
  @Autowired
  RoleRepository roleRepository;
 
  @Autowired
  PasswordEncoder encoder;
 
  @Autowired
  JwtProvider jwtProvider;
 
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
 
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
    SecurityContextHolder.getContext().setAuthentication(authentication);
 
    String jwt = jwtProvider.generateJwtToken(authentication);
    System.out.println(jwt);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
    User user = userRepository.findByUsername(loginRequest.getUsername());
    UserSession userSession = new UserSession();
    userSession.setUser(user);
    userSession.setStart_date(new Date());
    userSession.setStatus(true);
    
    userSessionRepository.save(userSession);
    
    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
  }
 
  
  @GetMapping("/logout/{id}")
  public ResponseEntity<String> logOutUser(@PathVariable(value = "id") Long userId) {   
	  
    UserSession userSession = userSessionRepository.findActiveSessionByUserId(userId).orElse(null);
    userSession.setFinish_date(new Date());
    userSession.setStatus(false);
    userSessionRepository.save(userSession);
    System.out.println("logout succed");
    return ResponseEntity.ok().body("session closed");
  }
  
 
  @GetMapping("/list")
  public ResponseEntity<List<Role>> getAllRoles() {
      return ResponseEntity.ok(roleRepository.findAll());
  }
  
  

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterForm registerRequest) {
      if(userRepository.existsByUsername(registerRequest.getUsername())) {
          return new ResponseEntity<String>("Fail -> Username is already taken!",
                  HttpStatus.BAD_REQUEST);
      }

      if(userRepository.existsByEmail(registerRequest.getEmail())) {
          return new ResponseEntity<String>("Fail -> Email is already in use!",
                  HttpStatus.BAD_REQUEST);
      }

      // Creating user's account
      User user = new User(registerRequest.getName(), registerRequest.getUsername(),
    		  registerRequest.getEmail(), encoder.encode(registerRequest.getPassword()));

      Set<String> strRoles = registerRequest.getRole();
      Set<Role> roles = new HashSet<>();
      
      strRoles.forEach(role -> {
      	switch(role) {
	    		case "ADMIN":
	    			Role adminRole = roleRepository.findByName(RoleName.ADMIN)
	                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
	    			roles.add(adminRole);
	    			
	    			break;
	    		case "LineManager":
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
}
