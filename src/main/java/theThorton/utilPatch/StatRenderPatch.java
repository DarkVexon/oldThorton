package theThorton.utilPatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

@SpirePatch(
        clz = EnergyPanel.class,
        method = "render",
        paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class StatRenderPatch {
    public static void Prefix(EnergyPanel __instance, SpriteBatch sb) {
        if (ThortMod.renderStuff || AbstractDungeon.player instanceof TheThorton) {
            if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) || AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
                TipHelperCopy.renderGenericTip(ThortMod.newHitbox.hb.x, ThortMod.newHitbox.hb.y + ThortMod.newHitbox.hb.height, "Important Info (Draggable)", "#yBusiness #yCards #yPlayed: #b" + ThortMod.businessCardAmt + " / #b" + ThortMod.businessCardMax + " NL #yInvestments #yPlayed: #b" + ThortMod.investmentAmt + " / #b" + ThortMod.investmentMax + " NL After #yRunning #b" + (ThortMod.fleeMax - ThortMod.fleeAmt) + " times, #pdeal #b" + ThortMod.BossModeDamage + " #pdamage #pto #pALL #penemies.");
            } else {
                TipHelperCopy.renderGenericTip(ThortMod.newHitbox.hb.x, ThortMod.newHitbox.hb.y + ThortMod.newHitbox.hb.height, "Important Info (Draggable)", "#yBusiness #yCards #yPlayed: #b" + ThortMod.businessCardAmt + " / #b" + ThortMod.businessCardMax + " NL #yInvestments #yPlayed: #b" + ThortMod.investmentAmt + " / #b" + ThortMod.investmentMax + " NL After #yRunning #b" + (ThortMod.fleeMax - ThortMod.fleeAmt) + " times, #gwin #gcombat.");
            }
            TipHelperCopy.render(sb);
        }
    }
}
