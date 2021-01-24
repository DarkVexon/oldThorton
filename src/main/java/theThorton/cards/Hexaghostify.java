package theThorton.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import theThorton.ThortMod;
import theThorton.characters.SexyHexaghost;
import theThorton.characters.TheThorton;
import theThorton.powers.CurlUpCopy;
import theThorton.powers.ReactiveCopy;
import theThorton.powers.SplitCopy;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;

public class Hexaghostify extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("Hexaghostify");
    public static final String IMG = makeCardPath("Hexaghostify.png");
    public static final AbstractCard.CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 3;

    public Hexaghostify() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("Hexaghostify.png"));
        this.exhaust = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.actNum == 4 && AbstractDungeon.player instanceof TheThorton) {
            ((TheThorton) AbstractDungeon.player).hexaghostifiedHeart = true;
        }
        if (upgraded) {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.halfDead && !q.isDead && !q.isDying && !(q instanceof Hexaghost)) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(q.hb.cX, q.hb.cY)));
                    int basehitpoint = q.currentHealth;
                    int baseMaxHitPoint = q.maxHealth;
                    int basedefends = q.currentBlock;
                    AbstractMonster hexyfriend;
                    if (q.type == AbstractMonster.EnemyType.BOSS) {
                        hexyfriend = new SexyHexaghost(true);
                        CardCrawlGame.music.silenceBGMInstantly();
                        CardCrawlGame.music.silenceTempBgmInstantly();
                    } else {
                        hexyfriend = new SexyHexaghost(false);
                    }
                    hexyfriend.drawX = q.drawX;
                    AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                        public void update() {
                            q.isDead = true;
                            q.hideHealthBar();
                            AbstractDungeon.getCurrRoom().monsters.monsters.remove(q);
                            this.isDone = true;
                        }
                    });
                    AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(hexyfriend, false));
                    hexyfriend.maxHealth = baseMaxHitPoint;
                    hexyfriend.currentHealth = basehitpoint;
                    hexyfriend.currentBlock = basedefends;
                    hexyfriend.flipHorizontal = q.flipHorizontal;
                    for (AbstractPower f : q.powers) {
                        if (f instanceof ReactivePower) {
                            hexyfriend.powers.add(new ReactiveCopy(hexyfriend));
                        } else if (f instanceof CurlUpPower) {
                            hexyfriend.powers.add(new CurlUpCopy(hexyfriend, f.amount));
                        } else if (f instanceof SplitPower) {
                            hexyfriend.powers.add(new SplitCopy(hexyfriend));
                        } else {
                            hexyfriend.powers.add(f);
                            f.owner = hexyfriend;
                        }
                        if (f instanceof UnawakenedPower) {
                            AbstractDungeon.getCurrRoom().cannotLose = true;
                        }
                    }
                    hexyfriend.setMove((byte) 5, AbstractMonster.Intent.UNKNOWN);
                    hexyfriend.createIntent();
                    hexyfriend.healthBarUpdatedEvent();
                }
            }

        } else {
            if (!(m instanceof Hexaghost)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(m.hb.cX, m.hb.cY)));
                int basehitpoint = m.currentHealth;
                int baseMaxHitPoint = m.maxHealth;
                int basedefends = m.currentBlock;
                AbstractMonster hexyfriend;
                if (m.type == AbstractMonster.EnemyType.BOSS) {
                    hexyfriend = new SexyHexaghost(true);
                    CardCrawlGame.music.silenceBGMInstantly();
                    CardCrawlGame.music.silenceTempBgmInstantly();
                } else {
                    hexyfriend = new SexyHexaghost(false);
                }
                hexyfriend.drawX = m.drawX;
                AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                    public void update() {
                        m.isDead = true;
                        m.hideHealthBar();
                        AbstractDungeon.getCurrRoom().monsters.monsters.remove(m);
                        this.isDone = true;
                    }
                });
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(hexyfriend, false));
                hexyfriend.maxHealth = baseMaxHitPoint;
                hexyfriend.currentHealth = basehitpoint;
                hexyfriend.currentBlock = basedefends;
                hexyfriend.flipHorizontal = m.flipHorizontal;
                for (AbstractPower f : m.powers) {
                    if (f instanceof ReactivePower) {
                        hexyfriend.powers.add(new ReactiveCopy(hexyfriend));
                    } else if (f instanceof CurlUpPower) {
                        hexyfriend.powers.add(new CurlUpCopy(hexyfriend, f.amount));
                    } else if (f instanceof SplitPower) {
                        hexyfriend.powers.add(new SplitCopy(hexyfriend));
                    } else {
                        hexyfriend.powers.add(f);
                        f.owner = hexyfriend;
                    }
                    if (f instanceof UnawakenedPower) {
                        AbstractDungeon.getCurrRoom().cannotLose = true;
                    }
                }
                hexyfriend.setMove((byte) 5, AbstractMonster.Intent.UNKNOWN);
                hexyfriend.createIntent();
                hexyfriend.healthBarUpdatedEvent();
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.target = CardTarget.ALL_ENEMY;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
