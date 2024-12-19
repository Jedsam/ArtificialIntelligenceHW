package Reversi;

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

public class ReversiStart {
    private static final int INPUT_BUFFER_SIZE = 100;
    private static Queue<Integer> inputBuffer = new LinkedList<Integer>(); 
    
    public static void main(String[] args) {

        Board myBoard = new Board();
        ReversiGUI myGui = ReversiGUI.createGUI(myBoard);



    }


    public static void addToInputBuffer(int val){
        if(inputBuffer.size() <= INPUT_BUFFER_SIZE)
            inputBuffer.add(val);
        else{
            System.out.println("Input buffer full!");
        }
    }
    private static int readInputFromBuffer(){
        Integer returnVal = inputBuffer.poll();
        if(returnVal == null){
            return -1;
        }
        return returnVal.intValue();
    }
}
