package com.wat128.rpg01_for_android_scene;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wat128.rpg01_for_android.MainActivity;
import com.wat128.rpg01_for_android.R;
import com.wat128.rpg01_for_android_character.*;

import java.util.ArrayList;
import java.util.Random;

public class Battle extends AppCompatActivity {

    private ArrayList<Battler> _battler;
    private Enemy _enemy;
    private ImageView _enemyImage;

    private Button _btnAttack;
    private Button _btnMagic;
    private Button _btnEscape;
    private Button _btnItem;

    private TextView _msgBoxView;

    private TextView _hpView;
    private TextView _mpView;
    private TextView _lvView;

    private TableLayout _skillTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);

        Intent intent = getIntent();

        final Player player = Player.getInstance();

        _hpView = findViewById(R.id.hp_val);
        _hpView.setText(String.valueOf(player.getHp()));

        _mpView = findViewById(R.id.mp_val);
        _mpView.setText(String.valueOf(player.getMp()));

        _lvView = findViewById(R.id.level_val);
        _lvView.setText(String.valueOf(player.getLv()));

        final BattlerList enemyId = (BattlerList)intent.getSerializableExtra("Enemy_Data");
        _enemy = EnemyFactory.create(enemyId);
        Log.d("debug", "Enemy ID : " + String.valueOf(enemyId));

        _enemyImage = findViewById(R.id.enemyImage);
        _enemyImage.setImageResource(_enemy.getImageId());

        _msgBoxView = findViewById(R.id.msg_box);
        _msgBoxView.setText(getString(R.string.appear, _enemy.getName()));

        _btnAttack = findViewById(R.id.attack);
        _btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BattleStart(0);
            }
        });

        initSkillTable();
        _btnMagic = findViewById(R.id.magic);
        _btnMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _skillTable.setVisibility(View.VISIBLE);
            }
        });

        _btnEscape = findViewById(R.id.escape);
        _btnEscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonEnabled();
                _skillTable.setVisibility(View.INVISIBLE);
                _msgBoxView.setText(getString(R.string.succes_escape, player.getName()));
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

        _btnItem = findViewById(R.id.item);
        _btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initSkillTable() {
        Player player = Player.getInstance();

        _skillTable = findViewById(R.id.skill_button_table);
        ArrayList<String> skillsName = new ArrayList<>();
        skillsName = player.getSkillsName();

        final int skillNum = skillsName.size();

        int loopMax = 0;
        if      (skillNum <= 3) loopMax = 1;
        else if (skillNum <= 6) loopMax = 2;
        else if (skillNum <= 9) loopMax = 3;

        for(int i = 0; i < loopMax; i++) {
            TableRow row = new TableRow(this);
            _skillTable.addView(row);
            for(int index = 0; index < skillsName.size(); index++) {
                row.addView(createSkillButton(skillsName.get(index), index));
            }
        }
    }

    private Button createSkillButton(final String skillName, final int index) {

        final Player player = Player.getInstance();

        Button button = new Button(this);
        TableLayout.LayoutParams buttonLayoutParams = new TableLayout.LayoutParams(
                0,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        buttonLayoutParams.weight = 1;

        button.setText(skillName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isSkillAvailable(index))
                    BattleStart(player.activateSkill(index));
                else {
                    _skillTable.setVisibility(View.INVISIBLE);
                    _msgBoxView.setText(getString(R.string.skill_unusable));
                }
            }
        });
        return button;
    }

    private void buttonEnabled() {
        _btnAttack.setEnabled(false);
        _btnMagic.setEnabled(false);
        _btnEscape.setEnabled(false);
        _btnItem.setEnabled(false);
    }

    private void BattleStart(final int skillPower) {

        Player player = Player.getInstance();

        // 前ターンのメッセージの初期化
        _msgBoxView.setText("");

        if(_battler == null) {
            _battler = new ArrayList<>();

            final boolean playerIsFaster = player.getAgility() >= _enemy.getAgility();
            if (playerIsFaster) {
                _battler.add(player);
                _battler.add(_enemy);
            } else {
                _battler.add(_enemy);
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

            int damage = off.getAttack() + skillPower - def.getDefence(); //TODO: ダメージ計算式の修正
            if(damage < 0)
                damage = 0;

            def.recievedDamage(damage);
            _msgBoxView.append(getString(R.string.damageMsg,
                    off.getName(), def.getName(), damage));

            if(def.died()){

                buttonEnabled();
                Intent intent = new Intent();
                if(def.getId() == BattlerList.PLAYER) {

                    intent.putExtra(Field.WINNER, getString(R.string.enemy));
                    setResult(RESULT_OK, intent);

                    _msgBoxView.append(getString(R.string.lose, player.getName()));
                }
                else {

                    _enemyImage.setVisibility(View.INVISIBLE);

                    _msgBoxView.append(getString(R.string.win,
                            player.getName(), _enemy.getName()));
                    _msgBoxView.append(getString(R.string.recieveExp, _enemy.getExpGained()));

                    final int lvIncrease = player.growUp(_enemy.getExpGained());
                    if(lvIncrease > 0) {
                        _msgBoxView.append(getString(R.string.lvup,
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

        _hpView.setText(String.valueOf(player.getHp()));
        _mpView.setText(String.valueOf(player.getMp()));
        _skillTable.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

    }
}
