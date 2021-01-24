package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TemporalPlayCardAction extends AbstractGameAction {

    public AbstractCard card;

    public TemporalPlayCardAction(AbstractCard cardtwo) {
        this.card = cardtwo;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractCard tmp = card.makeStatEquivalentCopy();
        AbstractMonster sadMonster = AbstractDungeon.getRandomMonster();
        tmp.freeToPlayOnce = true;
        tmp.purgeOnUse = true;
        GameActionManager.queueExtraCard(tmp, sadMonster);

        this.isDone = true;
    }
}