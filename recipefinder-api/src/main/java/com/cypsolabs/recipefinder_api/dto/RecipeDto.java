package com.cypsolabs.recipefinder_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String imageUrl;
    private String description;
    private String instructions;
    private String preparation_time;
    private List<String> ingredientLines;

}
