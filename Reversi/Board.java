package Reversi;

import java.util.ArrayList;
import java.util.BitSet;

public class Board {

    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    public static final int BLACK = 3;
    public static final int WHITE = 2;
    public static final int EMPTY = 0;
    public static final int BOARD_SIDE_LENGTH = 8;
    public static final int BOARD_SIZE = BOARD_SIDE_LENGTH * BOARD_SIDE_LENGTH;

    BitSet boardArray;
    public boolean currentTurn; // Black = true, White = false
    public ArrayList<Integer> lastMoves = new ArrayList<Integer>();

    Board() {
        // initialise the board
        boardArray = new BitSet(128);
        // prepare the lastMoves arraylist
        for (int i = 0; i < ReversiStart.depth; i++) {
            lastMoves.add(-1);
        }
        // The game starts with black
        currentTurn = true;

        // Starting board
        setSquareFromBoard(27, WHITE, true);
        setSquareFromBoard(28, BLACK, true);
        setSquareFromBoard(35, BLACK, true);
        setSquareFromBoard(36, WHITE, true);
    }

    Board(Board board) {
        this.boardArray = (BitSet) board.boardArray.clone();
        this.currentTurn = board.currentTurn;
        this.lastMoves = new ArrayList<>(board.lastMoves);
    }

    public boolean makeAMove(int index, boolean UIChange) {
        if (checkValidMove(index)) {
            setSquareFromBoard(index, currentTurn ? BLACK : WHITE, UIChange);
            flipSquares(index, UIChange);
            currentTurn = !currentTurn;
            lastMoves.remove(0);
            lastMoves.add(index);
            return true;
        }
        return false;
    }
    

    public void flipSquares(int index, boolean UIChange) {
        // Check for below squares
        if (checkPositionalAvailability(index + DOWN)) {
            if (checkLine(index, DOWN, true)) {
                flipLine(index, DOWN, true, UIChange);
            }
        }

        // Check for bottom squares
        if (checkPositionalAvailability(index + UP)) {
            if (checkLine(index, UP, true)) {
                flipLine(index, UP, true, UIChange);
            }
        }

        // check for right squares
        if (checkHorizontalOutOfBounds(index, RIGHT)) {
            if (checkLine(index, RIGHT, false)) {
                flipLine(index, RIGHT, false, UIChange);
            }
        }
        // check for left squares
        if (checkHorizontalOutOfBounds(index, LEFT)) {
            if (checkLine(index, LEFT, false)) {
                flipLine(index, LEFT, false, UIChange);
            }
        }

        // Check for corner squares

        // check for above right
        if (checkPositionalAvailability(index + RIGHT_UP)) {
            if (checkLine(index, RIGHT_UP, false)) {
                flipLine(index, RIGHT_UP, false, UIChange);
            }
        }

        // check for down right
        if (checkPositionalAvailability(index + RIGHT_DOWN)) {
            if (checkLine(index, RIGHT_DOWN, false)) {
                flipLine(index, RIGHT_DOWN, false, UIChange);
            }

        }

        // check for down left
        if (checkPositionalAvailability(index + LEFT_DOWN)) {
            if (checkLine(index, LEFT_DOWN, false)) {
                flipLine(index, LEFT_DOWN, false, UIChange);
            }
        }

        // check for above left
        if (checkPositionalAvailability(index + LEFT_UP)) {
            if (checkLine(index, LEFT_UP, false)) {
                flipLine(index, LEFT_UP, false, UIChange);
            }
        }
    }

    private void flipLine(int index, int increment, boolean dontCheckHorizontal, boolean UIChange) {
        int currentVal;
        int currentColor = currentTurn ? BLACK : WHITE;
        // checking for the square right next to the current piece

        // Check if it is out of bounds from the box
        while (checkPositionalAvailability(index + increment) && (dontCheckHorizontal ||
                checkHorizontalOutOfBounds(index, increment))) {
            index += increment;
            // If it is the different color from the current turns color then flip the color
            currentVal = getSquareFromBoard(index);
            if ((currentVal == BLACK && !currentTurn) || (currentVal == WHITE && currentTurn)) {
                setSquareFromBoard(index, currentColor, UIChange);
            } else
                return;
        }
    }

    public ArrayList<Short> findValidMoves() {
        ArrayList<Short> moves = new ArrayList<Short>();

        // Go through all the squares
        for (short i = 0; i < 64; i++) {
            if (checkValidMove(i)) {
                moves.add(i);
            }
        }

        if (moves.isEmpty()) {
            return null;
        }

        return moves;
    }

    // The board starts from top left and goes to bottom right
    private static final int UP = -BOARD_SIDE_LENGTH;
    private static final int DOWN = BOARD_SIDE_LENGTH;
    private static final int RIGHT = 1;
    private static final int LEFT = -1;
    private static final int RIGHT_UP = -(BOARD_SIDE_LENGTH - 1); // -7
    private static final int RIGHT_DOWN = (BOARD_SIDE_LENGTH + 1); // 9
    private static final int LEFT_UP = -(BOARD_SIDE_LENGTH + 1); // -9
    private static final int LEFT_DOWN = (BOARD_SIDE_LENGTH - 1); // 7

    private boolean checkValidMove(int index) {
        // Return false if the square is not empty
        if (getSquareFromBoard(index) != EMPTY) {
            return false;
        }

        // Check if any of the adjacent squares have a different color
        // If it does then run until it finds a square with different color, return
        // false if it doesnt find a different color

        // Check for down squares
        if (checkPositionalAvailability(index + DOWN)) {
            if (checkLine(index, DOWN, true)) {
                return true;
            }
        }

        // Check for up squares
        if (checkPositionalAvailability(index + UP)) {
            if (checkLine(index, UP, true)) {
                return true;
            }
        }

        // check for right squares
        if (checkHorizontalOutOfBounds(index, RIGHT)) {
            if (checkLine(index, RIGHT, false)) {
                return true;
            }
        }
        // check for left squares
        if (checkHorizontalOutOfBounds(index, LEFT)) {
            if (checkLine(index, LEFT, false)) {
                return true;
            }
        }

        // Check for corner squares

        // check for above right
        if (checkPositionalAvailability(index + RIGHT_UP)) {
            if (checkLine(index, RIGHT_UP, false)) {
                return true;
            }
        }

        // check for down right
        if (checkPositionalAvailability(index + RIGHT_DOWN)) {
            if (checkLine(index, RIGHT_DOWN, false)) {
                return true;
            }
        }

        // check for down left
        if (checkPositionalAvailability(index + LEFT_DOWN)) {
            if (checkLine(index, LEFT_DOWN, false)) {
                return true;
            }
        }

        // check for above left
        if (checkPositionalAvailability(index + LEFT_UP)) {
            if (checkLine(index, LEFT_UP, false)) {
                return true;
            }
        }

        return false;

    }

    // A check to see if an index reaches out of bounds horizontally
    // Use with values -1,1,-7,7,-9,9
    private boolean checkHorizontalOutOfBounds(int index, int increment) {
        int row = index % 8;
        if (row == 0) {
            return !((index + increment) % 8 == 7);
        }
        if (row == 7) {
            return !((index + increment) % 8 == 0);
        } else
            return true;
    }

    // Checks whether the given index is out of bounds for this board
    private boolean checkPositionalAvailability(int index) {
        return index < BOARD_SIZE && index >= 0;
    }

    // Checks the line given the increment if its vertical increment only then
    // dontCheckHorizontal is true
    private boolean checkLine(int index, int increment, boolean dontCheckHorizontal) {
        int currentVal;

        // checking for the square right next to the current piece
        if (checkPositionalAvailability(index + increment) && (dontCheckHorizontal ||
                checkHorizontalOutOfBounds(index, increment))) {
            index += increment;
        }
        currentVal = getSquareFromBoard(index);
        if (!((currentVal == BLACK && !currentTurn) || (currentVal == WHITE && currentTurn))) {
            return false;
        }

        // Check if it is out of bounds from the box
        while (checkPositionalAvailability(index + increment) && (dontCheckHorizontal ||
                checkHorizontalOutOfBounds(index, increment))) {
            index += increment;
            // If it is the different color from the current turns color then return true
            currentVal = getSquareFromBoard(index);
            if (currentVal == EMPTY) {
                return false;
            }
            if ((currentVal == BLACK && currentTurn) || (currentVal == WHITE && !currentTurn)) {
                return true;
            }
            // Otherwise search the next color
        }
        return false;
    }

    // Sets the board value given the color integer value
    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    public void setSquareFromBoard(int index, int value, boolean UIChange) {
        if (UIChange) ReversiStart.addPiece(index, value);
        if (value == BLACK) {
            // Black case
            boardArray.set(index * 2);
            boardArray.set(index * 2 + 1);
        } else if (value == WHITE) {
            // White case
            boardArray.set(index * 2);
            boardArray.clear(index * 2 + 1);
        } else {
            // Empty case
            boardArray.clear(index * 2);
            // No need to set the 2nd value
        }
    }

    // Gets the color integer value at the given index
    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    public int getSquareFromBoard(int index) {
        if (boardArray.get(index * 2)) {
            if (boardArray.get(index * 2 + 1)) {
                return BLACK;
            } else {
                return WHITE;
            }
        } else {
            // Empty case
            return EMPTY;
        }
    }

    public void startGame(Player player1, Player player2) {
        int skipCount = 0;
        int input;
        int moveCount = 0;
        ArrayList<Short> validMovesList;
        while (skipCount < 2 || moveCount >= 64) {
            Player CurrentPlayer = currentTurn ? player1 : player2;
            validMovesList = findValidMoves();
            if (validMovesList == null) {
                // skip turn if no valid moves found
                skipCount++;
                currentTurn = !currentTurn;
                continue;
            }
            
            moveCount++;
            ReversiStart.addValidMoves(validMovesList);
           /*  try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            skipCount = 0;
            input = CurrentPlayer.getInput();
            while (!checkPositionalAvailability(input)) {
                // Exit number
                if (input == ReversiStart.EXIT_CODE) {
                    return;
                }
                input = CurrentPlayer.getInput();
            }
            ReversiStart.removeValidMoves(validMovesList);
            makeAMove(input, true);
            
        }
        int matchResult = getMatchResult();
        String printMessage;
        if (matchResult == BLACK) {
            printMessage = player1.getVictoryMessage();
        } else if (matchResult == WHITE) {
            printMessage = player2.getVictoryMessage();
        } else {
            printMessage = "It is a draw!";
        }

        ReversiStart.setMessage(printMessage);
        ComputerPlayer.ComputerCounter = 1;
        // Wait for return back to main menu message
        input = ReversiStart.readInputFromBuffer();
        while (input != ReversiStart.EXIT_CODE) {
            input = ReversiStart.readInputFromBuffer();
        }

    }

    public boolean isGameOver() {
        // Check if there are valid moves for the current player
        ArrayList<Short> currentValidMoves = findValidMoves();
        if (currentValidMoves != null && !currentValidMoves.isEmpty()) {
            return false;
        }
    
        // Temporarily switch turn to check the other player's valid moves
        currentTurn = !currentTurn;
        ArrayList<Short> otherValidMoves = findValidMoves();
        currentTurn = !currentTurn; // Restore the turn
    
        // If neither player has valid moves, the game is over
        return otherValidMoves == null || otherValidMoves.isEmpty();
    }
    

    private int getMatchResult() {
        int blackPieces = 0;
        int whitePieces = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            int currentPiece = getSquareFromBoard(i);
            if (currentPiece == BLACK) {
                blackPieces++;
            } else if (currentPiece == WHITE) {
                whitePieces++;
            }
        }
        if (blackPieces > whitePieces) {
            return BLACK;
        } else if (blackPieces < whitePieces) {
            return WHITE;
        } else {
            return EMPTY; // Draw case
        }

    }

    public short[] calculatePieces() {
        short blackPieces = 0;
        short whitePieces = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            int currentPiece = getSquareFromBoard(i);
            if (currentPiece == BLACK) {
                blackPieces++;
            } else if (currentPiece == WHITE) {
                whitePieces++;
            }
        }
        return new short[] { blackPieces, whitePieces };
    }

}
