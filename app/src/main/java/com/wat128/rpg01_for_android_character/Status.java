package com.wat128.rpg01_for_android_character;

import androidx.appcompat.app.AppCompatActivity;
import com.wat128.rpg01_for_android.*;
import static com.wat128.rpg01_for_android_character.BattlerList.*;

public class Status extends AppCompatActivity {
    String name;
    int imageId;
    int id;
    int lv;
    int exp;
    int strength;
    int agility;
    int resilience;
    int wisdom;
    int luck;
    int maxHp;
    int maxMp;
    int hp;
    int mp;
    int attack;
    int defence;
}

class playerStatus extends Status {

    public playerStatus() {
        super.name = "Hero";
        super.imageId = 0; // TODO:
        super.id = PLAYER;
        super.lv = 1;
        super.exp = 0;
        super.strength = 5;
        super.agility = 5;
        super.resilience = 5;
        super.wisdom = 5;
        super.luck = 5;
        super.maxHp = 15;
        super.maxMp = 15;
        super.hp = maxHp;
        super.mp = maxMp;
        super.attack = strength;
        super.defence = resilience;
    }
}

class SlimeStatus extends Status {

    public SlimeStatus() {
        name = "スライム";
        imageId = R.drawable.slime;
        id = SLIME;
        lv = 1;
        exp = 0;
        strength = 2;
        agility = 3;
        resilience = 2;
        wisdom = 7;
        luck = 7;
        maxHp = 10;
        maxMp = 5;
        hp = maxHp;
        mp = maxMp;
        attack = strength;
        defence = resilience;
    }
}

class NineTailStatus extends Status {

    public NineTailStatus() {
        name = "キュウビ";
        imageId = R.drawable.nine_taled_fox;
        id = NINE_TAILED_FOX;
        lv = 1;
        exp = 0;
        strength = 10;
        agility = 3;
        resilience = 2;
        wisdom = 7;
        luck = 7;
        maxHp = 10;
        maxMp = 5;
        hp = maxHp;
        mp = maxMp;
        attack = strength;
        defence = resilience;
    }
}