


package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theThorton.cards.SummonProfit;

public class SummonProfitAction extends AbstractGameAction {
    private boolean upgraded;

    public SummonProfitAction(boolean upgrad) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        upgraded = upgrad;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToTop(new SummonProfitAction(this.upgraded));
            AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }

        if (!AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            AbstractCard d = SummonProfit.getAnyCardRandomly();
            if (upgraded) {
                c.freeToPlayOnce = true;
                d.freeToPlayOnce = true;
            }
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(d));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        this.isDone = true;
    }
}
