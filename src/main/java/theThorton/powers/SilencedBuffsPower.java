//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class SilencedBuffsPower extends AbstractPower implements OnReceivePowerPower {
    public static final String POWER_ID = ThortMod.makeID("SilencedBuffsPower");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;
    private static final Texture tex84 = TextureLoader.getTexture(ThortMod.makePowerPath("LoseFocus_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ThortMod.makePowerPath("LoseFocus_32.png"));

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public SilencedBuffsPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.canGoNegative = false;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[0];
        }
    }

    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.BUFF && source == this.owner) {
            this.flash();
            return false;
        } else {
            return true;
        }
    }
}
