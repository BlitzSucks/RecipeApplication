package com.example.recipeapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class SearchRecipePage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipesearch);

        Button SubmitButton = findViewById(R.id.submitButton);
        final Spinner RecipeType = findViewById(R.id.recipeType);

        SubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (RecipeType.getSelectedItem().toString().equals("Breakfast"))
                {
                    Intent mIntent = new Intent(SearchRecipePage.this, BreakfastSearch.class);
                    startActivity(mIntent);
                    finish();
                }
                else if (RecipeType.getSelectedItem().toString().equals("Lunch"))
                {
                    Intent mIntent = new Intent(SearchRecipePage.this, LunchSearch.class);
                    startActivity(mIntent);
                    finish();
                }
                else if (RecipeType.getSelectedItem().toString().equals("Dinner"))
                {
                    Intent mIntent = new Intent(SearchRecipePage.this, DinnerSearch.class);
                    startActivity(mIntent);
                    finish();
                }
                else
                {
                    Intent mIntent = new Intent(SearchRecipePage.this, DrinkSearch.class);
                    startActivity(mIntent);
                    finish();
                }
            }

        });
    }
}