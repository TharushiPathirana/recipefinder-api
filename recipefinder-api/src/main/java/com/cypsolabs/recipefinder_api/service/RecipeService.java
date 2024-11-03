package com.cypsolabs.recipefinder_api.service;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private final EdamamRecipeService edamamRecipeService;

    public RecipeService(EdamamRecipeService edamamRecipeService) {
        this.edamamRecipeService = edamamRecipeService;
    }

    private String getJsonArrayAsString(JSONObject recipe, String key) {
        JSONArray jsonArray = recipe.optJSONArray(key);
        return jsonArray != null ? jsonArray.join(", ").replace("\"", "") : "";
    }



    public String getRecipes(List<String> ingredients) throws IOException, InterruptedException {

       String recipes = edamamRecipeService.callEdamamApi(ingredients, null);
       JSONObject jsonObject = new JSONObject(recipes);
       JSONArray recipeHits = jsonObject.getJSONArray("hits");
        JSONArray recipesArray = new JSONArray();

       for (int i = 0; i < recipeHits.length(); i++) {
           JSONObject recipeHit = recipeHits.getJSONObject(i);
           JSONObject recipe = recipeHit.getJSONObject("recipe");

           String dietLabels = getJsonArrayAsString(recipe, "dietLabels");
           String healthLabels = getJsonArrayAsString(recipe, "healthLabels");
           String mealType = getJsonArrayAsString(recipe, "mealType");
           String dishType = getJsonArrayAsString(recipe, "dishType");
           String uri = String.valueOf(recipe.optString("uri"));

           String description = edamamRecipeService.createDescription(mealType, dishType, dietLabels, healthLabels);
           recipe.remove("dietLabels");
           recipe.remove("healthLabels");
           recipe.remove("mealType");
           recipe.remove("dishType");
           recipe.put("description",description);
           recipe.put("id", uri.substring(uri.lastIndexOf("#") + 1));
           recipe.remove("uri");
           recipesArray.put(recipe);
           recipe.put("title", recipe.remove("label"));
           recipe.put("ingredients", recipe.remove("ingredientLines"));
           recipe.put("instructions", recipe.remove("shareAs"));
           recipe.put("preparation_time", recipe.remove("totalTime"));

       }

       return recipesArray.toString();
    }

    public JSONObject fetchRecipeById(String id) throws IOException, InterruptedException {
        // Call the API with `null` for ingredients and the specific `id`
        String recipeResponse = edamamRecipeService.callEdamamApi(null, id);

        // Convert the response to JSON
        JSONObject jsonObject = new JSONObject(recipeResponse);
        JSONObject recipe = jsonObject.getJSONObject("recipe");  // Ensure you’re accessing the right level of the JSON

        // Map the fields as needed
        recipe.put("title", recipe.remove("label"));
        recipe.put("ingredients", recipe.remove("ingredientLines"));
        recipe.put("instructions", recipe.remove("shareAs"));
        recipe.put("preparation_time", recipe.remove("totalTime"));

        return recipe;
    }






}
