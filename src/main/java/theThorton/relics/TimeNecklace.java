package theThorton.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theThorton.ThortMod;
import theThorton.actions.TemporalPlayCardAction;
import theThorton.utilPatch.TextureLoader;

public class TimeNecklace extends CustomRelic {

    public static final String ID = ThortMod.makeID("TimeNecklace");

    private static final Texture IMG = TextureLoader.getTexture(ThortMod.makeRelicPath("PreRecordedMusic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(ThortMod.makeRelicOutlinePath("PreRecordedMusic.png"));

    public TimeNecklace() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().eliteTrigger) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3), 3));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 3), 3));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(CouragePotion.ID)) {// 52
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {// 53
                if (AbstractDungeon.player.relics.get(i).relicId.equals(CouragePotion.ID)) {// 54
                    this.instantObtain(AbstractDungeon.player, i, true);// 55
                    break;// 56
                }
            }
        } else {
            super.obtain();// 60
        }
    }// 62

    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.player.hasRelic(CouragePotion.ID));
    }

    @Override
    public String getUpdatedDescription() {
        String name = (new CouragePotion()).name;// 38
        StringBuilder sb = new StringBuilder();// 39
        String[] var3 = name.split(" ");

        for (String word : var3) {// 40
            sb.append("[#").append(ThortMod.THORT_GRAY.toString()).append("]").append(word).append("[] ");// 41
        }

        sb.setLength(sb.length() - 1);// 43
        sb.append("[#").append(ThortMod.THORT_GRAY.toString()).append("]");// 44
        return this.DESCRIPTIONS[0] + sb.toString() + this.DESCRIPTIONS[1];// 46
    }

}
