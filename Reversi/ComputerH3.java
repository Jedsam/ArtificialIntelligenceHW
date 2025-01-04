package Reversi;

import java.util.ArrayList;

public class ComputerH3 extends ComputerPlayer{

    private static final int[][] WEIGHT_BOARD = {
        { 45, -15,  15,  15,  15,  15, -15,  45 },
        {-15, -15,  -3,  -3,  -3,  -3, -15, -15 },
        { 15,  -3,   3,   3,   3,   3,  -3,  15 },
        { 15,  -3,   3,   3,   3,   3,  -3,  15 },
        { 15,  -3,   3,   3,   3,   3,  -3,  15 },
        { 15,  -3,   3,   3,   3,   3,  -3,  15 },
        {-15, -15,  -3,  -3,  -3,  -3, -15, -15 },
        { 45, -15,  15,  15,  15,  15, -15,  45 }
    };
    
    ComputerH3(int color) {
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public int getInput() {

        ArrayList<Short> validMovesList = ReversiStart.currentGame.findValidMoves();
        int depth = ReversiStart.depth;
        int bestScore = Integer.MIN_VALUE;
        int bestMove = validMovesList.get(0);

        for (Short move : validMovesList) {
            Board tempBoard = new Board(ReversiStart.currentGame);
            tempBoard.makeAMove(move, false);
            int score = alphaBeta(tempBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    
    }

    private int alphaBeta(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || board.isGameOver()) {
            return calculateHeuristicValue(board);
        }

        ArrayList<Short> validMoves = board.findValidMoves();
        if (validMoves == null || validMoves.isEmpty()) {
            return alphaBeta(board, depth - 1, alpha, beta, !maximizingPlayer);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Short move : validMoves) {
                Board childBoard = new Board(board);
                childBoard.makeAMove(move, false);
                int eval = alphaBeta(childBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Pruning
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Short move : validMoves) {
                Board childBoard = new Board(board);
                childBoard.makeAMove(move, false);
                int eval = alphaBeta(childBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Pruning
                }
            }
            return minEval;
        }
    }

    private int calculateHeuristicValue(Board board) {
        int score = 0;
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int square = i*8 + j;
                if (board.getSquareFromBoard(square) == this.color) {
                    score += WEIGHT_BOARD[i][j];
                } else if ((board.getSquareFromBoard(square) != this.color) && (board.getSquareFromBoard(square) != 0)) {
                    score -= WEIGHT_BOARD[i][j];
                }
            }
        }
        int difference = calculateHeuristicValueH1(board);
        return score + difference;
    }

    public int calculateHeuristicValueH1(Board board) {
        short[] piecesCount = board.calculatePieceCounts();
        short blackPieces = piecesCount[0];
        short whitePieces = piecesCount[1];

        if (this.color == 3) {  // Computer is black
            return blackPieces - whitePieces;
        } else {    // Computer is white
            return whitePieces - blackPieces;
        }
    }
}
