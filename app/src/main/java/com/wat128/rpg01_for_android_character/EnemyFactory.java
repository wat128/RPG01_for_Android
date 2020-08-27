package com.wat128.rpg01_for_android_character;

import android.util.Log;
import static com.wat128.rpg01_for_android_character.EnemyList.*;

public class EnemyFactory {

    public static Enemy create(final int enemyId) {

        final Enemy enemy;

        switch(enemyId) {
            case SLIME:
                enemy = new EnemyData(new SlimeStatus());
                break;
            case NINE_TAILED_FOX:
                enemy = new EnemyData(new NineTailStatus());
                break;
            default:
                Log.d("debug", "EnemyFactory Failed");
                enemy = new EnemyData(new SlimeStatus());
                break;
        }

        return enemy;
    }
}
