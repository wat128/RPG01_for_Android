package com.wat128.rpg01_for_android;

import android.os.Handler;
import android.util.Log;
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

    private static class PlayerHolder {
        private static final Player INSTANCE = new Player();
    }

    private final float SPEED = 10.0f;

    private View _chara;
    private MoveHandler _moveHandler;
    private Status _status;

    private Player() {
        _moveHandler = new MoveHandler();
        _status = new playerStatus();
    }

    public static Player getInstance() {
        return PlayerHolder.INSTANCE;
    }

    public void setImageView(final View img) {
        _chara = img;
    }

    public Status getStatus() {
        return _status;
    }

    public void move(final Direction direction, final int boundary, final boolean keyPressed) {

        if(keyPressed) {
            _moveHandler.handler = new Handler();
            _moveHandler.runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("debug", String.valueOf(Encounter.encounterIntervalAccum));
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

    public void stopMoving() {
        if(_moveHandler.handler != null)
            _moveHandler.handler.removeCallbacks(_moveHandler.runnable);
    }

    private void setPos(final Direction direction, final int boundary) {

        final float curPos;
        final float offset;

        switch (direction) {
            case Up:
                curPos = _chara.getTranslationY();
                offset = curPos - SPEED;
                final float chara_cy = offset - _chara.getHeight() / 2.0f;

                if (boundary <= chara_cy)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary + _chara.getHeight() / 2.0f);

                break;
            case Down:
                curPos = _chara.getTranslationY();
                offset = curPos + SPEED;
                final float chara_ey = offset + _chara.getHeight() / 2.0f;

                if (boundary >= chara_ey)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary - _chara.getHeight() / 2.0f);

                break;
            case Left:
                curPos = _chara.getTranslationX();
                offset = curPos - SPEED;
                final float chara_cx = offset - _chara.getWidth() / 2.0f;

                if (boundary <= chara_cx)
                    _chara.setTranslationX(offset);
                else
                    _chara.setTranslationX(boundary + _chara.getWidth() / 2.0f);

                break;
            case Right:
                curPos = _chara.getTranslationX();
                offset = curPos + SPEED;
                final float chara_ex = offset + _chara.getWidth() / 2.0f;

                if (boundary >= chara_ex)
                    _chara.setTranslationX(offset);
                else
                    _chara.setTranslationX(boundary - _chara.getWidth() / 2.0f);

                break;
        }
    }
}
