package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import static com.wat128.rpg01_for_android.Direction.*;

// onWindowFocusChanged()以降で参照すること
class ScreenArea {
    static int cx;
    static int ex;
    static int cy;
    static int ey;
}

class EncountObserveHandler {
    Handler handler;
    Runnable runnable;
}

public class MainActivity extends AppCompatActivity {

    private Player _player;
    EncountObserveHandler _encounterObserver;

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

        _encounterObserver = new EncountObserveHandler();
        _encounterObserver.handler = new Handler();
        _encounterObserver.runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(Encounter.isAccumMoreThanEncounterInterval()) {
                        Log.d("debug", "スラ○ムがあらわれた！");
                }
                _encounterObserver.handler.postDelayed(this, 100L);
            }
        };
        _encounterObserver.handler.post(_encounterObserver.runnable);
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

    private void movePlayer(final Direction direction, final int action, final int boundary) {

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