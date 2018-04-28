package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject name = sandwichDetails.getJSONObject("name");
        String mainName = name.getString("mainName");

        JSONArray akaArray = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for(int i = 0; i < akaArray.length(); i++) {
            alsoKnownAs.add(akaArray.getString(i));
        }

        String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");
        String description = sandwichDetails.getString("description");
        String image = sandwichDetails.getString("image");

        JSONArray ingredientArray = sandwichDetails.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for(int i = 0; i < ingredientArray.length(); i++) {
            ingredients.add(ingredientArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
