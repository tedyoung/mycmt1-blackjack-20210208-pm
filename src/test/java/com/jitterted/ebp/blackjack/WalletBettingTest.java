package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WalletBettingTest {

  @Test
  public void walletWith12Bet8BalanceIs4() throws Exception {
    // GIVEN
    Wallet wallet = createWalletWithInitialBalanceOf(12);

    // WHEN
    wallet.bet(8);

    // THEN
    assertThat(wallet.balance())
        .isEqualTo(12 - 8); // EVIDENT DATA
  }

  @Test
  public void walletWith27Bet7AndBet9BalanceIs11() throws Exception {
    Wallet wallet = createWalletWithInitialBalanceOf(27);
    wallet.bet(7);

    wallet.bet(9);

    assertThat(wallet.balance())
        .isEqualTo(27 - 7 - 9);
  }

  @Test
  public void walletWhenBetFullAmountIsEmpty() throws Exception {
    Wallet wallet = createWalletWithInitialBalanceOf(33);

    wallet.bet(33);

    assertThat(wallet.isEmpty())
        .isTrue();
  }

  @Test
  public void betMoreThanBalanceThrowsException() throws Exception {
    Wallet wallet = createWalletWithInitialBalanceOf(73);

    assertThatThrownBy(() -> {
      wallet.bet(74);
    })
      .isInstanceOf(IllegalStateException.class);
  }

  @Test
  public void betNegativeAmountThrowsException() throws Exception {
    Wallet wallet = new Wallet();

    assertThatThrownBy(() -> {
      wallet.bet(-1);
    })
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void betZeroIsAllowed() throws Exception {
    Wallet wallet = createWalletWithInitialBalanceOf(11);

    wallet.bet(0);

    assertThat(wallet.balance())
        .isEqualTo(11);
  }

  private Wallet createWalletWithInitialBalanceOf(int i) {
    Wallet wallet = new Wallet();
    wallet.addMoney(i);
    return wallet;
  }
}