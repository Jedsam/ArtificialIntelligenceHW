package Reversi;

import java.util.ArrayList;

public class HumanPlayer extends Player {
    
    
    @Override
    public int getInput() {
        int move = ReversiStart.readInputFromBuffer();
        ReversiStart.updateLastMove(move + "");
        return move;
    }

    HumanPlayer (String name, int color){
        this.name = name;
        this.color = color;
    }

}
