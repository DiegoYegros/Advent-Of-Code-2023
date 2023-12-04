import java.util.List;

public class Card {

    int id;
    List<Integer> winning;
    List<Integer> hand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getWinning() {
        return winning;
    }

    public void setWinning(List<Integer> winning) {
        this.winning = winning;
    }

    public List<Integer> getHand() {
        return hand;
    }

    public void setHand(List<Integer> hand) {
        this.hand = hand;
    }
}
