package com.wat128.rpg01_for_android_character;

public interface Enemy {

    void performAction();
    String getName();
    int getImageId();
    int getId();
    int getLv();
    int getExp();
    int getStrength();
    int getAgility();
    int getResilience();
    int getWisdom();
    int getLuck();
    int getMaxHp();
    int getMaxMp();
    int getHp();
    int getMp();
    int getAttack();
    int getDefence();

}

class EnemyData implements Enemy {

    private Status _status;

    public EnemyData(Status status) {

        _status = status;
    }

    @Override
    public void performAction() {

    }

    @Override public String getName()     { return _status.name; }
    @Override public int getImageId()     { return _status.imageId; }
    @Override public int getId()          { return _status.id; }
    @Override public int getLv()          { return _status.lv; }
    @Override public int getExp()         { return _status.exp; }
    @Override public int getStrength()    { return _status.strength; }
    @Override public int getAgility()     { return _status.agility; }
    @Override public int getResilience()  { return _status.resilience; }
    @Override public int getWisdom()      { return _status.wisdom; }
    @Override public int getLuck()        { return _status.luck; }
    @Override public int getMaxHp()       { return _status.maxHp; }
    @Override public int getMaxMp()       { return _status.maxMp; }
    @Override public int getHp()          { return _status.hp; }
    @Override public int getMp()          { return _status.mp; }
    @Override public int getAttack()      { return _status.attack; }
    @Override public int getDefence()     { return _status.defence; }
}
