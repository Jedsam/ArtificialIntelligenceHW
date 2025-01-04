package Reversi;

import java.util.ArrayList;

public class ComputerH3Train1 extends ComputerPlayer {

    public static int[][] WEIGHT_BOARD = {
            { 245, -76, 78, 50, 50, 78, -76, 245 },
            { -84, -104, -12, 4, 4, -12, -104, -84 },
            { 72, -2, 24, 10, 10, 24, -2, 72 },
            { 68, -28, 10, 12, 12, 10, -28, 68 },
            { 68, -28, 10, 12, 12, 10, -28, 68 },
            { 72, -2, 24, 10, 10, 24, -2, 72 },
            { -84, -104, -12, 4, 4, -12, -104, -84 },
            { 245, -76, 78, 50, 50, 78, -76, 245 },

    };
    private Board game;
    public ArrayList<Short> moves;

    ComputerH3Train1(int color, Board game) {
        this.game = game;
        this.color = color;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public void prepareForNewGame(Board game) {
        this.game = game;
        moves = new ArrayList<Short>();
    }

    public int getInput() {

        ArrayList<Short> validMovesList = game.findValidMoves();
        int depth = ReversiTrainH3.depth;
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
                int square = i * 8 + j;
                if (board.getSquareFromBoard(square) == this.color) {
                    score += WEIGHT_BOARD[i][j];
                } else if ((board.getSquareFromBoard(square) != this.color)
                        && (board.getSquareFromBoard(square) != 0)) {
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

        if (this.color == 3) { // Computer is black
            return blackPieces - whitePieces;
        } else { // Computer is white
            return whitePieces - blackPieces;
        }
    }

    public void UpdateTable(int matchResult) {
        int increment;
        if (color == matchResult) {
            increment = 1;
        } else {
            increment = -1;
        }
        for (short currentMove : moves) {
            int i = currentMove / 8;
            int j = currentMove % 8;
            WEIGHT_BOARD[i][j] += increment;
        }

    }

    public void UpdateTable(int matchResult, ArrayList<Short> movesList) {
        int increment;
        if (color == matchResult) {
            increment = 1;
        } else {
            increment = -1;
        }
        for (short currentMove : movesList) {
            int i = currentMove / 8;
            int j = currentMove % 8;
            WEIGHT_BOARD[i][j] += increment;
        }

    }

}
