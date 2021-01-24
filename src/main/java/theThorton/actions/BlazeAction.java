package theThorton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class BlazeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public BlazeAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        ArrayList<AbstractPower> blazeList = new ArrayList<>();

        blazeList.add(new ArtifactPower(p, 3));
        blazeList.add(new MalleablePower(p, 3));
        blazeList.add(new PlatedArmorPower(p, 3));
        blazeList.add(new ThornsPower(p, 3));

        AbstractPower blazePower = blazeList.get(AbstractDungeon.cardRandomRng.random(0, (blazeList.size() - 1)));

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, blazePower, blazePower.amount, AttackEffect.NONE));

        this.isDone = true;
    }
}