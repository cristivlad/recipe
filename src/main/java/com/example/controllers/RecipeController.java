package com.example.controllers;

import com.example.commands.RecipesCommand;
import com.example.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipesCommand());

        return "recipe/recipeForm";
    }

    @GetMapping
    @RequestMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipesCommand command) {
        RecipesCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping
    @RequestMapping("recipe/delete/{id}")
    public String deleteById(@PathVariable String id) {

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
