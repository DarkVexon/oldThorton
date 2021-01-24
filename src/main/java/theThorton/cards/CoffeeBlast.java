package theThorton.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

import static theThorton.ThortMod.makeCardPath;


public class CoffeeBlast extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("CoffeeBlast");
    public static final String IMG = makeCardPath("CoffeeBlast.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public CoffeeBlast() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0; i < 2; i++) {
                    AbstractCard q = SummonProfit.getAnyCardRandomly();
                    AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                        @Override
                        public void update() {
                            if (this.amount + AbstractDungeon.player.hand.size() > 10) {// 63
                                AbstractDungeon.player.createHandIsFullDialog();// 64
                                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(q, (float) Settings.WIDTH / 2.0F + (25.0F * Settings.scale) + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));// 160 162
                            } else {
                                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(q));// 92
                            }


                            if (this.amount > 0) {// 72
                                AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));// 73
                            }

                            this.isDone = true;// 76
                        }
                    });
                    AlwaysRetainField.alwaysRetain.set(q, true);
                    q.rawDescription = "Retain. NL " + q.rawDescription;
                    q.initializeDescription();
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
