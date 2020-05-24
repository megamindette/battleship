package com.battleship;
//Author: Annette Mathew; Date: 05/23/2020
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

class Board {
  private int boardWidth;
  private int boardLength;
  private int[] lengths = {2, 3, 3, 4, 5};

  String[][] board;
  String[][] boardComp;
  ArrayList<Ship> list = new ArrayList<Ship>();
  ArrayList<Ship> list2 = new ArrayList<Ship>();
  public Board() {
    this.boardWidth = 10;
    this.boardLength = 10;
    board = new String[boardWidth][boardLength];
    boardComp = new String[boardWidth][boardLength];
    //list = new Ship[5];
    for(int i = 0; i < boardLength; i++) {
      Arrays.fill(board[i], "0");
      Arrays.fill(boardComp[i], "0");
    }
  }

  public void printBoard() {
    System.out.println("   A B C D E F G H I J");
    for(int i = 0; i < this.boardLength; i++) {
      for(int j = 0; j < this.boardWidth; j++) {
        if(j == 0) {
          System.out.print((i + 1) + "  ");
        }
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void promptUser() {
    System.out.println("Welcome to Battleship!");
    printBoard();
    askForShip(2);
    printBoard();
    askForShip(3);
    printBoard();
    askForShip(3);
    printBoard();
    askForShip(4);
    printBoard();
    askForShip(5);
    printBoard();
    /*
    Scanner s = new Scanner(System.in);
    int length = s.nextInt();
    System.out.println("What are the coordinates of your ship(A-J),(1-10)?");
    Scanner t = new Scanner(System.in);
    String input = t.nextLine();
    System.out.println("What direction do you want your ship to face(R/L/U/D)?");
    Scanner p = new Scanner(System.in);
    String dir = p.nextLine(); */
  }

  public void askForShip(int length) {
    String first, last;
    do {
      System.out.println("You have a ship of length " + length);
      System.out.println("What is the starting point for your ship(A-J), (1-10)?");
      Scanner s = new Scanner(System.in);
      first = s.nextLine();
      System.out.println("What is your end point?(A-J), (1-10)");
      last = s.nextLine();
    } while(isValidShip(first, last, length, board, list) == false);

  }

  public boolean isValidShip(String first, String last, int length, String[][] board, ArrayList<Ship> currentList) {
    String alphabet = "ABCDEFGHIJ";
    int sCol = alphabet.indexOf(first.charAt(0));
    int sRow = Integer.parseInt(first.substring(1 ));
    int eCol = alphabet.indexOf(last.charAt(0));
    int eRow = Integer.parseInt(last.substring(1 ));
    System.out.println(sCol + ", " + sRow + ", " + eCol + ", " + eRow);
    if(sRow < 0 || eRow < 0 || sCol < 0 || eCol < 0 || sRow > 10 || eRow > 10 || sCol > 9 || eCol > 9) {
      return false;
    } else if(sCol == eCol && (sRow + length - 1 == eRow || eRow + length - 1 == sRow)) {
      int min = Math.min(sRow, eRow);
      int max = Math.max(sRow, eRow);
      for(int i = min; i <= max; i++) {
        if(board[i - 1][sCol] == "☐") {
          return false;
        }
      }
      Ship ship = new Ship(length);
      for(int i = min; i <= max; i++) {
        board[i - 1][sCol] = "☐";
        ship.addCoord(i - 1, sCol, i - min);
      }
      currentList.add(ship);
      return true;
    } else if(sRow == eRow && (sCol + length - 1 == eCol || eCol + length - 1 == sCol)) {
      int min = Math.min(sCol, eCol);
      int max = Math.max(sCol, eCol);
      for(int i = min; i <= max; i++) {
        if(board[sRow - 1][i] == "☐") {
          return false;
        }
      }
      Ship ship = new Ship(length);
      for(int i = min; i <= max; i++) {
        board[sRow - 1][i] = "☐";
        ship.addCoord(sRow - 1, i, i - min);
      }
      currentList.add(ship);
      return true;
    } else {
      return false;
    }
  }

  public void promptComp() {
    for(int i = 0; i < lengths.length; i++) {
      String alphabet = "ABCDEFGHIJ";
      Random ship = new Random();
      String sPos, ePos;
      do {
        int dir = ship.nextInt(2);
        char sCol, eCol;
        int sRow, eRow;
        if(dir == 0) { //vertical
          sCol = alphabet.charAt(ship.nextInt(boardWidth));
          sRow = ship.nextInt(boardLength - lengths[i]);
          eCol = sCol;
          eRow = sRow + lengths[i] - 1;

        } else { //horizontal
          sCol = alphabet.charAt(ship.nextInt(boardWidth - lengths[i]));
          sRow = ship.nextInt(boardLength);
          eRow = sRow;
          eCol = alphabet.charAt(alphabet.indexOf(sCol) + lengths[i] - 1);
        }
        sPos = sCol + "" + sRow + 1;
        ePos = eCol + "" + eRow + 1;
      } while(isValidShip(sPos, ePos, lengths[i], boardComp, list2) == false);
    }  
  }

  public boolean shoot(String[][] board) {
    String alphabet = "ABCDEFGHIJ";
    System.out.println("Where would you like to shoot?(A-J), (1-10)");
    Scanner t = new Scanner(System.in);
    String shot = t.nextLine();
    int sCol = alphabet.indexOf(shot.charAt(0));
    int sRow = Integer.parseInt(shot.substring(1 ));
    if(board[sRow - 1][sCol] == "☐") {
      //hit
      System.out.println("You hit a ship!");
      board[sRow - 1][sCol] = "1";
      shipCheck(sRow - 1, sCol, list2);
      return true;
    } else if(board[sRow - 1][sCol] == "0") {
      //miss
      System.out.println("You missed!");
      return true;
    } else {
      System.out.println("You've already shot here. Try again!");
      return false;
    }
  }

  public boolean shootComp(String[][] board) {
    System.out.println("Computer's turn. ");
    Random ship = new Random();
    int sCol = ship.nextInt(10);
    int sRow = ship.nextInt(10);
    if(board[sRow/*-1*/][sCol] == "☐") {
      board[sRow/* - 1*/][sCol] = "1";
      shipCheck(sRow/* - 1*/, sCol, list);
      System.out.println("The computer hit a player's ship.");
      return true;
    } else if(board[sRow/* - 1*/][sCol] == "0") {
      System.out.println("The computer missed.");
      board[sRow][sCol] = "2";
      return true;
    } else {
      return false;
    }
  }

  public void shipCheck(int xPos, int yPos, ArrayList<Ship> ship) {
    for(int i = 0; i < ship.size(); i++) {
      if(ship.get(i).compareShip(xPos, yPos) == true) {
        //System.out.println("A ship has been hit!");
        ship.remove(i);
      }
    }
  }

  public boolean playerWin() {
    if(list2.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public boolean compWin() {
    if(list.size() == 0) {
      return true;
    } else {
      return false;
    }
  }

}
