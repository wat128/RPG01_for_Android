package com.wat128.rpg01_for_android_character;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.wat128.rpg01_for_android.MyAppContext;
import com.wat128.rpg01_for_android.R;

import java.util.HashMap;
import java.util.Map;


// 新しいバトル用効果音の追加方法
// 1.enum IdにIDを追加
// 2.addSound()に追加
public class SoundEffect {

    public enum Id {
        ATTACK01,
        ESCAPE,
        FIRE,
        BUF,
        DEBUF,
        HEAL
    }

    private SoundPool soundPool;
    private Map<Id, Integer> soundList = new HashMap<>();

    private static class SoundEffectHolder {
        private static final SoundEffect INSTANCE = new SoundEffect();
    }

    private SoundEffect() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build();

        addSound();

    }

    public void play(SoundEffect.Id id) {
        soundPool.play(soundList.get(id), 1.0f, 1.0f, 0,0,1);
    }

    public static SoundEffect getInstance() {
        return SoundEffectHolder.INSTANCE;
    }

    private void addSound() {
        soundList.put(
                Id.ATTACK01,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_attack_kurage_attack01, 1));

        soundList.put(
                Id.ESCAPE,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_escape_kurage_esc01, 1));

        soundList.put(
                Id.FIRE,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_skill_fire_kurage_sburst02, 1));

        soundList.put(
                Id.BUF,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_skill_buf_maodama_magic10, 1));

        soundList.put(
                Id.DEBUF,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_skill_debuf_kurage_status02, 1));

        soundList.put(
                Id.HEAL,
                soundPool.load(MyAppContext.getInstance().getApplicationContext(), R.raw.s_skill_heal_kurage_heal02, 1));
    }
}
