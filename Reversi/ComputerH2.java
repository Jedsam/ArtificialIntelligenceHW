package Reversi;

import java.util.ArrayList;

public class ComputerH2 extends ComputerPlayer {

    private int[][] WEIGHT_BOARD = {
        { 45, -10,  10,  10,  10,  10, -10,  45 },
        {-10, -10,  -3,  -3,  -3,  -3, -10, -10 },
        { 10,  -3,   3,   3,   3,   3,  -3,  10 },
        { 10,  -3,   3,   3,   3,   3,  -3,  10 },
        { 10,  -3,   3,   3,   3,   3,  -3,  10 },
        { 10,  -3,   3,   3,   3,   3,  -3,  10 },
        {-10, -10,  -3,  -3,  -3,  -3, -10, -10 },
        { 45, -10,  10,  10,  10,  10, -10,  45 }
    };

    ComputerH2(int color, Board game) {
        this.game = game;
        this.color = color;
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
        moves.add((short) bestMove);
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
        int score = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int square = i * 8 + j;
                if (board.getSquareFromBoard(square) == this.color) {
                    score += WEIGHT_BOARD[i][j];
                } else if ((board.getSquareFromBoard(square) != this.color)
                        && (board.getSquareFromBoard(square) != 0)) {
                    score -= WEIGHT_BOARD[i][j];
                }
            }
        }

        return score;
    }
}
