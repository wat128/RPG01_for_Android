package com.wat128.rpg01_for_android_character;

import java.util.ArrayList;

public abstract class Battler {

    Status _status;
    ArrayList<Skill> _skills;

    public Battler(Status status) {
        _status = status;
        _skills = new ArrayList<>();
    }

    public void fullRecovery() {
        _status.hp = _status.maxHp;
        _status.mp = _status.maxMp;
    }

    public void recievedDamage(int damage) {
        _status.hp -= damage;
        if(_status.hp < 0)
            _status.hp = 0;
    }

    public boolean died() {
        if(_status.hp <= 0)
            return true;

        return false;
    }

    public int growUp(final int exp) {
        return _status.growUp(exp);
    }

    public ArrayList<String> getSkillsName() {
        ArrayList<String> skillsName = new ArrayList<>();

        for(Skill skill : _skills) {
            skillsName.add(skill.getName());
        }

        return skillsName;
    }

    public int getSkillPower(final int index) {
        return _skills.get(index).getPower();
    }

    public void performAction() { }


    public String getName()         { return _status.name; }
    public int getImageId()         { return _status.imageId; }
    public int getId()              { return _status.id; }
    public int getLv()              { return _status.lv.val; }
    public int getStatusIncrease()  { return _status.lv.statusIncrease; }
    public int getExpGained()       { return _status.exp.gain; }
    public int getStrength()        { return _status.strength; }
    public int getAgility()         { return _status.agility; }
    public int getResilience()      { return _status.resilience; }
    public int getWisdom()          { return _status.wisdom; }
    public int getLuck()            { return _status.luck; }
    public int getMaxHp()           { return _status.maxHp; }
    public int getMaxMp()           { return _status.maxMp; }
    public int getHp()              { return _status.hp; }
    public int getMp()              { return _status.mp; }
    public int getAttack()          { return _status.attack; }
    public int getDefence()         { return _status.defence; }
}

enum Type {
    attack,
    support,
    recovery
}

abstract class Skill {
    String _name;
    int _id;
    int _power;
    Type _type;

    public Skill(String name, int id, int power, Type type) {
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

class Fire extends Skill {
    public Fire() {
        super("火炎", 1, 10, Type.attack);
    }
}

class PowerUp extends Skill {
    public PowerUp() {
        super("パワーアップ", 30, 10, Type.support);
    }
}

class Heal extends Skill {
    public Heal() {
        super("ヒール", 50, 30, Type.recovery);
    }
}