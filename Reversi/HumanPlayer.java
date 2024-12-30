package Reversi;

import java.util.ArrayList;

public class HumanPlayer extends Player {
    
    
    @Override
    public int getInput(ArrayList<Short> validMovesList) {
        return ReversiStart.readInputFromBuffer();
    }

    HumanPlayer (String name, int color){
        this.name = name;
        this.color = color;
    }

}
