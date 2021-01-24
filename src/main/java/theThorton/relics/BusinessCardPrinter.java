package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.ThortMod;
import theThorton.cards.BusinessCard;
import theThorton.utilPatch.TextureLoader;

public class BusinessCardPrinter extends CustomRelic {

    public static final String ID = ThortMod.makeID("BusinessCardPrinter");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("BrokenCalc.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("BrokenCalc.png"));

    public BusinessCardPrinter() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new BusinessCard()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
