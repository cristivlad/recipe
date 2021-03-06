package com.example.services;

import com.example.commands.IngredientsCommand;

public interface IngredientService {

    IngredientsCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientsCommand saveIngredientCommand(IngredientsCommand command);

    void deleteIngredientById(Long recipeId, Long id);
}
