package theThorton.utilPatch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import theThorton.cards.Jackpot;

@SpirePatch(
        clz = FastCardObtainEffect.class,
        method = "update"
)
public class OnPickupCardDoStuffPatch {
    public static void Postfix(FastCardObtainEffect __instance) {
        if (__instance.isDone && ((AbstractCard) ReflectionHacks.getPrivate(__instance, FastCardObtainEffect.class, "card")) instanceof Jackpot) {
            AbstractDungeon.player.gainGold(300);
            CardCrawlGame.sound.play("GOLD_GAIN");
        }
    }
}