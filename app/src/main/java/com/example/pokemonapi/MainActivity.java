package com.example.pokemonapi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etsearch;
    Button btSearch, btClear;
    TextView tvIndex, tvPokeName, tvType, tvHP,tvAttack, tvDefense,tvSpecAttack, tvSpecDef,tvSpeed;
    ImageView imgProfile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        listener();
    }

    //function where all event will occur
    private void listener() {

        //search the pokemon
        btSearch.setOnClickListener(v->{
            String pokemonSearch = etsearch.getText().toString();

            if(pokemonSearch.equals(""))
                Toast.makeText(this, "Enter a pokemon first", Toast.LENGTH_SHORT).show();
            else
                APICall(pokemonSearch);

        });


        //clear the details
        btClear.setOnClickListener(v-> clearContent());

    }

    private void clearContent() {
        etsearch.setText("");
        imgProfile.setImageResource(R.drawable.pngegg);
        tvIndex.setText(R.string.indexID);
        tvPokeName.setText(R.string.pkName);
        tvType.setText(R.string.typeString);
        tvHP.setText(R.string.hpString);
        tvAttack.setText(R.string.attackString);
        tvDefense.setText(R.string.defenseString);
        tvSpecAttack.setText(R.string.specialAttString);
        tvSpecDef.setText(R.string.specDefString);
        tvSpeed.setText(R.string.speedString);
    }

    private void APICall(String pokemon) {

        clearContent();
        String url = "https://pokeapi.co/api/v2/pokemon/"+pokemon; // url with what pokemon to be find
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.start();

        //get response
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,url,
                null, response -> {

            int index = 0; //default index if it is not necessary to traverse the array

            String hpVal = "";
            String attack = "";
            String defense = "";
            String specialAtt = "";
            String specialDef = "";
            String speed = "";

            try {

                //pokemon image
                JSONObject profile = response.getJSONObject("sprites");
                JSONObject other = profile.getJSONObject("other");
                JSONObject home = other.getJSONObject("home");
                String front_default = home.getString("front_default");


                //for tvindex
                String id = response.getString("id");
                String pokemonIndex = tvIndex.getText()+id;

                //for tvPokeName
                JSONObject species = response.getJSONObject("species");
                String pokemonName = tvPokeName.getText()+species.getString("name")
                        .toUpperCase();


                //for tvType
                JSONArray type = response.getJSONArray("types");
                JSONObject typeOBJ = type.getJSONObject(index).getJSONObject("type");
                String typeVal = tvType.getText()+typeOBJ.getString("name").toUpperCase();

                //for tvHP
                JSONArray stats = response.getJSONArray("stats");

                //traverse all the values in the  stat array
                for(int i = 0; i<stats.length(); i++){
                    JSONObject statsOBJ = stats.getJSONObject(i);

                    if(i==0)
                        hpVal = " "+tvHP.getText()+statsOBJ.getString("base_stat");
                    else if(i == 1)
                        attack = " "+tvAttack.getText()+statsOBJ.getString("base_stat");
                    else if(i == 2)
                        defense = " "+tvDefense.getText()+statsOBJ.getString("base_stat");
                    else if(i==3)
                        specialAtt = " "+tvSpecAttack.getText()+statsOBJ.getString("base_stat");
                    else if(i==4)
                        specialDef = " "+tvSpecDef.getText()+statsOBJ.getString("base_stat");
                    else if(i==5)
                        speed = " "+tvSpeed.getText()+statsOBJ.getString("base_stat");
                }


                //display the image
                Picasso.get().load(front_default).into(imgProfile);

                //displaying values
                tvIndex.setText(pokemonIndex);
                tvPokeName.setText(pokemonName);
                tvType.setText(typeVal);
                tvHP.setText(hpVal);
                tvAttack.setText(attack);
                tvDefense.setText(defense);
                tvSpecAttack.setText(specialAtt);
                tvSpecDef.setText(specialDef);
                tvSpeed.setText(speed);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

            //build alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(R.layout.alert_dial);

            //create dialog
            Dialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // make the background transparent

            dialog.show(); //display the dialog
        });

        queue.add(objectRequest);

    }

    //function where initialize all the views
    private void initialize() {
        imgProfile = findViewById(R.id.ivPokemon);
        etsearch = findViewById(R.id.etsearch);
        btSearch = findViewById(R.id.btSearch);
        btClear = findViewById(R.id.btClear);
        tvIndex = findViewById(R.id.tvIndex);
        tvPokeName = findViewById(R.id.tvPokeName);
        tvType = findViewById(R.id.tvType);
        tvHP = findViewById(R.id.tvHP);
        tvAttack = findViewById(R.id.tvAttack);
        tvDefense = findViewById(R.id.tvDefense);
        tvSpecAttack = findViewById(R.id.tvSpecAttack);
        tvSpecDef = findViewById(R.id.tvSpecDef);
        tvSpeed = findViewById(R.id.tvSpeed);

    }

}