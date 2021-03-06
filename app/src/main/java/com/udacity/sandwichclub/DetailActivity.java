package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // There is no need to create member fields of this elements
        // because they are only used in this method.
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);

        setAlsoKnownAs(sandwich, alsoKnownAsTextView);
        setOrigin(sandwich, originTextView);
        ingredientsTextView.setText(join(sandwich.getIngredients()));
        descriptionTextView.setText(sandwich.getDescription());
    }

    private void setOrigin(Sandwich sandwich, TextView originTextView) {
        String placeOfOrigin = sandwich.getPlaceOfOrigin();

        if("".equals(placeOfOrigin)) {
            originTextView.setText(R.string.unknown);

        } else {
            originTextView.setText(placeOfOrigin);
        }
    }

    private void setAlsoKnownAs(Sandwich sandwich, TextView alsoKnownAsTextView) {
        String alsoKnownAs = join(sandwich.getAlsoKnownAs());

        if("".equals(alsoKnownAs)) {
            alsoKnownAsTextView.setText(String.format("Only known as '%s'", sandwich.getMainName()));

        } else {
            alsoKnownAsTextView.setText(alsoKnownAs);
        }
    }

    /*
     * I decided to join the list of elements into a single String separated by commas.
     */
    private String join(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for(int index = 0; index < list.size(); index++) {
            String item = list.get(index);

            if(index == 0) {
                sb.append(item);

            } else if(index == list.size() - 1){
                sb.append(" and ").append(item);

            } else {
                sb.append(", ").append(item);
            }
        }

        return sb.toString();
    }

}
