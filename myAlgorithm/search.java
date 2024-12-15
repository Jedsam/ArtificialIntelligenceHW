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

    public static long openedNodes = 0;

    public static void main(String[] args) {

        PrintWriter writer;
        try {
            writer = new PrintWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;

        }
        // Taking input from user
        System.out.print("Enter the board size: ");
        Scanner input = new Scanner(System.in);
        int boardSize = input.nextInt();
        System.out.print("Enter the method(a=BFS, b=DFS, c=h1b, d=h2): ");
        char method = input.next().charAt(0);
        System.out.print("Enter the time limit in minutes: ");
        int timeLimit = input.nextInt();
        System.out.print("\n");

        long start_time = System.currentTimeMillis();
        ArrayList<String> result;

        result = startSearch(boardSize, String.valueOf(method), timeLimit);

        long end_time = System.currentTimeMillis();
        if (result != null)
            Collections.reverse(result);
        System.out.println("Run time of the algorithm: " + (end_time - start_time) + " miliseconds");
        if (result == null) {
            System.out.println("No result found");
        } else {
            System.out.println("A solution found.");
            for (String string : result) {
                writer.println(string);
            }
            System.out.println("Opened nodes: " + openedNodes);
        }
        input.close();
        writer.close();
    }

    private static ArrayList<String> startSearch(int boardSize, String method, int timeLimit) {
        Board.start(boardSize); // set up static variables
        Board board = new Board();

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
            return future.get(timeLimit, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("Timeout.");
            future.cancel(true); // Cancel the task
            return null; // Return null if timeout occurs
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {

            Throwable errorType = e.getCause();
            if (errorType instanceof OutOfMemoryError) {
                System.out.println("Out of Memory");
                return null;
            }
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdownNow(); // Clean up the executor
        }
    }

    private static ArrayList<String> startSearchBoard(Board board, String method) {
        MyQueue frontier; // a class to use queue or stack with polymorphism
        if (method.equals("a")) { // BFS
            Queue<Board> tempQueue = new LinkedList<Board>();
            frontier = (MyQueue) (new BFSQueue(tempQueue));
        } else if (method.equals("b")) { // DFS
            Stack<Board> tempStack = new Stack<Board>();
            frontier = (MyQueue) (new DFSStack(tempStack));
        } else if (method.equals("c")) { // h1b
            Stack<Board> tempStack = new Stack<Board>();
            frontier = (MyQueue) (new H1BStack(tempStack));
        } else { // h2
            Stack<Board> tempStack = new Stack<Board>();
            frontier = (MyQueue) (new H2Stack(tempStack));
        }
        frontier.add(board); // add the initial board to the queue
        return SearchBoard(frontier);
    }

    private static ArrayList<String> SearchBoard(MyQueue frontier) {
        Board currentBoard;
        while (!frontier.isEmpty()) {

            currentBoard = frontier.get();

            if (currentBoard.isDone()) {
                ArrayList<String> result = new ArrayList<>();
                while (currentBoard != null) {
                    result.add(currentBoard.getCurrentCoordinate());
                    currentBoard = currentBoard.getParentBoard();
                }
                return result;
            }
            frontier.calculateNextBoards(currentBoard);

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
