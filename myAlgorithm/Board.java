package myAlgorithm;

import java.util.ArrayList;
import java.util.BitSet;

public class Board {
    private BitSet board;
    private int currentPosition;
    private ArrayList<String> movements;
    private int boardSize;

    Board(int boardSize) {
        this.board = new BitSet(boardSize * boardSize);
        currentPosition = 0;
        movements = new ArrayList<>();
        this.boardSize = boardSize;
    }

    Board(BitSet board, int currentPosition, ArrayList<String> movements, int boardSize) {
        this.board = board;
        this.currentPosition = currentPosition;
        this.movements = movements;
        this.boardSize = boardSize;
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
        return movements.size() >= boardSize * boardSize;
    }

    private int calculateMove(int moveNumber) {
        int returnVal;
        int row = moveNumber % boardSize;
        switch (moveNumber) {
            case 1:

                returnVal = row < 1 ? 0 : currentPosition - boardSize - 2;
                break;
            case 2:
                returnVal = row < 0 ? 0 : currentPosition - 2 * boardSize - 1;
                break;
            case 3:
                returnVal = row > 0 ? 0 : currentPosition - 2 * boardSize + 1;
                break;
            case 4:
                returnVal = row > 1 ? 0 : currentPosition - boardSize + 2;
                break;
            case 5:
                returnVal = row > 1 ? 0 : currentPosition + boardSize + 2;
                break;
            case 6:
                returnVal = row > 0 ? 0 : currentPosition + 2 * boardSize + 1;
                break;
            case 7:
                returnVal = row < 0 ? 0 : currentPosition + 2 * boardSize - 1;
                break;
            case 8:
                returnVal = row < 1 ? 0 : currentPosition + boardSize - 2;
                break;
            default:
                returnVal = 0;
        }
        return returnVal;

    }

    public Board moveBoardNew(int moveNumber) {

        Board newBoard = this.clone();
        moveNumber = calculateMove(moveNumber);

        if (moveNumber < 0 || board.get(moveNumber)) {
            return null;
        }
        newBoard.movePosition(moveNumber);
        return newBoard;
    }

    private void movePosition(int newPosition) {

        movements.add(movements.size(), "" + (char) ((int) 'a' + newPosition / boardSize) + (newPosition % boardSize));
        currentPosition = newPosition;
        board.set(newPosition);
    }

}
