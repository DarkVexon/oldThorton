package theThorton.utilPatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.EventRoom;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

import java.util.ArrayList;

public class NextQuestionShopPatch2 {

    @SpirePatch(clz = MapRoomNode.class, method = "render")

    public static class MapRoomNodeRender {
        public static void Postfix(MapRoomNode self, SpriteBatch sb) {
            if (self.hb.hovered && self.room instanceof EventRoom && AbstractDungeon.player instanceof TheThorton) {
                if (ThortMod.nextQuestionShop) {
                    ArrayList<PowerTip> tips = new ArrayList<>();
                    tips.add(new PowerTip("? Room Contents", "This ? room contains a shop."));
                    TipHelper.queuePowerTips(self.hb.x + self.hb.width + 20.0f * Settings.scale, self.hb.y + self.hb.height, tips);
                }
            }
        }
    }
}
