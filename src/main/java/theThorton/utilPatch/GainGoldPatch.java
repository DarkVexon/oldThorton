package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.relics.GreedFountain;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "gainGold"
)

public class GainGoldPatch {
    public static void Prefix(AbstractPlayer __instance, @ByRef int[] amount) {
        if (AbstractDungeon.player.hasRelic(GreedFountain.ID)) {
            AbstractDungeon.player.getRelic(GreedFountain.ID).flash();
            amount[0] *= 1.5;
        }
    }
}