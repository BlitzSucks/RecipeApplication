package com.example.recipeapplication;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Aniq on 26/4/2019.
 */
@IgnoreExtraProperties
public class Recipe
{
    private String recipeID, recipeName, recipeType, recipeIngredient, recipeInstruction, recipeNote;

    public Recipe ()
    {}

    public Recipe(String recipeID, String recipeName, String recipeType, String recipeIngredient, String recipeInstruction, String recipeNote)
    {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeType = recipeType;
        this.recipeIngredient = recipeIngredient;
        this.recipeInstruction = recipeInstruction;
        this.recipeNote = recipeNote;
    }

    public String getRecipeID()
    {
        return recipeID;
    }

    public String getRecipeName()
    {
        return recipeName;
    }

    public String getRecipeIngredient()
    {
        return recipeIngredient;
    }

    public String getRecipeInstruction()
    {
        return recipeInstruction;
    }

    public String getRecipeNote()
    {
        return recipeNote;
    }

    public String getRecipeType()
    {
        return recipeType;
    }
}