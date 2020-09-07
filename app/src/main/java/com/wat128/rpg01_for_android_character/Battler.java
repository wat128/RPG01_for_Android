package com.wat128.rpg01_for_android_character;

import com.wat128.rpg01_for_android_scene.Battle;

import java.util.ArrayList;

public abstract class Battler {

    Status _status;
    ArrayList<Skill> _skills;
    AcquireSkillList _acquireSkills;

    public Battler(Status status) {
        _status = status;
        _skills = new ArrayList<>();
        _acquireSkills = null;
    }

    public Battler(Status status, AcquireSkillList acquireSkills) {
        _status = status;
        _skills = new ArrayList<>();
        _acquireSkills = acquireSkills;
    }

    public void fullRecovery() {
        _status.hp = _status.maxHp;
        _status.mp = _status.maxMp;
    }

    public void recievedDamage(final int damage) {
        _status.hp -= damage;
        if(_status.hp < 0)
            _status.hp = 0;
    }

    public int recovery(final int healing) {
        int actual = healing;

        final int diff = _status.maxHp - _status.hp;
        _status.hp += healing;
        if(_status.hp > _status.maxHp) {
            _status.hp = _status.maxHp;
            actual = diff;

        }
        return actual;
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

    public Skill getSkill(final int index) {

        if(index < 0)
            return null;

        return _skills.get(index);
    }

    public boolean isSkillAvailable(final int index) {
        if(_status.mp >= _skills.get(index).getMp())
            return true;

        return false;
    }

    public int activateSkill(final int index) {

        if(isSkillAvailable(index)){
            Skill skill = _skills.get(index);
            _status.mp -= skill.getMp();
            return skill.getPower();
        }

        return 0;
    }

    // バトルでの行動を決定する。通常攻撃なら-1, スキルならインデックスを返す。
    // スキル発動はインデックスを使用して別メソッドのコールが必要
    public int decideToAction() {
        return -1;  // TODO;実装
    }


    public String getName()         { return _status.name; }
    public int getImageId()         { return _status.imageId; }
    public BattlerList getId()      { return _status.id; }
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

