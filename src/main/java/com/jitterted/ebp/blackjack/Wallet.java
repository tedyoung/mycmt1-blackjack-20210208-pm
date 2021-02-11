package com.jitterted.ebp.blackjack;

public class Wallet {

  private int balance;

  public Wallet() {
    balance = 0;
  }

  public boolean isEmpty() {
    return balance == 0;
  }

  public void addMoney(int amount) {
    ensureAmountGreaterThanZero(amount);
    balance += amount;
  }

  public int balance() {
    return balance;
  }

  public void bet(int amount) {
    ensureAmountIsZeroOrMore(amount);
    ensureSufficientBalance(amount);
    balance -= amount;
  }

  private void ensureAmountGreaterThanZero(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException();
    }
  }

  private void ensureAmountIsZeroOrMore(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException();
    }
  }

  private void ensureSufficientBalance(int amount) {
    if (amount > balance) {
      throw new IllegalStateException();
    }
  }
}
