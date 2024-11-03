package com.cypsolabs.recipefinder_api.repository;

import com.cypsolabs.recipefinder_api.model.FavoriteRecipe;
import com.cypsolabs.recipefinder_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {
    List<FavoriteRecipe> findByUser(User user);
}
