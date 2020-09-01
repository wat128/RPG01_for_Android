package com.wat128.rpg01_for_android_character;

import androidx.appcompat.app.AppCompatActivity;
import com.wat128.rpg01_for_android.*;

import java.util.Random;

import static com.wat128.rpg01_for_android_character.BattlerList.*;

class Lv {
    int val;
    int statusIncrease;
}

class Exp {
    int cur;
    int[][] table;
    int gain;
}

public class Status extends AppCompatActivity {
    String name;
    int imageId;
    int id;
    Lv lv;
    Exp exp;
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

    public Status() {
        lv = new Lv();
        exp = new Exp();
    }

    public int growUp(final int exp) {

        int growUpNum = 0;
        int remainingExp = exp;

        while(remainingExp > 0) {
            // this.exp.table[lv][ExpTable,EXP]は次のレベルの必要経験値を指す
            // 例：lv == 1 なら Lv.2のEXP
            final int tnl = this.exp.table[lv.val][ExpTable.EXP] - this.exp.cur;

            if(remainingExp >= tnl) {
                growUpNum++;
                this.lv.val++;
                this.exp.cur = 0;
                remainingExp -= tnl;

                statusIncrease();
            }
            else {
                this.exp.cur += remainingExp;
                remainingExp = 0;
            }
        }

        return growUpNum;
    }

    private void statusIncrease() {

        Random random = new Random();
        final int increaseVal = random.nextInt(lv.statusIncrease) + 1;

        strength += increaseVal;
        agility += increaseVal;
        resilience += increaseVal;
        wisdom += increaseVal;
        luck += increaseVal;
        maxHp += increaseVal;
        maxMp += increaseVal;
        attack += increaseVal;      // TODO: 装備実装後に一度見直す
        defence += increaseVal;     // TODO: 装備実装後に一度見直す
    }
}

class playerStatus extends Status {

    public playerStatus() {
        super.name = "Hero";
        super.imageId = 0; // TODO:
        super.id = PLAYER;
        super.lv.val = 1;
        super.lv.statusIncrease = 3;
        super.exp.cur = 0;
        super.exp.table = ExpTable.player;
        super.exp.gain = 0;
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
        super.name = "スライム";
        super.imageId = R.drawable.slime;
        super.id = SLIME;
        super.lv.val = 1;
        super.lv.statusIncrease = 0;
        super.exp.cur = 0;
        super.exp.table = null;
        super.exp.gain = 7;
        super.strength = 2;
        super.agility = 3;
        super.resilience = 2;
        super.wisdom = 7;
        super.luck = 7;
        super.maxHp = 10;
        super.maxMp = 5;
        super.hp = maxHp;
        super.mp = maxMp;
        super.attack = strength;
        super.defence = resilience;
    }
}

class NineTailStatus extends Status {

    public NineTailStatus() {
        super.name = "キュウビ";
        super.imageId = R.drawable.nine_taled_fox;
        super.id = NINE_TAILED_FOX;
        super.lv.val = 1;
        super.lv.statusIncrease = 0;
        super.exp.cur = 0;
        super.exp.table = null;
        super.exp.gain = 10;
        super.strength = 10;
        super.agility = 3;
        super.resilience = 2;
        super.wisdom = 7;
        super.luck = 7;
        super.maxHp = 10;
        super.maxMp = 5;
        super.hp = maxHp;
        super.mp = maxMp;
        super.attack = strength;
        super.defence = resilience;
    }
}