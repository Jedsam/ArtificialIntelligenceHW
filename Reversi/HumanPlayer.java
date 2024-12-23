package Reversi;

public class HumanPlayer extends Player {
    
    
    @Override
    public int getInput() {
        return ReversiStart.readInputFromBuffer();
    }

    HumanPlayer (String name, int color){
        this.name = name;
        this.color = color;
    }

}
