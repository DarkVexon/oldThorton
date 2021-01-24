package theThorton.utilPatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;

public class SuperWordAboveCreatureEffect extends BlockedWordEffect {

    public SuperWordAboveCreatureEffect(AbstractCreature target, float x, float y, String text) {
        super(target, x, y, text);
    }

    public void update()
    {
        super.update();
        this.color = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
    }
}
