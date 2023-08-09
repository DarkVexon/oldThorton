package theThorton;

import java.util.ArrayList;

public class VexSaveWrapper {
    public int fleeMax;
    public int businessCardAmt;
    public int businessCardMax;
    public int investmentAmt;
    public boolean nextQuestionShop;
    public ArrayList<NextCombatInfo> nextCombatActionsList;
    public ArrayList<String> usedAdmins;

    public VexSaveWrapper(int fleeMax, int businessCardAmt, int businessCardMax, int investmentAmt, boolean nextQuestionShop, ArrayList<NextCombatInfo> myActions, ArrayList<String> usedAdmins) {
        this.fleeMax = fleeMax;
        this.businessCardAmt = businessCardAmt;
        this.businessCardMax = businessCardMax;
        this.investmentAmt = investmentAmt;
        this.nextQuestionShop = nextQuestionShop;
        this.nextCombatActionsList = myActions;
        this.usedAdmins = usedAdmins;
    }
}
