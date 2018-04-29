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

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);

        TextView originLabel = findViewById(R.id.origin_label);
        TextView descriptionLabel = findViewById(R.id.description_label);
        TextView ingredientsLabel = findViewById(R.id.ingredients_label);
        TextView alsoKnownLabel = findViewById(R.id.also_known_label);

        descriptionTv.setText(sandwich.getDescription());

        if(descriptionTv.length() == 0) {
            descriptionTv.setText(R.string.no_data_error_message);
        }

        originTv.setText(sandwich.getPlaceOfOrigin());

        if(originTv.length() == 0) {
            originTv.setText(R.string.no_data_error_message);
        }

        List<String> akaArray = sandwich.getAlsoKnownAs();

        if(akaArray.size() > 0) {
            for (int i = 0; i < akaArray.size(); i++) {
                alsoKnownTv.append(akaArray.get(i));

                if (i < akaArray.size() - 1) {
                    alsoKnownTv.append(", ");
                }
            }
        }

        if(akaArray.size() == 0) {
            alsoKnownTv.setText(R.string.no_data_error_message);
        }

        List<String> ingredientsArray = sandwich.getIngredients();

        for(int i = 0; i < ingredientsArray.size(); i++) {
            ingredientsTv.append(ingredientsArray.get(i));

            if(i < ingredientsArray.size() - 1) {
                ingredientsTv.append(", ");
            }
        }

        if(ingredientsArray.size() == 0) {
            ingredientsTv.setText(R.string.no_data_error_message);
        }
    }
}
