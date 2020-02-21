package com.example.recipeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{

    private Button SearchRecipe, AddRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchRecipe = findViewById(R.id.searchRecipe);
        AddRecipe = findViewById(R.id.addRecipe);

        SearchRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(MainActivity.this, SearchRecipePage.class);
                startActivity(mIntent);
                finish();
            }
        });

        AddRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mIntent = new Intent(MainActivity.this, AddRecipePage.class);
                startActivity(mIntent);
                finish();
            }
        });
    }
}