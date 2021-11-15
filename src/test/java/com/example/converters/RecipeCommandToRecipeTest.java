package com.example.converters;

import com.example.commands.CategoryCommand;
import com.example.commands.IngredientsCommand;
import com.example.commands.NotesCommand;
import com.example.commands.RecipesCommand;
import com.example.domain.Difficulty;
import com.example.domain.Recipe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = 5;
    public static final Integer PREP_TIME = 7;
    public static final String DESCRIPTION = "Description";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = 3;
    public static final String SOURCE = "Some Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID1 = 3L;
    public static final Long INGRED_ID2 = 4L;
    public static final Long NOTES_ID = 9L;

    static RecipeCommandToRecipe converter;

    @BeforeAll
    static void beforeAll() {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipesCommand()));
    }

    @Test
    public void convert() {
        RecipesCommand recipesCommand = new RecipesCommand();
        recipesCommand.setId(RECIPE_ID);
        recipesCommand.setCookTime(COOK_TIME);
        recipesCommand.setPrepTime(PREP_TIME);
        recipesCommand.setDescription(DESCRIPTION);
        recipesCommand.setDifficulty(DIFFICULTY);
        recipesCommand.setDirections(DIRECTIONS);
        recipesCommand.setServings(SERVINGS);
        recipesCommand.setSource(SOURCE);
        recipesCommand.setUrl(URL);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipesCommand.setNotes(notesCommand);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID1);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);
        recipesCommand.getCategories().add(categoryCommand1);
        recipesCommand.getCategories().add(categoryCommand2);

        IngredientsCommand ingredientsCommand1 = new IngredientsCommand();
        ingredientsCommand1.setId(INGRED_ID1);
        IngredientsCommand ingredientsCommand2 = new IngredientsCommand();
        ingredientsCommand2.setId(INGRED_ID2);
        recipesCommand.getIngredients().add(ingredientsCommand1);
        recipesCommand.getIngredients().add(ingredientsCommand2);

        Recipe recipe = converter.convert(recipesCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}