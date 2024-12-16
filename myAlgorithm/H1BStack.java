package myAlgorithm;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;

public class H1BStack extends DFSStack {

    H1BStack(Stack<Node> boardList) {
        super(boardList);
    }

    @Override
    public void calculateAndAddNextBoards(Node currentBoard) { // method for create next board and add to the stack for h1b

        // list of board and heuristic value couples
        ArrayList<Map.Entry<Node, Integer>> tempList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            Map.Entry<Node, Integer> result = currentBoard.createNextNodeWithH1B(i);
            if (result != null) {
                tempList.add(result);
            }
        }

        tempList.sort((a, b) -> Integer.compare(a.getValue(), b.getValue())); // sort the list based on heuristic values

        if (!tempList.isEmpty()) {
            tempList = reverseArrayList(tempList);
        }
        for (Map.Entry<Node, Integer> result : tempList) {
            this.add(result.getKey()); // take board with getKey() and add to the stack
        }

    }

    private ArrayList<Map.Entry<Node, Integer>> reverseArrayList(ArrayList<Map.Entry<Node, Integer>> boardList) {
        ArrayList<Map.Entry<Node, Integer>> reversedList = new ArrayList<Map.Entry<Node, Integer>>();

        for (int i = boardList.size() - 1; i >= 0; i--) {
            reversedList.add(boardList.get(i));
        }

        return reversedList;
    }
}
