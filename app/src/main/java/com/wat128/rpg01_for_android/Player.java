package com.wat128.rpg01_for_android;

import android.os.Handler;
import android.view.View;

class MoveHandler {
    Handler handler;
    Runnable runnable;
}

enum Direction {
    Up,
    Down,
    Left,
    Right
}

public class Player {

    final float SPEED = 10.0f;


    private View _chara;
    private MoveHandler _moveHandler;

    public Player(final View img) {

        _chara = img;
        _moveHandler = new MoveHandler();

    }

    public void move(final Direction direction, final int boundary, final boolean keyPressed) {

        if(keyPressed) {
            _moveHandler.handler = new Handler();
            _moveHandler.runnable = new Runnable() {
                @Override
                public void run() {
                    setPos(direction, boundary);
                    Encounter.encounterIntervalAccum++;
                    _moveHandler.handler.postDelayed(this, 10L);
                }
            };
            _moveHandler.handler.post(_moveHandler.runnable);
        }
        else{
            _moveHandler.handler.removeCallbacks(_moveHandler.runnable);
        }

    }

    private void setPos(final Direction direction, final int boundary) {

        final float curPos;
        final float offset;
        switch (direction) {
            case Up:
                curPos = _chara.getTranslationY();
                offset = curPos - SPEED;

                if (boundary <= offset)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary);

                break;
            case Down:
                curPos = _chara.getTranslationY();
                offset = curPos + SPEED;

                if (boundary >= offset)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary);

                break;
            case Left:
                curPos = _chara.getTranslationX();
                offset = curPos - SPEED;

                if (boundary <= offset)
                    _chara.setTranslationX(offset);
                else
                    _chara.setTranslationX(boundary);

                break;
            case Right:
                curPos = _chara.getTranslationX();
                offset = curPos + SPEED;

                if (boundary >= offset)
                    _chara.setTranslationX(offset);
                else
                    _chara.setTranslationX(boundary);

                break;
        }
    }
}
