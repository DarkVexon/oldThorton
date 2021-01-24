package theThorton.utilPatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import theThorton.ThortMod;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.map.MapRoomNode", method = "render")
public class IconColorHook {
    @SpireInsertPatch(locator = Locator.class, localvars = {"room"})
    public static void Insert(Object meObj, Object sbObj, Object roomObj) {
        if ((AbstractRoom) roomObj instanceof EventRoom && ThortMod.nextQuestionShop) {
            ((SpriteBatch) sbObj).setColor(0.858F, 0.854F, 0, 1F);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(
                    "com.badlogic.gdx.graphics.g2d.SpriteBatch", "draw");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), matcher)[1]};
        }
    }
}