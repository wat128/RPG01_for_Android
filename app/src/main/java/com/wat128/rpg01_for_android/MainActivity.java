package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final float MARGIN = 10.0f;

    private TextView textView;

    private Handler handler;
    private boolean isPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        Button buttonUp = findViewById(R.id.up);
        buttonUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isPress = true;

                        handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if(!isPress)
                                    return;

                                final float currentY = textView.getTranslationY();
                                textView.setTranslationY( currentY - MARGIN);

                                handler.postDelayed(this, 10L);
                            }
                        };
                        handler.post(runnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        isPress = false;
                        break;
                }
                return false;
            }
        });
    }
}