package com.example.recipeapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddRecipePage extends AppCompatActivity
{
    public static final String RECIPE_NAME = "com.example.recipeapplication.recipename";
    public static final String RECIPE_ID = "com.example.recipeapplication.recipeid";

    private EditText NameRecipe, IngredientRecipe, InstructionRecipe, NoteRecipe;
    private Spinner TypeRecipe;
    private Button AddRecipe;

    ArrayList <Recipe> recipes = new ArrayList<>();

    DatabaseReference databaseRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipeadd);

        databaseRecipes = FirebaseDatabase.getInstance().getReference("recipes");

        NameRecipe = findViewById(R.id.nameRecipe);
        IngredientRecipe = findViewById(R.id.ingredientRecipe);
        InstructionRecipe = findViewById(R.id.instructionRecipe);
        NoteRecipe = findViewById(R.id.noteRecipe);
        TypeRecipe = findViewById(R.id.typeRecipe);

        AddRecipe = findViewById(R.id.addRecipe);

        recipes = new ArrayList<>();

        AddRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addRecipe();
            }
        });
    }

    private void addRecipe()
    {

        String name = NameRecipe.getText().toString().trim();
        String type = TypeRecipe.getSelectedItem().toString();
        String ingredient = IngredientRecipe.getText().toString().trim();
        String instruction = InstructionRecipe.getText().toString().trim();
        String note = NoteRecipe.getText().toString().trim();

        if (!TextUtils.isEmpty(name))
        {
            String id = databaseRecipes.push().getKey();

            Recipe recipe = new Recipe (id, name, type, ingredient, instruction,  note);

            assert id != null;
            databaseRecipes.child(id).setValue(recipe);

            NameRecipe.setText("");
            IngredientRecipe.setText("");
            InstructionRecipe.setText("");
            NoteRecipe.setText("");

            Toast.makeText(this,"Recipe added", Toast.LENGTH_LONG).show();
        }
        else if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(ingredient)&&!TextUtils.isEmpty(instruction))
        {
            Toast.makeText(this, "You might have not entered either name, instruction and/or ingredient", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //attaching value event listener
        databaseRecipes.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                //clearing the previous player list
                recipes.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    //getting recipe
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    //adding recipe to the list
                    recipes.add(recipe);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }}