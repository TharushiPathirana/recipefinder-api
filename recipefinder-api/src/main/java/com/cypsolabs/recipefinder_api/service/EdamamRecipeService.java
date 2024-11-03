package com.cypsolabs.recipefinder_api.service;

import com.cypsolabs.recipefinder_api.config.EdamamApiConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class EdamamRecipeService {


    private final EdamamApiConfig edamamApiConfig;

    public EdamamRecipeService(EdamamApiConfig edamamApiConfig) {
        this.edamamApiConfig = edamamApiConfig;
    }

    public String callEdamamApi(List<String> ingredients, String recipeId) throws IOException, InterruptedException {
        String url;

        // Check if recipeId is provided
        if (recipeId != null && !recipeId.isEmpty()) {
            // Fetch a specific recipe by ID
            url = edamamApiConfig.getEdamamRecipeByIdUrl(recipeId);
        } else {
            // Search for recipes based on ingredients
            url = edamamApiConfig.getEdamamApiUrl(ingredients);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }
    }

    public String createDescription(String mealType, String dishType, String dietLabels, String healthLabels){
        return String.format(
                "This recipe is perfect for %s, especially if you're in the mood for a %s. It’s %s and aligns with %s diets, offering health benefits like %s.",
                mealType.isEmpty() ? "any meal" : String.join("/", mealType),
                dishType.isEmpty() ? "dish" : String.join("/", dishType),
                dietLabels.isEmpty() ? "suitable for various preferences" : String.join(", ", dietLabels),
                dietLabels.isEmpty() ? "a variety of" : String.join(", ", dietLabels),
                healthLabels.isEmpty() ? "general well-being" : String.join(", ", healthLabels)
        );
    }




}
