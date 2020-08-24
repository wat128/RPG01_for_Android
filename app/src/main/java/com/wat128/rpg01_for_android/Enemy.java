package com.wat128.rpg01_for_android;

public interface Enemy {

    void performAction();
    int getImageId();
}

class EnemyList {
    public static final int SLIME = 10;
    public static final int NINE_TAILED_FOX = 30;
}

class EnemyData implements Enemy {

    private Status _status;

    public EnemyData(Status status) {

        _status = status;
    }

    @Override
    public void performAction() {

    }

    @Override
    public int getImageId() {
        return _status.imageId;
    }
}
