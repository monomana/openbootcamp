package ar.modularsoft;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "ar.modularsoft")
@EnableJpaRepositories
@EnableTransactionManagement
public class RantiApplication extends SpringBootServletInitializer {
//	@Value("${spring.mail.username}")
//	private String username;
//	@Value("${spring.mail.password}")
//	private String password;

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(RantiApplication.class);
		app.run();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RantiApplication.class);
	}
	@PostConstruct
	public void init() {

//		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));
//		System.out.println("Date in UTC: " +  LocalDateTime.now().toString());
	//	System.out.println("MAIL: "+username );
	//	System.out.println("PASSW: "+password );
	}
}
