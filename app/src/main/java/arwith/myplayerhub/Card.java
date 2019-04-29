package arwith.myplayerhub;

public class Card {

    public String accountType;
    public String accountInfo;
    public int cardID;
    public int backCol;
    public boolean isLinked;
    public String link;
    public boolean deleter;

    public Card(String accountType, String accountInfo, int cardID, int backCol, boolean isLinked, String link, boolean deleter) {
        this.accountType = accountType;
        this.accountInfo = accountInfo;
        this.cardID = cardID;
        this.backCol = backCol;
        this.isLinked = isLinked;
        this.link = link;
        this.deleter = deleter;
    }
}
