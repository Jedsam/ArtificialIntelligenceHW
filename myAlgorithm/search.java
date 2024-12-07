package myAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class search {
    public static void main(String[] args) {

        long start_time = System.currentTimeMillis();

        /*Scanner scanner = new Scanner(System.in);     // For taking input from user
        System.out.println("Enter the board size: ");   // Commented out for easy testing
        int boardSize = scanner.nextInt();
        System.out.println("Enter the method(a=BFS, b=DFS, c=h1b, d=h2b): ");
        char method = scanner.next().charAt(0);
        System.out.println("Enter the time limit in minutes: ");
        int timeLimit = scanner.nextInt();

        ArrayList<String> result = searchBoard(boardSize, String.valueOf(method), timeLimit);*/

        ArrayList<String> result = searchBoard(5, "a", 15);

        long end_time = System.currentTimeMillis();
        System.out.println("Run time of the algorithm " + ((end_time - start_time) / 1000) /60 + " minutes " + ((end_time - start_time) / 1000) %60 + " seconds");
        if (result == null) {
            System.out.println("No result found");
        } else {
            for (String string : result) {
                System.out.print(string);
            }

        }
        // 8, 16, 32, 41, 52 sizes

    }

    private static ArrayList<String> searchBoard(int boardSize, String method, int timeLimit) {
        Board board = new Board(boardSize);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<ArrayList<String>> future = executor.submit(() -> {
            try {
                System.out.println("Function started");
                switch (method) {
                    case "a":
                        return breadFirstSearch(board);
                    case "b":
                        return DepthFirstSearch(board);
                    case "c":
                        return DepthFirstHeuristich1b(board);
                    case "d":
                        return DepthFirstHeuristich2(board);
                    default:
                        System.out.println("Invalid method");
                        return new ArrayList<>(); // Return an empty list for invalid method
                }
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

    private static ArrayList<String> breadFirstSearch(Board board) {

        Queue<Board> currentBoards = new LinkedList<>();

        currentBoards.add(board);
        Board currentBoard, tempBoard;
        currentBoard = board;

        while (!currentBoard.isDone()) {
            currentBoard = currentBoards.poll();
            for (int i = 1; i < 9; i++) {

                tempBoard = currentBoard.moveBoardNew(i);
                if (tempBoard != null) {
                    currentBoards.add(tempBoard);
                }
            }
        }
        if (currentBoard.isDone()) {
            return currentBoard.getMovements();
        } else {
            return null; // not found
        }
    }

    private static ArrayList<String> DepthFirstSearch(Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DepthFirstSearch'");
    }

    private static ArrayList<String> DepthFirstHeuristich1b(Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DepthFirstHeuristich1b'");
    }

    private static ArrayList<String> DepthFirstHeuristich2(Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DepthFirstHeuristich2'");
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
