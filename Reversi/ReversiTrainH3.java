package Reversi;

import java.io.FileWriter;
import java.io.IOException;

public class ReversiTrainH3 {
    public static int depth = 4;
    private static final int GAME_COUNTER = 100;

    public static void main(String[] args) {
        int matchResult;
        ComputerH4 player1 = new ComputerH4(Board.BLACK, null);
        ComputerH3_1 player2 = new ComputerH3_1(Board.WHITE, null);
        int player1Wins = 0;
        int increment;
        for (int i = 0; i < GAME_COUNTER; i++) {
            Board currentGame = new Board();
            player1.prepareForNewGame(currentGame);
            player2.prepareForNewGame(currentGame);
            matchResult = currentGame.startGame((Player) player1, (Player) player2, false);

            if (matchResult != Board.EMPTY) {
                if (matchResult == Board.BLACK) {
                    increment = 1;
                    player1Wins++;
                } else {
                    increment = -1;
                }
                ComputerH3_1.UpdateTable(increment, player1.moves);
                ComputerH3_1.UpdateTable(-increment, player2.moves);
            }

            System.out.println(i);
        }
        int calculatedWeight, totalWeight = 0;
        int maxWeight = Integer.MIN_VALUE;
        int minWeight = Integer.MAX_VALUE;

        // Calculate necessary values
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                calculatedWeight = ComputerH3_1.WEIGHT_BOARD[i][j] + ComputerH3_1.WEIGHT_BOARD[7 - i][j]
                        + ComputerH3_1.WEIGHT_BOARD[i][7 - j]
                        + ComputerH3_1.WEIGHT_BOARD[7 - i][7 - j];
                if (calculatedWeight > maxWeight) {
                    maxWeight = calculatedWeight;
                }
                if (calculatedWeight < minWeight) {
                    minWeight = calculatedWeight;
                }
                totalWeight += calculatedWeight;
            }
        }

        // Board accepts values inbetween -50 and 50

        double dividing_value = (maxWeight - minWeight) / 100.0; // 50 - (-50) = 100
        double subtract_val_to_normalize = ((maxWeight) / dividing_value) - 50;

        // Calculate the resulting weight board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                calculatedWeight = ComputerH3_1.WEIGHT_BOARD[i][j] + ComputerH3_1.WEIGHT_BOARD[7 - i][j]
                        + ComputerH3_1.WEIGHT_BOARD[i][7 - j]
                        + ComputerH3_1.WEIGHT_BOARD[7 - i][7 - j];
                calculatedWeight = (int) Math.ceil((calculatedWeight / dividing_value) - subtract_val_to_normalize);
                ComputerH3_1.WEIGHT_BOARD[i][j] = calculatedWeight;
                ComputerH3_1.WEIGHT_BOARD[7 - i][j] = calculatedWeight;
                ComputerH3_1.WEIGHT_BOARD[i][7 - j] = calculatedWeight;
                ComputerH3_1.WEIGHT_BOARD[7 - i][7 - j] = calculatedWeight;
            }
        }

        System.err.println("Player1 : " + player1Wins + " " + "Player2 : " + (GAME_COUNTER - player1Wins));
        String resultString = "{";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                resultString += "\t" + ComputerH3_1.WEIGHT_BOARD[i][j] + ",";
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
