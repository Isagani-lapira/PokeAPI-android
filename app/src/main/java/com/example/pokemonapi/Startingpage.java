package com.example.pokemonapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Startingpage extends AppCompatActivity {

    Button start;
    Context context = Startingpage.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startingpage);

        initialize();
        listener();
    }

    private void listener() {
        start.setOnClickListener(v->{
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
        });
    }

    private void initialize() {
        start = findViewById(R.id.btStart);
        Animation scalingEffect = AnimationUtils.loadAnimation(context,R.anim.start_zooming);
        start.setAnimation(scalingEffect);
    }
}