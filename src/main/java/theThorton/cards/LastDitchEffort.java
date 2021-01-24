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
import org.apache.commons.lang3.StringUtils;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;


public class LastDitchEffort extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("LastDitchEffort");
    public static final String IMG = makeCardPath("LastDitchEffort.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int DAMAGE_UP = 3;

    public LastDitchEffort() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("LastDitchEffort.png"));
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2F) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 4F) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = colorMe(this.rawDescription);
        initializeDescription();
    }

    private static String colorMe(String description) {
        ArrayList<String> parsedDescription = new ArrayList<>(Arrays.asList(description.split(" NL ")));
        ListIterator<String> it = parsedDescription.listIterator();
        while (it.hasNext()) {
            String s = it.next();
            if ((s.contains("50%") && AbstractDungeon.player.currentHealth >= AbstractDungeon.player.maxHealth / 2F) || (s.contains("25%") && AbstractDungeon.player.currentHealth >= AbstractDungeon.player.maxHealth / 4F)) {
                ArrayList<String> blahblah = new ArrayList<>(Arrays.asList(s.split(" ")));
                ListIterator<String> wah = blahblah.listIterator();
                while (wah.hasNext()) {
                    String r = wah.next();
                    if (!r.startsWith("!")) {
                        wah.set("[#9a9b9c]" + r + "[]");
                    }
                }
                it.set(StringUtils.join(blahblah, " "));
            } else {
                ArrayList<String> blahblah = new ArrayList<>(Arrays.asList(s.split(" ")));
                ListIterator<String> wah = blahblah.listIterator();
                while (wah.hasNext()) {
                    String r = wah.next();
                    if (!r.startsWith("!")) {
                        wah.set(r.replaceAll("#9a9b9c", ""));
                    }
                }
                it.set(StringUtils.join(blahblah, " "));
            }
        }
        return StringUtils.join(parsedDescription, " NL ");
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGE_UP);
            initializeDescription();
        }
    }
}
