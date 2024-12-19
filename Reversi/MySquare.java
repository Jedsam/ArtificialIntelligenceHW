package Reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MySquare extends JPanel implements MouseListener {
    public static final int GUESS = 1;
    int boardState = Board.EMPTY;

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean currentTurn = ReversiGUI.getGUI().myBoard.currentTurn;
        boardState = currentTurn ? Board.BLACK : Board.WHITE;
        ReversiGUI.getGUI().myBoard.currentTurn = !currentTurn;

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardState != Board.EMPTY) {
            Graphics2D g2d = (Graphics2D) g;

            int diameter = Math.min(getWidth(), getHeight()) * 3 / 4; // Circle diameter
            int x = (getWidth() - diameter) / 2; // Center horizontally
            int y = (getHeight() - diameter) / 2; // Center vertically

            if (boardState == MySquare.GUESS) {
                g2d.setColor(Color.red);
                g2d.drawOval(x, y, diameter, diameter);
            } else {
                Color pieceColor = boardState == Board.BLACK ? Color.BLACK : Color.WHITE;
                g2d.setColor(pieceColor);
                g2d.fillOval(x, y, diameter, diameter);
            }
        }
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
