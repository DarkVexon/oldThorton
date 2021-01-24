package theThorton.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.DamageCurvy;
import theThorton.utilPatch.DamageLine;

public class TryToFleeAction extends AbstractGameAction {
    public TryToFleeAction() {
        this.actionType = ActionType.SPECIAL;
    }

    private float offset = MathUtils.random(-180.0F, 180.0F);

    @Override
    public void update() {
        if (!ThortMod.renderStuff && !(AbstractDungeon.player instanceof TheThorton)) {
            ThortMod.renderStuff = true;
        }
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new DamageLine(ThortMod.newHitbox.hb.cX, ThortMod.newHitbox.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new DamageCurvy(ThortMod.newHitbox.hb.cX, ThortMod.newHitbox.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1)));
            }
        }
        ThortMod.fleeAmt++;
        if (ThortMod.fleeAmt >= ThortMod.fleeMax) {
            if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)) {
                AbstractDungeon.actionManager.actions.removeIf(c -> (c instanceof TryToFleeAction && c != this));
            }
            ThortMod.fleeMax++;
            ThortMod.fleeAmt = 0;
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(ThortMod.BossModeDamage, true), DamageInfo.DamageType.THORNS, AttackEffect.SMASH));
                    } else {
                        AbstractDungeon.player.hideHealthBar();
                        AbstractDungeon.player.isEscaping = true;
                        AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
                        AbstractDungeon.overlayMenu.endTurnButton.disable();
                        AbstractDungeon.player.escapeTimer = 2.5F;
                    }
                    isDone = true;
                }
            });
        } else {
            if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) && !AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
                if (AbstractDungeon.player.flipHorizontal) {
                    AbstractDungeon.actionManager.addToBottom(new MoveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.drawX + (40 * Settings.scale), AbstractDungeon.player.drawY));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new MoveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.drawX - (40 * Settings.scale), AbstractDungeon.player.drawY));
                }
            }
        }
        this.isDone = true;
    }
}