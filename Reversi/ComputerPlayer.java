package Reversi;

import java.util.ArrayList;

public abstract class ComputerPlayer extends Player {
    public static int ComputerCounter = 1;

    @Override
    public abstract int getInput(); /*{
        ArrayList<Board> nextBoards = new ArrayList<Board>();
        for (int i = 0; i < validMovesList.size(); i++) {
            Board temp = new Board(ReversiStart.currentGame);
            
            temp.boardArray.set(2 * validMovesList.get(i));
            if (temp.currentTurn == true) temp.boardArray.set((2 * validMovesList.get(i)) + 1);
            temp.lastMove = validMovesList.get(i);
            nextBoards.add(temp);
        }
        
        Board bestBoard = nextBoards.get(0);
        short difference = -100;
        for (int i = 0; i < nextBoards.size(); i++) {
            Board tempBoard = nextBoards.get(i);
            short[] piecesCount = tempBoard.calculatePieces();
            short blackPieces = (short) piecesCount[0];
            short whitePieces = (short) piecesCount[1];
            if (tempBoard.currentTurn == true) {
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
        bestBoard.boardArray.clear(2 * bestBoard.lastMove);
        bestBoard.boardArray.clear((2 * bestBoard.lastMove) + 1);
        return bestBoard.lastMove;
    }*/

}
