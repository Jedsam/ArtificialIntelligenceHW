package Reversi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReversiStart {
    private static final int INPUT_BUFFER_SIZE = 100;
    private static Queue<Integer> inputBuffer = new LinkedList<Integer>();

    private static ReversiGUI myGui;

    public static void main(String[] args) {

        myGui = ReversiGUI.createGUI();
        Board myBoard = new Board();

        Player player1 = (Player) new HumanPlayer("Player 1");
        Player player2 = (Player) new HumanPlayer("Player 2");
        myBoard.startGame(player1, player2);

    }

    public static void addToInputBuffer(int val) {
        if (inputBuffer.size() <= INPUT_BUFFER_SIZE)
            inputBuffer.add(val);
        else {
            System.out.println("Input buffer full!");
        }
    }

    public static void addPiece(int index, int value) {
        myGui.addPiece(index, value);
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static int readInputFromBuffer() {
        Integer returnVal = inputBuffer.poll();
        if (returnVal == null) {
            return -1;
        }
        return returnVal.intValue();
    }

    public static void addValidMoves(ArrayList<Integer> validMovesList) {
        myGui.addValidMoves(validMovesList);
    }

    public static void removeValidMoves(ArrayList<Integer> validMovesList) {
        myGui.removeValidMoves(validMovesList);
    }
}
