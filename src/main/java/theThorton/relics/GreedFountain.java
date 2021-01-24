package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class GreedFountain extends CustomRelic {

    public static final String ID = ThortMod.makeID("GreedFountain");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("LastWill.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("LastWill.png"));

    public GreedFountain() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.actNum == 1;
    }
}
