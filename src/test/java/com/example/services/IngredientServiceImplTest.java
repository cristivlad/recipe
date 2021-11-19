package com.example.services;

import com.example.commands.IngredientsCommand;
import com.example.converters.IngredientCommandToIngredient;
import com.example.converters.IngredientToIngredientCommand;
import com.example.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.example.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.domain.Ingredient;
import com.example.domain.Recipe;
import com.example.repositories.RecipeRepository;
import com.example.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void beforeAll() {
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand,ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() {

    }

    @Test
    public void findByRecipeIdAndRecipeIdHappyPath() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientsCommand ingredientsCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(3L, ingredientsCommand.getId());
        assertEquals(1L, ingredientsCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() {
        IngredientsCommand ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(3L);
        ingredientsCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientsCommand savedCommand = ingredientService.saveIngredientCommand(ingredientsCommand);

        assertEquals(3L, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }
}