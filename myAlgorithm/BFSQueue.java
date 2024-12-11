package myAlgorithm;

import java.util.Queue;

public class BFSQueue implements MyQueue {
    Queue<Board> boardList;

    BFSQueue(Queue<Board> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void add(Board board) {
        boardList.add(board);
    }

    @Override
    public Board get() {
        return boardList.poll();
    }

    @Override
    public boolean isEmpty() {
        return boardList.isEmpty();
    }

}
