package com.wat128.rpg01_for_android_character;

enum SkillId {
    Fire,
    PowerUp,
    Heal
}

enum TargetStatus {
    None,
    Atk,
    Def,
    Agi,
    Mind,
    Luk
}

public abstract class Skill {
    String _name;
    SkillId _id;
    float _power;
    int _mp;
    Type _type;
    TargetStatus _target;

    public Skill(String name, SkillId id, float power, int mp, Type type, TargetStatus target) {
        _name = name;
        _id = id;
        _power = power;
        _mp = mp;
        _type = type;
        _target = target;
    }
    public String getName()                 { return _name; }
    public float getPower()                 { return _power; }
    public int getMp()                      { return _mp;}
    public Type getType()                   { return _type; }
    public TargetStatus getTargetStatus()   { return _target; }
}

/*　2020/09/01

    スキル追加時の必要手順
    1. Skillクラスを継承する（例：Fire）
    2. SkillIdに新規スキル名のIDを追加
    3. SkillFactoryに新規スキルを追加
 */
class Fire extends Skill {
    public Fire() {
        super("火炎", SkillId.Fire, 10f, 3, Type.S_Attack, TargetStatus.None);
    }
}

class PowerUp extends Skill {
    public PowerUp() {
        super("パワーアップ", SkillId.PowerUp, 1.3f, 4, Type.S_Support, TargetStatus.Atk);
    }
}

class Heal extends Skill {
    public Heal() {
        super("ヒール", SkillId.Heal, 30f, 5, Type.S_Recovery, TargetStatus.None);
    }
}