package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.TextureLoader;

import java.util.ArrayList;

public class BrokenCalculator extends CustomRelic {

    public static final String ID = ThortMod.makeID("BrokenCalculator");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("BetterTron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("BetterTron.png"));

    public BrokenCalculator() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        {
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (c.color == TheThorton.Enums.COLOR_GRAY) {
                    allCards.add(c.makeCopy());
                    AbstractCard d = c.makeCopy();
                    d.upgrade();
                    allCards.add(d.makeStatEquivalentCopy());
                }
            }
        }
        AbstractCard q = allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1));
        AbstractMonster sadMonster = AbstractDungeon.getRandomMonster();
        q.freeToPlayOnce = true;
        q.purgeOnUse = true;
        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(q.makeStatEquivalentCopy()));
        AbstractDungeon.actionManager.addToBottom(new QueueCardAction(q, sadMonster));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
