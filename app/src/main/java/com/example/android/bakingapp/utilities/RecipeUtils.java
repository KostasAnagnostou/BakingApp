package com.example.android.bakingapp.utilities;

public class RecipeUtils {

    // Constants for Ingredient Measure
    private static final String INGREDIENT_MEASURE_TABLESPOON = "Tablespoon";
    private static final String INGREDIENT_MEASURE_TEASPOON = "Teaspoon";
    private static final String INGREDIENT_MEASURE_CUP = "Cup";
    private static final String INGREDIENT_MEASURE_KG = "Kg";
    private static final String INGREDIENT_MEASURE_GRAMS = "G";
    private static final String INGREDIENT_MEASURE_OUNCE = "Ounce";
    private static final String INGREDIENT_MEASURE_UNIT = "Unit";

    public static String setMeasureText(String measure) {
        // hold and return the Measure Icon Resource
        String measureFullText;
        switch (measure) {
            case "TBLSP":
                measureFullText = INGREDIENT_MEASURE_TABLESPOON;
                break;
            case "TSP":
                measureFullText = INGREDIENT_MEASURE_TEASPOON;
                break;
            case "CUP":
                measureFullText = INGREDIENT_MEASURE_CUP;
                break;
            case "K":
                measureFullText = INGREDIENT_MEASURE_KG;
                break;
            case "G":
                measureFullText = INGREDIENT_MEASURE_GRAMS;
                break;
            case "OZ":
                measureFullText = INGREDIENT_MEASURE_OUNCE;
                break;
            case "UNIT":
                measureFullText = INGREDIENT_MEASURE_UNIT;
                break;
            default:
                measureFullText = INGREDIENT_MEASURE_UNIT;
                break;
        }
        return measureFullText;
    }
}
