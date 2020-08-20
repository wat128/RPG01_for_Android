package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import static com.wat128.rpg01_for_android.Player.Direction.*;

// onWindowFocusChanged()以降で参照すること
class ScreenArea {
    static int cx;
    static int ex;
    static int cy;
    static int ey;
}

public class MainActivity extends AppCompatActivity {

    private Player _player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _player = new Player(findViewById(R.id.player));

        Button buttonUp = findViewById(R.id.up);
        buttonUp.setOnTouchListener(_ButtonListener);

        Button buttonDown = findViewById(R.id.down);
        buttonDown.setOnTouchListener(_ButtonListener);

        Button buttonLeft = findViewById(R.id.left);
        buttonLeft.setOnTouchListener(_ButtonListener);

        Button buttonRight = findViewById(R.id.right);
        buttonRight.setOnTouchListener(_ButtonListener);

    }

    private View.OnTouchListener _ButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            final int action = event.getAction();

            switch (v.getId()) {
                case R.id.up:       movePlayer(Up, action, ScreenArea.cy);     break;
                case R.id.down:     movePlayer(Down, action, ScreenArea.ey);   break;
                case R.id.left:     movePlayer(Left, action, ScreenArea.cx);   break;
                case R.id.right:    movePlayer(Right, action, ScreenArea.ex);  break;
            }

            return false;
        }
    };

    private void movePlayer(final Player.Direction direction, final int action, final int boundary) {

        switch(action){
            case MotionEvent.ACTION_DOWN:
                _player.move(direction, boundary, true);
                break;

            case MotionEvent.ACTION_UP:
                _player.move(direction, boundary, false);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = findViewById(R.id.activity_main);
        ScreenArea.cx = -(view.getWidth() / 2);
        ScreenArea.ex = view.getWidth() / 2;
        ScreenArea.cy = -(view.getHeight() / 2);
        ScreenArea.ey = view.getWidth() / 2;
    }
}