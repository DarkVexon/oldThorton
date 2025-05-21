package theThorton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.actions.LifeDrainAction;
import theThorton.characters.TheThorton;

import java.util.ArrayList;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class PowerCreep extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("PowerCreep");
    public static final String IMG = makeCardPath("PowerCreep.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;

    public PowerCreep() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard result = getAnyCardRandomly();
        result.baseDamage *= magicNumber;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(result));
    }

    public static AbstractCard getAnyCardRandomly() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if ((c.type == CardType.ATTACK) && !(c.color == CardColor.CURSE) && !(c.cost == -2)) {
                allCards.add(c.makeCopy());
            }
        }
        return allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1)).makeCopy();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
