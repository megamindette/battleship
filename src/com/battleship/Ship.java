package com.battleship;
//Author: Annette Mathew; Date: 05/23/2020
class Ship {
  int[][] coords;
  public Ship(int shipLength) {
    this.shipLength = shipLength;
    this.numHits = 0;
    coords = new int[shipLength][2];
  }

  public void addCoord(int xPos, int yPos, int spot) {
    coords[spot][0] = xPos;
    coords[spot][1] = yPos;
  }

  public boolean compareShip(int xPos, int yPos) {
    for(int i = 0; i < shipLength; ++i) {
      if(coords[i][0] == xPos && coords[i][1] == yPos) {
        numHits++;
        if(numHits == shipLength) {
          System.out.println("A ship has been sunk!");
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  private int shipLength;
  private int numHits;
}
