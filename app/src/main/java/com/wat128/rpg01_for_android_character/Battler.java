package com.wat128.rpg01_for_android_character;

import java.util.ArrayList;
import java.util.Random;

public abstract class Battler {

    Status _status;
    ArrayList<Skill> _skills;
    AcquireSkillList _acquireSkills;

    public Battler(Status status) {
        _status = status;
        _skills = null;
        _acquireSkills = null;
    }

    public Battler(Status status, AcquireSkillList acquireSkills) {
        _status = status;
        _skills = new ArrayList<>();
        _acquireSkills = acquireSkills;
        pickUpNewSkill();
    }

    public void fullRecovery() {
        _status.hp.fullRecovery();
        _status.mp.fullRecovery();
    }

    public void recievedDamage(final float damage) {
        _status.hp.decrease(damage);
    }

    public int recovery(final float healing) {
        return _status.hp.recovery(healing);
    }

    public boolean died() {
        if(_status.hp.isEmpty())
            return true;

        return false;
    }

    public void buf(final float val, final TargetStatus target) {

        switch (target) {
            case Atk:   _status.atk.buf(val);   break;
            case Def:   _status.def.buf(val);   break;
            case Agi:   _status.agi.buf(val);   break;
            case Mind:  _status.mind.buf(val);  break;
            case Luk:   _status.luk.buf(val);   break;
        }
    }

    public void resetBuf() {
        _status.atk.reset();
        _status.def.reset();
        _status.agi.reset();
        _status.mind.reset();
        _status.luk.reset();
    }

    public int growUp(final int exp) {

        final int growUpNum = _status.growUp(exp);;
        if(growUpNum <= 0)
            return growUpNum;

        pickUpNewSkill();

        return growUpNum;
    }

    public void pickUpNewSkill() {

        if(_acquireSkills == null)
            return;

        for(int i = 0; i < _acquireSkills.size; i++) {
            AcquireSkill data = _acquireSkills.list.get(i);

            if(data.acquired)
                continue;

            if(_status.lv.val() >= data.lv) {
                _skills.add(SkillFactory.create(data.skillId));
                data.acquired = true;
            }
        }
    }

    public ArrayList<String> getSkillsName() {

        if(_skills == null)
            return null;

        ArrayList<String> skillsName = new ArrayList<>();

        for(Skill skill : _skills) {
            skillsName.add(skill.getName());
        }

        return skillsName;
    }

    public Skill getSkill(final int index) {

        if(index < 0 || _skills == null)
            return null;

        return _skills.get(index);
    }

    public boolean isSkillAvailable(final int index) {

        if(index < 0 || _skills == null)
            return false;

        if(_status.mp.cur() >= _skills.get(index).getMp())
            return true;

        return false;
    }

    public float activateSkill(final int index) {

        if(isSkillAvailable(index)){
            Skill skill = _skills.get(index);
            _status.mp.decrease(skill.getMp());
            return skill.getPower();
        }

        return 0f;
    }

    // バトルでの行動を決定する。通常攻撃なら-1, スキルならインデックスを返す。
    // スキル発動はインデックスを使用して別メソッドのコールが必要
    public int decideToAction() {

        if(_skills == null)
            return -1;

        Random random = new Random();

        // スキル数+1 で 通常攻撃とスキル数を合わせた値。その後の-1で_skillのindexに合わせる
        final int index = random.nextInt(_skills.size() + 1) - 1;
        if(!isSkillAvailable(index))
            return -1;

        return index;
    }


    public String getName()         { return _status.name; }
    public int getImageId()         { return _status.imageId; }
    public BattlerList getId()      { return _status.id; }
    public int getLv()              { return _status.lv.val(); }
    public int getExpGained()       { return _status.exp.getGain(); }
    public int getHp()              { return _status.hp.cur(); }
    public int getMp()              { return _status.mp.cur(); }
    public int getAttack()          { return _status.atk.cur(); }
    public int getDefence()         { return _status.def.cur(); }
    public int getAgility()         { return _status.agi.cur(); }
    public int getMind()            { return _status.mind.cur(); }
    public int getLuck()            { return _status.luk.cur(); }
}

