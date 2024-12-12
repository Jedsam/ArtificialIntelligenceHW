package myAlgorithm;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;

public class H1BStack extends DFSStack {

    H1BStack(Stack<Board> boardList) {
        super(boardList);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) {
        ArrayList<Map.Entry<Board, Integer>> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Map.Entry<Board, Integer> result = currentBoard.createNextBoardWithH1B(i);
            if (result != null) {
                tempList.add(result);
            }
        }

        tempList.sort((a, b) -> b.getValue() - a.getValue());
        for (Map.Entry<Board, Integer> result : tempList) {
            this.add(result.getKey()); // take board with getKey()
            search.openedNodes++;
        }
    }
}
