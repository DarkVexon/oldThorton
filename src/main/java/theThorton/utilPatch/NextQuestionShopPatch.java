package theThorton.utilPatch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;
import theThorton.ThortMod;

@SpirePatch(
        clz = EventHelper.class,
        method = "roll",
        paramtypez = {
                Random.class
        }
)

public class NextQuestionShopPatch {
    public static EventHelper.RoomResult Postfix(EventHelper.RoomResult choice, Random eventRng) {
        if (ThortMod.nextQuestionShop) {
            ThortMod.nextQuestionShop = false;
            return EventHelper.RoomResult.SHOP;
        }
        return choice;
    }
}