package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonSandwich = new JSONObject(json);
            Sandwich sandwich = new Sandwich();

            sandwich.setMainName(jsonSandwich.getJSONObject("name").getString("mainName"));
            sandwich.setDescription(jsonSandwich.getString("description"));
            sandwich.setImage(jsonSandwich.getString("image"));
            sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));

            // The processing of arrays is refactored into their own methods
            setIngredients(sandwich, jsonSandwich);
            setAlsoKnownAs(sandwich, jsonSandwich);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void setAlsoKnownAs(Sandwich sandwich, JSONObject jsonSandwich) throws JSONException {
        JSONArray jsonAlsoKnownAs = jsonSandwich.getJSONObject("name").getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = jsonArrayToList(jsonAlsoKnownAs);
        sandwich.setAlsoKnownAs(alsoKnownAs);
    }

    private static void setIngredients(Sandwich sandwich, JSONObject jsonSandwich) throws JSONException {
        JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
        List<String> ingredients = jsonArrayToList(jsonIngredients);
        sandwich.setIngredients(ingredients);
    }

    /*
     * Helper method that transforms a JSONArray of String elements into a List<String>.
     * This clarifies the processing of the setAlsoKnownAs and setIngredients methods.
     */
    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }

        return list;
    }
}
