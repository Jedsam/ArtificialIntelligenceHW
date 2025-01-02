package Reversi;

import java.util.ArrayList;

public class ComputerH1 extends ComputerPlayer {

    ComputerH1(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public int getInput() {
        int depth = ReversiStart.depth;
        ArrayList<Board> nextBoards = calculateNextBoards(depth);
        if (nextBoards.size() == 0) {
            depth = 1;
            nextBoards = calculateNextBoards(depth);
        }
        Board bestBoard = nextBoards.get(0);
        short difference = -100;
        for (int i = 0; i < nextBoards.size(); i++) {
            Board tempBoard = nextBoards.get(i);
            short[] piecesCount = tempBoard.calculatePieces();
            short blackPieces = (short) piecesCount[0];
            short whitePieces = (short) piecesCount[1];
            if (this.color == 3) {
                if (blackPieces-whitePieces > difference) {
                    difference = (short)(blackPieces-whitePieces);
                    bestBoard = tempBoard;
                }
            } else {
                if (whitePieces-blackPieces > difference) {
                    difference = (short)(whitePieces-blackPieces);
                    bestBoard = tempBoard;
                }
            }
        }

        return bestBoard.lastMoves.get(bestBoard.lastMoves.size() - depth);
    
    }

    private ArrayList<Board> calculateNextBoards(int depth) {
        ArrayList<Board> nextBoards = new ArrayList<Board>();
        nextBoards.add(ReversiStart.currentGame);
        ArrayList<Short> validMovesList;

        for (int i = 0; i < depth; i++) {
            int size = nextBoards.size();
            for (int k = 0; k < size; k++) {
                Board currentBoard = nextBoards.remove(0);
                validMovesList = currentBoard.findValidMoves();
                if (validMovesList != null) {
                    for (int j = 0; j < validMovesList.size(); j++) {
                        Board temp = new Board(currentBoard);
                        temp.makeAMove(validMovesList.get(j), false);
                        nextBoards.add(temp);
                    }
                }
            }
        }
        return nextBoards;
    }
}