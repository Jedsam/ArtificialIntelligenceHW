package Reversi;

import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MySquare extends JPanel implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked!");
        // Add a circle
        Piece myPiece = new Piece(this.getX(), this.getY(), PlayReversi.SQUARE_WIDTH, PlayReversi.SQUARE_HEIGHT,
                PlayReversi.myBoard.currentTurn ? Color.BLACK : Color.WHITE);
        this.add(myPiece);
        PlayReversi.myBoard.currentTurn = !PlayReversi.myBoard.currentTurn;
        myPiece.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
