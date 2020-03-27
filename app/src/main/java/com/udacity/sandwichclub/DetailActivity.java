package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adds all Sandwich information to the view
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        // Get all relevant views
        TextView alsoKnownAsTextview;
        TextView descriptionTextView;
        TextView placeOfOriginTextView;
        TextView ingredientsTextView;
        ImageView ingredientsImageView;

        alsoKnownAsTextview = findViewById(R.id.also_known_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        ingredientsImageView = findViewById(R.id.image_iv);

        // Load Image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        // Add name
        setTitle(sandwich.getMainName());

        // Add description
        descriptionTextView.append(sandwich.getDescription());

        // Add place of origin
        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        // Add also known as
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            alsoKnownAs.add(getString(R.string.missing_alsoKnownNotice));
        }
        alsoKnownAsTextview.setText(generateList(sandwich.getAlsoKnownAs()));

        // Add ingredients
        ingredientsTextView.setText(generateList(sandwich.getIngredients()));
    }

    /**
     * Helper method which generates TextView data
     *
     * @param data
     * @return A string with all list elements from data separated by \n
     */
    private String generateList(List<String> data) {
        Iterator i = data.iterator();
        StringBuilder viewData = new StringBuilder("");

        while (i.hasNext()) {
            viewData.append(i.next());

            if (i.hasNext()) {
                viewData.append("\n");
            }
        }

        return viewData.toString();
    }
}
