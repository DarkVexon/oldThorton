//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theThorton.ThortMod;

public class SplitCopy extends AbstractPower {
    public static final String POWER_ID = ThortMod.makeID("SplitCopy");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public SplitCopy(AbstractCreature owner) {
        this.name = NAME;
        this.ID = ThortMod.makeID("SplitCopy");
        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
        this.loadRegion("split");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ThortMod.makeID("SplitCopy"));
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
