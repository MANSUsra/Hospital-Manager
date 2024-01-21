package ma.ensa.patmvc;

import ma.ensa.patmvc.entities.Patient;
import ma.ensa.patmvc.repositories.PatientRepository;
import ma.ensa.patmvc.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatMvcApplication {


	public static void main(String[] args) {
		SpringApplication.run(PatMvcApplication.class, args);
	}

	//@Bean
//	CommandLineRunner commandLineRunner(PatientRepository patientRepository){
//		return args -> {
//			patientRepository.save(new Patient(null, "Hassan", new Date(), false, 1200));
//			patientRepository.save(new Patient(null, "Mohamed", new Date(), true, 321));
//			patientRepository.save(new Patient(null, "Yasmine", new Date(), true, 865));
//			patientRepository.save(new Patient(null, "Hanae", new Date(), false, 192));
//
//			patientRepository.findAll().forEach(p -> {
//				System.out.println(p.getNom());
//			});
//		};
//
//	}
	//@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder = passwordEncoder();
		return args ->{
			UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user33");
			if(u1 == null ) jdbcUserDetailsManager.createUser(User.withUsername("user33").password(passwordEncoder.encode("1234")).roles("USER").build());
			UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user44");
			if(u2 == null ) jdbcUserDetailsManager.createUser(User.withUsername("user44").password(passwordEncoder.encode("1234")).roles("USER").build());
			UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin3");
			if(u3 == null ) jdbcUserDetailsManager.createUser(User.withUsername("admin3").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build());
		};
	}

	//@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user1", "1234","user1@gmail.com", "1234");
			accountService.addNewUser("user2", "1234","user2@gmail.com", "1234");
			accountService.addNewUser("admin", "1234","admin@gmail.com", "1234");

			accountService.addRoleToUser("user1", "USER");
			accountService.addRoleToUser("user2", "USER");
			accountService.addRoleToUser("admin", "USER");
			accountService.addRoleToUser("admin","ADMIN");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
