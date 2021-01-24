package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theThorton.ThortMod;

import java.util.ArrayList;

public class PetalPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getRewardCards"
    )
    public static class AddCardReward {
        public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result) {
            if (ThortMod.bonusBig) {
                int blah = __result.size();
                __result.clear();
                for (int i = 0; i < blah; i++) {
                    __result.add(CardLibrary.getAnyColorCard(AbstractCard.CardRarity.RARE));
                }
                ThortMod.bonusBig = false;
            }

            return __result;
        }
    }
}