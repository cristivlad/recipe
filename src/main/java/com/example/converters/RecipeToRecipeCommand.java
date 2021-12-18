package com.example.converters;

import com.example.commands.RecipesCommand;
import com.example.domain.Recipe;
import com.google.common.base.Preconditions;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.*;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipesCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipesCommand convert(Recipe source) {
        if(source == null) {
            return null;
        }

        final RecipesCommand recipesCommand = new RecipesCommand();
        recipesCommand.setId(source.getId());
        recipesCommand.setCookTime(source.getCookTime());
        recipesCommand.setPrepTime(source.getPrepTime());
        recipesCommand.setDescription(source.getDescription());
        recipesCommand.setDifficulty(source.getDifficulty());
        recipesCommand.setDirections(source.getDirections());
        recipesCommand.setServings(source.getServings());
        recipesCommand.setSource(source.getSource());
        recipesCommand.setUrl(source.getUrl());
        recipesCommand.setImage(source.getImage());
        recipesCommand.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipesCommand.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipesCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipesCommand;
    }
}
