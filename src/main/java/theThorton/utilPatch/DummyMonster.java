package theThorton.utilPatch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import theThorton.powers.TrueFormPower;

public class DummyMonster extends AbstractMonster {
    public BobEffect effect;

    public DummyMonster(float hb_x, float hb_y, float hb_w, float hb_h, Texture t) {
        super("", "", 1, hb_x, hb_y, hb_w, hb_h, null);
        this.img = t;
        this.effect = new BobEffect();
    }

    @Override
    public void takeTurn() {

    }

    @Override
    protected void getMove(int i) {

    }

    @SpireOverride
    protected void renderIntent(SpriteBatch sb) {
        SpireSuper.call(sb);
    }

    @Override
    public void renderHealth(SpriteBatch sb) {

    }

    public void refresh() {
        this.refreshHitboxLocation();
        this.refreshIntentHbLocation();
    }

    @Override
    public void update() {
        this.effect.update();
        this.updateIntent();
    }

    @SpireOverride
    protected void calculateDamage(int dmg) {
        AbstractMonster target = TrueFormPower.frontMonster();// 1266
        float tmp = (float) dmg;// 1267

        for (AbstractPower q : AbstractDungeon.player.powers) {
            tmp = q.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
        }
        for (AbstractPower q : target.powers) {
            tmp = q.atDamageReceive(tmp, DamageInfo.DamageType.NORMAL);
        }

        for (AbstractPower q : AbstractDungeon.player.powers) {
            tmp = q.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
        }
        for (AbstractPower q : target.powers) {
            tmp = q.atDamageFinalReceive(tmp, DamageInfo.DamageType.NORMAL);
        }

        dmg = MathUtils.floor(tmp);// 1303
        if (dmg < 0) {// 1304
            dmg = 0;// 1305
        }

        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }

    @SpireOverride
    protected void updateIntent() {
        SpireSuper.call();
    }
}