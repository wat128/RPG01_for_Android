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
            case PowerDown:
                skill = new PowerDown();
                break;
            case MindUp:
                skill = new MindUp();
                break;
            case MindDown:
                skill = new MindDown();
                break;
            case GuardUp:
                skill = new GuardUp();
                break;
            case GuardDown:
                skill = new GuardDown();
                break;
            case SpeedUp:
                skill = new SpeedUp();
                break;
            case SpeedDown:
                skill = new SpeedDown();
                break;
            default:
                Log.d("debug", "SkillFactory Failed");
                skill = null;
                break;
        }

        return skill;
    }
}
