package com.cypsolabs.recipefinder_api.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
public class EdamamApiConfig {

    @Value("${edamam.api.url}")
    private String edamamApiUrl;

    @Value("${edamam.api.app_id}")
    private String edamamApiAppId;

    @Value("${edamam.api.app_key}")
    private String edamamApiAppKey;

    public String getEdamamApiUrl(List<String> ingredients) {
        String joinedIngredients = String.join(",", ingredients);

        return String.format(
                "%s?type=public&app_id=%s&app_key=%s&q=%s&field=label&field=image&field=healthLabels&field=mealType&field=dishType" +
                        "&field=ingredientLines&field=totalTime&field=shareAs&field=uri",
                edamamApiUrl, edamamApiAppId, edamamApiAppKey, joinedIngredients
        );
    }

    public String getEdamamRecipeByIdUrl(String recipeId) {
        return String.format(
                "%s/%s?type=public&app_id=%s&app_key=%s&field=label&field=image&field=healthLabels&field=mealType&field=dishType" +
                        "&field=ingredientLines&field=totalTime&field=shareAs&field=totalNutrients",
                edamamApiUrl, recipeId, edamamApiAppId, edamamApiAppKey
        );
    }







}
