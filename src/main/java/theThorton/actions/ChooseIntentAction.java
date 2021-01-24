package theThorton.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import theThorton.characters.TheThorton;
import theThorton.powers.TrueFormPower;
import theThorton.utilPatch.CenterGridCardSelectScreen;
import theThorton.utilPatch.DummyMonster;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static theThorton.ThortMod.makeCardPath;

public class ChooseIntentAction extends AbstractGameAction {
    private DummyMonster dum;
    private boolean pickCard = false;

    public ChooseIntentAction(DummyMonster d) {
        this.dum = d;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        target = AbstractDungeon.player;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            group.addToTop(new IntentChoiceCard(1, "Aggressive", makeCardPath("ATTACK.png"), "Intend to Attack for !D! damage.", 25));
            group.addToTop(new IntentChoiceCard(2, "Aggressive", makeCardPath("ATTACK_BUFF.png"), "Intend to Attack for !D! damage and gain 2 Strength.", 15));
            group.addToTop(new IntentChoiceCard(3, "Strategic", makeCardPath("ATTACK_DEBUFF.png"), "Intend to Attack for !D! damage and apply 2 Weak.", 20));
            group.addToTop(new IntentChoiceCard(4, "Aggressive", makeCardPath("ATTACK_BLOCK.png"), "Intend to gain 10 Block and Attack for !D! damage.", 15));
            group.addToTop(new IntentChoiceCard(5, "Strategic", makeCardPath("BUFF.png"), "Intend to gain 5 Strength.", -1));
            group.addToTop(new IntentChoiceCard(6, "Strategic", makeCardPath("DEBUFF.png"), "Intend to apply 2 Weak and 2 Vulnerable.", -1));
            group.addToTop(new IntentChoiceCard(7, "Strategic", makeCardPath("MEGA_DEBUFF.png"), "Intend to decrease enemy Strength by 2.", -1));
            group.addToTop(new IntentChoiceCard(8, "Defensive", makeCardPath("BLOCK.png"), "Intend to gain 20 Block.", -1));
            group.addToTop(new IntentChoiceCard(9, "Defensive", makeCardPath("BLOCK_DEBUFF.png"), "Intend to gain 15 Block and apply 2 Vulnerable.", -1));
            group.addToTop(new IntentChoiceCard(10, "Defensive", makeCardPath("BLOCK_BUFF.png"), "Intend to gain 10 Block and 3 Strength.", -1));
            for (AbstractCard q : group.group) {
                q.applyPowers();
                if (q.baseDamage > -1)
                    q.calculateCardDamage(TrueFormPower.frontMonster());
            }
            CenterGridCardSelectScreen.centerGridSelect = true;
            AbstractDungeon.gridSelectScreen.open(group, 1, "Choose an Intent.", false, false, false, false);
            try {
                Method m = GridCardSelectScreen.class.getDeclaredMethod("updateCardPositionsAndHoverLogic");
                m.setAccessible(true);
                m.invoke(AbstractDungeon.gridSelectScreen);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignore) {
            }
        } else {
            if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                pickCard = false;
                CenterGridCardSelectScreen.centerGridSelect = false;

                for (AbstractCard cardChoice : AbstractDungeon.gridSelectScreen.selectedCards) {
                    switch (cardChoice.cardID) {
                        case "Intending1": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.ATTACK, 25);
                            break;
                        }
                        case "Intending2": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.ATTACK_BUFF, 15);
                            break;
                        }
                        case "Intending3": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.ATTACK_DEBUFF, 20);
                            break;
                        }
                        case "Intending4": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.ATTACK_DEFEND, 15);
                            break;
                        }
                        case "Intending5": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.BUFF);
                            break;
                        }
                        case "Intending6": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.DEBUFF);
                            break;
                        }
                        case "Intending7": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.STRONG_DEBUFF);
                            break;
                        }
                        case "Intending8": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.DEFEND);
                            break;
                        }
                        case "Intending9": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.DEFEND_DEBUFF);
                            break;
                        }
                        case "Intending10": {
                            dum.setMove((byte) 0, AbstractMonster.Intent.DEFEND_BUFF);
                            break;
                        }
                    }
                }

                dum.createIntent();
                if (AbstractDungeon.player.hasPower(TrueFormPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(TrueFormPower.POWER_ID).updateDescription();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                CenterGridCardSelectScreen.centerGridSelect = false;
                isDone = true;
            }
        }
        tickDuration();
    }

    private static class IntentChoiceCard extends CustomCard {
        private static final int COST = -2;
        public int baseID;
        public String name2;
        public String IMG2;
        public String description2;
        public int dmg2;

        IntentChoiceCard(int ID, String name, String IMG, String description, int dmg) {
            super(makeID(Integer.toString(ID)), name, IMG, COST, description, CardType.POWER, TheThorton.Enums.COLOR_GRAY, CardRarity.SPECIAL, CardTarget.NONE);
            baseDamage = dmg;
            baseID = ID;
            name2 = name;
            IMG2 = IMG;
            description2 = description;
            dmg2 = dmg;
        }

        private static String makeID(String id) {
            return "Intending" + id;
        }

        @Override
        public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        }

        @Override
        public void upgrade() {
        }

        @Override
        public AbstractCard makeCopy() {
            return new IntentChoiceCard(baseID, name2, IMG2, description2, dmg2);
        }
    }
}