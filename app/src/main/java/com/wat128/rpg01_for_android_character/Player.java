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

    public void setImageView(final View img) { _chara = img; }

    public String getName()     { return _status.name; }
    public int getImageId()     { return _status.imageId; }
    public int getId()          { return _status.id; }
    public int getLv()          { return _status.lv; }
    public int getExp()         { return _status.exp; }
    public int getStrength()    { return _status.strength; }
    public int getAgility()     { return _status.agility; }
    public int getResilience()  { return _status.resilience; }
    public int getWisdom()      { return _status.wisdom; }
    public int getLuck()        { return _status.luck; }
    public int getMaxHp()       { return _status.maxHp; }
    public int getMaxMp()       { return _status.maxMp; }
    public int getHp()          { return _status.hp; }
    public int getMp()          { return _status.mp; }
    public int getAttack()      { return _status.attack; }
    public int getDefence()     { return _status.defence; }


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
