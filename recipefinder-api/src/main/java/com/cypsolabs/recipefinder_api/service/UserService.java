package com.cypsolabs.recipefinder_api.service;

import com.cypsolabs.recipefinder_api.dto.FavouriteResponseDto;
import com.cypsolabs.recipefinder_api.model.AuthenticationResponse;
import com.cypsolabs.recipefinder_api.model.FavoriteRecipe;
import com.cypsolabs.recipefinder_api.model.User;
import com.cypsolabs.recipefinder_api.repository.FavouriteRecipeRepository;
import com.cypsolabs.recipefinder_api.repository.UserRepository;
import com.cypsolabs.recipefinder_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavouriteRecipeRepository recipeRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public String addFavourite(Integer userId, String recipeId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return "User not found";
        }

        User user = userOptional.get();
        FavoriteRecipe userFavorite = new FavoriteRecipe(user, recipeId);
        recipeRepository.save(userFavorite);

        return "Recipe added to favorites successfully";
    }

    public List<FavouriteResponseDto> getFavourites(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return Collections.emptyList();
        }
        User user = userOptional.get();

        List<FavoriteRecipe> favorites = recipeRepository.findByUser(user);

        return favorites.stream()
                .map(favorite -> new FavouriteResponseDto(
                        favorite.getId(),
                        favorite.getRecipeId(),
                        favorite.getAddedAt()
                ))
                .collect(Collectors.toList());
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public String register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists. Please choose a different username.");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
            return jwtUtil.generateToken(user);
        }catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }


    }




}
