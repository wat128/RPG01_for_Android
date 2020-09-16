package com.wat128.rpg01_for_android_scene;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wat128.rpg01_for_android.R;
import com.wat128.rpg01_for_android_character.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Battle extends AppCompatActivity {

    private ArrayList<ActionOrder> _battlers;
    private Player _player;
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

        _player = Player.getInstance();
        _battlers = new ArrayList<>();

        _hpView = findViewById(R.id.hp_val);
        _hpView.setText(String.valueOf(_player.getHp()));

        _mpView = findViewById(R.id.mp_val);
        _mpView.setText(String.valueOf(_player.getMp()));

        _lvView = findViewById(R.id.level_val);
        _lvView.setText(String.valueOf(_player.getLv()));

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
                BattleStart(-1);
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
                _msgBoxView.setText(getString(R.string.succes_escape, _player.getName()));
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

        _skillTable = findViewById(R.id.skill_button_table);
        ArrayList<String> skillsName = new ArrayList<>();
        skillsName = _player.getSkillsName();

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
                if(_player.isSkillAvailable(index))
                    BattleStart(index);
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

    // スキル未使用(通常攻撃)なら pSkillIndex に -1 を渡す
    private void BattleStart(final int pSkillIndex) {

        _msgBoxView.setText("");
        _battlers.clear();
        // プレイヤーの行動を決める
        final Skill pSkill = _player.getSkill(pSkillIndex);
        final Type pAction = decideToActionType(pSkill);

        ActionOrder pOrder = new ActionOrder(_player, pAction, pSkillIndex);

        // エネミーの行動を決める
        final int eSkillIndex = _enemy.decideToAction();
        final Skill eSkill = _enemy.getSkill(eSkillIndex);
        final Type eAction = decideToActionType(eSkill);

        ActionOrder eOrder = new ActionOrder(_enemy, eAction, eSkillIndex);

        // 行動順を決定
        final boolean playerIsFaster = _player.getAgility() >= _enemy.getAgility();
        if (playerIsFaster) {
            _battlers.add(pOrder);
            _battlers.add(eOrder);
        } else {
            _battlers.add(eOrder);
            _battlers.add(pOrder);
        }

        // 行動実行
        for(int count = 0; count < _battlers.size(); count++) {
            final ActionOrder offence = _battlers.get(0);
            final ActionOrder defense = _battlers.get(1);

            switch (offence.actionType) {
                case N_Attack:
                    normalAttack(offence.battler, defense.battler);
                    break;
                case S_Attack:
                    attackSkill(offence.battler, defense.battler, offence.skillIndex);
                    break;
                case S_Support:
                    supportSkill(offence.battler, offence.skillIndex);
                    break;
                case S_Recovery:
                    recoverySkill(offence.battler, offence.skillIndex);
                    break;
                default:
                    normalAttack(_battlers.get(0).battler, _battlers.get(1).battler);
                    break;
            }

            if(died(defense.battler))
                break;

            // バトル続行により攻守交代　TODO:１対１を想定により複数となる際は変更
            Collections.reverse(_battlers);
        }

        _hpView.setText(String.valueOf(_player.getHp()));
        _mpView.setText(String.valueOf(_player.getMp()));
        _lvView.setText(String.valueOf(_player.getLv()));
        _skillTable.setVisibility(View.INVISIBLE);
    }

    private Type decideToActionType(final Skill skill) {
        final Type actionType;
        if(skill == null) {
            actionType = Type.N_Attack;
        }
        else {
            switch (skill.getType()) {
                case S_Attack:      actionType = Type.S_Attack;    break;
                case S_Support:     actionType = Type.S_Support;   break;
                case S_Recovery:    actionType = Type.S_Recovery;  break;
                default:            actionType = Type.N_Attack;    break;
            }
        }

        return actionType;
    }

    private void normalAttack(final Battler off, final Battler def) {

        Random random = new Random();
        final int rndNum = random.nextInt(55) + 99;

        int damage = (int)((off.getAttack() - (def.getDefence() / 2)) * rndNum / 256);
        if (damage < 0)
            damage = 0;

        def.recievedDamage(damage);
        _msgBoxView.append(getString(R.string.attackMsg, off.getName(), def.getName(), damage));
    }

    private void attackSkill(final Battler off, final Battler def, final int index) {

        int damage = 0;
        final TargetStatus target = off.getSkill(index).getTargetStatus();

        Random random = new Random();
        final int rndNum = random.nextInt(55) + 99;

        if(target == TargetStatus.Atk)
            damage = (int)((off.getAttack() + off.activateSkill(index) - (def.getDefence() / 2)) * rndNum / 256);
        else if (target == TargetStatus.Mind)
            damage = (int)((off.getMind() + off.activateSkill(index) - (def.getMind() / 2)) * rndNum / 256);

        if (damage < 0)
            damage = 0;

        def.recievedDamage(damage);
        _msgBoxView.append(getString(R.string.skillAttackMsg,
                off.getName(), off.getSkill(index).getName(), def.getName(), damage));
    }

    private void supportSkill(final Battler battler, final int index) {

        float buf = battler.activateSkill(index);
        if(buf < 0)
            buf = 0;

        final Skill skill = battler.getSkill(index);
        battler.buf(buf, skill.getTargetStatus());
        _msgBoxView.append(getString(R.string.skillSuppoerMsg,
                battler.getName(), skill.getName()));
    }

    private void recoverySkill(final Battler battler, final int index) {

        int healing = (int)battler.activateSkill(index);
        if (healing < 0)
            healing = 0;

        final int actualHealing = battler.recovery(healing);
        _msgBoxView.append(getString(R.string.skillRecoveryMsg,
                battler.getName(), battler.getSkill(index).getName(), actualHealing));
    }

    private boolean died(final Battler battler) {

        if(!battler.died()) {
            return false;
        }
        else {

            buttonEnabled();
            _player.resetBuf();
            Intent intent = new Intent();

            // 以下条件式以降、battlerではなく、playerとenemyを直接使用する
            // true     : battler == player
            // false    : battler == enemy
            if(battler.getId() == BattlerList.PLAYER) {

                intent.putExtra(Field.WINNER, getString(R.string.enemy));
                setResult(RESULT_OK, intent);

                _msgBoxView.append(getString(R.string.lose, _player.getName()));
            }
            else {

                _enemyImage.setVisibility(View.INVISIBLE);

                _msgBoxView.append(getString(R.string.win, _player.getName(), _enemy.getName()));
                _msgBoxView.append(getString(R.string.recieveExp, _enemy.getExpGained()));

                final int lvIncrease = _player.growUp(_enemy.getExpGained());
                if(lvIncrease > 0) {
                    _msgBoxView.append(getString(R.string.lvup, _player.getName(), lvIncrease));
                }
            }

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }

        return true;
    }

    @Override
    public void onBackPressed() {

    }
}

class ActionOrder {

    Battler battler;
    Type actionType;
    int skillIndex;

    ActionOrder(final Battler battler, final Type actionType, final int skillIndex) {
        this.battler = battler;
        this.actionType = actionType;
        this.skillIndex = skillIndex;
    }

}
