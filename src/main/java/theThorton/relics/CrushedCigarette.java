package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class CrushedCigarette extends CustomRelic {

    public static final String ID = ThortMod.makeID("CrushedCigarette");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("clover.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("GildedClover.png"));

    public CrushedCigarette() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.cardRandomRng.randomBoolean(0.67F)) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
