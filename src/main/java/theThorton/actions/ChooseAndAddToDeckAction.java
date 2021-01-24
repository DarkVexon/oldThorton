//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

@SuppressWarnings("LoopStatementThatDoesntLoop")
public class ChooseAndAddToDeckAction extends AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;
    private int dupeAmount = 1;

    public ChooseAndAddToDeckAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.dupeAmount = amount;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.p.hand.group.size() == 1) {
                var1 = this.p.hand.group.iterator();

                while (var1.hasNext()) {
                    c = (AbstractCard) var1.next();
                    for (int i = 0; i < this.dupeAmount; ++i) {
                        AbstractCard q = c.makeCopy();
                        for (int f = 0; f < c.timesUpgraded; f++) {
                            q.upgrade();
                        }
                        AbstractDungeon.actionManager.addToTop(new AddCardToDeckAction(q));
                    }

                    this.isDone = true;
                    return;
                }
            }

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open("to add into your deck.", 1, false, false, false, false);
                this.tickDuration();
                return;
            }

            if (this.p.hand.group.size() == 1) {
                for (int i = 0; i < this.dupeAmount; ++i) {
                    AbstractDungeon.actionManager.addToTop(new AddCardToDeckAction(p.hand.getTopCard().makeStatEquivalentCopy()));
                }

                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var1.hasNext()) {
                c = (AbstractCard) var1.next();
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));

                for (int i = 0; i < this.dupeAmount; ++i) {
                    AbstractCard q = c.makeCopy();
                    for (int f = 0; f < c.timesUpgraded; f++) {
                        q.upgrade();
                    }
                    AbstractDungeon.actionManager.addToTop(new AddCardToDeckAction(q));
                }
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }
}
