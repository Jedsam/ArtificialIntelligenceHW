package Reversi;

import java.util.ArrayList;

public class ComputerH1 extends ComputerPlayer {

    ComputerH1(int colorBoard, Board game) {
        this.game = game;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
        this.moves = new ArrayList<Short>();
    }

    public int getInput() {

        ArrayList<Short> validMovesList = game.findValidMoves();
        int depth = ReversiStart.depth;
        int bestScore = Integer.MIN_VALUE;
        int bestMove = validMovesList.get(0);

        for (Short move : validMovesList) {
            Board tempBoard = new Board(game);
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
        int matchResult;
        if (board.isGameOver()) {
            matchResult = board.getMatchResult();
            if (matchResult == Board.EMPTY)
                return 0;
            else if (matchResult == this.color && maximizingPlayer)
                return Integer.MAX_VALUE;
            else {
                return Integer.MIN_VALUE;
            }
        }
        if (depth == 0) {
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
        short[] piecesCount = board.calculatePieceCounts();
        short blackPieces = piecesCount[0];
        short whitePieces = piecesCount[1];

        if (this.color == 3) { // Computer is black
            return blackPieces - whitePieces;
        } else { // Computer is white
            return whitePieces - blackPieces;
        }
    }

}