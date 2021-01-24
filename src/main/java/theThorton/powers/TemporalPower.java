package theThorton.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theThorton.NextCombatInfo;
import theThorton.ThortMod;
import theThorton.actions.TemporalPlayCardAction;
import theThorton.cards.TemporalBlast;
import theThorton.utilPatch.TextureLoader;

public class TemporalPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ThortMod.makeID("TemporalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(ThortMod.makePowerPath("EvasiveThoughts_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(ThortMod.makePowerPath("EvasiveThoughts_32.png"));

    public TemporalPower(final AbstractCreature owner, final int amount) {
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
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ThortMod.importantInfoStuff.add(new NextCombatInfo(TemporalBlast.ID, -1, -1, card.cardID, card.upgraded));
                isDone = true;
            }
        });
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TemporalPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        {
            if (this.amount == 0) {
                description = DESCRIPTIONS[0];
            } else {
                description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }
        }
    }
}