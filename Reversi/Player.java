package Reversi;

import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected int color;
    
    protected Board game;
    public ArrayList<Short> moves;

    public abstract int getInput();

    public String getVictoryMessage() {
        return this.name + "(" + (color == Board.BLACK ? "Black" : "White") + ")" + " has won!!!";
    }

    public String getTurnMessage() {
        return this.name + "(" + (color == Board.BLACK ? "Black" : "White") + ")" + " 's turn!";
    }

    public void prepareForNewGame(Board game) {
        this.game = game;
        moves = new ArrayList<Short>();
    }
}
