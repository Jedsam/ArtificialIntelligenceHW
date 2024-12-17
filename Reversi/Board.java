package Reversi;

import java.util.ArrayList;
import java.util.BitSet;




public class Board {

    
    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    public static final int BLACK = 3;
    public static final int WHITE = 2;
    public static final int EMPTY = 0; 
    public static final int BOARD_SIDE_LENGTH = 8;
    public static final int BOARD_SIZE = 64;
    BitSet boardArray;
    boolean currentTurn; // Black = true, White = false
    Board() {
        // initialise the board
        boardArray = new BitSet(128);
        // The game starts with black
        currentTurn = true;

        // Starting board
        setSquareOfBoard(27, 1);
        setSquareOfBoard(28, 2);
        setSquareOfBoard(35, 2);
        setSquareOfBoard(36, 1);
    }
    public boolean makeAMove(int index){
        if(checkValidMove(index)){
            setSquareOfBoard(index, currentTurn ? BLACK : WHITE);
            currentTurn = !currentTurn;
            return true;
        }
        return false;
    }
    public ArrayList<Integer> findValidMoves(){
        ArrayList<Integer> moves = new ArrayList<Integer>();


        // Go through all the squares
        for(int i = 0; i < 64; i++){
            if(checkValidMove(i)){
                moves.add(i);
            }
        }

        if(moves.isEmpty()){
            // Skip turn
            currentTurn = !currentTurn;
            return null;
        }
        return moves;
    }

    private boolean checkValidMove(int index){
        // Return false if the square is not empty
        if(getSquareOfBoard(index) != EMPTY){
            return false;
        }

        // Check if any of the adjacent squares have a different color 
        // If it does then run until it finds a square with different color, return false if it doesnt find a different color
        if(checkVerticalAvailability(index, true)){
            if(checkLine(index + 8, 8, true)){
                return true;
            }
        }
        // Check for bottom squares
        if(checkVerticalAvailability(index, false)){
            if(checkLine(index - 8, -8, true)){
                return true;
            }
        }
        // check for right squares
        if(checkHorizontalAvailability(index, true)){
            if(checkLine(index + 1, 1, false)){
                return true;
            }
        }
        // check for left squares
        if(checkHorizontalAvailability(index, false)){
            if(checkLine(index - 1, -1, false)){
                return true;
            }
        }
        return false;
        
    }
    private boolean checkHorizontalAvailability(int index, boolean isRight){
        int column = index % BOARD_SIDE_LENGTH;
        if(isRight){
            return column < 7; // False on 7 and above
        }
        else{
            return column > 0; // True for 0 and below
        }
    }
    private boolean checkVerticalAvailability(int index, boolean isUp){
        int row = index / BOARD_SIDE_LENGTH;
        if(isUp){
            return row < 7; // False on 7 and above
        }
        else{
            return row > 0; // True for 0 and below
        }
    }
    private boolean checkLine(int index, int increment, boolean isVertical){
        index += increment;
        boolean isRightOrUp = increment > 0;

        while((isVertical && checkVerticalAvailability(index, isRightOrUp)) 
        || (!isVertical && checkHorizontalAvailability(index, isRightOrUp))){

            // If it is the different color from the current turns color then return true 
            if(getSquareOfBoard(index) == BLACK ^ currentTurn){
                return true;
            }
            // Otherwise search the next color
            index += increment;
        }
        return false;
    }
    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    private void setSquareOfBoard(int index, int value) {

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
            boardArray.set(index * 2 + 1);
        }
    }
    // 01,00 (1,0)= empty, 10 (2) = white, 11 (3)= black
    private int getSquareOfBoard(int index){
        if(boardArray.get(index * 2)){
            if(boardArray.get(index * 2 + 1)){
                return BLACK;
            }
            else{
                return WHITE;
            }
        }
        else {
            // Empty case
            return EMPTY;
        }
    }
}

