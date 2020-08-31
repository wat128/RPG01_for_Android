package com.wat128.rpg01_for_android_character;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.wat128.rpg01_for_android.Util.*;
import com.wat128.rpg01_for_android_scene.Encounter;


class MoveHandler {
    Handler handler;
    Runnable runnable;
}

public class Player extends Battler {

    private static class PlayerHolder {
        private static final Player INSTANCE = new Player();

    }

    private class Move {
        Handler handler;
        Runnable runnable;
    }

    private final float SPEED = 10.0f;
    private View _chara;
    private Move _move;

    private Player() {
        super(new playerStatus());
        _move = new Move();
    }

    public static Player getInstance() {
        return PlayerHolder.INSTANCE;
    }

    public void setImageView(final View img) { _chara = img; }

    public void move(final Direction direction, final int boundary, final boolean keyPressed) {

        if(_move.handler != null)
            stopMoving();

        if(keyPressed) {
            _move.handler = new Handler();
            _move.runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("debug", String.valueOf(Encounter.encounterIntervalAccum));
                    setPos(direction, boundary);
                    Encounter.encounterIntervalAccum++;
                    _move.handler.postDelayed(this, 10L);
                }
            };
            _move.handler.post(_move.runnable);
        }
    }

    public void stopMoving() {
        if(_move.handler != null) {
            _move.handler.removeCallbacks(_move.runnable);
            _move.handler = null;
            _move.runnable = null;
        }
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

    @Override
    public int growUp(final int exp) {
        final int growUpNum = _status.growUp(exp);;

        if(_status.lv.val == 2) {
            _skills.add(new Fire());
            _skills.add(new PowerUp());
            _skills.add(new Heal());
        }

        return growUpNum;
    }
}
