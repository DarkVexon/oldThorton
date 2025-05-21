package theThorton.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theThorton.ThortMod;
import theThorton.cards.AdministrativeActions;
import theThorton.utilPatch.TextureLoader;

import javax.smartcardio.Card;

public class JellyG extends CustomRelic {

    public static final String ID = ThortMod.makeID("JellyG");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("JellyG.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("JellyG.png"));

    public JellyG() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
        tips.add(new CardPowerTip(new AdministrativeActions()));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AdministrativeActions(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
