//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package theThorton.utilPatch;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theThorton.cards.OhNo;

import java.util.ArrayList;
import java.util.List;

public class GhostsThortonEvent extends AbstractImageEvent {
    public static final String ID = "thorton:Ghosts";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] OPTIONS;
    private static final String INTRO_BODY_M;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final float HP_DRAIN = 0.5F;
    private int screenNum = 0;
    private int hpLoss = 0;

    public GhostsThortonEvent() {
        super(NAME, "test", "images/events/ghost.jpg");// 30
        this.body = INTRO_BODY_M;// 31
        this.hpLoss = MathUtils.ceil((float)AbstractDungeon.player.maxHealth * 0.5F);// 33
        if (this.hpLoss >= AbstractDungeon.player.maxHealth) {// 34
            this.hpLoss = AbstractDungeon.player.maxHealth - 1;// 35
        }

        if (AbstractDungeon.ascensionLevel >= 15) {// 38
            this.imageEventText.setDialogOption("[Nod] #gReceive #g2 Oh No. #rLose #r" + this.hpLoss + OPTIONS[1], new OhNo());// 39
        } else {
            this.imageEventText.setDialogOption( "[Nod] #gReceive #g4 Oh No. #rLose #r" + this.hpLoss + OPTIONS[1], new OhNo());// 41
        }

        this.imageEventText.setDialogOption("[RUN AAAAAHH GOSTTT]");// 44
    }// 45

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {// 49
            CardCrawlGame.sound.play("EVENT_GHOSTS");// 50
        }

    }// 52

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {// 56
            case 0:
                switch(buttonPressed) {// 59
                    case 0:
                        this.imageEventText.updateBodyText(ACCEPT_BODY);// 63
                        AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);// 66
                        this.becomeGhost();// 68
                        this.screenNum = 1;// 71
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);// 72
                        this.imageEventText.clearRemainingOptions();// 73
                        return;// 94
                    default:
                        logMetricIgnored("Ghosts");// 77
                        this.imageEventText.updateBodyText(EXIT_BODY);// 78
                        this.screenNum = 2;// 79
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);// 80
                        this.imageEventText.clearRemainingOptions();// 81
                        return;
                }
            case 1:
                this.openMap();// 87
                break;// 88
            default:
                this.openMap();// 91
        }

    }

    private void becomeGhost() {
        List<String> cards = new ArrayList();// 97
        int amount = 4;// 98
        if (AbstractDungeon.ascensionLevel >= 15) {// 99
            amount -= 2;// 100
        }

        for(int i = 0; i < amount; ++i) {// 102
            AbstractCard c = new OhNo();// 103
            cards.add(c.cardID);// 104
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));// 105
        }

        logMetricObtainCardsLoseMapHP("Ghosts", "Became a Ghost", cards, this.hpLoss);// 109
    }// 110

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Ghosts");// 19
        NAME = eventStrings.NAME;// 20
        OPTIONS = eventStrings.OPTIONS;// 22
        INTRO_BODY_M = "As you continue your ascent, #p~thick~ #p~black~ #p~smoke~ begins to billow out of the ground and walls around you, coalescing into three masked forms that start to speak... NL NL OH MY GOD, THEY'RE GHOSTS!!! NL Ever since he was a child, Thorton has had a ~terrible~ ~fear~ ~of~ #p~ghosts!~ NL NL The #p~specters~ are saying something, but you can't feel your legs... ";
        ACCEPT_BODY = "You nod - anything to get away from the ~super~ ~scary~ ~spooky~ ~ghosts!~ NL NL Darkness envelops you, billowing from the largest's mouth. NL NL You wake up hours later... oh no.. YOU ARE @GHOST@ AS WELL!?";
        EXIT_BODY = "You book it out of there faster than a baseball player who accidentally went to the football field with a golf ball instead of the game he was supposed to be at, and now the whole team is going to lose because of him. NL NL GHOSTS ARE SCARY!";

    }
}
