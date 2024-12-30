package Reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BasicStroke;

import javax.swing.JPanel;

public class MySquare extends JPanel implements MouseListener {
    public static final int GUESS = 1;

    private int boardState = Board.EMPTY;
    private int index;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (boardState == Board.EMPTY || boardState == GUESS) {
            ReversiStart.addToInputBuffer(index);
        }
    }

    public void setCoordinates(int index) {
        this.index = index;
    }

    public void setState(int state) {
        this.boardState = state;
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
                g2d.setColor(Color.blue);
                g2d.setStroke(new BasicStroke(2.0f));
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
