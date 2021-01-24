package theThorton.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.ChangeGoldAction;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class Portfolio extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("Portfolio");
    public static final String IMG = makeCardPath("Portfolio.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 2;

    public Portfolio() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("Portfolio.png"));
        exhaust = true;
        this.tags.add(CardTags.HEALING);
        baseMagicNumber = magicNumber = 20;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChangeGoldAction(magicNumber));
        AbstractCard business = new BusinessCard();
        if (upgraded) {
            business.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(business, 2, true, false));
        AbstractCard invest = new Investment();
        if (upgraded) {
            invest.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(invest, 1, true, false));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(5);
            initializeDescription();
        }
    }
}
