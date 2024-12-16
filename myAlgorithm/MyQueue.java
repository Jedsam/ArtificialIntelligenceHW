package myAlgorithm;

public interface MyQueue {
    public void add(Node board);

    public void calculateAndAddNextBoards(Node currentBoard);

    public Node get();

    public boolean isEmpty();
}
