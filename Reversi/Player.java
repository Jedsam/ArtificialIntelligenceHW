package Reversi;

import java.util.ArrayList;

public abstract class Player {
    protected String name;
    protected int color;

    public abstract int getInput(ArrayList<Short> validMovesList);

    public String getVictoryMessage() {
        return this.name + "(" + (color == Board.BLACK ? "Black" : "White") + ")" + " has won!!!";
    }

    public String getTurnMessage() {
        return this.name + "(" + (color == Board.BLACK ? "Black" : "White") + ")" + " 's turn!";
    }
}
