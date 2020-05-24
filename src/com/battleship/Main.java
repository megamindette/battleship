package com.battleship;
//Author: Annette Mathew; Date: 05/23/2020
class Main {
  public static void main(String[] args) {
    Board newGame = new Board();
    newGame.promptUser();
    newGame.promptComp();
    while(newGame.playerWin() == false && newGame.compWin() == false) {
      while(newGame.shoot(newGame.boardComp) == false) {
        System.out.println("");
      }
      while(newGame.shootComp(newGame.board) == false) {
        System.out.println("");
      }
      newGame.printBoard();
    }
  }
}

