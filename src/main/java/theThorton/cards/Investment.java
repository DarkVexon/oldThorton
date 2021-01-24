package theThorton.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.DamageCurvy;
import theThorton.utilPatch.DamageLine;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class Investment extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("Investment");
    public static final String IMG = makeCardPath("Investment.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    private float offset = MathUtils.random(-180.0F, 180.0F);

    public Investment() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("Investment.png"));
        exhaust = true;
        isEthereal = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!ThortMod.renderStuff && !(AbstractDungeon.player instanceof TheThorton)) {
            ThortMod.renderStuff = true;
        }
        for (int i = 0; i < 36; i++) {
            AbstractDungeon.effectList.add(new DamageLine(ThortMod.newHitbox.hb.cX, ThortMod.newHitbox.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1), ((10 * i) + MathUtils.random(-10, 10) + offset)));
            if (i % 2 == 0) {
                AbstractDungeon.effectList.add(new DamageCurvy(ThortMod.newHitbox.hb.cX, ThortMod.newHitbox.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1)));
            }
        }
        ThortMod.investmentAmt++;
        if (ThortMod.investmentAmt >= ThortMod.investmentMax) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, ThortMod.returnTrueRandomScreenlessRelic());
            ThortMod.investmentAmt = 0;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
