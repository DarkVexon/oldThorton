package theThorton.powers;

import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.IntentFlashAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import theThorton.ThortMod;
import theThorton.actions.ChooseIntentAction;
import theThorton.actions.ReplayThisAction;
import theThorton.utilPatch.DummyMonster;
import theThorton.utilPatch.TextureLoader;

import java.util.ArrayList;

public class TrueFormPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = ThortMod.makeID("TrueFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("thethortonResources/images/powers/TrueFormPower_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("thethortonResources/images/powers/TrueFormPower_32.png");

    private DummyMonster mo;

    public TrueFormPower() {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = -1;

        canGoNegative = false;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        mo = new DummyMonster(0, 0, 0, 0, TextureLoader.getTexture("thethortonResources/images/ui/EMPTY_PIXEL.png"));
        mo.drawX = AbstractDungeon.player.hb.cX;
        mo.drawY = AbstractDungeon.player.hb.y + AbstractDungeon.player.hb.height;
        mo.setMove((byte) 0, AbstractMonster.Intent.NONE, -1, -1, false);
        mo.refresh();
        mo.createIntent();
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new ChooseIntentAction(mo));
    }

    public static AbstractMonster frontMonster() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDying && !m.isDead && !m.halfDead) {
                return m;
            }
        }
        System.out.println("WHAT THE FUCK???");
        return AbstractDungeon.getRandomMonster();
    }

    public void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractMonster m = frontMonster();
        AbstractDungeon.actionManager.addToBottom(new IntentFlashAction(mo));
        switch (mo.intent) {
            case ATTACK:
                atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY)));
                atb(new DamageAction(m, new DamageInfo(this.owner, mo.getIntentDmg(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                break;
            case ATTACK_BUFF:
                atb(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                atb(new DamageAction(m, new DamageInfo(this.owner, mo.getIntentDmg(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                atb(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2), 2));
                break;
            case ATTACK_DEBUFF:
                atb(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                atb(new DamageAction(m, new DamageInfo(this.owner, mo.getIntentDmg(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                atb(new ApplyPowerAction(m, this.owner, new WeakPower(m, 2, false), 2));
                break;
            case ATTACK_DEFEND:
                atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY)));
                atb(new GainBlockAction(this.owner, this.owner, 10));
                atb(new DamageAction(m, new DamageInfo(this.owner, mo.getIntentDmg(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                break;
            case BUFF:
                atb(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 5), 5));
                break;
            case DEBUFF:
                atb(new ApplyPowerAction(m, this.owner, new WeakPower(m, 2, false), 2));
                atb(new ApplyPowerAction(m, this.owner, new VulnerablePower(m, 2, false), 2));
                break;
            case STRONG_DEBUFF:
                atb(new ApplyPowerAction(m, this.owner, new StrengthPower(m, -2), -2));
                break;
            case DEBUG:
                break;
            case DEFEND:
                atb(new GainBlockAction(this.owner, this.owner, 20));
                break;
            case DEFEND_DEBUFF:
                atb(new GainBlockAction(this.owner, this.owner, 15));
                atb(new ApplyPowerAction(m, this.owner, new VulnerablePower(m, 2, false), 2));
                break;
            case DEFEND_BUFF:
                atb(new GainBlockAction(this.owner, this.owner, 10));
                atb(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3), 3));
                break;
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        mo.applyPowers();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        mo.update();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, c);
        mo.render(sb);
    }

    @Override
    public AbstractPower makeCopy() {
        return new TrueFormPower();
    }

    @Override
    public void updateDescription() {
        if (mo.intent == null) {
            description = "Choose an Intent at the start of your turn.";
        } else {
            String blah = ((PowerTip) ReflectionHacks.getPrivate(mo, AbstractMonster.class, "intentTip")).body;
            blah = blah.replaceAll("This enemy", "You");
            blah = blah.replaceAll("intends", "intend");
            blah = blah.replaceAll("is", "are");
            description = blah;
        }
    }
}