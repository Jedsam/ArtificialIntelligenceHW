package myAlgorithm;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class H2Stack extends DFSStack {
    H2Stack(Stack<Board> boardList) {
        super(boardList);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) { // method for create next board and add to the stack for h2

        // list of board and heuristic value couples
        ArrayList<Map.Entry<Board, Integer>> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Map.Entry<Board, Integer> result = currentBoard.createNextBoardWithH1B(i);
            if (result != null) {
                tempList.add(result);
            }
        }

        tempList.sort((a, b) -> {
            int compareByValue = Integer.compare(b.getValue(), a.getValue());
            if (compareByValue != 0) {
                return compareByValue;
            }
            return Double.compare(b.getKey().lengthFromCorner(), a.getKey().lengthFromCorner());
        });

        for (Map.Entry<Board, Integer> result : tempList) {
            this.add(result.getKey()); // take board with getKey() and add to the stack
        }
    }

}
