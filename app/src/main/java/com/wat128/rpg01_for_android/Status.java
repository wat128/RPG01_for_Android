package com.wat128.rpg01_for_android;

import androidx.appcompat.app.AppCompatActivity;

import static com.wat128.rpg01_for_android.EnemyList.*;

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
        name = "Hero";
        imageId = 0; // TODO:
        lv = 1;
        exp = 0;
        strength = 5;
        agility = 5;
        resilience = 5;
        wisdom = 5;
        luck = 5;
        maxHp = 15;
        maxMp = 15;
        hp = maxHp;
        mp = maxMp;
        attack = strength;
        defence = resilience;
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