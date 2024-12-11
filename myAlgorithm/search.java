package myAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class search {

    static long openedNodes = 1;

    public static void main(String[] args) {

        long start_time = System.currentTimeMillis();

        /*
         * Scanner scanner = new Scanner(System.in); // For taking input from user
         * System.out.println("Enter the board size: "); // Commented out for easy
         * testing
         * int boardSize = scanner.nextInt();
         * System.out.println("Enter the method(a=BFS, b=DFS, c=h1b, d=h2b): ");
         * char method = scanner.next().charAt(0);
         * System.out.println("Enter the time limit in minutes: ");
         * int timeLimit = scanner.nextInt();
         * 
         * ArrayList<String> result = searchBoard(boardSize, String.valueOf(method),
         * timeLimit);
         */

        ArrayList<String> result = startSearch(8, "b", 15);

        long end_time = System.currentTimeMillis();
        Collections.reverse(result);
        System.out.println("Run time of the algorithm " + (end_time - start_time) + "miliseconds");
        if (result == null) {
            System.out.println("No result found");
        } else {
            for (String string : result) {
                System.out.print(string);
            }
            System.out.println("\nOpened nodes: " + openedNodes);
        }
        // 8, 16, 32, 41, 52 sizes

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
            System.out.println("Timeout reached, canceling function");
            future.cancel(true); // Cancel the task
            return null; // Return null if timeout occurs
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null for other exceptions
        } finally {
            executor.shutdownNow(); // Clean up the executor
        }
    }

    private static ArrayList<String> startSearchBoard(Board board, String method) {
        MyQueue myQueue;
        if (method.equals("a")) {
            Queue<Board> tempQueue = new LinkedList<Board>();
            myQueue = (MyQueue) (new BFSQueue(tempQueue));
        } else {
            Stack<Board> tempStack = new Stack<Board>();
            myQueue = (MyQueue) (new DFSQueue(tempStack));
        }
        myQueue.add(board);
        return SearchBoard(myQueue);
    }

    private static ArrayList<String> SearchBoard(MyQueue collection) {
        Board currentBoard;
        Board tempBoard;

        while (!collection.isEmpty()) {

            currentBoard = collection.get();

            if (currentBoard.isDone()) {
                ArrayList<String> result = new ArrayList<>();
                while (currentBoard != null) {
                    result.add(currentBoard.getCurrentCoordinate());
                    currentBoard = currentBoard.getParentBoard();
                }
                return result;
            }

            for (int i = 1; i < 9; i++) {
                tempBoard = currentBoard.moveBoardNew(i);
                if (tempBoard != null) {

                    collection.add(tempBoard);
                    openedNodes++;
                }
            }
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
