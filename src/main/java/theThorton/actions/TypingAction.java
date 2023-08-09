package theThorton.actions;

import basemod.BaseMod;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.TextReceiver;
import basemod.patches.com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor.TextInput;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import theThorton.cards.AdministrativeActions;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static basemod.DevConsole.consoleFont;

public class TypingAction extends AbstractGameAction implements TextReceiver, RenderSubscriber {
    private Consumer<String> callback;
    private Predicate<Character> myFunc;
    private String text = "";
    private boolean openedScreen = false;

    public TypingAction(Consumer<String> callback, Predicate<Character> myFunc) {
        this.callback = callback;
        this.myFunc = myFunc;
    }

    public TypingAction(Consumer<String> callback) {
        this(callback, c -> true);
    }

    @Override
    public void update() {
        if (!openedScreen) {
            BaseMod.subscribe(this);
            TextInput.startTextReceiver(this);
            openedScreen = true;
        }
    }

    @Override
    public void receiveRender(SpriteBatch spriteBatch) {
        float curY = 350 * Settings.scale + (AdministrativeActions.used.size() * (50 * Settings.scale));
        if (AdministrativeActions.used.isEmpty()) {
            FontHelper.renderDeckViewTip(spriteBatch, "Typing Card. Used: None!", curY, Settings.CREAM_COLOR);
            curY -= 50 * Settings.scale;
        } else {
            FontHelper.renderDeckViewTip(spriteBatch, "Typing Card. Used:", curY, Settings.CREAM_COLOR);
            curY -= 50 * Settings.scale;
            for (String s : AdministrativeActions.used) {
                FontHelper.renderDeckViewTip(spriteBatch, s, curY, Settings.CREAM_COLOR);
                curY -= (50 * Settings.scale);
            }
        }

        FontHelper.renderDeckViewTip(spriteBatch, this.getCurrentText(), curY, Settings.CREAM_COLOR);
    }

    @Override
    public String getCurrentText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean acceptCharacter(char c) {
        return consoleFont.getData().hasGlyph(c) && myFunc.test(c) && c != '+' && !AdministrativeActions.used.stream().anyMatch(q -> q.equalsIgnoreCase(getCurrentText() + c));
    }

    @Override
    public boolean onKeyUp(int keycode) {
        if (keycode == Input.Keys.ENTER) {
            isDone = true;
            callback.accept(getCurrentText());
            TextInput.stopTextReceiver(this);
            BaseMod.unsubscribe(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}

