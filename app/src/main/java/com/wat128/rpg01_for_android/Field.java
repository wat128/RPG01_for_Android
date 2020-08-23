package com.wat128.rpg01_for_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.wat128.rpg01_for_android.Direction.*;

class EncountObserveHandler {
    Handler handler;
    Runnable runnable;
}

public class Field extends AppCompatActivity {

    private final int BATTLE_RESULT = 1000;

    private Player _player;
    private EncountObserveHandler _encounterObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filed);

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
            @Override
            public void run() {
                if(Encounter.isAccumMoreThanEncounterInterval()) {
                    _player.stopMoving();
                    Intent intent = new Intent(Field.this, Battle.class);
                    startActivityForResult(intent, BATTLE_RESULT);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug", "onActivityResult()");
    }

}
