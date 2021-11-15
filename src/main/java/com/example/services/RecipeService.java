package com.example.services;

import com.example.commands.RecipesCommand;
import com.example.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipesCommand saveRecipeCommand(RecipesCommand command);
}
