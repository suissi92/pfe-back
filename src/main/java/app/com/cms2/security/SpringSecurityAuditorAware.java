package app.com.cms2.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import app.com.cms2.model.User;

class SpringSecurityAuditorAware implements AuditorAware<User> {

	  @Override
	  public Optional<User> getCurrentAuditor() {

	    return Optional.ofNullable(SecurityContextHolder.getContext())
	            .map(SecurityContext::getAuthentication)
	            .filter(Authentication::isAuthenticated)
	            .map(Authentication::getPrincipal)
	            .map(User.class::cast);
	  }

}
