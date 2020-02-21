package com.example.recipeapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Aniq on 15/7/2019.
 */

public class DinnerSearch extends AppCompatActivity
{
    //we will use these constants later to pass the artist name and id to another activity
    public static final String RECIPE_NAME = "com.example.recipeapplication.recipename";
    public static final String RECIPE_ID = "com.example.recipeapplication.recipeid";

    private ListView DinnerListview;

    ArrayList<Recipe> recipes = new ArrayList<>();

    //our database reference object
    DatabaseReference databaseRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchdinner);

        //getting the reference of players node
        databaseRecipes = FirebaseDatabase.getInstance().getReference("recipes");

        ListView DinnerListview = findViewById(R.id.dinnerListview);

        DinnerListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Recipe recipe = recipes.get(i);
                showUpdateDeleteDialog(recipe.getRecipeID(), recipe.getRecipeName());
                return true;
            }
        });
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
                    //getting player
                    Recipe recipe = postSnapshot.getValue(Recipe.class);
                    if(recipe.getRecipeType().equals("Dinner"))
                    {
                        //adding player to the list
                        recipes.add(recipe);
                    }
                }

                //creating adapter
                DinnerRefer recipeAdapter = new DinnerRefer(DinnerSearch.this, recipes);

                //attaching adapter to the list view
                ListView DinnerListview = findViewById(R.id.dinnerListview);
                DinnerListview.setAdapter(recipeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private boolean updateRecipe (String id, String name, String type, String ingredient, String instruction, String note )
    {
        //getting the specified player reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("recipes").child(id);

        //updating player
        Recipe recipe = new Recipe(id, name, type, ingredient, instruction, note);
        dR.setValue(recipe);
        Toast.makeText(getApplicationContext(), "Recipe Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String recipeID, final String recipeName)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatedialog, null);
        dialogBuilder.setView(dialogView);

        final EditText EditName = dialogView.findViewById(R.id.editName);
        final EditText EditIngredient = dialogView.findViewById(R.id.editIngredient);
        final EditText EditInstruction = dialogView.findViewById(R.id.editInstruction);
        final EditText EditNote = dialogView.findViewById(R.id.editNote);

        final Spinner EditType = dialogView.findViewById(R.id.editType);

        final Button DeleteRecipe = dialogView.findViewById(R.id.deleteRecipe);
        final Button UpdateRecipe = dialogView.findViewById(R.id.updateRecipe);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        UpdateRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String recipeName = EditName.getText().toString().trim();
                String recipeIngredient = EditIngredient.getText().toString().trim();
                String recipeInstruction = EditInstruction.getText().toString().trim();
                String recipeNote = EditNote.getText().toString().trim();
                String recipeType = EditType.getSelectedItem().toString();
                if (!TextUtils.isEmpty(recipeName))
                {
                    updateRecipe(recipeID, recipeName, recipeType, recipeIngredient, recipeInstruction, recipeNote);
                    b.dismiss();
                }
            }
        });

        DeleteRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*
                 * we will code this method to delete the artist
                 * */
                //attaching listener to listview

                DinnerListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
                    {
                        Recipe recipe = recipes.get(i);
                        showUpdateDeleteDialog(recipe.getRecipeID(), recipe.getRecipeName());
                        return true;
                    }
                });

                DeleteRecipe.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        deleteRecipe(recipeID);
                        b.dismiss();
                    }
                });
            }
        });
    }
    private boolean deleteRecipe(String recipeID)
    {
        //getting the specified player reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("recipes").child(recipeID);

        //removing recipe
        dR.removeValue();

        return true;
    }
}