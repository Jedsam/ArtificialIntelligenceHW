package Reversi;

public abstract class Player {
    protected String name;
    public abstract int getInput();

    public String getVictoryMessage(){
        return this.name + " has won!!!";
    }
}
