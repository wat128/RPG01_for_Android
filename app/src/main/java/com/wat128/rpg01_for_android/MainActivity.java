package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        final float margin = 10.0f;

        Button buttonUp = findViewById(R.id.up);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float currentY = textView.getTranslationY();
                textView.setTranslationY( currentY - margin);
            }
        });

        Button buttonDown = findViewById(R.id.down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float currentY = textView.getTranslationY();
                textView.setTranslationY( currentY + margin);
            }
        });

        Button buttonLeft = findViewById(R.id.left);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float currentX = textView.getTranslationX();
                textView.setTranslationX( currentX - margin);
            }
        });

        Button buttonRight = findViewById(R.id.right);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float currentX = textView.getTranslationX();
                textView.setTranslationX( currentX + margin);
            }
        });
    }
}
