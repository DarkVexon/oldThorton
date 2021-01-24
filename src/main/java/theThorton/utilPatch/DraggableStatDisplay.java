package theThorton.utilPatch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;


public class DraggableStatDisplay {
    public Hitbox hb;

    private int moveState = 0;
    private float dx;
    private float dy;
    private float startx;
    private float starty;

    public DraggableStatDisplay() {
        hb = new Hitbox(32 * Settings.scale, 32 * Settings.scale); // gets resized
    }

    public void update() {
        hb.update();
    }

    public void dragUpdate() {
        if (InputHelper.justClickedLeft) {
            if (hb.hovered) {
                dx = hb.cX - InputHelper.mX;
                dy = hb.cY - InputHelper.mY;
                moveState = 1;
                startx = InputHelper.mX;
                starty = InputHelper.mY;
            }
        }
        if (moveState > 0) {
            if (InputHelper.justReleasedClickLeft) {
                moveState = 0;
            } else {
                float x = Math.min(Math.max(InputHelper.mX + dx, 0.05f * Settings.WIDTH), 0.95f * Settings.WIDTH);
                float y = Math.min(Math.max(InputHelper.mY + dy, 0.3f * Settings.HEIGHT), 0.85f * Settings.HEIGHT);

                if ((startx - InputHelper.mX) * (startx - InputHelper.mX) + (starty - InputHelper.mY) * (starty - InputHelper.mY) > 64) {
                    moveState = 2;
                }

                if (moveState == 2) {
                    hb.move(x, y);
                }
            }
        }
    }

    public void render(SpriteBatch sb) {
    }
}