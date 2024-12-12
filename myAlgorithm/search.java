package myAlgorithm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class search {

    public static long openedNodes = 1;

    public static void main(String[] args) {

        

        PrintWriter writer;
        try {
            writer = new PrintWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;

        } 
        // Taking input from user
        System.out.println("Enter the board size: ");
        Scanner input = new Scanner(System.in);
        int boardSize = input.nextInt();
        System.out.println("Enter the method(a=BFS, b=DFS, c=h1b, d=h2): ");
        char method = input.next().charAt(0);
        System.out.println("Enter the time limit in minutes: ");
        int timeLimit = input.nextInt();

        long start_time = System.currentTimeMillis();
        ArrayList<String> result;

        result = startSearch(boardSize, String.valueOf(method), timeLimit);

        long end_time = System.currentTimeMillis();
        if (result != null)
            Collections.reverse(result);
        System.out.println("Run time of the algorithm " + (end_time - start_time) + "miliseconds");
        if (result == null) {
            System.out.println("No result found");
        } else {
            System.out.println("A solution found.");
            for (String string : result) {
                writer.println(string);
            }
            System.out.println("Opened nodes: " + openedNodes);
        }
        // 8, 16, 32, 41, 52 sizes
        input.close();
        writer.close();
    }

    private static ArrayList<String> startSearch(int boardSize, String method, int timeLimit) {
        Board.start(boardSize); // set up variables
        Board board = new Board(boardSize);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<ArrayList<String>> future = executor.submit(() -> {
            try {
                System.out.println("Function started");
                return startSearchBoard(board, method);
            } finally {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Function was interrupted");
                }
            }
        });

        try {
            // Wait for the function to complete, or timeout after the specified time limit
            return future.get(timeLimit, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            System.out.println("Timeout.");
            future.cancel(true); // Cancel the task
            return null; // Return null if timeout occurs
        } catch (OutOfMemoryError e) {
            System.out.println("Out of Memory");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdownNow(); // Clean up the executor
        }
    }

    private static ArrayList<String> startSearchBoard(Board board, String method) {
        MyQueue myQueue;
        if (method.equals("a")) {
            Queue<Board> tempQueue = new LinkedList<Board>();
            myQueue = (MyQueue) (new BFSQueue(tempQueue));
        } else if (method.equals("b")) {
            Stack<Board> tempStack = new Stack<Board>();
            myQueue = (MyQueue) (new DFSStack(tempStack));
        } else if (method.equals("c")) {
            Stack<Board> tempStack = new Stack<Board>();
            myQueue = (MyQueue) (new H1BStack(tempStack));
        } else {
            Stack<Board> tempStack = new Stack<Board>();
            myQueue = (MyQueue) (new H2Stack(tempStack));
        }
        myQueue.add(board);
        return SearchBoard(myQueue);
    }

    private static ArrayList<String> SearchBoard(MyQueue myBoardQueue) {
        Board currentBoard;
        while (!myBoardQueue.isEmpty()) {

            currentBoard = myBoardQueue.get();

            if (currentBoard.isDone()) {
                ArrayList<String> result = new ArrayList<>();
                while (currentBoard != null) {
                    result.add(currentBoard.getCurrentCoordinate());
                    currentBoard = currentBoard.getParentBoard();
                }
                return result;
            }
            myBoardQueue.calculateNextBoards(currentBoard);

        }
        return null;
    }

    protected class InterruptTimerTask extends TimerTask {

        private Thread theTread;

        public InterruptTimerTask(Thread theTread) {
            this.theTread = theTread;
        }

        @Override
        public void run() {
            theTread.interrupt();
        }
    }
}
