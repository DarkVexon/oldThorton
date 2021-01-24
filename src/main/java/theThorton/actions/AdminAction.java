package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theThorton.characters.TheThorton;


public class AdminAction extends AbstractGameAction {

    private CardGroup possCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private boolean upg;

    public AdminAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        upg = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (c.color == TheThorton.Enums.COLOR_GRAY && UnlockTracker.isCardSeen(c.cardID) || upg) {
                    possCards.addToTop(c);
                }
            }
            possCards.sortAlphabetically(true);
            AbstractDungeon.gridSelectScreen.open(possCards, 1, "Choose a card to add to your hand.", false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, false, true));
        }
        this.isDone = true;

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}