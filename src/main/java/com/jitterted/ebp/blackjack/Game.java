package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

  private final Deck deck;

  private final Hand dealerHand = new Hand();
  private final Hand playerHand = new Hand();
  private int playerBalance = 0;
  private int playerBet = 0;

  public static void main(String[] args) {
    displayWelcomeMessage();

    Game game = new Game();
    game.initialDeal();
    game.play();

    resetScreen();
  }

  private static void resetScreen() {
    System.out.println(ansi().reset());
  }

  private static void displayWelcomeMessage() {
    System.out.println(ansi()
                           .bgBright(Ansi.Color.WHITE)
                           .eraseScreen()
                           .cursor(1, 1)
                           .fgGreen().a("Welcome to")
                           .fgRed().a(" Jitterted's")
                           .fgBlack().a(" BlackJack"));
  }

  public Game() {
    deck = new Deck();
  }

  public void initialDeal() {
    dealRoundOfCards();
    dealRoundOfCards();
  }

  // players first: that's the rule of Blackjack
  private void dealRoundOfCards() {
    playerHand.drawCardFrom(deck);
    dealerHand.drawCardFrom(deck);
  }

  public void play() {
    boolean playerBusted = playerTurn();
    dealerTurn(playerBusted);
    displayFinalGameState();
    determineOutcome(playerBusted);
  }

  private boolean playerTurn() {
    // get Player's decision: hit until they stand, then they're done (or they go bust)
    boolean playerBusted = false;
    while (!playerBusted) {
      displayGameState();
      String playerChoice = inputFromPlayer().toLowerCase();
      if (playerStands(playerChoice)) {
        break;
      }
      if (playerHits(playerChoice)) {
        playerHand.drawCardFrom(deck);
        if (playerHand.isBusted()) {
          playerBusted = true;
        }
      } else {
        System.out.println("You need to [H]it or [S]tand");
      }
    }
    return playerBusted;
  }

  private boolean playerHits(String playerChoice) {
    return playerChoice.startsWith("h");
  }

  private boolean playerStands(String playerChoice) {
    return playerChoice.startsWith("s");
  }

  private void dealerTurn(boolean playerBusted) {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>=stand)
    if (!playerBusted) {
      while (dealerHand.dealerShouldHit()) {
        dealerHand.drawCardFrom(deck);
      }
    }
  }

  private GameOutcome determineOutcome(boolean playerBusted) {
    if (playerBusted) {
      System.out.println("You Busted, so you lose.  💸");
      return GameOutcome.PLAYER_LOSES;
    } else if (dealerHand.isBusted()) {
      System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
      return GameOutcome.PLAYER_WINS;
    } else if (playerHand.beats(dealerHand)) {
      System.out.println("You beat the Dealer! 💵");
      return GameOutcome.PLAYER_WINS;
    } else if (dealerHand.pushes(playerHand)) {
      System.out.println("Push: The house wins, you Lose. 💸");
      return GameOutcome.PLAYER_PUSHES;
    }

    System.out.println("You lost to the Dealer. 💸");
    return GameOutcome.PLAYER_LOSES;
  }

  private String inputFromPlayer() {
    System.out.println("[H]it or [S]tand?");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private void displayGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    System.out.println(dealerHand.firstCard().display()); // first card is Face Up

    // second card is the hole card, which is hidden
    displayBackOfCard();

    System.out.println();
    System.out.println("Player has: ");
    playerHand.displayHand();
    System.out.println(playerHand.displayValue());
  }

  private void displayBackOfCard() {
    System.out.print(
        ansi()
            .cursorUp(7)
            .cursorRight(12)
            .a("┌─────────┐").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
            .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
            .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
            .a("└─────────┘"));
  }

  private void displayFinalGameState() {
    System.out.print(ansi().eraseScreen().cursor(1, 1));
    System.out.println("Dealer has: ");
    dealerHand.displayHand();
    System.out.println(dealerHand.displayValue());

    System.out.println();
    System.out.println("Player has: ");
    playerHand.displayHand();
    System.out.println(playerHand.displayValue());
  }


  public void playerDeposits(int amount) {
    playerBalance += amount;
  }

  public void playerBets(int betAmount) {
    playerBalance -= betAmount;
    playerBet = betAmount;
  }

  public int playerBalance() {
    return playerBalance;
  }

  private void payoff(int i) {
    playerBalance += playerBet * i;
  }

}
