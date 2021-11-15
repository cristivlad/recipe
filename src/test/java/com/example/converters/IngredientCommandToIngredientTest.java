package com.example.converters;

import com.example.commands.IngredientsCommand;
import com.example.commands.UnitOfMeasureCommand;
import com.example.domain.Ingredient;
import com.example.domain.Recipe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM = 2L;

    static IngredientCommandToIngredient converter;

    @BeforeAll
    static void beforeAll() {

        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientsCommand()));
    }

    @Test
    public void convert() {
        IngredientsCommand ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(ID_VALUE);
        ingredientsCommand.setAmount(AMOUNT);
        ingredientsCommand.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM);
        ingredientsCommand.setUom(unitOfMeasureCommand);

        Ingredient ingredient = converter.convert(ingredientsCommand);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUom() {
        IngredientsCommand ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(ID_VALUE);
        ingredientsCommand.setAmount(AMOUNT);
        ingredientsCommand.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

        Ingredient ingredient = converter.convert(ingredientsCommand);

        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }
}