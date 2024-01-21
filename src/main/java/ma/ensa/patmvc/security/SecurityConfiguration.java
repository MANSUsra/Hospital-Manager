package ma.ensa.patmvc.security;


import lombok.AllArgsConstructor;
import ma.ensa.patmvc.security.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfiguration {
    private UserDetailServiceImpl userDetailServiceImpl;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
        http.rememberMe();
        http.authorizeHttpRequests().requestMatchers("/webjars/**", "h2-console/**").permitAll();
        //Proteger les ressources cote serveur
        http.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        http.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/notAuthorized");
        http.userDetailsService(userDetailServiceImpl);
        return http.build();
    }


    //@Bean
//   public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
//       return new JdbcUserDetailsManager(dataSource);
//   }

    //@Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        PasswordEncoder passwordEncoder = passwordEncoder();
//        String encodedPWD = passwordEncoder.encode("1234");
//        System.out.println(encodedPWD);
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(encodedPWD)
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, user2, admin);
//    }



    //@Bean
    //PasswordEncoder passwordEncoder(){

       // return new BCryptPasswordEncoder();
    //}

}
