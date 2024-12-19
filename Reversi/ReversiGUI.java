package Reversi;

import javax.naming.spi.ResolveResult;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class ReversiGUI {
    public static final int BOARD_WIDTH = 640;
    public static final int BOARD_HEIGHT = 640;
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 800;
    public static final int EDGE_THICKNESS = 10;
    public static final int SQUARE_WIDTH = BOARD_WIDTH / Board.BOARD_SIDE_LENGTH - EDGE_THICKNESS * 2;
    public static final int SQUARE_HEIGHT = BOARD_HEIGHT / Board.BOARD_SIDE_LENGTH - EDGE_THICKNESS * 2;

    private static ReversiGUI gui = null;
    private JFrame frame;
    private MySquare[] squares;

    public static ReversiGUI createGUI() {
        if (gui == null) {
            ReversiGUI.gui = new ReversiGUI();
            return ReversiGUI.gui;
        } else {
            return null;
        }
    }

    public void removeGUI() {
        ReversiGUI.gui = null;
    }

    private ReversiGUI() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes the program stop after closing the app
        frame.setResizable(false); // Constant size
        frame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT); // Border size
        frame.setVisible(true); // Make it visible
        frame.setLayout(new GridBagLayout());
        // Initilaise the board squares

        // Color of the board

        Color backgroundColor = new Color(0, 155, 72); // Colors for green

        // For setting the edges
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(backgroundColor);
        boardPanel.setLayout(new GridLayout(Board.BOARD_SIDE_LENGTH, Board.BOARD_SIDE_LENGTH));
        boardPanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        squares = new MySquare[Board.BOARD_SIZE];
        MySquare temp;
        // Set the background color
        int index = 0;
        for (int i = Board.BOARD_SIDE_LENGTH - 1; i >= 0; i--) {
            for (int j = Board.BOARD_SIDE_LENGTH - 1; j >= 0; j--) {
                temp = new MySquare();
                temp.setCoordinates(index);
                temp.setBackground(backgroundColor);
                temp.setBorder(BorderFactory.createLineBorder(Color.BLACK, EDGE_THICKNESS));
                temp.addMouseListener(temp);
                squares[index] = temp;
                index++;
                boardPanel.add(temp);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(boardPanel, gbc);

        // Add game status message
        JLabel gameStatus = new JLabel("This is Reversi", SwingConstants.CENTER);
        gameStatus.setFont(new Font("Arial", Font.PLAIN, 40)); // Set the font size and style

        // Create a JPanel with FlowLayout to center the JLabel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(gameStatus); // Add the label to the topPanel

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(topPanel, gbc);
    }

    public void addPiece(int index, int value) {

        squares[index].setState(value);
        frame.repaint();
    }

    public void addValidMoves(ArrayList<Integer> validMovesList) {
        for (int index : validMovesList) {
            squares[index].setState(MySquare.GUESS);
        }
    }

    public void removeValidMoves(ArrayList<Integer> validMovesList) {
        for (int index : validMovesList) {
            squares[index].setState(Board.EMPTY);
        }
    }
}
