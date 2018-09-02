package com.example.android.bakingapp.utilities;

// I used Retrofit Example from here ---> https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792

import com.example.android.bakingapp.retrofit.APIServiceInterface;
import com.example.android.bakingapp.retrofit.RetrofitClient;

public class APIUtils {

    // The Base Url (from the Baking API) for the Recipes
    private static final String BAKING_BASE_API_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static APIServiceInterface getAPIInterface() {
        return RetrofitClient.getClient(BAKING_BASE_API_URL).create(APIServiceInterface.class);
    }
}
