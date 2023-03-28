package theThorton.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.powers.GhostPower;
import theThorton.utilPatch.Wiz;

import java.util.List;
import java.util.stream.Collectors;

import static theThorton.ThortMod.makeCardPath;
import static theThorton.utilPatch.Wiz.applyToEnemy;
import static theThorton.utilPatch.Wiz.att;


public class Ghost extends AbstractThortonCard implements StartupCard {

    public static final String ID = ThortMod.makeID("Ghost");
    public static final String IMG = makeCardPath("Ghost.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int DAMAGE = 14;
    private static final int DAMAGE_UP = 4;

    public Ghost() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(scream()));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        applyToEnemy(m, new GhostPower(m, magicNumber));
    }

    private static String scream() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            return "VO_NEMESIS_2A";
        } else {
            return "VO_NEMESIS_2B";
        }
    }

    private static boolean canDisguiseAs(AbstractCard target) {
        return target.cost != -2 && !target.cardID.equals(Ghost.ID);
    }

    @Override
    public boolean atBattleStartPreDraw() {
        att(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                List<AbstractCard> possibilities = AbstractDungeon.player.drawPile.group.stream().filter(Ghost::canDisguiseAs).collect(Collectors.toList());
                if (!possibilities.isEmpty() && AbstractDungeon.player.drawPile.contains(Ghost.this)) {
                    int index = AbstractDungeon.player.drawPile.group.indexOf(Ghost.this);
                    AbstractDungeon.player.drawPile.removeCard(Ghost.this);
                    AbstractCard disguise = Wiz.getRandomItem(possibilities, AbstractDungeon.cardRandomRng).makeStatEquivalentCopy();
                    CardModifierManager.addModifier(disguise, new IsGhostModifier(Ghost.this));
                    if (index > 0) {
                        AbstractDungeon.player.drawPile.group.add(index, disguise);
                    } else {
                        AbstractDungeon.player.drawPile.addToRandomSpot(disguise);
                    }
                }
            }
        });
        return false;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}
