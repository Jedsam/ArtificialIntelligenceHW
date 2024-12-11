package myAlgorithm;

import java.util.BitSet;

public class Board {
    private static int tileCount;
    private static int boardSize;

    public int heuristicVal;
    public static long openedNodes = 1;
    public static int lastDepth = 0;

    private BitSet board;
    private int currentPosition;
    private Board parentBoard;

    public static void start(int boardSize) {
        tileCount = boardSize * boardSize;
    }

    Board(int boardsize) {
        this.board = new BitSet(boardsize * boardsize);
        this.parentBoard = null;
        boardSize = boardsize;

        setStartingPosition(0);
    }

    Board(BitSet board, int currentPosition, Board parent) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.parentBoard = parent;
    }

    private void setStartingPosition(int statingPosition) {
        this.board.set(statingPosition);
        currentPosition = statingPosition;
    }

    public Board getParentBoard() {
        return parentBoard;
    }

    public Board clone() {
        return new Board((BitSet) this.board.clone(), this.currentPosition, this.parentBoard);
    }

    public boolean isDone() {
        int size = board.cardinality();
        boolean result = size >= tileCount;
        if (size > lastDepth) {
            System.out.print(size + " ");
            lastDepth = size;
        }
        return result;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public String getCurrentCoordinate() {
        return convertPositionToCoordinate(this.currentPosition);
    }

    private int calculateMove(int moveNumber) {
        return calculateMove(moveNumber, currentPosition);
    }

    private int calculateMove(int moveNumber, int currentPositionTemp) {
        int returnVal;
        int column = (currentPositionTemp % boardSize) + 1; // starting from 1
        switch (moveNumber) {
            case 1:
                returnVal = (column < 3) ? -1 : (currentPositionTemp + boardSize) - 2;
                break;
            case 2:
                returnVal = (column < 2) ? -1 : (currentPositionTemp + 2 * boardSize) - 1;
                break;
            case 3:
                returnVal = ((boardSize - column) < 1) ? -1 : (currentPositionTemp + 2 * boardSize) + 1;
                break;
            case 4:
                returnVal = ((boardSize - column) < 2) ? -1 : (currentPositionTemp + boardSize) + 2;
                break;
            case 5:
                returnVal = ((boardSize - column) < 2) ? -1 : (currentPositionTemp - boardSize) + 2;
                break;
            case 6:
                returnVal = ((boardSize - column) < 1) ? -1 : (currentPositionTemp - 2 * boardSize) + 1;
                break;
            case 7:
                returnVal = (column < 2) ? -1 : (currentPositionTemp - 2 * boardSize) - 1;
                break;
            case 8:
                returnVal = (column < 3) ? -1 : (currentPositionTemp - boardSize) - 2;
                break;

            default:
                returnVal = -1;
        }
        return returnVal;

    }

    public boolean moveBoard(int moveNumber) {

        moveNumber = calculateMove(moveNumber);

        if (moveNumber < 0 || board.get(moveNumber) || moveNumber >= tileCount) {
            return false;
        }
        movePosition(moveNumber);
        return true;
    }

    public Board moveBoardNew(int moveNumber) {

        moveNumber = calculateMove(moveNumber);

        if (moveNumber < 0 || board.get(moveNumber) || moveNumber >= tileCount) {
            return null;
        }
        Board newBoard = clone();
        newBoard.parentBoard = this;
        newBoard.movePosition(moveNumber);
        return newBoard;
    }

    public Board moveBoardNewWithH1B(int moveNumber) {

        int newPosition = calculateMove(moveNumber);

        int heuristicValNew = calculateH1B(newPosition);
        if (moveNumber < 0 || board.get(moveNumber) || moveNumber >= tileCount ||
                (heuristicValNew == 0 && board.cardinality() > tileCount)) {
            return null;
        }
        Board newBoard = clone();
        newBoard.heuristicVal = heuristicValNew;
        newBoard.parentBoard = this;
        newBoard.movePosition(moveNumber);
        return newBoard;
    }

    private int calculateH1B(int newPosition) {
        int heuristic = 0;
        int calcPosition;
        for (int i = 1; i < 9; i++) {
            calcPosition = calculateMove(i, newPosition);
            if (calcPosition < 0 || board.get(calcPosition) || calcPosition >= tileCount) {
                continue;
            }
            heuristic++;
        }
        return heuristic;
    }

    private String convertPositionToCoordinate(int position) {
        return "" + (char) ((int) 'a' + position % boardSize) + (1 + (position / boardSize)) + " ";
    }

    private void movePosition(int newPosition) {
        currentPosition = newPosition;
        board.set(newPosition);
    }
}