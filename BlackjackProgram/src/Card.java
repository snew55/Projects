
public class Card {
    private int value;
    private String suit;

    public Card(int v, String s){
        this.value = v;
        this.suit = s;
    }
    // Getter methods
    public int getValue(){
        return this.value;
    }
    public String getSuit(){
        return this.suit;
    }

    // Setter methods
    public void setValue(int newValue){
        this.value = newValue;
    }
    public void setSuit(String newSuit){
        this.suit = newSuit;
    }

    // Updated toString method to avoid null values
    public String toString(){
        return value + " of " + suit;
    }
}
