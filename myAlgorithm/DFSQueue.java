package myAlgorithm;

import java.util.Stack;

public class DFSQueue implements MyQueue {
    Stack<Board> boardList;

    DFSQueue(Stack<Board> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void add(Board board) {
        boardList.push(board);
    }

    @Override
    public Board get() {
        return boardList.pop();
    }

    @Override
    public boolean isEmpty() {
        return boardList.isEmpty();
    }

}
