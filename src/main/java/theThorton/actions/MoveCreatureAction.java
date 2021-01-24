package theThorton.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveCreatureAction extends AbstractGameAction {
    AbstractCreature ac;

    float startX;
    float startY;
    float endX;
    float endY;

    float cur;
    float speed;

    public MoveCreatureAction(AbstractCreature ac, float x, float y, float speed) {
        this.ac = ac;

        this.endX = x;
        this.endY = y;

        this.speed = speed;
        this.cur = 0;
    }

    public MoveCreatureAction(AbstractCreature ac, float x, float y) {
        this(ac, x, y, 0.5F);
    }

    public void update() {
        if (startX == 0.0F) {
            this.startX = ac.drawX;
            this.startY = ac.drawY;
        }
        cur += Gdx.graphics.getDeltaTime();

        if (cur >= speed) {
            this.isDone = true;
            return;
        }
        ac.drawX = Interpolation.linear.apply(startX, endX, cur);
        ac.drawY = Interpolation.linear.apply(startY, endY, cur);
    }
}