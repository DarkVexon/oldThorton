package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import javassist.CtBehavior;
import org.apache.logging.log4j.Logger;
import theThorton.characters.SexyHexaghost;

import java.util.ArrayList;

@SpirePatch(
        clz = Darkling.class,
        method = "damage"
)
public class DarklingsPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = "allDead"
    )
    public static void Insert(Darkling __instance, DamageInfo info, @ByRef boolean[] allDead) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (q instanceof SexyHexaghost) {
                allDead[0] = false;
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
        }
    }
}
