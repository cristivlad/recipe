package com.example.commands;

import com.example.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RecipesCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientsCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
    private NotesCommand notes;
}
