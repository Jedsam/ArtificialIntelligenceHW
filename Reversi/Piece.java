package Reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Piece extends JPanel {
    int x;
    int y;
    int squareWidth;
    int squareHeight;
    Color color;

    public Piece(int x, int y, int squareWidth, int squareHeight, Color color) {
        this.x = x;
        this.y = y;
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        setVisible(true);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        // Draw a circle (x, y, width, height)
        // (x, y) is the top-left corner of the bounding box of the oval
        // width and height define the size of the oval (circle in this case)
        g2d.fillOval(0, 0, (squareWidth * 4) / 10, (squareHeight * 4) / 10); // A piece with diameter 8/10 of the
                                                                             // square
    }

}
