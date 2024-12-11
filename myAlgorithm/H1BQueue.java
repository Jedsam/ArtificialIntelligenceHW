package myAlgorithm;

import java.util.ArrayList;
import java.util.Stack;

public class H1BQueue extends DFSQueue {

    H1BQueue(Stack<Board> boardList) {
        super(boardList);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) {
        Board tempBoard;
        ArrayList<Board> tempList = new ArrayList<Board>();
        for (int i = 1; i < 9; i++) {
            tempBoard = currentBoard.moveBoardNewWithH1B(i);
            if (tempBoard != null) {
                tempList.add(tempBoard);
            }
        }

        tempList.sort((a, b) -> {
            return b.heuristicVal - a.heuristicVal;
        });
        for (Board board : tempList) {
            this.add(board);
            Board.openedNodes++;
        }
    }
}
