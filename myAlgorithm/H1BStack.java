package myAlgorithm;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;

public class H1BStack extends DFSStack {

    H1BStack(Stack<Board> boardList) {
        super(boardList);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) {   // method for create next board and add to the stack for h1b

        // list of board and heuristic value couples                                                  
        ArrayList<Map.Entry<Board, Integer>> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Map.Entry<Board, Integer> result = currentBoard.createNextBoardWithH1B(i);
            if (result != null) {
                tempList.add(result);
            }
        }

        tempList.sort((a, b) -> b.getValue() - a.getValue());   // sort the list based on heuristic values
        for (Map.Entry<Board, Integer> result : tempList) {
            this.add(result.getKey()); // take board with getKey() and add to the stack
            search.openedNodes++;
        }
    }
}
