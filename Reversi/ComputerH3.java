package Reversi;

import java.util.ArrayList;

public class ComputerH3 extends ComputerPlayer {

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
    
    ComputerH3(int color, Board game) {
        this.color = color;
        this.game = game;
        this.name = "Computer" + ComputerCounter;
        ComputerCounter++;
    }

    public int getInput() {

        updateWeightBoard(WEIGHT_BOARD, game);

        ArrayList<Short> validMovesList = game.findValidMoves();
        int depth = ReversiStart.depth;
        int bestScore = Integer.MIN_VALUE;
        int bestMove = validMovesList.get(0);

        for (Short move : validMovesList) {
            Board tempBoard = new Board(game);
            int[][] weightClone = new int[8][8];
                for (int i = 0; i < 8; i++) {
                weightClone[i] = WEIGHT_BOARD[i].clone();
            }
            tempBoard.makeAMove(move, false);
            updateWeightBoard(weightClone, tempBoard);
            int score = alphaBeta(tempBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false, weightClone);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        ReversiStart.updateLastMove(bestMove + "");
        return bestMove;

    }

    private int alphaBeta(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, int[][] WEIGHT_BOARD) {
        int matchResult;
        updateWeightBoard(WEIGHT_BOARD, board);
        if (board.isGameOver()) {
            matchResult = board.getMatchResult();
            if (matchResult == Board.EMPTY)
                return 0;
            else if (matchResult == this.color ^ maximizingPlayer)
                return Integer.MIN_VALUE;
            else {
                return Integer.MAX_VALUE;
            }
        }

        if (depth == 0) {
            return calculateHeuristicValue(board);
        }

        ArrayList<Short> validMoves = board.findValidMoves();
        if (validMoves == null || validMoves.isEmpty()) {
            return alphaBeta(board, depth - 1, alpha, beta, !maximizingPlayer, WEIGHT_BOARD);
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Short move : validMoves) {
                Board childBoard = new Board(board);
                childBoard.makeAMove(move, false);
                int eval = alphaBeta(childBoard, depth - 1, alpha, beta, false, WEIGHT_BOARD);
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
                int eval = alphaBeta(childBoard, depth - 1, alpha, beta, true, WEIGHT_BOARD);
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
        int mobility = calculateHeuiristicMobility(board);
        return score + (difference + mobility)/4;
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

    private int calculateHeuiristicMobility(Board board) {
        ArrayList<Short> temp = board.findValidMoves();
        if (temp == null) {
            return 0;
        } else
            return temp.size();
    }

    private void updateWeightBoard(int[][] WEIGHT_BOARD, Board tempGame) {

        if (tempGame.getSquareFromBoard(0) != 0) {
            WEIGHT_BOARD[0][1] = WEIGHT_BOARD[0][2]; WEIGHT_BOARD[1][0] = WEIGHT_BOARD[2][0]; WEIGHT_BOARD[1][1] = WEIGHT_BOARD[2][0];
        }
        if (tempGame.getSquareFromBoard(7) != 0) {
            WEIGHT_BOARD[0][6] = WEIGHT_BOARD[0][5]; WEIGHT_BOARD[1][7] = WEIGHT_BOARD[2][7]; WEIGHT_BOARD[2][6] = WEIGHT_BOARD[2][7];
        }
        if (tempGame.getSquareFromBoard(56) != 0) {
            WEIGHT_BOARD[6][0] = WEIGHT_BOARD[5][0]; WEIGHT_BOARD[7][1] = WEIGHT_BOARD[7][2]; WEIGHT_BOARD[6][1] = WEIGHT_BOARD[7][2];
        }
        if (tempGame.getSquareFromBoard(63) != 0) {
            WEIGHT_BOARD[6][7] = WEIGHT_BOARD[5][7]; WEIGHT_BOARD[7][6] = WEIGHT_BOARD[7][5]; WEIGHT_BOARD[6][6] = WEIGHT_BOARD[7][5];
        }
    }
}
