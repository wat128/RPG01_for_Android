package com.wat128.rpg01_for_android_character;

import java.util.ArrayList;
import java.util.Arrays;

class AcquireSkill {
    int lv;
    SkillId skillId;
    boolean acquired;

    AcquireSkill(int lvToAcquire, SkillId skillId, boolean acquired) {
        this.lv = lvToAcquire;
        this.skillId = skillId;
        this.acquired = acquired;
    }
}

class AcquireSkillList {
    ArrayList<AcquireSkill> list;
    int size;

    public AcquireSkillList(ArrayList<AcquireSkill> list) {
        this.list = list;
        this.size = list.size();
    }
}

// プレイヤーがレベルに達した際に覚えるスキルリスト
// プレイヤー以外に必要になった場合は以下に追加すること
class PlayerSkillList extends AcquireSkillList {

    public PlayerSkillList() {
        super(new ArrayList<AcquireSkill>(Arrays.asList(
                new AcquireSkill(1, SkillId.Fire, false),
                new AcquireSkill(2, SkillId.Heal, false),
                new AcquireSkill(2, SkillId.PowerUp, false))
        ));
    }
}

class NineTailedFoxSkillList extends AcquireSkillList {

    public NineTailedFoxSkillList() {
        super(new ArrayList<AcquireSkill>(Arrays.asList(
                new AcquireSkill(1, SkillId.Fire, false),
                new AcquireSkill(1, SkillId.Heal, false),
        ));
    }
}