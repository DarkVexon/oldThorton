package theThorton.utilPatch;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainMenuAdPatch {
    private static final MainMenuAd advert = new MainMenuAd();


    private static class MainMenuAdInfo {
        private String text;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
        private String textheader;
        private String url;
        private Texture img;

        private MainMenuAdInfo(String text, String text2, String text3, String text4, String text5, String textheader, String url, Texture img) {
            this.text = text;
            this.text2 = text2;
            this.text3 = text3;
            this.text4 = text4;
            this.text5 = text5;
            this.textheader = textheader;
            this.url = url;
            this.img = img;
        }
    }

    private static ArrayList<MainMenuAdInfo> ads = new ArrayList<>();

    static {
        // T9 Game Info
        ads.add(new MainMenuAdInfo("", "", "", "", "", "", "https://store.steampowered.com/app/1652250/Tales__Tactics/", TextureLoader.getTexture("thethortonResources/images/menuTNT.png")));
        advert.current = ads.get(0);
    }

    @SpirePatch(clz = TitleBackground.class, method = "render")
    public static class RenderPatch {
        @SpirePostfixPatch
        public static void renderAd(TitleBackground instance, SpriteBatch sb) {
            advert.render(sb);
        }
    }

    @SpirePatch(clz = TitleBackground.class, method = "update")
    public static class UpdatePatch {
        @SpirePostfixPatch
        public static void updateAd(TitleBackground instance) {
            advert.update();
        }
    }

    private static class MainMenuAd {
        private static final Texture tex = TextureLoader.getTexture("thethortonResources/images/menuPanelHalfBlue.png");
        private MainMenuAdInfo current;

        public final Hitbox hb;

        private Color tint = new Color(1, 1, 1, 0);
        private Color btnTint = new Color(1, 1, 1, 0);
        private float xPos = Settings.WIDTH * 0.7F;
        private float yPos = Settings.HEIGHT / 2F;
        private float angle = 0.0f;
        private float scale = 0.5f;
        private float adjustedWidth = tex.getWidth() * Settings.scale * scale;
        private float adjustedHeight = tex.getHeight() * Settings.scale * scale;

        public MainMenuAd() {
            hb = new Hitbox(xPos, yPos, adjustedWidth, adjustedHeight);
        }

        public void render(SpriteBatch sb) {

            sb.setColor(Color.WHITE.cpy());

            sb.draw(current.img, xPos, yPos, 0, 0, current.img.getWidth(), current.img.getHeight(), Settings.scale, Settings.scale, angle, 0, 0, current.img.getWidth(), current.img.getHeight(), false, false);
            if (tint.a > 0.0f) {
                sb.setBlendFunction(770, 1);
                sb.setColor(tint);
                sb.draw(current.img, xPos, yPos, 0, 0, current.img.getWidth(), current.img.getHeight(), Settings.scale, Settings.scale, angle, 0, 0, current.img.getWidth(), current.img.getHeight(), false, false);
                sb.setBlendFunction(770, 771);
            }

            sb.setColor(Color.WHITE.cpy());
            sb.setColor(Color.WHITE.cpy());

            sparkles.forEach(q -> {
                q.render(sb);
            });

            hb.render(sb);
        }

        private float particleTimer = 0.4F;
        private ArrayList<AbstractGameEffect> sparkles = new ArrayList<>();

        public void update() {
            if (CardCrawlGame.mainMenuScreen.screen != MainMenuScreen.CurScreen.MAIN_MENU) {
                return;
            }

            if (!Settings.DISABLE_EFFECTS) {
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    this.particleTimer = 0.4F;
                    sparkles.add(new PrettyAdEffect(MathUtils.random(xPos, xPos + adjustedWidth), MathUtils.random(yPos, yPos + adjustedHeight)));
                }
            }

            sparkles.forEach(q -> q.update());

            hb.update();
            if (hb.hovered) {
                this.tint.a = 0.25F;
                if (InputHelper.justClickedLeft) {
                    CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
                    try {
                        Desktop.getDesktop().browse(new URI(current.url));
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                this.tint.a = 0.0f;
            }
        }
    }
}
