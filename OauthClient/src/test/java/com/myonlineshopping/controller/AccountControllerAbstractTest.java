package com.myonlineshopping.controller;

import com.myonlineshopping.model.AuthRequest;
import com.myonlineshopping.model.AuthResponse;
import com.myonlineshopping.model.ERole;
import com.myonlineshopping.model.User;
import com.myonlineshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class AccountControllerAbstractTest {

    @Autowired
    AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public User createUser(String name, String passw, ERole role){
     return new User(null,name,passw,role);
    }
    private String accessToken = null;

    public void persistUser(User u) {
        // Create user
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String enc_password = passwordEncoder.encode(u.getPassword());

        User aUser = new User(null, u.getEmail(), enc_password, u.getRole());

        userRepository.save(aUser);
        u.setId(aUser.getId());
    }


    public String setUp(User u) {


        // Get Token
        AuthRequest authRequest = new AuthRequest(u.getEmail(), u.getPassword());
        ResponseEntity <AuthResponse> result = (ResponseEntity<AuthResponse>) authService.login(authRequest);

        System.out.println("JWT TokeN: " + result.getBody().getAccessToken());

        accessToken = result.getBody().getAccessToken();
        return accessToken;
    }
}
