package com.wat128.rpg01_for_android_character;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.wat128.rpg01_for_android.R;
import com.wat128.rpg01_for_android.Util.*;
import com.wat128.rpg01_for_android_scene.Encounter;

public class Player extends Battler {

    private static class PlayerHolder {
        private static final Player INSTANCE = new Player();
    }

    private class Move {
        Handler handler;
        Runnable runnable;
    }

    private final float SPEED = 10.0f;
    private ImageView _chara;
    private Direction _direction;
    private Move _move;

    private Player() {
        super(new playerStatus(), new PlayerSkillList());
        super._normalAttackAnime = R.raw.attack_01;
        _chara = null;
        _direction = Direction.Down;
        _move = new Move();
    }

    public static Player getInstance() {
        return PlayerHolder.INSTANCE;
    }

    public void setImageView(final ImageView img) {
        _chara = img;
        Glide.with(_chara.getContext())
                .load(R.raw.hero_down)
                .placeholder(R.drawable.hero_down)
                .into(_chara);
    }

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

        Log.d("debud", "PlayerX : " + _chara.getX());
        Log.d("debud", "PlayerY : " + _chara.getY());

        final float curPos;
        final float offset;

        switch (direction) {
            case Up:
                if(_direction != direction) {
                    _direction = direction;
                    Glide.with(_chara.getContext())
                            .load(R.raw.hero_up)
                            .placeholder(R.drawable.hero_up)
                            .into(_chara);
                }
                curPos = _chara.getTranslationY();
                offset = curPos - SPEED;
                final float chara_cy = offset - _chara.getHeight() / 2.0f;

                if (boundary <= chara_cy)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary + _chara.getHeight() / 2.0f);

                break;
            case Down:
                if(_direction != direction) {
                    _direction = direction;
                    Glide.with(_chara.getContext())
                            .load(R.raw.hero_down)
                            .placeholder(R.drawable.hero_down)
                            .into(_chara);
                }

                curPos = _chara.getTranslationY();
                offset = curPos + SPEED;
                final float chara_ey = offset + _chara.getHeight() / 2.0f;

                if (boundary >= chara_ey)
                    _chara.setTranslationY(offset);
                else
                    _chara.setTranslationY(boundary - _chara.getHeight() / 2.0f);

                break;
            case Left:
                if(_direction != direction) {
                    _direction = direction;
                    Glide.with(_chara.getContext())
                            .load(R.raw.hero_left)
                            .placeholder(R.drawable.hero_left)
                            .into(_chara);
                }

                curPos = _chara.getTranslationX();
                offset = curPos - SPEED;
                final float chara_cx = offset - _chara.getWidth() / 2.0f;

                if (boundary <= chara_cx)
                    _chara.setTranslationX(offset);
                else
                    _chara.setTranslationX(boundary + _chara.getWidth() / 2.0f);

                break;
            case Right:
                if(_direction != direction) {
                    _direction = direction;
                    Glide.with(_chara.getContext())
                            .load(R.raw.hero_right)
                            .placeholder(R.drawable.hero_right)
                            .into(_chara);
                }

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
