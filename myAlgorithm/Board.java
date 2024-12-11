package myAlgorithm;

import java.util.BitSet;
import java.util.Map;

public class Board {
    private static int tileCount;
    private static int boardSize;

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
            // System.out.print(size + " ");
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

    private int calculateNextPosition(int moveNumber) {
        return calculateNextPosition(moveNumber, currentPosition);
    }

    private int calculateNextPosition(int moveNumber, int currentPositionTemp) {
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

    public Board createNextBoard(int moveNumber) {

        moveNumber = calculateNextPosition(moveNumber);

        if (moveNumber < 0 || board.get(moveNumber) || moveNumber >= tileCount) {
            return null;
        }
        Board newBoard = clone();
        newBoard.parentBoard = this;
        newBoard.movePosition(moveNumber);
        return newBoard;
    }

    public Map.Entry<Board, Integer> createNextBoardH(int moveNumber) {

        int newPosition = calculateNextPosition(moveNumber);

        int heuristicVal = calculateH1BHeuristic(newPosition);
        if (newPosition < 0 || board.get(newPosition) || newPosition >= tileCount ||
                (heuristicVal == 0 && board.cardinality() > tileCount)) {
            return null;
        }
        Board newBoard = clone();
        newBoard.parentBoard = this;
        newBoard.movePosition(newPosition);
        return Map.entry(newBoard, heuristicVal);
    }

    private int calculateH1BHeuristic(int newPosition) {
        int heuristic = 0;
        int calcPosition;
        for (int i = 1; i < 9; i++) {
            calcPosition = calculateNextPosition(i, newPosition);
            if (calcPosition < 0 || board.get(calcPosition) || calcPosition >= tileCount) {
                continue;
            }
            heuristic++;
        }
        return heuristic;
    }

    // Returns 1 for true -1 for false
    public int h2Compare(Board board) {
        if (this.lengthFromCorner() > board.lengthFromCorner())
            return 1;
        else
            return -1;

    }

    private double lengthFromCorner() {
        int row = this.currentPosition / boardSize + 1;
        int column = this.currentPosition % boardSize + 1;

        if (row >= boardSize / 2)
            row = boardSize - row;
        if (column >= boardSize / 2)
            column = boardSize - column;

        return Math.sqrt(column * column + row * row);
    }

    private String convertPositionToCoordinate(int position) {
        return "" + (1 + position % boardSize) + "-" + (1 + (position / boardSize)) + " ";
    }

    private void movePosition(int newPosition) {
        currentPosition = newPosition;
        board.set(newPosition);
    }
}