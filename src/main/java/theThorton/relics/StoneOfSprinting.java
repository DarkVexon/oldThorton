package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.ThortMod;
import theThorton.cards.*;
import theThorton.utilPatch.TextureLoader;

import java.util.ArrayList;

public class StoneOfSprinting extends CustomRelic {

    public static final String ID = ThortMod.makeID("StoneOfSprinting");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("PaidLearning.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("PaidLearning.png"));

    public StoneOfSprinting() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStartPreDraw() {
        ArrayList<AbstractCard> bigCardGroup = new ArrayList<>();
        bigCardGroup.add(new DesperateRun());
        bigCardGroup.add(new DodgeAndRollAndRun());
        bigCardGroup.add(new DoubleRun());
        bigCardGroup.add(new FearfulRun());
        bigCardGroup.add(new RunningKick());
        bigCardGroup.add(new RunningFeint());
        bigCardGroup.add(new SicklyRun());
        bigCardGroup.add(new ShoutAndRun());
        AbstractCard card = bigCardGroup.get(AbstractDungeon.cardRandomRng.random(bigCardGroup.size() - 1));
        if (card.cost > 0) {
            card.cost = 0;
            card.costForTurn = 0;
            card.isCostModified = true;
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
