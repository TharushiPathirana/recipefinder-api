package com.cypsolabs.recipefinder_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponseDto {
    private Long id;
    private String recipeId;
    private LocalDateTime addedAt;

}
