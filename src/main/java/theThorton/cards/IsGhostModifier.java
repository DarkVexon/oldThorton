package theThorton.cards;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theThorton.ThortMod.makeID;

@AbstractCardModifier.SaveIgnore
public class IsGhostModifier extends AbstractCardModifier {
    public static final String ID = makeID("IsGhostModifier");

    public AbstractCard ghost;

    public IsGhostModifier(AbstractCard source) {
        this.ghost = source;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IsGhostModifier(ghost.makeStatEquivalentCopy()); // Might need to do stat equiv copy of the ghost too
    }
}
