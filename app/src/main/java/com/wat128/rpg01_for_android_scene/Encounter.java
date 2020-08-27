package com.wat128.rpg01_for_android_scene;

public class Encounter {

    private final static int ENCOUNTER_INTERVAL = 300;
    public static int encounterIntervalAccum;

    static boolean isAccumMoreThanEncounterInterval() {

        if(encounterIntervalAccum < ENCOUNTER_INTERVAL) {
            return false;
        }
        else {
            encounterIntervalAccum = 0;
            return true;
        }
    }
}
