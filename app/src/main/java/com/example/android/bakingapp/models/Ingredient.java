package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Ingedient class holds the information for an Ingredient
 * We implement Parcelable to be able to pass objects through activities etc
 * followed an example from here for Parcelable -> http://www.vogella.com/tutorials/AndroidParcelable/article.html
 * I used an example for retrofit from here -- > https://www.journaldev.com/13639/retrofit-android-example-tutorial
 */

public class Ingredient implements Parcelable {

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };
    @SerializedName("quantity")
    @Expose
    private Double mQuantity;
    @SerializedName("measure")
    @Expose
    private String mMeasure;
    @SerializedName("ingredient")
    @Expose
    private String mIngredient;

    // Parcelling Implementation
    public Ingredient(Parcel in) {
        this.mQuantity = in.readDouble();
        this.mMeasure = in.readString();
        this.mIngredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters & Setters
    public Double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Double quantity) {
        this.mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }
}
