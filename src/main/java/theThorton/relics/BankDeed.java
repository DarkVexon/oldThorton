package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theThorton.ThortMod;
import theThorton.utilPatch.TextureLoader;

public class BankDeed extends CustomRelic {

    public static final String ID = ThortMod.makeID("BankDeed");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("ConsolationPrize.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("ConsolationPrize.png"));

    public BankDeed() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        AbstractDungeon.player.gainGold(AbstractDungeon.player.gold / 50);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
