package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.ForgottenAltar;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;
import theThorton.characters.TheThorton;

import java.util.ArrayList;

public class EventRemovalPatch {

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getEvent"
    )
    public static class EventSpawn {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(Random rng, ArrayList<String> tmp) {
            if (AbstractDungeon.player instanceof TheThorton) {
                tmp.remove(ForgottenAltar.ID);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "getShrine"
    )
    public static class ShrineSpawn {

        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(Random rng, ArrayList<String> tmp) {
            if (AbstractDungeon.player instanceof TheThorton) {
                tmp.remove(ForgottenAltar.ID);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}