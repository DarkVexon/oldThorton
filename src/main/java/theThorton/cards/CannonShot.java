package theThorton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import theThorton.NextCombatInfo;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

import static theThorton.ThortMod.makeCardPath;

public class CannonShot extends AbstractNextCombatThortonCard {

    public static final String ID = ThortMod.makeID("CannonShot");
    public static final String IMG = makeCardPath("CannonShot.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;

    public CannonShot() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 4;
        exhaust = true;
        isEthereal = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public boolean doNextBattleCheck() {
        return AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
    }

    @Override
    public void doNextBattle(int magic1, int magic2, String magicString, boolean upgrade) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, magic1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                ThortMod.importantInfoStuff.add(new NextCombatInfo(cardID, magicNumber, -1, "fuck", false));
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }


    @Override
    public void applyPowers() {
        magicNumber = baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = baseMagicNumber;

        super.applyPowers();

        magicNumber = damage;
        baseDamage = tmp;

        super.applyPowers();

        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicNumber = baseMagicNumber;

        int tmp = baseDamage;
        baseDamage = baseMagicNumber;

        super.calculateCardDamage(mo);

        magicNumber = damage;
        baseDamage = tmp;

        super.calculateCardDamage(mo);

        isMagicNumberModified = (magicNumber != baseMagicNumber);
    }

    @Override
    public void onMoveToDiscard() {
        magicNumber = baseMagicNumber;
        isMagicNumberModified = false;
    }
}
