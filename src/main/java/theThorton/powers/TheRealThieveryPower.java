package theThorton.powers;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TheRealThieveryPower extends AbstractPower {
    public static final String POWER_ID = "Thievery";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public TheRealThieveryPower(AbstractCreature owner, int stealAmount) {
        this.name = NAME;
        this.ID = "Thievery";
        this.owner = owner;
        this.amount = stealAmount;
        this.updateDescription();
        this.loadRegion("thievery");
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL && target == AbstractDungeon.player) {
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(this.amount * -1));
            AbstractDungeon.getCurrRoom().addStolenGoldToRewards(this.amount);
        }
    }

    public void updateDescription() {
        this.description = this.owner.name + DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Thievery");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
