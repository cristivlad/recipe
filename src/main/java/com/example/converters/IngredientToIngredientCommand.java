package com.example.converters;

import com.example.commands.IngredientsCommand;
import com.example.domain.Ingredient;
import com.google.common.base.Preconditions;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.*;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientsCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientsCommand convert(Ingredient source) {
        if(source == null) {
            return null;
        }

        final IngredientsCommand ingredientsCommand = new IngredientsCommand();
        ingredientsCommand.setId(source.getId());
        if (source.getRecipe() != null) {
            ingredientsCommand.setRecipeId(source.getRecipe().getId());
        }
        ingredientsCommand.setDescription(source.getDescription());
        ingredientsCommand.setAmount(source.getAmount());
        ingredientsCommand.setUom(uomConverter.convert(source.getUom()));
        return ingredientsCommand;
    }
}
