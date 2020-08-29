package com.wat128.rpg01_for_android_character;

public abstract class Battler {

    Status _status;

    public Battler(Status status) {
        _status = status;
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

    public int growUp(final int exp) {    // TODO:
        return _status.growUp(exp);
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