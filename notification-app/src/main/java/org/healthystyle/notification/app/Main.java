package org.healthystyle.notification.app;

import org.healthystyle.notification.repository.status.StatusRepository;
import org.healthystyle.notification.status.Status;
import org.healthystyle.notification.status.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "org.healthystyle.notification" })
@EnableJpaRepositories(basePackages = "org.healthystyle.notification.repository")
@EntityScan(basePackages = "org.healthystyle.notification")
public class Main {
	@Autowired
	private StatusRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			// status
			Status status = new Status(Type.UNWATCHED);
//			repository.save(status);

			Status status1 = new Status(Type.WATCHED);
//			repository.save(status1);

		};
	}
}
