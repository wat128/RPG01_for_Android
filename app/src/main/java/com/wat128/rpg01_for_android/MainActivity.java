package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import static com.wat128.rpg01_for_android.Player.Direction.*;

public class MainActivity extends AppCompatActivity {

    private Player player;
    int cx, ex, cy, ey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new Player(findViewById(R.id.player));

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
                case R.id.up:       movePlayer(Up, action, cy);     break;
                case R.id.down:     movePlayer(Down, action, ey);   break;
                case R.id.left:     movePlayer(Left, action, cx);   break;
                case R.id.right:    movePlayer(Right, action, ex);  break;
            }

            return false;
        }
    };

    private void movePlayer(final Player.Direction direction, final int action, final int boundary) {
        switch(action){
            case MotionEvent.ACTION_DOWN:
                player.move(direction, boundary, true);
                break;

            case MotionEvent.ACTION_UP:
                player.move(direction, boundary, false);
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = findViewById(R.id.activity_main);
        cx = -(view.getWidth() / 2);
        ex = view.getWidth() / 2;
        cy = -(view.getHeight() / 2);
        ey = view.getWidth() / 2;
    }
}