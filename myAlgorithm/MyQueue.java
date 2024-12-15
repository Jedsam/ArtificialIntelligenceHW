package myAlgorithm;

public interface MyQueue {
    public void add(Board board);

    public void calculateAndAddNextBoards(Board currentBoard);

    public Board get();

    public boolean isEmpty();
}
