package theThorton.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theThorton.ThortMod;
import theThorton.actions.GKsNightmareAction;
import theThorton.characters.TheThorton;

import static theThorton.ThortMod.makeCardPath;


public class GKsNightmare extends AbstractThortonCard implements StartupCard {

    public static final String ID = ThortMod.makeID("GKsNightmare");
    public static final String IMG = makeCardPath("GKsNightmare.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = -1;

    public GKsNightmare() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        cardsToPreview = new IronWave();
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new GKsNightmareAction(p, freeToPlayOnce, energyOnUse, upgraded));
    }

    @Override
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, magicNumber, DamageInfo.DamageType.HP_LOSS));
        return true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
        }
    }
}
