
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class BlackJack {
    private LinkedList<Card> dealer;
    private LinkedList<Card> player;
    private Deck deck;
    private int credits;

    public BlackJack(){
        this.dealer = new LinkedList<Card>();
        this.player = new LinkedList<Card>();
        this.deck = initializeGame();
        this.credits = initializeCredits();
    };

    public Deck initializeGame(){
        System.out.println("Enter the amount of decks you would like to be shuffled (Enter 1-6): ");
        Scanner scanner = new Scanner(System.in);
        int numDecks = Integer.parseInt(scanner.nextLine());
        Deck deck = new Deck(numDecks);
        return deck;
    }

    public int initializeCredits(){
        System.out.println("Enter the amount of credits you would like to play with: ");
        Scanner s = new Scanner(System.in);
        credits = Integer.parseInt(s.nextLine());
        return credits;
    }

    public void playGame(){
        while (credits != 0){
            playRound();
        }
    }

    // Helper function to calculate the hand's total value
    private int calculateHandValue(LinkedList<Card> hand) {
        int total = 0;
        int aceCount = 0;
        for (Card card : hand) {
            total += card.getValue();
            if (card.getValue() == 11) {
                aceCount++;
            }
        }
        // Adjust for Aces if total exceeds 21
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    // Helper function to handle dealer's turn
    private void dealerTurn() {
        while (calculateHandValue(dealer) < 17) {
            Card newDealerCard = deck.drawFromDeck();
            dealer.add(newDealerCard);
            System.out.println("Dealer draws a " + newDealerCard.toString());
        }
    }

    // playRound method with dealer's turn and payout calculation
    private void playRound() {
        System.out.println("You have " + credits + " credits.");
        System.out.println("Enter a bet (minimum $1): ");
        Scanner sca = new Scanner(System.in);
        int bet = Integer.parseInt(sca.nextLine());
        credits -= bet;

        // Initialize hands and draw initial cards
        player.clear();
        dealer.clear();
        LinkedList<Card> splitHand = new LinkedList<>();
        int splitBet = bet;

        // Deal initial cards
        player.add(deck.drawFromDeck());
        player.add(deck.drawFromDeck());
        dealer.add(deck.drawFromDeck());
        dealer.add(deck.drawFromDeck());

        System.out.println("Your hand: " + player);
        System.out.println("Dealer shows: " + dealer.get(1).toString());

        if (calculateHandValue(player) == 21) {
            if (calculateHandValue(dealer) == 21) {
                System.out.println("Push! Both you and the dealer have Blackjack.");
            } else {
                System.out.println("Blackjack! You win 3:2 payout.");
                credits += (bet * 3 / 2);
            }
            return;
        }

        // Player's turn
        boolean playerBust = playerTurn(player, bet, sca);
        boolean splitActive = false;

        // If player chose to split
        if (player.size() == 2 && player.get(0).getValue() == player.get(1).getValue() && !playerBust) {
            System.out.println("Would you like to split? (yes/no)");
            if (sca.nextLine().equalsIgnoreCase("yes")) {
                splitHand.add(player.removeLast());
                splitHand.add(deck.drawFromDeck());
                player.add(deck.drawFromDeck());
                credits -= splitBet;
                splitActive = true;
            }
        }

        // Dealer's turn if player didn't bust
        if (!playerBust) {
            dealerTurn();
        }

        // Calculate outcomes and payouts
        int playerValue = calculateHandValue(player);
        int dealerValue = calculateHandValue(dealer);

        if (playerValue <= 21) {
            if (dealerValue > 21 || playerValue > dealerValue) {
                System.out.println("You win! Payout: " + (bet * 2));
                credits += (bet * 2);
            } else if (playerValue == dealerValue) {
                System.out.println("Push! Your bet is returned.");
                credits += bet;
            } else {
                System.out.println("Dealer wins.");
            }
        } else {
            System.out.println("Bust! Dealer wins.");
        }

        // If split was active, resolve split hand
        if (splitActive) {
            resolveSplitHand(splitHand, splitBet, dealerValue);
        }
    }

    // Handles the player's turn for a hand
    private boolean playerTurn(LinkedList<Card> hand, int bet, Scanner scanner) {
        while (true) {
            System.out.println("Your current hand: " + hand + " (Total: " + calculateHandValue(hand) + ")");
            System.out.println("Options: 1. Hit  2. Stand  3. Double");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                Card newCard = deck.drawFromDeck();
                hand.add(newCard);
                System.out.println("You drew a " + newCard.toString());
                if (calculateHandValue(hand) > 21) {
                    System.out.println("Bust!");
                    return true;
                }
            } else if (choice == 2) {
                break;
            } else if (choice == 3) {
                bet *= 2;
                Card newCard = deck.drawFromDeck();
                hand.add(newCard);
                System.out.println("You drew a " + newCard.toString());
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
        return false;
    }

    // Handles resolution of a split hand
    private void resolveSplitHand(LinkedList<Card> splitHand, int splitBet, int dealerValue) {
        System.out.println("Resolving split hand...");
        boolean splitBust = playerTurn(splitHand, splitBet, new Scanner(System.in));
        int splitValue = calculateHandValue(splitHand);

        if (!splitBust) {
            if (splitValue > dealerValue || dealerValue > 21) {
                System.out.println("Split hand wins! Payout: " + (splitBet * 2));
                credits += (splitBet * 2);
            } else if (splitValue == dealerValue) {
                System.out.println("Split hand push. Your bet is returned.");
                credits += splitBet;
            } else {
                System.out.println("Dealer wins the split hand.");
            }
        } else {
            System.out.println("Bust! Dealer wins the split hand.");
        }
    }
}
