package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Extracts Sandwich JSON information and returns a new Sandwich object.
     * In case something goes wrong the function will return null.
     * @param json String with Sandwich json information
     * @return the Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {

        // START - JSON node name initialization

        final String JSON_NAME = "name";

        // main name and aliases are children from the name node
        final String JSON_MAIN_NAME = "mainName";
        final String JSON_ALSO_KNOWN_AS = "alsoKnownAs";

        final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";

        final String JSON_DESCRIPTION = "description";

        final String JSON_IMAGE = "image";

        final String JSON_INGREDIENTS = "ingredients";

        // FINISH - JSON node name initialization finish

        // Fields which we need to retrieve from the JSON
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        Sandwich sandwich = null;
        JSONObject sandwichJson;
        try {
            sandwichJson = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return sandwich;
        }

        // Retrieve Sandwich information from sandwichJson
        try {
            JSONObject nameObject = sandwichJson.getJSONObject(JSON_NAME);
            // Retrieve main name
            mainName = nameObject.getString(JSON_MAIN_NAME);

            // Retrieve aliases
            JSONArray aliases = nameObject.getJSONArray(JSON_ALSO_KNOWN_AS);

            alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < aliases.length(); i++) {
                alsoKnownAs.add(aliases.getString(i));
            }

            // Retrieve place of origin
            placeOfOrigin = sandwichJson.getString(JSON_PLACE_OF_ORIGIN);

            // Retrieve description
            description = sandwichJson.getString(JSON_DESCRIPTION);

            // Retrieve image
            image = sandwichJson.getString(JSON_IMAGE);

            // Retrieve ingredients
            // TODO Refactor duplicate code

            JSONArray arrayIngredients = sandwichJson.getJSONArray(JSON_INGREDIENTS);
            ingredients = new ArrayList<String>();
            for (int i = 0; i < arrayIngredients.length(); i++) {
                ingredients.add(arrayIngredients.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return sandwich;
        }

        sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        return sandwich;
    }
}
