package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.random.Random;
import javassist.CtBehavior;
import theThorton.characters.TheThorton;

import java.util.ArrayList;

@SpirePatch(clz = AbstractDungeon.class, method = "getEvent")
public class ThortonExclusiveEvent {

    @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
    public static void Insert(Random rng, ArrayList<String> tmp) {
        if (AbstractDungeon.player.chosenClass == TheThorton.Enums.THE_THORT) {
            tmp.removeIf(d -> d.equals(Vampires.ID));
            tmp.removeIf(d -> d.equals(Ghosts.ID));
        } else {
            tmp.removeIf(d -> d.equals(VampiresThortonEvent.ID));
            tmp.removeIf(d -> d.equals(GhostsThortonEvent.ID));
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "isEmpty");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }

}