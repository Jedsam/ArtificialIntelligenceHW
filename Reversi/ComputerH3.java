package Reversi;

import java.util.ArrayList;

public class ComputerH3 extends ComputerPlayer{
    
    ComputerH3(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public int getInput() {

        return 0;
    }
}
