package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameBettingTest {

  @Test
  public void playerStartsWithZeroBalance() throws Exception {
    Game game = new Game();

    assertThat(game.playerBalance())
        .isZero();
  }

  @Test
  public void playerDeposits25BalanceIs25() throws Exception {
    Game game = new Game();

    game.playerDeposits(25);

    assertThat(game.playerBalance())
        .isEqualTo(25);
  }


  @Test
  public void playerWith100BalanceBets75BalanceIs25() throws Exception {
    Game game = new Game();
    game.playerDeposits(100);

    game.playerBets(75);

    assertThat(game.playerBalance())
        .isEqualTo(100 - 75);
  }

  //  @Test
//  public void playerWith100BalanceBets50AndWinsBalanceIs150() throws Exception {
//    Game game = new Game();
//    game.playerDeposits(100);
//    game.playerBets(50);
//
//    game.playerWins();
//
//    assertThat(game.playerBalance())
//        .isEqualTo(100 - 50 + 100);
//  }

}