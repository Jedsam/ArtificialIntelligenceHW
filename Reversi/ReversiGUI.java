package Reversi;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel gameStatus;
    private Player player1, player2;

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
    }

    public void startSelectionScreen() {
        JPanel mainMenuFrame = new JPanel(); // Null layout requires manual positioning
        mainMenuFrame.setLayout(new BoxLayout(mainMenuFrame, BoxLayout.Y_AXIS));

        JLabel player1 = new JLabel("Player 1");
        JLabel player2 = new JLabel("Player 2");

        JComboBox cb1 = new JComboBox(ReversiStart.PLAYERS);
        JComboBox cb2 = new JComboBox(ReversiStart.PLAYERS);
        cb1.setPreferredSize(new Dimension(300, 100));
        cb2.setPreferredSize(new Dimension(300, 100));

        cb1.add(player1);
        cb2.add(player2);

        mainMenuFrame.add(cb1);
        mainMenuFrame.add(Box.createVerticalStrut(50));
        mainMenuFrame.add(cb2);
        mainMenuFrame.add(Box.createVerticalStrut(50));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JButton startGameButton = new JButton("Start the game!");
        startGameButton.setPreferredSize(new Dimension(350, 100));
        buttonPanel.add(startGameButton);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected values from combo boxes
                int player1Selection = cb1.getSelectedIndex();
                int player2Selection = cb2.getSelectedIndex();

                // Human player selection + player selection
                ReversiStart.addToInputBuffer(player1Selection + ReversiStart.HUMAN_PLAYER);
                ReversiStart.addToInputBuffer(player2Selection + ReversiStart.HUMAN_PLAYER);
            }
        });

        mainMenuFrame.add(buttonPanel);
        changePanel(mainMenuFrame);
    }

    public void startGameGUI(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        JPanel gameFrame = new JPanel();
        gameFrame.setLayout(new GridBagLayout());

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
        gbc.gridy = 0;
        gameFrame.add(boardPanel, gbc);

        // Adding a return to the main menu button
        JButton returnToMainMenuButton = new JButton("Return back to the Main Menu!");
        returnToMainMenuButton.setPreferredSize(new Dimension(350, 100));
        returnToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReversiStart.addToInputBuffer(-2); // Add return val to the buffer
            }
        });

        gameFrame.add(returnToMainMenuButton, gbc);

        // Add game status message
        gameStatus = new JLabel(player1.getTurnMessage(), SwingConstants.CENTER);
        gameStatus.setFont(new Font("Arial", Font.PLAIN, 20)); // Set the font size and style

        // Create a JPanel with FlowLayout to center the JLabel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(gameStatus); // Add the label to the topPanel

        topPanel.add(returnToMainMenuButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gameFrame.add(topPanel, gbc);

        changePanel(gameFrame);
    }

    private void changePanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void addPiece(int index, int value) {
        squares[index].setState(value);
        String turnText;
        if (value == Board.WHITE) {
            turnText = player1.getTurnMessage();
        } else {
            turnText = player2.getTurnMessage();
        }
        gameStatus.setText(turnText);
        frame.repaint();
        frame.revalidate();
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

    public void setMessage(String printMessage) {
        gameStatus.setText(printMessage);
    }

}
