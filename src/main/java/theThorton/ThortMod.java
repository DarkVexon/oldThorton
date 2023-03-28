package theThorton;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theThorton.cards.*;
import theThorton.characters.TheThorton;
import theThorton.relics.*;
import theThorton.utilPatch.DraggableStatDisplay;
import theThorton.utilPatch.GhostsThortonEvent;
import theThorton.utilPatch.TextureLoader;
import theThorton.utilPatch.VampiresThortonEvent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@SpireInitializer
public class ThortMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostBattleSubscriber,
        PostDeathSubscriber,
        PostPlayerUpdateSubscriber,
        StartGameSubscriber,
        OnStartBattleSubscriber,
        CustomSavable<VexSaveWrapper> {
    private static final Logger logger = LogManager.getLogger(ThortMod.class.getName());
    //private static final String ALL_CHARACTERS = "allCharacters";
    private static final String BADGE_IMAGE = "thethortonResources/images/Badge.png";
    public static final Color THORT_GRAY = new Color(0.0f, 84.0f, 102.0f, 1);
    public static final String THE_THORT_SHOULDER_1 = "thethortonResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_THORT_SHOULDER_2 = "thethortonResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_THORT_CORPSE = "thethortonResources/images/char/defaultCharacter/corpse.png";
    private static final String ATTACK_THORT_GRAY = "thethortonResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_THORT_GRAY = "thethortonResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_THORT_GRAY = "thethortonResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_THORT_GRAY = "thethortonResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "thethortonResources/images/512/card_small_orb.png";
    private static final String ATTACK_THORT_GRAY_PORTRAIT = "thethortonResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_THORT_GRAY_PORTRAIT = "thethortonResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_THORT_GRAY_PORTRAIT = "thethortonResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_THORT_GRAY_PORTRAIT = "thethortonResources/images/1024/card_default_gray_orb.png";
    private static final String THE_THORT_BUTTON = "thethortonResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_THORT_PORTRAIT = "thethortonResources/images/charSelect/DefaultCharacterPortraitBG.png";
    //private static boolean allCharacters = false;
    public static int goldamt = 99;
    private static TheThorton TheThortonObject;
    private static String modID;
    public static int BossModeDamage;

    private static boolean thindone;
    public static boolean bonusBig;

    public static int fleeAmt = 0;
    public static int fleeMax = 3;
    public static int businessCardAmt = 0;
    public static int businessCardMax = 10;
    public static int investmentAmt = 0;
    public static int investmentMax = 4;
    public static boolean nextQuestionShop = false;
    public static boolean renderStuff = false;

    public static ArrayList<NextCombatInfo> importantInfoStuff = new ArrayList<>();

    public static DraggableStatDisplay newHitbox;

    static ThortMod thortonMod;

    public ThortMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        modID = "thethorton";


        logger.info("Done subscribing");

        logger.info("Creating the color " + TheThorton.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheThorton.Enums.COLOR_GRAY, THORT_GRAY, THORT_GRAY, THORT_GRAY,
                THORT_GRAY, THORT_GRAY, THORT_GRAY, THORT_GRAY,
                ATTACK_THORT_GRAY, SKILL_THORT_GRAY, POWER_THORT_GRAY, ENERGY_ORB_THORT_GRAY,
                ATTACK_THORT_GRAY_PORTRAIT, SKILL_THORT_GRAY_PORTRAIT, POWER_THORT_GRAY_PORTRAIT,
                ENERGY_ORB_THORT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");

        newHitbox = new DraggableStatDisplay();

        loadData();
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeBetaCardPath(String resourcePath) {
        return getModID() + "Resources/images/betacards/" + resourcePath;
    }

    private static String getModID() {
        return modID;
    }

    public static void initialize() {
        logger.info("========================= IfsdfsdfMod. Hi. =========================");
        thortonMod = new ThortMod();
        logger.info("========================= /Defadfasdfadgdfdgkln./ =========================");
    }

    private static void saveData() {
        try {
            SpireConfig config = new SpireConfig("thortonMod", "theThortonConfig");
            //config.setBool(ALL_CHARACTERS, allCharacters);
            config.setInt("goldamt", goldamt);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadData() {
        try {
            SpireConfig config = new SpireConfig("thortonMod", "theThortonConfig");
            config.load();
            if (config.has("goldamt")) {
                //allCharacters = config.getBool(ALL_CHARACTERS);
                goldamt = config.getInt("goldamt");
            } else {
                //allCharacters = false;
                goldamt = 99;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheThorton.Enums.THE_THORT.toString());

        TheThortonObject = new TheThorton("the Thorton", TheThorton.Enums.THE_THORT);
        BaseMod.addCharacter(TheThortonObject,
                THE_THORT_BUTTON, THE_THORT_PORTRAIT, TheThorton.Enums.THE_THORT);

        logger.info("Added " + TheThorton.Enums.THE_THORT.toString());
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.addEvent(VampiresThortonEvent.ID, VampiresThortonEvent.class, TheCity.ID);
        BaseMod.addEvent(GhostsThortonEvent.ID, GhostsThortonEvent.class, TheCity.ID);
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        //String allCharsText = "Make some relics available for all characters? (Requires restart.)";

        /*ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton(allCharsText,
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                allCharacters, settingsPanel, (label) -> {
        }, (button) -> {
            allCharacters = button.enabled;
            saveData();
        });
        settingsPanel.addUIElement(enableNormalsButton);
        */

        BaseMod.registerModBadge(badgeTexture, "The Thorton", "Vex'd", "This is extremely serious business!", settingsPanel);

        BaseMod.addSaveField("ThortonMod", thortonMod);

        logger.info("Done loading badge Image and mod options");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelic(new BankDeed(), RelicType.SHARED);
        BaseMod.addRelic(new CrushedCigarette(), RelicType.SHARED);
        BaseMod.addRelic(new BottledFunds(), RelicType.SHARED);
        BaseMod.addRelic(new GreedFountain(), RelicType.SHARED);
        BaseMod.addRelic(new BrokenCalculator(), RelicType.SHARED);

        BaseMod.addRelicToCustomPool(new CouragePotion(), TheThorton.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new BusinessCardPrinter(), TheThorton.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Resume(), TheThorton.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new StoneOfSprinting(), TheThorton.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new TimeNecklace(), TheThorton.Enums.COLOR_GRAY);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {

        BaseMod.addCard(new AdministrativeActions());
        BaseMod.addCard(new AngerMoment());
        BaseMod.addCard(new BattleTrash());
        BaseMod.addCard(new BoardroomBlast());
        BaseMod.addCard(new BookBash());
        BaseMod.addCard(new BusinessBash());
        BaseMod.addCard(new BusinessBoom());
        BaseMod.addCard(new BusinessBurst());
        BaseMod.addCard(new BusinessCard());
        BaseMod.addCard(new CallIn());
        BaseMod.addCard(new CardboardBox());
        BaseMod.addCard(new CashBash());
        BaseMod.addCard(new ChartProject());
        BaseMod.addCard(new CoinClash());
        BaseMod.addCard(new CoinShield());
        BaseMod.addCard(new CowardiceForm());
        BaseMod.addCard(new Crash());
        BaseMod.addCard(new CrashingStocks());
        BaseMod.addCard(new CreditCard());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Demotion());
        BaseMod.addCard(new DesperateRun());
        BaseMod.addCard(new Distance());
        BaseMod.addCard(new DockPay());
        BaseMod.addCard(new DodgeAndRollAndRun());
        BaseMod.addCard(new DoubleRun());
        BaseMod.addCard(new DownPayment());
        BaseMod.addCard(new DualBap());
        BaseMod.addCard(new ElbowSmash());
        BaseMod.addCard(new EquivalentExchange());
        BaseMod.addCard(new Escape());
        BaseMod.addCard(new Fatigue());
        BaseMod.addCard(new FearfulRun());
        BaseMod.addCard(new FocusStrength());
        BaseMod.addCard(new Fortune());
        BaseMod.addCard(new GoldBuffer());
        BaseMod.addCard(new Heckle());
        BaseMod.addCard(new HedgeFund());
        BaseMod.addCard(new Hexaghostify());
        BaseMod.addCard(new Hindsight());
        BaseMod.addCard(new HitAhead());
        BaseMod.addCard(new Investment());
        BaseMod.addCard(new LastDitchEffort());
        BaseMod.addCard(new LongDaysWork());
        BaseMod.addCard(new LongTermPayoff());
        BaseMod.addCard(new MomentaryBreak());
        BaseMod.addCard(new OddChange());
        BaseMod.addCard(new PennyToss());
        BaseMod.addCard(new PocketChange());
        BaseMod.addCard(new Portfolio());
        BaseMod.addCard(new PowerUp());
        BaseMod.addCard(new Rebalance());
        BaseMod.addCard(new RichRun());
        BaseMod.addCard(new ROI());
        BaseMod.addCard(new RunningKick());
        BaseMod.addCard(new ScalingIsWin());
        BaseMod.addCard(new SeeingDouble());
        BaseMod.addCard(new ShoutAndRun());
        BaseMod.addCard(new SicklyRun());
        BaseMod.addCard(new Siphon());
        BaseMod.addCard(new SnowDay());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new StumblingSlap());
        BaseMod.addCard(new TakeAccounts());
        BaseMod.addCard(new ThickSkin());
        BaseMod.addCard(new ToxicGreed());
        BaseMod.addCard(new WhirlingBriefcase());
        BaseMod.addCard(new YoureFired());
        BaseMod.addCard(new SellShort());
        BaseMod.addCard(new SummonProfit());
        BaseMod.addCard(new FeelNoBrain());
        BaseMod.addCard(new Octopus());
        BaseMod.addCard(new CannonShot());
        BaseMod.addCard(new CoffeeBlast());
        BaseMod.addCard(new Jackpot());
        BaseMod.addCard(new GoldenPetal());
        BaseMod.addCard(new Nibble());
        BaseMod.addCard(new SheetShred());
        BaseMod.addCard(new GemGun());
        BaseMod.addCard(new Cuckoo());
        BaseMod.addCard(new Ghost());
        BaseMod.addCard(new StruckByATrain());
        BaseMod.addCard(new OhNo());
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/ThortonMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/ThortonMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/ThortonMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/ThortonMod-Character-Strings.json");

        logger.info("Done editting strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/ThortonMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveStartGame() {
        if (AbstractDungeon.player.chosenClass == TheThorton.Enums.THE_THORT) {
            ReflectionHacks.setPrivate(CardCrawlGame.cursor, GameCursor.class, "img", ImageMaster.loadImage("thethortonResources/images/winxpCursor.png"));
        }
        if (!thindone) {
            newHitbox.hb.resize(500 * Settings.scale, 160 * Settings.scale);
            newHitbox.hb.move(256 * Settings.scale, 600 * Settings.scale);
            thindone = true;
        }
    }

    @Override
    public void receivePostPlayerUpdate() {
        if (AbstractDungeon.player instanceof TheThorton || renderStuff) {
            newHitbox.dragUpdate();
            newHitbox.update();
        }
    }

    @Override
    public void receivePostDeath() {
        fleeAmt = 0;
        businessCardAmt = 0;
        businessCardMax = 10;
        investmentAmt = 0;
        investmentMax = 4;
        fleeMax = 3;
        nextQuestionShop = false;
        bonusBig = false;
        if ((AbstractDungeon.actNum == 3 && AbstractDungeon.player.currentHealth > 0 && AbstractDungeon.player instanceof TheThorton) || AbstractDungeon.actNum == 4) {
            goldamt++;
        }
        saveData();
    }

    public static AbstractRelic returnTrueRandomScreenlessRelic() {
        ArrayList<AbstractRelic> eligibleRelicsList = new ArrayList<>();
        ArrayList<AbstractRelic> myGoodStuffList = new ArrayList<>();
        for (String r : AbstractDungeon.commonRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        for (String r : AbstractDungeon.uncommonRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        for (String r : AbstractDungeon.rareRelicPool) {
            eligibleRelicsList.add(RelicLibrary.getRelic(r));
        }
        try {
            for (AbstractRelic r : eligibleRelicsList)
                if (r.getClass().getMethod("onEquip").getDeclaringClass() == AbstractRelic.class && r.getClass().getMethod("onUnequip").getDeclaringClass() == AbstractRelic.class) {
                    myGoodStuffList.add(r);
                }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (myGoodStuffList.isEmpty()) {
            return new Circlet();
        } else {
            myGoodStuffList.removeIf(r -> AbstractDungeon.player.hasRelic(r.relicId));
            return myGoodStuffList.get(AbstractDungeon.cardRandomRng.random(myGoodStuffList.size() - 1));
        }
    }

    public static void loadJokeCardImage(AbstractCard card, String img) {
        if (card instanceof AbstractThortonCard) {
            ((AbstractThortonCard) card).betaArtPath = img;
        }
        Texture cardTexture;
        cardTexture = TextureLoader.getTexture(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(card, AbstractCard.class, "jokePortrait", cardImg);
    }

    public void receiveOnBattleStart(AbstractRoom room) {
        ThortMod.fleeAmt = 0;
        BossModeDamage = 0;
        if (room instanceof MonsterRoomBoss || (room instanceof MonsterRoomElite && AbstractDungeon.id.equals(TheEnding.ID))) {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (q.currentHealth > BossModeDamage) {
                    BossModeDamage = q.currentHealth;
                }
            }
            BossModeDamage /= 4;
        }
        ArrayList<NextCombatInfo> toRemove = new ArrayList<>();
        for (NextCombatInfo n : importantInfoStuff) {
            AbstractCard q = CardLibrary.getCard(n.cardID).makeCopy();
            if (q instanceof AbstractNextCombatThortonCard) {
                if (((AbstractNextCombatThortonCard) q).doNextBattleCheck()) {
                    ((AbstractNextCombatThortonCard) q).doNextBattle(n.magic1, n.magic2, n.magicString, n.upgraded);
                    toRemove.add(n);
                }
            }
        }
        importantInfoStuff.removeIf(toRemove::contains);
    }


    @Override
    public void receivePostBattle(AbstractRoom room) {
        ThortMod.fleeAmt = 0;
        if (ThortMod.renderStuff)
            renderStuff = false;
    }

    @Override
    public void onLoad(VexSaveWrapper s) {
        if (AbstractDungeon.player instanceof TheThorton) {
            ThortMod.fleeMax = s.fleeMax;
            ThortMod.businessCardAmt = s.businessCardAmt;
            ThortMod.businessCardMax = s.businessCardMax;
            ThortMod.investmentAmt = s.investmentAmt;
            ThortMod.nextQuestionShop = s.nextQuestionShop;
            ThortMod.importantInfoStuff = s.nextCombatActionsList;
        }
    }

    @Override
    public VexSaveWrapper onSave() {
        return new VexSaveWrapper(fleeMax, businessCardAmt, businessCardMax, investmentAmt, nextQuestionShop, importantInfoStuff);
    }
}
