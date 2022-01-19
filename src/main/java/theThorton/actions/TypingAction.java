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
        this(callback, c->true);
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
        FontHelper.renderDeckViewTip(spriteBatch, this.getCurrentText(), 300 * Settings.scale, Settings.CREAM_COLOR);
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
        return consoleFont.getData().hasGlyph(c) && myFunc.test(c);
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

