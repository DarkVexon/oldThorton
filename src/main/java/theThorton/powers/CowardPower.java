package theThorton.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theThorton.ThortMod;
import theThorton.actions.TryToFleeAction;
import theThorton.utilPatch.TextureLoader;

public class CowardPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ThortMod.makeID("CowardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(ThortMod.makePowerPath("CowardForm_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ThortMod.makePowerPath("CowardForm_32.png"));

    public CowardPower(final AbstractCreature owner, int amount) {
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
    public void atStartOfTurnPostDraw() {
        this.flash();
        for (int i = 0; i < this.amount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new TryToFleeAction());
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CowardPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        {
            if (this.amount == 1) {
                description = DESCRIPTIONS[0];
            } else {
                description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }
        }
    }
}