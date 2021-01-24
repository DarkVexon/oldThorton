package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class Resume extends CustomRelic {

    public static final String ID = ThortMod.makeID("Resume");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("Resume.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("LastWill.png"));

    public Resume() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        ThortMod.businessCardMax -= 3;
        if (ThortMod.businessCardAmt >= ThortMod.businessCardMax) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier()));
            ThortMod.businessCardAmt = 0;
        }
    }

    @Override
    public void onUnequip() {
        ThortMod.businessCardMax += 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
