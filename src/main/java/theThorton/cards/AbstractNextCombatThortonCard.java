package theThorton.cards;

public abstract class AbstractNextCombatThortonCard extends AbstractThortonCard {
    public AbstractNextCombatThortonCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public boolean doNextBattleCheck() {
        return true;
    }

    public abstract void doNextBattle(int magic1, int magic2, String magicString, boolean upgrade);
}
