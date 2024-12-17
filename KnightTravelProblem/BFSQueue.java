package KnightTravelProblem;

import java.util.Queue;

public class BFSQueue implements MyQueue {
    Queue<Node> boardList;

    BFSQueue(Queue<Node> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void calculateAndAddNextBoards(Node currentBoard) { // method for create next board and add to the queue
        Node tempBoard;
        for (int i = 1; i < 9; i++) {
            tempBoard = currentBoard.createNextNode(i);
            if (tempBoard != null) {

                this.add(tempBoard);
            }
        }
    }

    @Override
    public void add(Node board) {
        boardList.add(board);
    }

    @Override
    public Node get() {
        Search.expandedNodes++;
        return boardList.poll();
    }

    @Override
    public boolean isEmpty() {
        return boardList.isEmpty();
    }

}
