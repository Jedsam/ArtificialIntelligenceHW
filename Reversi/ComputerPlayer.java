package Reversi;

public class ComputerPlayer extends Player {
    public static int ComputerCounter = 1;

    @Override
    public int getInput() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInput'");
    }

    ComputerPlayer(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }
}
