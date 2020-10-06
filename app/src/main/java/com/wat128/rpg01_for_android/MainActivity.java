package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.wat128.rpg01_for_android.Util.*;
import com.wat128.rpg01_for_android_character.SoundEffect;
import com.wat128.rpg01_for_android_scene.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.game_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ScreenArea.cx < 0
                    && ScreenArea.ex > 0
                    && ScreenArea.cy < 0
                    && ScreenArea.ey > 0)
                {
                    Intent intent = new Intent(MainActivity.this, Field.class);
                    startActivity(intent);
                }
            }
        });

        // 効果音を事前にロードしておく
        SoundEffect.getInstance();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = findViewById(R.id.gameScreenArea);
        ScreenArea.cx = -(view.getWidth() / 2);
        ScreenArea.ex = view.getWidth() / 2;
        ScreenArea.cy = -(view.getHeight() / 2);
        ScreenArea.ey = view.getHeight() / 2;
    }

    @Override
    public void onBackPressed() {

    }
}