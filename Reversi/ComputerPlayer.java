package Reversi;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
    public static int ComputerCounter = 1;

    @Override
    public int getInput(ArrayList<Short> validMovesList) {
        return validMovesList.get((int) (Math.random() * validMovesList.size()));
    }

    ComputerPlayer(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }
}
