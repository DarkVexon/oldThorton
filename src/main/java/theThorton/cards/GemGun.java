package theThorton.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.actions.ShootAnythingAction;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.*;

import java.util.ArrayList;

import static theThorton.ThortMod.makeCardPath;
import static theThorton.ThortMod.makeID;


public class GemGun extends AbstractThortonCard {

    public static final String ID = makeID(GemGun.class.getSimpleName());
    public static final String IMG = makeCardPath("GemGun.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;

    public GemGun() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, TheThorton.Enums.COLOR_GRAY, RARITY, TARGET);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
        tags.add(CardTags.HEALING);
    }

    private int maxLines = 36;
    private int stride = 360 / maxLines;
    private float offset = MathUtils.random(-180.0F, 180.0F);


    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<Integer> myList = new ArrayList<>();
        for (int i = 0; i < magicNumber; i++) {
            int x = AbstractDungeon.cardRandomRng.random(1, 7);
            myList.add(x);
            addToBot(new ShootAnythingAction(m, TextureLoader.getTexture("thethortonResources/images/ui/Gem" + x + ".png")));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        int trackNum = Integer.MIN_VALUE;
        int count = 0;
        for (int i : myList) {
            if (trackNum != i) {
                trackNum = i;
                count = 0;
            } else {
                count++;
                if (count > 2) {
                    CardCrawlGame.sound.play("UNLOCK_PING");
                    for (int q = 0; q < maxLines; q++) {
                        AbstractDungeon.effectList.add(new DamageLine(p.hb.cX, p.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1), ((stride * i) + MathUtils.random(-stride, stride) + offset)));
                        if (q % 2 == 0) {
                            AbstractDungeon.effectList.add(new DamageCurvy(p.hb.cX, p.hb.cY, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1)));
                        }
                    }
                    AbstractDungeon.effectList.add(new SuperWordAboveCreatureEffect(p, p.hb.cX, p.hb.cY, "LUCKY!"));
                    AbstractDungeon.actionManager.addToBottom(new ChangeGoldAction(50));
                }
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}