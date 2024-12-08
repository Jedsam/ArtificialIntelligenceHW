package myAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import myAlgorithm.Board;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class search {
    public static void main(String[] args) {

        long start_time = System.currentTimeMillis();
        ArrayList<String> result = searchBoard(42, "b", 15);
        long end_time = System.currentTimeMillis();

        System.out.println("Run time of the algorithm " + (end_time - start_time) + "miliseconds");
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
                        if (DepthFirstSearch(board))
                            return board.getMovements();
                        else
                            return null;
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
            currentBoards.remove();
        }
        if (currentBoard.isDone()) {
            return currentBoard.getMovements();
        } else {
            return null; // not found
        }
    }

    private static boolean DepthFirstSearch(Board board) {
        int currentMove;

        for (int i = 1; i < 9; i++) {
            currentMove = board.getCurrentPosition();
            if (board.isDone()) {
                return true;
            }
            if (!board.moveBoard(i)) {
                continue;
            }
            if (!DepthFirstSearch(board)) {
                if (!board.unMoveBoard(currentMove)) {
                    System.err.println("Could not unmove board!");
                }
            } else {
                return true;
            }
        }
        return false;
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
