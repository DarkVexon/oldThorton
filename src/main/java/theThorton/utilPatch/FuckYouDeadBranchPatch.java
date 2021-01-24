//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.utilPatch;

import com.evacipated.cardcrawl.mod.stslib.RelicTools;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.DeadBranch;
import com.megacrit.cardcrawl.relics.StrangeSpoon;
import theThorton.characters.TheThorton;

public class FuckYouDeadBranchPatch {
    public FuckYouDeadBranchPatch() {
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnEndRandomRelicKey"
    )
    public static class EndRandomRelic {
        private static int depth = 0;

        public EndRandomRelic() {
        }

        public static String Postfix(String __result, RelicTier tier) {
            if (depth == 0 && (RelicLibrary.getRelic(__result) instanceof DeadBranch || RelicLibrary.getRelic(__result) instanceof StrangeSpoon) && AbstractDungeon.player instanceof TheThorton) {
                RelicTools.returnRelicToPool(tier, __result);
                ++depth;
                __result = AbstractDungeon.returnEndRandomRelicKey(tier);
                --depth;
            }

            return __result;
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "returnRandomRelicKey"
    )
    public static class RandomRelic {
        private static int depth = 0;

        public RandomRelic() {
        }

        public static String Postfix(String __result, RelicTier tier) {
            if (depth == 0 && RelicLibrary.getRelic(__result) instanceof DeadBranch && AbstractDungeon.player instanceof TheThorton) {
                RelicTools.returnRelicToPool(tier, __result);
                ++depth;
                __result = AbstractDungeon.returnRandomRelicKey(tier);
                --depth;
            }
            return __result;
        }
    }
}