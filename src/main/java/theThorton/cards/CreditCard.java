package theThorton.cards;

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


public class CreditCard extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("CreditCard");
    public static final String IMG = makeCardPath("CreditCard.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 0;
    private static final int AMOUNT = 5;

    public CreditCard() {
        this(0);
    }

    public CreditCard(int upgrades) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("CreditCard.png"));
        baseMagicNumber = magicNumber = AMOUNT;
        exhaust = true;
        tags.add(CardTags.HEALING);
        this.timesUpgraded = upgrades;
    }

    public boolean canUpgrade() {
        return true;// 58
    }

    @Override
    public AbstractCard makeCopy() {
        return new CreditCard(this.timesUpgraded);
    }

    public void upgrade() {
        this.upgradeMagicNumber(5 + this.timesUpgraded);// 49
        ++this.timesUpgraded;// 50
        this.upgraded = true;// 51
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;// 52
        this.initializeTitle();// 53
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChangeGoldAction(magicNumber));
    }
}
