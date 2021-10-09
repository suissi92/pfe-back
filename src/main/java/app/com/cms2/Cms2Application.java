package app.com.cms2;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


@SpringBootApplication(scanBasePackages = { "app.com" })
@ComponentScan




public class Cms2Application {
	

	public static void main(String[] args) {
		SpringApplication.run(Cms2Application.class, args);
	}
	
	

}
