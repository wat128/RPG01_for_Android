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

import com.wat128.rpg01_for_android.R;
import com.wat128.rpg01_for_android_character.*;

import java.util.ArrayList;

public class Battle extends AppCompatActivity {

    private ArrayList<Battler> _battler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        Intent intent = getIntent();

        final Player player = Player.getInstance();

        TextView hpView = findViewById(R.id.hp_val);
        hpView.setText(String.valueOf(player.getHp()));

        TextView mpView = findViewById(R.id.mp_val);
        mpView.setText(String.valueOf(player.getMp()));

        TextView lvView = findViewById(R.id.level_val);
        lvView.setText(String.valueOf(player.getLv()));

        final int enemyId = intent.getIntExtra("Enemy_Data", 0);
        final Enemy enemy = EnemyFactory.create(enemyId);
        Log.d("debug", "Enemy ID : " + String.valueOf(enemyId));

        ImageView enemyImage = findViewById(R.id.enemyImage);
        enemyImage.setImageResource(enemy.getImageId());

        final TextView msgBoxView = findViewById(R.id.msg_box);
        msgBoxView.setText(getString(R.string.appear, enemy.getName()));

        Button btnAttack = findViewById(R.id.attack);
        btnAttack.setOnClickListener(new View.OnClickListener() {
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

                    int damage = off.getAttack() - def.getDefence();
                    if(damage < 0)
                        damage = 0;

                    def.recievedDamage(off.getAttack());
                    msgBoxView.append(getString(R.string.damageMsg,
                            off.getName(), def.getName(), damage));

                    if(def.died()){
//                        if(off.growUp(10) > 0) { }

                        msgBoxView.append(getString(R.string.battleFinished, off.getName()));

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);

                        break;
                    }
                }
            }
        });

        Button btnEscape = findViewById(R.id.escape);
        btnEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
