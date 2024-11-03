package com.cypsolabs.recipefinder_api.service;

import com.cypsolabs.recipefinder_api.model.AuthenticationResponse;
import com.cypsolabs.recipefinder_api.model.User;
import com.cypsolabs.recipefinder_api.repository.UserRepository;
import com.cypsolabs.recipefinder_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    public AuthenticationResponse authenticate(User request) {
        User user = (User) userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthenticationResponse(token, user.getId(), user.getUsername());
    }
}
