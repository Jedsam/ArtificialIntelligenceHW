package myAlgorithm;

import java.util.Stack;

public class DFSStack implements MyQueue {
    Stack<Board> boardList;

    DFSStack(Stack<Board> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void add(Board board) {
        boardList.push(board);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) {
        Board tempBoard;
        for (int i = 1; i < 9; i++) {
            tempBoard = currentBoard.createNextBoard(i);
            if (tempBoard != null) {

                this.add(tempBoard);
                search.openedNodes++;
            }
        }
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
