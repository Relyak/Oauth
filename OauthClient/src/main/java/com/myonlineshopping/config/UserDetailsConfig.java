package com.myonlineshopping.config;
import com.myonlineshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Configuration
public class UserDetailsConfig {

    //@Autowired
    private UserRepository userRepo;
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepo.findByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "User " + email + " not found"
                                )
                        );
            }


        };
    }

}
