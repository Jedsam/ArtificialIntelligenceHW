package myAlgorithm;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class H2Stack extends DFSStack {

    H2Stack(Stack<Board> boardList) {
        super(boardList);
    }

    @Override
    public void calculateNextBoards(Board currentBoard) {
        ArrayList<Map.Entry<Board, Integer>> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Map.Entry<Board, Integer> result = currentBoard.createNextBoardH(i);
            if (result != null) {
                tempList.add(result);
            }
        }

        tempList.sort(((a, b) -> {
            int temp = b.getValue() - a.getValue();
            if (temp == 0) {
                return b.getKey().h2Compare(a.getKey());
            } else {
                return temp;
            }
        }));
        for (Map.Entry<Board, Integer> result : tempList) {
            this.add(result.getKey()); // take board with getKey()
            search.openedNodes++;
        }
    }

}
