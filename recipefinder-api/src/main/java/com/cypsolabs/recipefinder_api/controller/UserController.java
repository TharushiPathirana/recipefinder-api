package com.cypsolabs.recipefinder_api.controller;

import com.cypsolabs.recipefinder_api.dto.FavouriteRequestDto;
import com.cypsolabs.recipefinder_api.dto.FavouriteResponseDto;
import com.cypsolabs.recipefinder_api.model.AuthenticationResponse;
import com.cypsolabs.recipefinder_api.model.User;
import com.cypsolabs.recipefinder_api.service.UserDetailsService;
import com.cypsolabs.recipefinder_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String token = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        AuthenticationResponse response = userDetailsService.authenticate(user);
        System.out.println(user.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<String> favorites(@PathVariable("userId") Integer userId, @RequestBody FavouriteRequestDto requestDto) {
        String recipes = userService.addFavourite(userId, requestDto.getRecipeId());
        return ResponseEntity.ok(recipes);

    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<?> getFavorites(@PathVariable("userId") Integer userId) {
        List<FavouriteResponseDto> recipes = userService.getFavourites(userId);
        return ResponseEntity.ok(recipes);
    }


}
