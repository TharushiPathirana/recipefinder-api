package com.cypsolabs.recipefinder_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "favourite_recipe")
public class FavoriteRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "recipe_id", nullable = false)
    private String recipeId;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;


    public FavoriteRecipe(User user, String recipeId) {
        this.user = user;
        this.recipeId = recipeId;
        this.addedAt = LocalDateTime.now();
    }
}
