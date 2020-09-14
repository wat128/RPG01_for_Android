package com.wat128.rpg01_for_android_character;

import com.wat128.rpg01_for_android.*;
import java.util.Random;


public class Status {
    final String name;
    final int imageId;
    final BattlerList id;
    Lv lv;
    Exp exp;
    Hp hp;
    Mp mp;
    Atk atk;
    Def def;
    Agi agi;
    Mind mind;
    Luk luk;

    public Status(
            final String name, final int imageId, BattlerList id,
            final Lv lv, final Exp exp, final float hp, final float mp, final float atk,
            final float def, final float agi, final float mind, final float luk) {
        this.name = name;
        this.imageId = imageId;
        this.id = id;
        this.lv = lv;
        this.exp = exp;
        this.hp = new Hp(hp);
        this.mp = new Mp(mp);
        this.atk = new Atk(atk);
        this.def = new Def(def);
        this.agi = new Agi(agi);
        this.mind = new Mind(mind);
        this.luk = new Luk(luk);
    }

    public int growUp(final int exp) {

        int growUpNum = 0;
        int remainingExp = exp;

        while(remainingExp > 0) {

            final int tnl = this.exp.tnl(lv.val());

            if(remainingExp >= tnl) {
                growUpNum++;
                statusIncrease(this.lv.up());
                this.exp.clear();
                remainingExp -= tnl;
            }
            else {
                this.exp.increase(remainingExp);
                remainingExp = 0;
            }
        }

        return growUpNum;
    }

    private void statusIncrease(final int maxVal) {

        this.hp.rndIncreaseMax(maxVal);
        this.mp.rndIncreaseMax(maxVal);
        this.atk.rndIncreaseMax(maxVal);
        this.def.rndIncreaseMax(maxVal);
        this.agi.rndIncreaseMax(maxVal);
        this.mind.rndIncreaseMax(maxVal);
        this.luk.rndIncreaseMax(maxVal);
    }
}

//-------------------------------------------------------------------------------
// レベル、経験値

class Lv {
    private int val;
    private int statusIncrease;

    Lv(final int val, final int statusIncreaseMaxVal) {
        this.val = val;
        this.statusIncrease = statusIncreaseMaxVal;
    }

    int val() {
        return val;
    }

    // １レベルあげて能力上昇値を返す
    int up() {
        val++;
        return statusIncrease;
    }

    // val分レベルをあげて、トータルの能力上昇値を返す
    int up(final int val) {
        this.val += val;
        return statusIncrease * val;
    }
}

class Exp {
    private int cur;
    private int[][] table;
    private int gain;

    Exp(final int[][] expTable, final int gainValExp){
        this.cur = 0;
        this.table = expTable;
        this.gain = gainValExp;
    }

    // 次のレベルの必要経験値を指す (例：lv == 1 なら Lv.2のEXP)
    int tnl(final int lv) {
        return table[lv][ExpTable.EXP] - cur;
    }

    void clear() {
        cur = 0;
    }

    void increase(final int val) {
        cur += val;
    }

    int getGain() {
        return gain;
    }
}


//-------------------------------------------------------------------------------
// ステータスの各要素

abstract class HpMp_Base {
    private float max;
    private float min;
    private float cur;

    HpMp_Base(final float val) {
        this.max = val;
        this.min = 0;
        this.cur = val;
    }

    boolean isEmpty() {
        if(cur <= 0)
            return true;

        return false;
    }

    void fullRecovery() {
        cur = max;
    }

    int recovery(final float healing) {
        float actual = healing;

        final float diff = max - cur;
        cur += healing;
        if(cur > max) {
            cur = max;
            actual = (int)diff;
        }

        return (int)actual;
    }

    void decrease(final float val) {
        cur -= val;
        if(cur < 0)
            cur = 0;
    }

    void increaseMax(final int val) {
        this.max += val;
        this.cur = this.max;
    }

    void rndIncreaseMax(final int max) {
        Random random = new Random();
        this.max += random.nextInt(max) + 1;
        this.cur = this.max;
    }

    int cur() {
        return (int)cur;
    }
}

abstract class Status_Base {
    private float org;
    private float cur;

    Status_Base(final float val) {
        this.org = val;
        this.cur = val;
    }

    void increaseMax(final int val) {
        this.org += val;
        this.cur = this.org;
    }

    void rndIncreaseMax(final int max) {
        Random random = new Random();
        this.org += random.nextInt(max) + 1;
        this.cur = this.org;
    }

    // valの値は 1.1 や 0.9 で指定
    void buf(final float val) {
        cur = org * val;
    }

    void reset() {
        cur = org;
    }

    int cur() {
        return (int)cur;
    }
}

class Hp extends HpMp_Base      { Hp(final float val)    { super(val); } }
class Mp extends HpMp_Base      { Mp(final float val)    { super(val); } }
class Atk extends Status_Base   { Atk(final float val)   { super(val); } }
class Def extends Status_Base   { Def(final float val)   { super(val); } }
class Agi extends Status_Base   { Agi(final float val)   { super(val); } }
class Mind extends Status_Base  { Mind(final float val)  { super(val); } }
class Luk extends Status_Base   { Luk(final float val)   { super(val); } }

//-------------------------------------------------------------------------------
//  キャラクターのステータス

class playerStatus extends Status {

    playerStatus() {
        super(
                "勇者",
                R.drawable.hero_down,
                BattlerList.PLAYER,
                new Lv(1, 3),
                new Exp(ExpTable.player, 0),
                15,
                15,
                5,
                5,
                5,
                5,
                5
        );
    }
}

class SlimeStatus extends Status {

    SlimeStatus() {
        super(
                "スライム",
                R.drawable.slime,
                BattlerList.SLIME,
                new Lv(1, 0),
                new Exp(null, 7),
                7,
                3,
                2,
                2,
                3,
                3,
                7
        );
    }
}

class NineTailStatus extends Status {

    NineTailStatus() {
        super(
                "キュウビ",
                R.drawable.nine_taled_fox,
                BattlerList.NINE_TAILED_FOX,
                new Lv(1, 0),
                new Exp(null, 10),
                10,
                7,
                10,
                2,
                3,
                7,
                7
        );
    }
}