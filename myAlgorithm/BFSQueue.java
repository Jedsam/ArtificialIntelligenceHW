package myAlgorithm;

import java.util.Queue;

public class BFSQueue implements MyQueue {
    Queue<Board> boardList;

    BFSQueue(Queue<Board> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void calculateNextBoards(Board currentBoard) { // method for create next board and add to the queue
        Board tempBoard;
        for (int i = 1; i < 9; i++) {
            tempBoard = currentBoard.createNextBoard(i);
            if (tempBoard != null) {

                this.add(tempBoard);
            }
        }
    }

    @Override
    public void add(Board board) {
        boardList.add(board);
    }

    @Override
    public Board get() {
        Search.expandedNodes++;
        return boardList.poll();
    }

    @Override
    public boolean isEmpty() {
        return boardList.isEmpty();
    }

}
