package Reversi;

import java.io.FileWriter;
import java.io.IOException;

public class ReversiTrainH3 {
    public static int depth = 4;
    private static final int GAME_COUNTER = 100;

    public static void main(String[] args) {
        int matchResult;
        ComputerH3Train player1 = new ComputerH3Train(Board.BLACK, null);
        ComputerH3Train1 player2 = new ComputerH3Train1(Board.WHITE, null);
        int player1Wins = 0;
        for (int i = 0; i < GAME_COUNTER; i++) {
            Board currentGame = new Board(false);
            player1.prepareForNewGame(currentGame);
            player2.prepareForNewGame(currentGame);
            matchResult = currentGame.startGame((Player) player1, (Player) player2, false);
            if (matchResult != Board.EMPTY) {
                player1.UpdateTable(matchResult);
                player1.UpdateTable(matchResult == Board.BLACK ? Board.WHITE : Board.BLACK, player2.moves);
            }
            if (matchResult == Board.BLACK) {
                player1Wins++;
            }
            System.out.println(i);
        }
        int totalWeight;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                totalWeight = ComputerH3Train.WEIGHT_BOARD[i][j] + ComputerH3Train.WEIGHT_BOARD[7 - i][j]
                        + ComputerH3Train.WEIGHT_BOARD[i][7 - j]
                        + ComputerH3Train.WEIGHT_BOARD[7 - i][7 - j];
                ComputerH3Train.WEIGHT_BOARD[i][j] = totalWeight;
                ComputerH3Train.WEIGHT_BOARD[7 - i][j] = totalWeight;
                ComputerH3Train.WEIGHT_BOARD[i][7 - j] = totalWeight;
                ComputerH3Train.WEIGHT_BOARD[7 - i][7 - j] = totalWeight;
            }
        }

        System.err.println(player1Wins + " " + (GAME_COUNTER - player1Wins));
        String resultString = "{";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                resultString += "\t" + ComputerH3Train.WEIGHT_BOARD[i][j] + ",";
            }
            resultString = resultString.substring(0, resultString.length() - 1);
            resultString += " },\n{";
        }
        resultString = resultString.substring(0, resultString.length() - 1);

        // save the results to the output.txt
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(resultString);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
