package myAlgorithm;

import java.util.Stack;

public class DFSStack implements MyQueue {
    Stack<Node> boardList;

    DFSStack(Stack<Node> boardList) {
        this.boardList = boardList;
    }

    @Override
    public void calculateAndAddNextBoards(Node currentBoard) {   // method for create next board and add to the stack for DFS
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
        boardList.push(board);
    }

    @Override
    public Node get() {
        Search.expandedNodes++;
        return boardList.pop();
    }

    @Override
    public boolean isEmpty() {
        return boardList.isEmpty();
    }

}
