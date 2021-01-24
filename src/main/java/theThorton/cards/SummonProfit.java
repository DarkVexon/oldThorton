package theThorton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.actions.SummonProfitAction;
import theThorton.characters.TheThorton;

import java.util.ArrayList;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class SummonProfit extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("SummonProfit");
    public static final String IMG = makeCardPath("SummonProfits.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;

    public SummonProfit() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("SummonProfits.png"));
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    public static AbstractCard getAnyCardRandomly() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!(c.type == CardType.STATUS) && !(c.type == CardType.CURSE) && !(c.color == CardColor.CURSE) && !(c.color == CardColor.COLORLESS) && !(c.cost == -2)) {
                allCards.add(c.makeCopy());
            }
        }
        return allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1)).makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SummonProfitAction(upgraded));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
