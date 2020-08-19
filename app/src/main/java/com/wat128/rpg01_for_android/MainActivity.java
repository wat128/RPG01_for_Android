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

    private enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    private TextView _textView;
    private Handler _handler;
    private boolean _pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _textView = findViewById(R.id.text);

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
                case R.id.up:       move(Direction.Up, action);     break;
                case R.id.down:     move(Direction.Down, action);   break;
                case R.id.left:     move(Direction.Left, action);   break;
                case R.id.right:    move(Direction.Right, action);  break;
            }

            return false;
        }
    };

    private void move(final Direction direction, final int action) {

        switch(action){
            case MotionEvent.ACTION_DOWN:
                _pressed = true;

                _handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if(!_pressed)
                            return;

                        final float curPos;
                        switch (direction) {
                            case Up:
                                curPos = _textView.getTranslationY();
                                _textView.setTranslationY( curPos - MARGIN);
                                break;
                            case Down:
                                curPos = _textView.getTranslationY();
                                _textView.setTranslationY( curPos + MARGIN);
                                break;
                            case Left:
                                curPos = _textView.getTranslationX();
                                _textView.setTranslationX( curPos - MARGIN);
                                break;
                            case Right:
                                curPos = _textView.getTranslationX();
                                _textView.setTranslationX( curPos + MARGIN);
                                break;
                        }

                        _handler.postDelayed(this, 10L);
                    }
                };

                _handler.post(runnable);
                break;

            case MotionEvent.ACTION_UP:
                _pressed = false;
                break;
        }
    }
}