package KnightTravelProblem;

import java.util.BitSet;
import java.util.Map;

public class Node {
    private static int tileCount;
    private static int boardSize;

    private BitSet board;
    private int currentPosition;
    private Node parentNode;

    public static void start(int boardsize) {
        boardSize = boardsize;
        tileCount = boardSize * boardSize;
    }

    Node() {
        this.board = new BitSet(boardSize * boardSize);
        this.parentNode = null;

        setStartingPosition(0);
    }

    Node(BitSet board, int currentPosition, Node parent) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.parentNode = parent;
    }

    private void setStartingPosition(int startingPosition) {
        this.board.set(startingPosition);
        currentPosition = startingPosition;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public Node clone() {
        return new Node((BitSet) this.board.clone(), this.currentPosition, this.parentNode);
    }

    public boolean isDone() { // Returns true if all tiles are visited
        return board.cardinality() >= tileCount;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public String getCurrentCoordinate() {
        return convertPositionToCoordinate(this.currentPosition);
    }

    private int calculateNextPosition(int moveNumber) { // for using this method with currentposition
        return calculateNextPosition(moveNumber, currentPosition);
    }

    // Calculates the next position based on the move number (from paper) and
    // position
    private int calculateNextPosition(int moveNumber, int currentPositionTemp) { // for using this method with any
                                                                                 // position(used in h1b)
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

    public Node createNextNode(int moveNumber) { // Creates next board based on the move number

        int newPosition = calculateNextPosition(moveNumber);

        if (newPosition < 0 || board.get(newPosition) || newPosition >= tileCount) {
            return null;
        }
        Node newBoard = clone();
        newBoard.parentNode = this;
        newBoard.movePosition(newPosition);
        return newBoard;
    }

    public Map.Entry<Node, Integer> createNextNodeWithH1B(int moveNumber) { // Creates next board based on the move
                                                                              // number with h1b heuristic

        int newPosition = calculateNextPosition(moveNumber);

        int heuristicVal = calculateH1BHeuristic(newPosition);
        if (newPosition < 0 || board.get(newPosition) || newPosition >= tileCount ||
                (heuristicVal == 0 && board.cardinality() >= tileCount)) {
            return null;
        }
        Node newBoard = clone();
        newBoard.parentNode = this;
        newBoard.movePosition(newPosition);
        return Map.entry(newBoard, heuristicVal);
    }

    private int calculateH1BHeuristic(int newPosition) { // Calculates possible move count from the new position
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

    public double lengthFromCorner() { // Calculates the distance from the corner
        int row = this.currentPosition / boardSize + 1;
        int column = this.currentPosition % boardSize + 1;

        if ((row - 1) > boardSize - row)
            row = boardSize - row;
        else {
            row = row - 1;
        }

        if ((column - 1) > boardSize - column)
            column = boardSize - column;
        else {
            column = column - 1;
        }

        return column + row;
    }

    private String convertPositionToCoordinate(int position) { // Converts position to coordinate
        return ((position % boardSize)) + "-" + ((position / boardSize)) + " ";
    }

    private void movePosition(int newPosition) { // Moves the board to the new position
        currentPosition = newPosition;
        board.set(newPosition);
    }
}