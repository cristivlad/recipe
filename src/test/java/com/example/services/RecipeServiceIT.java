package com.example.services;

import com.example.commands.RecipesCommand;
import com.example.converters.RecipeCommandToRecipe;
import com.example.converters.RecipeToRecipeCommand;
import com.example.domain.Recipe;
import com.example.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New Description!";

    final RecipeService recipeService;

    final RecipeRepository recipeRepository;

    final RecipeCommandToRecipe recipeCommandToRecipe;

    final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceIT(RecipeService recipeService, RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipesCommand testRecipesCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipesCommand.setDescription(NEW_DESCRIPTION);
        RecipesCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipesCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
