package Reversi;

import java.util.ArrayList;
import java.util.Random;

public class ComputerRandom extends ComputerPlayer {

    private Board game;
    public ArrayList<Short> moves;
    private Random rand;

    ComputerRandom(int color, Board game) {
        this.game = game;
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
        rand = new Random();
    }

    public void prepareForNewGame(Board game) {
        this.game = game;
        moves = new ArrayList<Short>();
    }

    public int getInput() {
        ArrayList<Short> validMovesList = game.findValidMoves();
        short randomMove = validMovesList.get(rand.nextInt(validMovesList.size()));
        moves.add(randomMove);
        return randomMove;
    }

}
