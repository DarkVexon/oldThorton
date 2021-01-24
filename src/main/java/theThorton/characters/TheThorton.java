package theThorton.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import theThorton.ThortMod;
import theThorton.cards.*;
import theThorton.relics.CouragePotion;

import java.util.ArrayList;
import java.util.List;

import static theThorton.ThortMod.*;
import static theThorton.characters.TheThorton.Enums.COLOR_GRAY;

public class TheThorton extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 68;
    public static final int MAX_HP = 68;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 2;
    public static final String[] orbTextures = {
            "thethortonResources/images/char/defaultCharacter/orb/layer1.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer2.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer3.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer4.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer5.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer6.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer1d.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer2d.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer3d.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer4d.png",
            "thethortonResources/images/char/defaultCharacter/orb/layer5d.png",};
    private static final String ID = makeID("theThorton");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public boolean hexaghostifiedHeart = false;

    public TheThorton(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "thethortonResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "thethortonResources/images/char/defaultCharacter/thorton.scml"));


        initializeClass(null,

                THE_THORT_SHOULDER_1,
                THE_THORT_SHOULDER_2,
                THE_THORT_CORPSE,
                getLoadout(), 20.0F, -10.0F, 224.0F, 324.0F, new EnergyManager(ENERGY_PER_TURN));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 250.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, goldamt, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Heckle.ID);
        retVal.add(CreditCard.ID);
        retVal.add(Normality.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(CouragePotion.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "GOLD_GAIN";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 6;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    @Override
    public Color getCardTrailColor() {
        return ThortMod.THORT_GRAY;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        ArrayList<AbstractCard> cardList = new ArrayList<>(CardLibrary.getAllCards());
        return cardList.get(AbstractDungeon.cardRandomRng.random(cardList.size() - 1));
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheThorton(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return ThortMod.THORT_GRAY;
    }

    @Override
    public Color getSlashAttackColor() {
        return ThortMod.THORT_GRAY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        if (AbstractDungeon.player instanceof TheThorton) {
            if (((TheThorton) AbstractDungeon.player).hexaghostifiedHeart) {
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Alt_Comic_01.png"));
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Alt_Comic_02.png"));
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Alt_Comic_03.png"));
            } else {
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_01.png"));
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_02.png"));
                panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_03.png"));
            }
        } else {
            panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_01.png"));
            panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_02.png"));
            panels.add(new CutscenePanel("thethortonResources/images/char/defaultCharacter/Comic_03.png"));
        }
        return panels;
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_THORT;
        @SpireEnum(name = "THORT_GRAY_COLOR")
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "THORT_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
