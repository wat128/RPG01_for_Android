package com.wat128.rpg01_for_android_character;

import android.util.Log;

class SkillFactory {
    static Skill create(final SkillId skillId) {

        final Skill skill;

        switch(skillId) {
            case Fire:
                skill = new Fire();
                break;
            case Heal:
                skill = new Heal();
                break;
            case PowerUp:
                skill = new PowerUp();
                break;
            default:
                Log.d("debug", "SkillFactory Failed");
                skill = null;
                break;
        }

        return skill;
    }
}
