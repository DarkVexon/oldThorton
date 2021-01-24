package theThorton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.ChangeGoldAction;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class WhirlingBriefcase extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("WhirlingBriefcase");
    public static final String IMG = makeCardPath("WhirlingBriefcase.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UP = 3;
    private static final int GOLD = 2;
    private static final int GOLD_UP = 1;

    public WhirlingBriefcase() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("WhirlingBriefcase.png"));
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = GOLD;
        isMultiDamage = true;
        exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDying && !q.isDead) {
                AbstractDungeon.actionManager.addToBottom(new ChangeGoldAction(magicNumber));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            upgradeMagicNumber(GOLD_UP);
            initializeDescription();
        }
    }
}
