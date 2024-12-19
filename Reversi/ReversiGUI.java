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

public class ReversiGUI {
    public static final int BOARD_WIDTH = 640;
    public static final int BOARD_HEIGHT = 640;
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 800;
    public static final int EDGE_THICKNESS = 10;
    public Board myBoard;
    private static ReversiGUI gui = null;
    public static final int SQUARE_WIDTH = BOARD_WIDTH / Board.BOARD_SIDE_LENGTH - EDGE_THICKNESS * 2;
    public static final int SQUARE_HEIGHT = BOARD_HEIGHT / Board.BOARD_SIDE_LENGTH - EDGE_THICKNESS * 2;

    public static ReversiGUI createGUI(Board board) {
        if (gui == null) {
            ReversiGUI.gui = new ReversiGUI(board);
            return ReversiGUI.gui;
        } else {
            return null;
        }
    }

    public static ReversiGUI getGUI() {
        return gui;
    }

    public void removeGUI() {
        ReversiGUI.gui = null;
    }

    private ReversiGUI(Board board) {

        myBoard = board;
        JFrame frame = new JFrame();
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

        JPanel squares[] = new JPanel[Board.BOARD_SIZE];
        MySquare temp;
        // Set the background color
        int index = 0;
        for (int i = 0; i < Board.BOARD_SIDE_LENGTH; i++) {
            for (int j = 0; j < Board.BOARD_SIDE_LENGTH; j++) {
                temp = new MySquare();
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
}
