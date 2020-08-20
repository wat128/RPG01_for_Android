package com.wat128.rpg01_for_android;

import android.os.Handler;
import android.view.View;

public class Player {

    final float SPEED = 10.0f;

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    private View chara;
    private boolean _pressingMoveKey;

    public Player(final View img) {

        chara = img;
    }

    public void move(final Direction direction, final int boundary, final boolean keyPressed) {

        _pressingMoveKey = keyPressed;

        final Handler moveHandler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if( !_pressingMoveKey )
                    return;

                final float curPos;
                final float offset;
                switch ( direction ) {
                    case Up:
                        curPos = chara.getTranslationY();
                        offset = curPos - SPEED;

                        if(boundary <= offset)
                            chara.setTranslationY( offset );
                        else
                            chara.setTranslationY( boundary );

                        break;
                    case Down:
                        curPos = chara.getTranslationY();
                        offset = curPos + SPEED;

                        if(boundary >= offset)
                            chara.setTranslationY( offset );
                        else
                            chara.setTranslationY( boundary );

                        break;
                    case Left:
                        curPos = chara.getTranslationX();
                        offset = curPos - SPEED;

                        if(boundary <= offset)
                            chara.setTranslationX( offset );
                        else
                            chara.setTranslationX( boundary );

                        break;
                    case Right:
                        curPos = chara.getTranslationX();
                        offset = curPos + SPEED;

                        if(boundary >= offset)
                            chara.setTranslationX( offset );
                        else
                            chara.setTranslationX( boundary );

                        break;
                }

                moveHandler.postDelayed(this, 10L);
            }
        };

        moveHandler.post( runnable );
    }
}
