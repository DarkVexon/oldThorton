//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.powers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReactiveCopy extends AbstractPower {
    public static final String POWER_ID = "Compulsive";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Compulsive");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public ReactiveCopy(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Compulsive";
        this.owner = owner;
        this.updateDescription();
        this.loadRegion("reactive");
        this.priority = 50;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < this.owner.currentHealth) {
            this.flash();
            int idleCount;
            AbstractMonster m = (AbstractMonster) this.owner;
            if (m.intent == AbstractMonster.Intent.UNKNOWN) {
                AbstractDungeon.actionManager.addToTop(new ChangeStateAction(m, "Activate"));
                idleCount = AbstractDungeon.player.currentHealth / 12 + 1;
                m.damage.get(2).base = idleCount;
                m.applyPowers();
                m.setMove((byte) 1, AbstractMonster.Intent.ATTACK, idleCount, 6, true);
                m.createIntent();
            }

            idleCount = (int) ReflectionHacks.getPrivate(m, Hexaghost.class, "orbActiveCount");
            if (idleCount == 6) {
                m.changeState("Deactivate");
            } else {
                m.changeState("Activate Orb");
            }

            m.rollMove();
            m.createIntent();
        }
        return damageAmount;
    }
}
