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
import theThorton.characters.TheThorton;

import java.util.ArrayList;

import static theThorton.ThortMod.makeCardPath;


public class Purgatory extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("Purgatory");
    public static final String IMG = makeCardPath("Purgatory.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 4;

    public Purgatory() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : p.drawPile.group) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    c.freeToPlayOnce = true;
                    isDone = true;
                }
            });
        }
        for (AbstractCard c : p.discardPile.group) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    c.freeToPlayOnce = true;
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}
