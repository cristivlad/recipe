package com.example.converters;

import com.example.commands.CategoryCommand;
import com.example.commands.IngredientsCommand;
import com.example.commands.NotesCommand;
import com.example.commands.RecipesCommand;
import com.example.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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

    static RecipeToRecipeCommand converter;

    @BeforeAll
    static void beforeAll() {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {
        Recipe recipes = new Recipe();
        recipes.setId(RECIPE_ID);
        recipes.setCookTime(COOK_TIME);
        recipes.setPrepTime(PREP_TIME);
        recipes.setDescription(DESCRIPTION);
        recipes.setDifficulty(DIFFICULTY);
        recipes.setDirections(DIRECTIONS);
        recipes.setServings(SERVINGS);
        recipes.setSource(SOURCE);
        recipes.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipes.setNotes(notes);

        Category category1 = new Category();
        category1.setId(CAT_ID1);
        Category category2 = new Category();
        category2.setId(CAT_ID2);
        recipes.getCategories().add(category1);
        recipes.getCategories().add(category2);

        Ingredient ingredients1 = new Ingredient();
        ingredients1.setId(INGRED_ID1);
        Ingredient ingredients2 = new Ingredient();
        ingredients2.setId(INGRED_ID2);
        recipes.getIngredients().add(ingredients1);
        recipes.getIngredients().add(ingredients2);

        RecipesCommand recipesCommand = converter.convert(recipes);

        assertNotNull(recipesCommand);
        assertEquals(RECIPE_ID, recipesCommand.getId(),"aici");
        assertEquals(COOK_TIME, recipesCommand.getCookTime());
        assertEquals(PREP_TIME, recipesCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipesCommand.getDescription());
        assertEquals(DIFFICULTY, recipesCommand.getDifficulty());
        assertEquals(DIRECTIONS, recipesCommand.getDirections());
        assertEquals(SERVINGS, recipesCommand.getServings());
        assertEquals(SOURCE, recipesCommand.getSource());
        assertEquals(URL, recipesCommand.getUrl());
        assertEquals(NOTES_ID, recipesCommand.getNotes().getId());
        assertEquals(2, recipesCommand.getCategories().size());
        assertEquals(2, recipesCommand.getIngredients().size());
    }

}