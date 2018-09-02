package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("ingredients")
    @Expose
    private ArrayList<Ingredient> mIngredients;
    @SerializedName("steps")
    @Expose
    private ArrayList<Step> mSteps;
    @SerializedName("servings")
    @Expose
    private Integer mServings;
    @SerializedName("image")
    @Expose
    private String mImage;

    // Parcelling Implementation
    public Recipe(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.mSteps = in.createTypedArrayList(Step.CREATOR);
        this.mServings = in.readInt();
        this.mImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeList(mIngredients);
        parcel.writeList(mSteps);
        parcel.writeInt(mServings);
        parcel.writeString(mImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters & Setters
    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

    public Integer getServings() {
        return mServings;
    }

    public void setServings(Integer servings) {
        this.mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
}
