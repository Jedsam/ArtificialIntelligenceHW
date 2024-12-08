package myAlgorithm;

import java.util.ArrayList;
import java.util.BitSet;

public class Board {
    private static int tileCount;
    private static int lastDepth = 0;
    private BitSet board;
    private int currentPosition;
    private ArrayList<String> movements;
    private int boardSize;

    public static void start(int boardSize) {
        tileCount = boardSize * boardSize;
    }

    Board(int boardSize) {
        this.board = new BitSet(boardSize * boardSize);
        movements = new ArrayList<>();
        this.boardSize = boardSize;

        setStartingPosition(0);
    }

    Board(BitSet board, int currentPosition, ArrayList<String> movements, int boardSize) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.movements = movements;
        this.boardSize = boardSize;
    }

    private void setStartingPosition(int statingPosition) {

        this.board.set(statingPosition);
        currentPosition = statingPosition;
        movements.add(0, convertPositionToCoordinate(statingPosition));

    }

    public ArrayList<String> getMovements() {
        return movements;
    }

    @SuppressWarnings("unchecked")
    public Board clone() {
        return new Board((BitSet) this.board.clone(), this.currentPosition, (ArrayList<String>) this.movements.clone(),
                this.boardSize);
    }

    public boolean isDone() {
        int size = movements.size();
        boolean result = size >= tileCount;
        if (size > lastDepth) {
            System.out.println(movements.size());
            lastDepth = size;
        }
        return result;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    private int calculateMove(int moveNumber) {
        int returnVal;
        int column = (currentPosition % boardSize) + 1; // starting from 1
        switch (moveNumber) {
            case 1:
                returnVal = (column < 3) ? -1 : (currentPosition + boardSize) - 2;
                break;
            case 2:
                returnVal = (column < 2) ? -1 : (currentPosition + 2 * boardSize) - 1;
                break;
            case 3:
                returnVal = ((boardSize - column) < 1) ? -1 : (currentPosition + 2 * boardSize) + 1;
                break;
            case 4:
                returnVal = ((boardSize - column) < 2) ? -1 : (currentPosition + boardSize) + 2;
                break;
            case 5:
                returnVal = ((boardSize - column) < 2) ? -1 : (currentPosition - boardSize) + 2;
                break;
            case 6:
                returnVal = ((boardSize - column) < 1) ? -1 : (currentPosition - 2 * boardSize) + 1;
                break;
            case 7:
                returnVal = (column < 2) ? -1 : (currentPosition - 2 * boardSize) - 1;
                break;
            case 8:
                returnVal = (column < 3) ? -1 : (currentPosition - boardSize) - 2;
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

    public boolean unMoveBoard(int previousPosition) {
        if (previousPosition < 0 || !board.get(previousPosition) || previousPosition >= tileCount) {
            return false;
        }
        board.clear(currentPosition);
        movements.remove(movements.size() - 1);
        movements.trimToSize();
        currentPosition = previousPosition;
        return true;
    }

    public Board moveBoardNew(int moveNumber) {

        Board newBoard = this.clone();
        moveNumber = calculateMove(moveNumber);

        if (moveNumber < 0 || board.get(moveNumber) || moveNumber >= tileCount) {
            return null;
        }
        newBoard.movePosition(moveNumber);
        return newBoard;
    }

    private String convertPositionToCoordinate(int position) {
        return "" + (char) ((int) 'a' + position % boardSize) + (1 + (position / boardSize)) + " ";
    }

    private void movePosition(int newPosition) {

        movements.add(movements.size(), convertPositionToCoordinate(newPosition));
        currentPosition = newPosition;
        board.set(newPosition);
    }
}