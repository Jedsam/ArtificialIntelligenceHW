package Reversi;

import java.util.ArrayList;

public class ComputerH2 extends ComputerPlayer{
    
    ComputerH2(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public int getInput() {
        return 0;
    }
}
