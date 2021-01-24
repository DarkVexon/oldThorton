//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theThorton.cards.AdministrativeActions;
import theThorton.cards.EquivalentExchange;

import java.util.ArrayList;
import java.util.Iterator;

public class ExfumeAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> exhumes = new ArrayList();

    public ExfumeAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        Iterator c;
        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else if (this.p.exhaustPile.size() == 1) {
                if (((AbstractCard) this.p.exhaustPile.group.get(0)).cardID.equals("Exhume")|| this.p.hand.getTopCard().cardID.equals(AdministrativeActions.ID) || this.p.hand.getTopCard().cardID.equals(EquivalentExchange.ID)) {
                    this.isDone = true;
                } else {
                    AbstractCard q = this.p.exhaustPile.getTopCard();
                    q.unfadeOut();
                    this.p.hand.addToHand(q);
                    if (AbstractDungeon.player.hasPower("Corruption") && q.type == CardType.SKILL) {
                        q.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(q);

                    q.unhover();
                    q.fadingOut = false;
                    this.isDone = true;
                }
            } else {
                c = this.p.exhaustPile.group.iterator();

                while (c.hasNext()) {
                    derp = (AbstractCard) c.next();
                    derp.stopGlowing();
                    derp.unhover();
                    derp.unfadeOut();
                }

                c = this.p.exhaustPile.group.iterator();

                while (c.hasNext()) {
                    derp = (AbstractCard) c.next();
                    if (derp.cardID.equals("Exhume") || derp.cardID.equals(AdministrativeActions.ID) || derp.cardID.equals(EquivalentExchange.ID)) {
                        c.remove();
                        this.exhumes.add(derp);
                    }
                }

                if (this.p.exhaustPile.isEmpty()) {
                    this.p.exhaustPile.group.addAll(this.exhumes);
                    this.exhumes.clear();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (c = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); c.hasNext(); derp.unhover()) {
                    derp = (AbstractCard) c.next();
                    this.p.hand.addToHand(derp);
                    if (AbstractDungeon.player.hasPower("Corruption") && derp.type == CardType.SKILL) {
                        derp.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(derp);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();

                for (c = this.p.exhaustPile.group.iterator(); c.hasNext(); derp.target_y = 0.0F) {
                    derp = (AbstractCard) c.next();
                    derp.unhover();
                    derp.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
