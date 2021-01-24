package theThorton.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class ThickSkinPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ThortMod.makeID("ThickSkinPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(ThortMod.makePowerPath("StoneSkin_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ThortMod.makePowerPath("StoneSkin_32.png"));

    public ThickSkinPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > TempHPField.tempHp.get(this.owner)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(this.owner, this.owner, this.amount));
        }
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new ThickSkinPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }
}