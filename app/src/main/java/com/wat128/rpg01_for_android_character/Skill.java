package com.wat128.rpg01_for_android_character;

enum Type {
    attack,
    support,
    recovery
}

enum SkillId {
    Fire,
    PowerUp,
    Heal
}

abstract class Skill {
    String _name;
    SkillId _id;
    int _power;
    Type _type;

    public Skill(String name, SkillId id, int power, Type type) {
        _name = name;
        _id = id;
        _power = power;
        _type = type;
    }
    public int getPower() {
        return _power;
    }

    public String getName() {
        return _name;
    }
}

/*　2020/09/01

    スキル追加時の必要手順
    1. Skillクラスを継承する（例：Fire）
    2. SkillIdに新規スキル名のIDを追加
    3. SkillFactoryに新規スキルを追加
 */
class Fire extends Skill {
    public Fire() {
        super("火炎", SkillId.Fire, 10, Type.attack);
    }
}

class PowerUp extends Skill {
    public PowerUp() {
        super("パワーアップ", SkillId.PowerUp, 10, Type.support);
    }
}

class Heal extends Skill {
    public Heal() {
        super("ヒール", SkillId.Heal, 30, Type.recovery);
    }
}



