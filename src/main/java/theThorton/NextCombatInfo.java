package theThorton;

public class NextCombatInfo {
    public String cardID;
    public int magic1;
    public int magic2;
    public String magicString;
    public boolean upgraded;

    public NextCombatInfo(String cardID, int magic1, int magic2, String magicString, boolean upgraded) {
        this.cardID = cardID;
        this.magic1 = magic1;
        this.magic2 = magic2;
        this.magicString = magicString;
        this.upgraded = upgraded;
    }
}
