package com.example.android.bakingapp.retrofit;

import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

// The Interface to communicate with Baking Database API
// We are executing a @GET Request
// I used an example for retrofit from here -- > https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792

public interface APIServiceInterface {
    // Get the Recipes from the Baking Movies URL
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getBakingRecipes();
}
