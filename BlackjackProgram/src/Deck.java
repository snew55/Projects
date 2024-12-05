import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
public class Deck {
    //keeping track of number of decks as some casinos use 4 decks, some use 6 decks etc.
    private int numDecks;
    private Stack deck;

    public Deck(int numDecks){

        this.numDecks = numDecks;
        this.deck = loadDeck(numDecks);
    }
    //load deck method linked lists for the suits and values of cards for random generation of cards
    //the "deck will take an input of 1-6 decks of 52 cards
    public Stack loadDeck(int numDecks){
        Stack deck = new Stack();

        LinkedList<String> suits = new LinkedList<String>();
        suits.add("Spades");
        suits.add("Clubs");
        suits.add("Diamonds");
        suits.add("Hearts");

        //4 '10' values for the 10 card as well as the value for each face card (K, Q, J)
        int[] values = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int i=0; i < (numDecks*52); i++){
            Random random = new Random();

            int randomIndexSuit = random.nextInt(suits.size());
            String suit = suits.get(randomIndexSuit);

            int randomIndexValue = random.nextInt(values.length);
            int value = values[randomIndexValue];

            Card card = new Card(value, suit);
            deck.push(card);
        }

  ;      return deck;
    }

    public Card drawFromDeck(){
        Card card = (Card) deck.pop();
        return card;
    }

}
