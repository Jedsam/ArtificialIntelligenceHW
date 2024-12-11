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
    public void calculateNextBoards(Board currentBoard){
        Board tempBoard;
        for (int i = 1; i < 9; i++) {
            tempBoard = currentBoard.moveBoardNew(i);
            if (tempBoard != null) {

                this.add(tempBoard);
                search.openedNodes++;
            }
        }
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
