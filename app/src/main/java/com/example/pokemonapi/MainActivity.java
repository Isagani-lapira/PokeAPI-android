package com.example.pokemonapi;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton btSearch, btClear;
    TextView tvIndex, tvPokeName, tvType, tvHP,tvAttack, tvDefense,tvSpecAttack, tvSpecDef,tvSpeed;
    ImageView imgProfile, curveBg,ivdesign;
    Context context = MainActivity.this;
    int color;

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
            String pokemonSearch = etsearch.getText().toString().toLowerCase();

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
        tvPokeName.setText("Who's your Pokemon?");
        tvType.setText(R.string.typeString);
        tvHP.setText(R.string.defaul_value);
        tvAttack.setText(R.string.defaul_value);
        tvDefense.setText(R.string.defaul_value);
        tvSpecAttack.setText(R.string.defaul_value);
        tvSpecDef.setText(R.string.defaul_value);
        tvSpeed.setText(R.string.defaul_value);
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
                String pokemonIndex = "#"+tvIndex.getText()+id;

                //for tvPokeName
                JSONObject species = response.getJSONObject("species");
                String pokemonName = species.getString("name")
                        .toUpperCase();


                //for tvType
                JSONArray type = response.getJSONArray("types");
                JSONObject typeOBJ = type.getJSONObject(index).getJSONObject("type");
                String typeVal = typeOBJ.getString("name").toUpperCase();

                //for tvHP
                JSONArray stats = response.getJSONArray("stats");

                //traverse all the values in the  stat array
                for(int i = 0; i<stats.length(); i++){
                    JSONObject statsOBJ = stats.getJSONObject(i);

                    if(i==0)
                        hpVal = statsOBJ.getString("base_stat");
                    else if(i == 1)
                        attack = statsOBJ.getString("base_stat");
                    else if(i == 2)
                        defense = statsOBJ.getString("base_stat");
                    else if(i==3)
                        specialAtt = statsOBJ.getString("base_stat");
                    else if(i==4)
                        specialDef = statsOBJ.getString("base_stat");
                    else if(i==5)
                        speed = statsOBJ.getString("base_stat");
                }


                //display the image
                Picasso.get().load(front_default).into(imgProfile);

                changeTheme(typeVal);

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

    private void changeTheme(String pokemonType) {

        switch (pokemonType){
            case "FIGHTING":
                color = R.color.fighter;
                break;
            case "PSYCHIC":
                color = R.color.pyschic;
                break;
            case "POISON":
                color = R.color.poison;
                break;
            case "DRAGON":
                color = R.color.dragon;
                break;
            case "GHOST":
                color = R.color.ghost;
                break;
            case "DARK":
                color = R.color.dark;
                break;
            case "GROUND":
                color = R.color.ground;
                break;
            case "FIRE":
                color = R.color.fire;
                break;
            case "FAIRY":
                color = R.color.fairy;
                break;
            case "WATER":
                color = R.color.water;
                break;
            case "FLYING":
                color = R.color.flying;
                break;
            case "NORMAL":
                color = R.color.normal;
                break;
            case "ROCK":
                color = R.color.rock;
                break;
            case "ELECTRIC":
                color = R.color.electric;
                break;
            case "BUG":
                color = R.color.bug;
                break;
            case "GRASS":
                color = R.color.grass;
                break;
            case "ICE":
                color = R.color.ice;
                break;
            case "STEEL":
                color = R.color.steel;
                break;
            default:
                color = R.color.purple_700;
        }

        //change the color of all svg file
        tvType.setBackgroundColor(getColor(color));
        curveBg.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_IN);
        ivdesign.setColorFilter(ContextCompat.getColor(this,color),PorterDuff.Mode.SRC_IN);

        changeColor(color); //change the text color

        //change the statusbar color
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, color));
    }

    void changeColor(int color){
        tvIndex.setTextColor(getColor(color));
        tvPokeName.setTextColor(getColor(color));
        tvType.setTextColor(getColor(R.color.white));
        tvHP.setTextColor(getColor(color));
        tvAttack.setTextColor(getColor(color));
        tvDefense.setTextColor(getColor(color));
        tvSpecAttack.setTextColor(getColor(color));
        tvSpecDef.setTextColor(getColor(color));
        tvSpeed.setTextColor(getColor(color));
    }

    //function where initialize all the views
    private void initialize() {
        curveBg = findViewById(R.id.ivcurveBg);
        imgProfile = findViewById(R.id.ivPokemon);
        ivdesign = findViewById(R.id.ivdesign);
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


        imgProfile.setAnimation(AnimationUtils.loadAnimation(this,R.anim.shaking));
    }

}