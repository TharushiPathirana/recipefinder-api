package com.cypsolabs.recipefinder_api.controller;

import com.cypsolabs.recipefinder_api.service.RecipeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/search")
    public ResponseEntity<?> searchRecipes(@RequestParam(name = "ingredients") List<String> ingredients) throws IOException, InterruptedException {
        String recipes = recipeService.getRecipes(ingredients);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable String id) throws IOException, InterruptedException {
        JSONObject recipe = recipeService.fetchRecipeById(id);
        return ResponseEntity.ok(recipe.toString());

    }




}
