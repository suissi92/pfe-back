package app.com.cms2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy
//@Configuration
@SpringBootApplication(scanBasePackages = { "app.com" })
@ComponentScan
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class})
public class Cms2Application {
	

	public static void main(String[] args) {
		SpringApplication.run(Cms2Application.class, args);
	}
	
	

}
