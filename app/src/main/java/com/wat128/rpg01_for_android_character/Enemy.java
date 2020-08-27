package com.wat128.rpg01_for_android_character;

public interface Enemy {

    void performAction();
    int getImageId();
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
