package Reversi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReversiStart {
    private static final int INPUT_BUFFER_SIZE = 100;
    private static Queue<Integer> inputBuffer = new LinkedList<Integer>();
    private static ReversiGUI myGui;
    private static Board currentGame;

    // Add the new players to the string with their integer order
    // Human player will always be 100
    public static final String[] PLAYERS = { "Human", "AI" };
    public static final int HUMAN_PLAYER = 100;
    public static final int AI_PLAYER = 101;

    public static void main(String[] args) {

        myGui = ReversiGUI.createGUI();
        int currentInput;
        while (true) {

            myGui.startSelectionScreen();
            // Read the first player information
            currentInput = readInputFromBuffer();
            while (checkInvalidPlayerInput(currentInput)) {
                try {
                    Thread.sleep(100); // Avoids high CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                currentInput = readInputFromBuffer();
            }
            Player player1 = getPlayer(currentInput, Board.BLACK);

            // Read the second player information
            currentInput = readInputFromBuffer();
            while (checkInvalidPlayerInput(currentInput)) {
                try {
                    Thread.sleep(100); // Avoids high CPU usage
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                currentInput = readInputFromBuffer();
            }
            Player player2 = getPlayer(currentInput, Board.WHITE);

            myGui.startGameGUI(player1, player2);

            currentGame = new Board();
            currentGame.startGame(player1, player2);
        }

    }

    public static void startGameGUI(Player player1, Player player2) {
        myGui.startGameGUI(player1, player2);
    }

    private static Player getPlayer(int currentInput, int playerNumber) {

        if (currentInput == HUMAN_PLAYER) {
            return (Player) new HumanPlayer(
                    "Player " + (playerNumber == Board.BLACK ? 1 : 2), playerNumber);
        } else
            return (Player) new HumanPlayer("playereeEE", playerNumber);

    }

    // Checks if the given integer is a player number
    private static boolean checkInvalidPlayerInput(int currentInput) {
        return !(currentInput == HUMAN_PLAYER || currentInput == AI_PLAYER);
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
