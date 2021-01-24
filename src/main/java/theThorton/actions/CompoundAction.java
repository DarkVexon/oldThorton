package theThorton.actions;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theThorton.ThortMod;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.CenterGridCardSelectScreen;

import static theThorton.ThortMod.makeCardPath;

public class CompoundAction extends AbstractGameAction {
    private boolean pickCard = false;
    private AbstractCard bigCard;

    public CompoundAction(AbstractCard card) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        bigCard = card;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(new ShiftingChoiceCard("Double", "Double", makeCardPath("ShiftingOctopus.png"), "Double this card's Gold.", AbstractCard.CardType.SKILL, -1, -1));
            group.addToTop(new ShiftingChoiceCard("CashIn", "Cash In", makeCardPath("ShiftingOctopus.png"), "Gain " + bigCard.magicNumber + " Gold. NL Remove this card from your deck permanently.", AbstractCard.CardType.SKILL, -1, -1));

            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose an Action", false);
        } else if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            pickCard = false;
            AbstractCard cardChoice = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (cardChoice.name.equals("Double")) {
                AbstractDungeon.actionManager.addToTop(new IncreaseMiscAction(bigCard.uuid, bigCard.misc, bigCard.misc));
            }
            if (cardChoice.name.equals("Cash In")) {
                AbstractDungeon.actionManager.addToTop(new GainGoldAction(bigCard.misc));
                if (StSLib.getMasterDeckEquivalent(bigCard) != null)
                {
                    AbstractDungeon.player.masterDeck.removeCard(StSLib.getMasterDeckEquivalent(bigCard));
                }
            }

            isDone = true;
        }
        tickDuration();
    }

    private static class ShiftingChoiceCard extends CustomCard {
        private static final int COST = -2;
        private String baseID;

        ShiftingChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt) {
            super(makeID(id), name, IMG, COST, description, type, TheThorton.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);

            baseID = id;

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
    }
}