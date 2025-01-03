package Reversi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReversiStart {
    private static final int INPUT_BUFFER_SIZE = 100;
    private static Queue<Integer> inputBuffer = new LinkedList<Integer>();
    private static ReversiGUI myGui;
    public static Board currentGame;

    // Variable for the sleep method between each input
    private static final int TIME_BETWEEN_INPUT = 50;

    public static final int depth = 4;

    // Add the new players to the string with their integer order
    // Human player will always be 100
    public static final String[] PLAYERS = { "Human", "AI-H1", "AI-H2", "AI-H3"};
    public static final int HUMAN_PLAYER = 100;
    public static final int AI_PLAYER_H1 = 101;
    public static final int AI_PLAYER_H2 = 102;
    public static final int AI_PLAYER_H3 = 103;

    // Input output variables
    public static final int INVALID_INPUT = -1;
    public static final int EXIT_CODE = -2;

    public static void main(String[] args) {

        myGui = ReversiGUI.createGUI();
        int currentInput;
        while (true) {

            myGui.startSelectionScreen();
            // Read the first player information
            currentInput = readInputFromBuffer();
            while (checkInvalidPlayerInput(currentInput)) {
                currentInput = readInputFromBuffer();
            }
            Player player1 = getPlayer(currentInput, Board.BLACK);

            // Read the second player information
            currentInput = readInputFromBuffer();
            while (checkInvalidPlayerInput(currentInput)) {
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
        } else if (currentInput == AI_PLAYER_H1) {
            return (Player) new ComputerH1(playerNumber);
        } else if (currentInput == AI_PLAYER_H2) {
            return (Player) new ComputerH2(playerNumber);
        } else {
            return (Player) new ComputerH3(playerNumber);
        } 
    }

    // Checks if the given integer is a player number
    private static boolean checkInvalidPlayerInput(int currentInput) {
        return !(currentInput == HUMAN_PLAYER || currentInput == AI_PLAYER_H1 || currentInput == AI_PLAYER_H2
                || currentInput == AI_PLAYER_H3);
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
        while (returnVal == null) {
            try {
                Thread.sleep(TIME_BETWEEN_INPUT); // Add delay to avoid high CPU usage and adds a small wait time
                                                  // between moves
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            returnVal = inputBuffer.poll();
        }
        return returnVal.intValue();
    }

    public static void addValidMoves(ArrayList<Short> validMovesList) {
        myGui.addValidMoves(validMovesList);
    }

    public static void removeValidMoves(ArrayList<Short> validMovesList) {
        myGui.removeValidMoves(validMovesList);
    }

    public static void setMessage(String printMessage) {
        myGui.setMessage(printMessage);
    }
}
