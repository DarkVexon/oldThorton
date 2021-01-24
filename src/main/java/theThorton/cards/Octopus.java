package theThorton.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.actions.OctopusAction;
import theThorton.characters.TheThorton;

import static theThorton.ThortMod.makeCardPath;

public class Octopus extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("Octopus");
    public static final String IMG = makeCardPath("AttackShiftingOctopus.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int BLOCK = 5;
    private static final int THINGS = 2;

    public Octopus() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        // ThortMod.loadJokeCardImage(this, makeBetaCardPath("Octopus.png"));
        this.baseDamage = DAMAGE;
        exhaust = true;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = THINGS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new OctopusAction(m, damageTypeForTurn, this));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
        AbstractDungeon.actionManager.addToBottom(new OctopusAction(m, damageTypeForTurn, this));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
            AbstractDungeon.actionManager.addToBottom(new OctopusAction(m, damageTypeForTurn, this));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
