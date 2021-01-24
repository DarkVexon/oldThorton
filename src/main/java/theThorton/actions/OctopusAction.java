package theThorton.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.CenterGridCardSelectScreen;

import static theThorton.ThortMod.makeCardPath;

public class OctopusAction extends AbstractGameAction {
    private boolean pickCard = false;
    private int damage = -1;
    private int block = -1;
    private AbstractMonster target;
    private AbstractCard funCard;
    private DamageInfo.DamageType damageTypeForTurn;

    public OctopusAction(AbstractMonster targetMonster, DamageInfo.DamageType damageType, AbstractCard q) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        target = targetMonster;
        funCard = q;
        damageTypeForTurn = damageType;
    }

    @Override
    public void update() {
        funCard.applyPowers();
        funCard.calculateCardDamage(target);
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(new ShiftingChoiceCard("Attack", "Attack", makeCardPath("AttackShiftingOctopus.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, funCard.damage, -1));
            group.addToTop(new ShiftingChoiceCard("Block", "Block", makeCardPath("ShiftingOctopus.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, funCard.block));
            group.addToTop(new ShiftingChoiceCard("BlockNextTurn", "Block Next Turn", makeCardPath("ShiftingOctopus.png"), "Gain !B! Block next turn.", AbstractCard.CardType.SKILL, -1, funCard.block));
            group.addToTop(new ShiftingChoiceCard("Weak", "Weak", makeCardPath("ShiftingOctopus.png"), "Apply 1 Weak.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Vulnerable", "Vulnerable", makeCardPath("ShiftingOctopus.png"), "Apply 1 Vulnerable.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Energy", "Energy", makeCardPath("ShiftingOctopus.png"), "Gain [E] .", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("EnergyNextTurn", "Energy Next Turn", makeCardPath("ShiftingOctopus.png"), "Next turn, gain [E] .", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Draw", "Draw", makeCardPath("ShiftingOctopus.png"), "Draw 1 card.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("DrawNextTurn", "Draw Next Turn", makeCardPath("ShiftingOctopus.png"), "Next turn, draw 1 additional card.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("StrengthDown", "Strength Down", makeCardPath("ShiftingOctopus.png"), "Enemy loses 2 Strength this turn.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("StrengthUp", "Strength Up", makeCardPath("ShiftingOctopus.png"), "Gain 2 Strength. At the end of this turn, lose 2 Strength.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Upgrade", "Upgrade", makeCardPath("ShiftingOctopus.png"), "Upgrade a random card in your hand for the rest of combat.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Exhaust", "Exhaust", makeCardPath("ShiftingOctopus.png"), "Exhaust a card in your hand.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Barrier", "Barrier", makeCardPath("ShiftingOctopus.png"), "Whenever you are attacked this turn, deal 4 damage back.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("Ink Spray", "Ink Spray", makeCardPath("ShiftingOctopus.png"), "Randomize the cost of your hand between 0 and 3.", AbstractCard.CardType.SKILL, -1, -1));

            CardGroup newGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < 3; i++) {
                newGroup.addToTop(group.getRandomCard(AbstractDungeon.cardRandomRng).makeCopy());
            }

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(newGroup, 1, "Choose an Action", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.name.equals("Attack")) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, cardChoice.baseDamage, damageTypeForTurn)));
            }
            if (cardChoice.name.equals("Block")) {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, cardChoice.baseBlock));
            }
            if (cardChoice.name.equals("Block Next Turn")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, funCard.baseBlock), funCard.baseBlock));
            }
            if (cardChoice.name.equals("Vulnerable")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
            }
            if (cardChoice.name.equals("Weak")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
            }
            if (cardChoice.name.equals("Energy")) {
                AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            }
            if (cardChoice.name.equals("Energy Next Turn")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
            }
            if (cardChoice.name.equals("Draw")) {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            }
            if (cardChoice.name.equals("Draw Next Turn")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
            }
            if (cardChoice.name.equals("Strength Down")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -2), -2));
                if (target != null && !target.hasPower("Artifact")) {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 2), 2));
                }
            }
            if (cardChoice.name.equals("Strength Up")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 2), 2));
            }
            if (cardChoice.name.equals("Upgrade")) {
                AbstractDungeon.actionManager.addToTop(new UpgradeRandomCardAction());
            }
            if (cardChoice.name.equals("Exhaust")) {
                AbstractDungeon.actionManager.addToTop(new ExhaustAction(1, false, false, false));
            }
            if (cardChoice.name.equals("Barrier")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlameBarrierPower(AbstractDungeon.player, 4), 4));
            }
            if (cardChoice.name.equals("Ink Spray")) {
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    int newCost = AbstractDungeon.cardRandomRng.random(3);
                    if (card.cost != newCost) {
                        card.cost = newCost;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                }
            }

            isDone = true;
        }
        tickDuration();
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;
        private String name2;
        private String img2;
        private String description2;
        private CardType type2;
        private int damageAmt2;
        private int blockAmt2;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt) {
            super(makeID(id), name, IMG, COST, description, type, TheThorton.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);

            baseID = id;
            name2 = name;
            img2 = IMG;
            description2 = description;
            type2 = type;
            damageAmt2 = damageAmt;
            blockAmt2 = blockAmt;

            baseDamage = damageAmt;
            baseBlock = blockAmt;

        }

        private static String makeID(String id) {
            return ThortMod.makeID("Shifting" + id);
        }


        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        }

        @Override
        public void upgrade() {

        }

        @Override
        public AbstractCard makeCopy() {
            return new OctopusAction.ShiftingChoiceCard(baseID, name, img2, description2, type2, damageAmt2, blockAmt2);
        }
    }
}