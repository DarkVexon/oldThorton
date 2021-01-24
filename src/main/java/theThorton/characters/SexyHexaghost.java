//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.characters;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.monsters.exordium.HexaghostOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class SexyHexaghost extends Hexaghost {
    private boolean boss;

    public SexyHexaghost(boolean isBoss) {
        super();
        if (isBoss) {
            this.type = EnemyType.BOSS;
            boss = true;
        } else this.type = EnemyType.NORMAL;
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.hasPower(RegrowPower.POWER_ID)) {
            if (this.currentHealth <= 0 && !this.halfDead) {
                this.halfDead = true;
                Iterator var2 = this.powers.iterator();

                while (var2.hasNext()) {
                    AbstractPower p = (AbstractPower) var2.next();
                    p.onDeath();
                }

                var2 = AbstractDungeon.player.relics.iterator();

                while (var2.hasNext()) {
                    AbstractRelic r = (AbstractRelic) var2.next();
                    r.onMonsterDeath(this);
                }

                this.powers.clear();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Deactivate"));
                ReflectionHacks.setPrivate(this, Hexaghost.class, "activated", false);
                boolean allDead = true;

                for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (q.hasPower(RegrowPower.POWER_ID) && !q.halfDead)
                        allDead = false;
                }

                if (!allDead) {
                    if (this.nextMove != 20) {
                        this.setMove((byte) 20, Intent.UNKNOWN);
                        this.createIntent();
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 20, Intent.UNKNOWN));
                    }
                } else {
                    AbstractDungeon.getCurrRoom().cannotLose = false;
                    this.halfDead = false;
                    for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        q.die();
                    }
                }
            }
        }
        if (this.hasPower(UnawakenedPower.POWER_ID)) {
            if (this.currentHealth <= 0 && !this.halfDead) {
                if (AbstractDungeon.getCurrRoom().cannotLose) {
                    this.halfDead = true;
                }

                Iterator s = this.powers.iterator();

                AbstractPower p;
                while (s.hasNext()) {
                    p = (AbstractPower) s.next();
                    p.onDeath();
                }

                s = AbstractDungeon.player.relics.iterator();

                while (s.hasNext()) {
                    AbstractRelic r = (AbstractRelic) s.next();
                    r.onMonsterDeath(this);
                }

                AbstractDungeon.actionManager.addToTop(new ClearCardQueueAction());
                s = this.powers.iterator();

                while (true) {
                    do {
                        if (!s.hasNext()) {
                            this.setMove((byte) 30, Intent.UNKNOWN);
                            this.createIntent();
                            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Deactivate"));
                            ReflectionHacks.setPrivate(this, Hexaghost.class, "activated", false);
                            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 30, Intent.UNKNOWN));
                            this.applyPowers();

                            return;
                        }

                        p = (AbstractPower) s.next();
                    } while (p.type != AbstractPower.PowerType.DEBUFF && !p.ID.equals("Curiosity") && !p.ID.equals("Unawakened") && !p.ID.equals("Shackled"));

                    s.remove();
                }
            }
        }
        if (this.hasPower(SplitPower.POWER_ID)) {
            if (!this.isDying && (float) this.currentHealth <= (float) this.maxHealth / 2.0F && this.nextMove != 3) {
                this.setMove("Split", (byte) 60, Intent.UNKNOWN);
                this.createIntent();
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, "Split", (byte) 60, Intent.UNKNOWN));
            }
        }
    }

    public void takeTurn() {
        super.takeTurn();
        switch (this.nextMove) {
            case 20:
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, "Regrowing..."));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte) 40, Intent.BUFF));
                break;
            case 40:
                this.halfDead = false;
                if (MathUtils.randomBoolean()) {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_2", MathUtils.random(-0.1F, 0.1F)));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("DARKLING_REGROW_1", MathUtils.random(-0.1F, 0.1F)));
                }

                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth / 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegrowPower(this), 1));
                AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
                break;
            case 50:
                break;
            case 30:
                if (AbstractDungeon.ascensionLevel >= 9) {
                    this.maxHealth = 320;
                } else {
                    this.maxHealth = 300;
                }

                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    this.maxHealth = (int) ((float) this.maxHealth * mod);
                }

                if (ModHelper.isModEnabled("MonsterHunter")) {
                    this.currentHealth = (int) ((float) this.currentHealth * 1.5F);
                }

                this.halfDead = false;
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.maxHealth));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate"));
                int dos = AbstractDungeon.player.currentHealth / 12 + 1;
                this.damage.get(2).base = dos;
                this.applyPowers();
                this.setMove((byte) 1, Intent.ATTACK, this.damage.get(2).base, 6, true);
                break;
            case 60:

                for (HexaghostOrb orb : (ArrayList<HexaghostOrb>) ReflectionHacks.getPrivate(this, Hexaghost.class, "orbs")) {
                    orb.hide();
                }

                AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
                AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(this, 1.0F, 0.1F));
                AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(this));
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this, false));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));
                AbstractMonster SplitOne = new SexyHexaghost(false);
                SplitOne.drawX = (float) Settings.WIDTH * 0.75F - 385 * Settings.scale;
                SplitOne.maxHealth = this.currentHealth;
                SplitOne.currentHealth = this.currentHealth;
                AbstractMonster SplitTwo = new SexyHexaghost(false);
                SplitTwo.drawX = (float) Settings.WIDTH * 0.75F + 120 * Settings.scale;
                SplitTwo.maxHealth = this.currentHealth;
                SplitTwo.currentHealth = this.currentHealth;
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SplitOne, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(SplitTwo, false));
                AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
                break;
        }

    }

    protected void getMove(int num) {
        if (this.hasPower(ExplosivePower.POWER_ID)) {
            if (this.getPower(ExplosivePower.POWER_ID).amount == 1) {
                this.setMove((byte) 50, Intent.UNKNOWN);
            } else if (this.halfDead) {
                this.setMove((byte) 40, Intent.BUFF);
            } else if (!((boolean) ReflectionHacks.getPrivate(this, Hexaghost.class, "activated"))) {
                ReflectionHacks.setPrivate(this, Hexaghost.class, "activated", true);
                this.setMove((byte) 5, Intent.UNKNOWN);
            } else {
                super.getMove(num);
            }
        } else if (this.halfDead) {
            this.setMove((byte) 40, Intent.BUFF);
        } else if (!((boolean) ReflectionHacks.getPrivate(this, Hexaghost.class, "activated"))) {
            ReflectionHacks.setPrivate(this, Hexaghost.class, "activated", true);
            this.setMove((byte) 5, Intent.UNKNOWN);
        } else {
            super.getMove(num);
        }

    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose || !(this.hasPower(UnawakenedPower.POWER_ID) || this.hasPower(RegrowPower.POWER_ID))) {
            if (this.boss) {
                this.useFastShakeAnimation(5.0F);
                CardCrawlGame.screenShake.rumble(4.0F);
            }
            super.die();

            for (HexaghostOrb orb : (ArrayList<HexaghostOrb>) ReflectionHacks.getPrivate(this, Hexaghost.class, "orbs")) {
                orb.hide();
            }

            if (this.boss) {
                this.onBossVictoryLogic();
                if (AbstractDungeon.actNum == 3 && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                    this.onFinalBossVictoryLogic();
                }
            }
            if (!this.hasPower(MinionPower.POWER_ID)) {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m.hasPower(MinionPower.POWER_ID)) {
                        if (!m.isDead && !m.isDying) {
                            AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                            AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                            AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
                        }
                    }
                }
            }
        }
    }
}
