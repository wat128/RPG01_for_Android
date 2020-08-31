package com.wat128.rpg01_for_android_scene;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wat128.rpg01_for_android.MainActivity;
import com.wat128.rpg01_for_android.R;
import com.wat128.rpg01_for_android_character.*;

import java.util.ArrayList;

public class Battle extends AppCompatActivity {

    private ArrayList<Battler> _battler;

    private Button _btnAttack;
    private Button _btnMagic;
    private Button _btnEscape;
    private Button _btnItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        Intent intent = getIntent();

        final Player player = Player.getInstance();

        final TextView hpView = findViewById(R.id.hp_val);
        hpView.setText(String.valueOf(player.getHp()));

        final TextView mpView = findViewById(R.id.mp_val);
        mpView.setText(String.valueOf(player.getMp()));

        final TextView lvView = findViewById(R.id.level_val);
        lvView.setText(String.valueOf(player.getLv()));

        final int enemyId = intent.getIntExtra("Enemy_Data", 0);
        final Enemy enemy = EnemyFactory.create(enemyId);
        Log.d("debug", "Enemy ID : " + String.valueOf(enemyId));

        ImageView enemyImage = findViewById(R.id.enemyImage);
        enemyImage.setImageResource(enemy.getImageId());

        final TextView msgBoxView = findViewById(R.id.msg_box);
        msgBoxView.setText(getString(R.string.appear, enemy.getName()));

        _btnAttack = findViewById(R.id.attack);
        _btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 前ターンのメッセージの初期化
                msgBoxView.setText("");

                if(_battler == null) {
                    _battler = new ArrayList<>();

                    final boolean playerIsFaster = player.getAgility() >= enemy.getAgility();
                    if (playerIsFaster) {
                        _battler.add(player);
                        _battler.add(enemy);
                    } else {
                        _battler.add(enemy);
                        _battler.add(player);
                    }
                }

                // TODO:１対１の戦闘を想定しているため、対複数となった場合はロジックを変更する
                for(int offense = 0; offense < _battler.size(); ++offense) {

                    final int defense;
                    if(offense == 0)
                        defense = 1;
                    else
                        defense = 0;

                    Battler off = _battler.get(offense);
                    Battler def = _battler.get(defense);

                    int damage = off.getAttack() - def.getDefence();    //TODO: ダメージ計算式の修正
                    if(damage < 0)
                        damage = 0;

                    def.recievedDamage(damage);
                    msgBoxView.append(getString(R.string.damageMsg,
                            off.getName(), def.getName(), damage));

                    if(def.died()){

                        buttonEnabled();
                        Intent intent = new Intent();
                        if(def.getId() == BattlerList.PLAYER) {

                            intent.putExtra(Field.WINNER, getString(R.string.enemy));
                            setResult(RESULT_OK, intent);

                            msgBoxView.append(getString(R.string.lose, player.getName()));
                        }
                        else {
                            msgBoxView.append(getString(R.string.win,
                                    player.getName(), enemy.getName()));
                            msgBoxView.append(getString(R.string.recieveExp, enemy.getExpGained()));

                            final int lvIncrease = player.growUp(enemy.getExpGained());
                            if(lvIncrease > 0) {
                                msgBoxView.append(getString(R.string.lvup,
                                        player.getName(), lvIncrease));
                            }
                        }

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);

                        break;
                    }
                }

                hpView.setText(String.valueOf(player.getHp()));
                mpView.setText(String.valueOf(player.getMp()));
            }
        });

        _btnMagic = findViewById(R.id.magic);
        _btnMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Player player = Player.getInstance();

                ArrayList<String> skillsName = new ArrayList<>();
                skillsName = player.getSkillsName();

                // レベル２になったらスキルが取得できるかを確認
                Log.d("debug", "スキル１：" + skillsName.get(0));
                Log.d("debug", "スキル2：" + skillsName.get(1));
                Log.d("debug", "スキル3：" + skillsName.get(2));
            }
        });

        _btnEscape = findViewById(R.id.escape);
        _btnEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _btnItem = findViewById(R.id.item);
        _btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void buttonEnabled() {
        _btnAttack.setEnabled(false);
        _btnMagic.setEnabled(false);
        _btnEscape.setEnabled(false);
        _btnItem.setEnabled(false);
    }
}
