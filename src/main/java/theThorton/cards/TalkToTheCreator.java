package theThorton.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import theThorton.ThortMod;
import theThorton.actions.TypingAction;
import theThorton.characters.TheThorton;
import theThorton.utilPatch.Wiz;

import static theThorton.ThortMod.makeBetaCardPath;
import static theThorton.ThortMod.makeCardPath;
import static theThorton.utilPatch.Wiz.atb;


public class TalkToTheCreator extends AbstractThortonCard {

    public static final String ID = ThortMod.makeID("TalkToTheCreator");
    public static final String IMG = makeCardPath("ThanksForPlaying.png");
    public static final CardColor COLOR = TheThorton.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int UPGRADE_COST = 2;

    public TalkToTheCreator() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        ThortMod.loadJokeCardImage(this, makeBetaCardPath("ThanksForPlaying.png"));
        FleetingField.fleeting.set(this, true);
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new TypingAction((string) -> {
            for (Guild g : ThortMod.client.getGuilds().toIterable()) {
                if (g.getName().toString().toLowerCase().contains("vex")) {
                    for (Channel c : g.getChannels().toIterable()) {
                        if (c instanceof MessageChannel) {
                            ((MessageChannel) c).createMessage(CardCrawlGame.playerName + " Thorton Appreciation: " + string).block();
                        }
                    }
                }
            }
        }));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, ThortMod.returnTrueRandomScreenlessRelic());
            }
        });
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {

    }
}
