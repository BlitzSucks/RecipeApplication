package com.example.recipeapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BreakfastRefer extends ArrayAdapter <Recipe>
{
    private Activity context;
    List <Recipe> recipes;

    public BreakfastRefer (Activity context, ArrayList<Recipe> recipes)
    {
        super(context, R.layout.referbreakfast, recipes);
        this.context = context;
        this.recipes = recipes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.referbreakfast, null, true);

        TextView NameRefer = listViewItem.findViewById(R.id.nameRefer);
        TextView TypeRefer = listViewItem.findViewById(R.id.typeRefer);

        Recipe player = recipes.get(position);

        NameRefer.setText(player.getRecipeName());
        TypeRefer.setText(player.getRecipeType());

        return listViewItem;
    }
}