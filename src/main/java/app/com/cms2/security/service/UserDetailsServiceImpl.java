package app.com.cms2.security.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.com.cms2.model.User;
import app.com.cms2.repository.UserRepository;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

   // @Autowired
   private final UserRepository userRepository;

  
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
        User user = userRepository.findByUsername(username);

        return UserPrinciple.build(user);
    }
}
