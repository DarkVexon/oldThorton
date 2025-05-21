package theThorton.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.actions.TypingAction;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.Wiz;

import java.util.ArrayList;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class AdministrativeActions extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("AdministrativeActions");
    public static final String IMG = makeCardPath("AdministrativeActions.png");
    public static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;

    public static ArrayList<String> used = new ArrayList<>();

    public AdministrativeActions() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("AdministrativeActions.png"));
        exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TypingAction((string) -> {
            used.add(string);
            ArrayList<AbstractCard> cards = Wiz.getCardsMatchingPredicate((card) -> card.originalName.equalsIgnoreCase(string), true);
            AbstractCard c = Wiz.getRandomItem(cards);
            if (c == null) {
                c = new Madness();
                c.name = string;
                ReflectionHacks.privateMethod(AbstractCard.class, "initializeTitle").invoke(c);
            }
            addToTop(new MakeTempCardInDrawPileAction(c, 1, false, true));
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
