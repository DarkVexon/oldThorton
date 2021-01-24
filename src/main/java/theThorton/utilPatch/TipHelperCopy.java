package theThorton.utilPatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TipHelperCopy {
    private static final Color BASE_COLOR;
    private static final float SHADOW_DIST_Y;
    private static final float SHADOW_DIST_X;
    private static final float BOX_EDGE_H;
    private static final float BOX_BODY_H;
    private static final float BOX_W;
    private static final float TEXT_OFFSET_X;
    private static final float HEADER_OFFSET_Y;
    private static final float BODY_OFFSET_Y;
    private static final float BODY_TEXT_WIDTH;
    private static final float TIP_DESC_LINE_SPACING;
    private static boolean renderedTipThisFrame;
    private static float drawX;
    private static float drawY;
    private static String HEADER;
    private static String BODY;
    private static float textHeight;

    static {
        renderedTipThisFrame = false;
        HEADER = null;
        BODY = null;
        BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
        SHADOW_DIST_Y = 14.0F * Settings.scale;
        SHADOW_DIST_X = 9.0F * Settings.scale;
        BOX_EDGE_H = 32.0F * Settings.scale;
        BOX_BODY_H = 64.0F * Settings.scale;
        BOX_W = 400.0F * Settings.scale;
        TEXT_OFFSET_X = 22.0F * Settings.scale;
        HEADER_OFFSET_Y = 12.0F * Settings.scale;
        BODY_OFFSET_Y = -20.0F * Settings.scale;
        BODY_TEXT_WIDTH = 360.0F * Settings.scale;
        TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    }

    public TipHelperCopy() {
    }

    public static void render(SpriteBatch sb) {
        if (!Settings.hidePopupDetails && renderedTipThisFrame) {
            if (HEADER != null) {
                textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, BODY, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
                renderTipBox(drawX, drawY, sb, HEADER, BODY);
                HEADER = null;
            }

            renderedTipThisFrame = false;
        }

    }

    public static void renderGenericTip(float x, float y, String header, String body) {
        if (!renderedTipThisFrame) {
            renderedTipThisFrame = true;
            HEADER = header;
            BODY = body;
            drawX = x;
            drawY = y;
        }

    }

    private static void renderTipBox(float x, float y, SpriteBatch sb, String title, String description) {
        float h = textHeight;
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - BOX_EDGE_H, BOX_W, h + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - BOX_BODY_H, BOX_W, BOX_EDGE_H);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }
}
