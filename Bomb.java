/*
 * Name: John Miller
 * Date: 10 October 2017
 * Class: CS 335 - 001
 *
 * NOTE: Starter Code from Memory Game (Program 0) was used
 *       to help design parts of this program
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Bomb extends JFrame implements ActionListener, MouseListener{

    private Board board;
    private Timer gameTimer;
    private JLabel timerLabel, flagLabel, winLabel;
    private JPanel labelView;
    private int flags;
    private int gameTime = 0;
    private int counter1 = 0;
    private int overallCount, counter2;

    // Starts a new game of width, height and bombs
    private Bomb(int width, int height, int bombs){

        super("Minesweeper");

        // Create a frame width and height that will change based on size of the board
        int frameWidth = width * 50;
        int frameHeight = height * 50 + 75;
        flags = bombs;

        // Create pieces of the board
        JButton restartGame = new JButton("Restart");
        timerLabel = new JLabel("Time: 0");
        flagLabel = new JLabel("Flags: " + flags);
        labelView = new JPanel();
        JPanel boardView = new JPanel();

        // Add an action listener to the button and for the game timer
        restartGame.addActionListener(this);
        ActionListener doGameTimer = e -> {
            gameTime++;
            timerLabel.setText("Timer: " + gameTime);
        };
        gameTimer = new Timer(1000, doGameTimer);
        gameTimer.setRepeats(true);

        Container c = getContentPane();

        // Create a new board
        board = new Board(width, height, bombs, this, this);

        // Set layout of board and add everything to JPanels
        boardView.setLayout(new GridLayout(height, width, 0, 0));
        board.fillBoardView(boardView);

        labelView.setLayout(new GridLayout(1, 3, 2, 2));
        labelView.add(flagLabel);
        labelView.add(restartGame);
        labelView.add(timerLabel);

        // Add the JPanels to the content pane
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.CENTER);

        // Create the menu bar
        createMenu();

        // set size of frame
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setVisible(true);
    }

    // Creates the menu in JFrame
    private void createMenu() {

        // Create all menu options
        // Make the Game tab
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');

        // Add new game button
        JMenuItem beginner = new JMenuItem("Beginner");
        beginner.setMnemonic('B');
        gameMenu.add(beginner);
        beginner.addActionListener(this);

        // Add new game button
        JMenuItem intermediate = new JMenuItem("Intermediate");
        intermediate.setMnemonic('I');
        gameMenu.add(intermediate);
        intermediate.addActionListener(this);

        // Add new game button
        JMenuItem expert = new JMenuItem("Expert");
        expert.setMnemonic('E');
        gameMenu.add(expert);
        expert.addActionListener(this);

        // Button in menu to create game setup
        JMenuItem custom = new JMenuItem("Custom");
        custom.setMnemonic('C');
        gameMenu.add(custom);
        custom.addActionListener(this);

        gameMenu.addSeparator();

        // Exit button for menu
        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setMnemonic('X');
        gameMenu.add(exitGame);
        exitGame.addActionListener(e -> System.exit(0));

        // Add gameMenu to the menu bar
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(gameMenu);

        // Create a help button for instructions
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        // Create new frame to pop up for simple instructions
        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.setMnemonic('I');
        helpMenu.add(instructions);
        instructions.addActionListener(e -> JOptionPane.showMessageDialog(
                Bomb.this,

                "- To start a new game choose Beginner, Intermediate,\n" +
                        "   Expert or Custom to create your own game\n" +
                        "- Click a square to reveal what is underneath.\n" +
                        "- If a bomb is hit you lose.\n" +
                        "- If a number is revealed, that is how many bombs are\n" +
                        "   in adjacent squares.\n" +
                        "- If you click every square without hitting a bomb, you win\n",
                "Instructions", JOptionPane.PLAIN_MESSAGE));

        // add the help menu to the menu bar
        bar.add(helpMenu);
    }

    // Executes all action listeners
    public void actionPerformed(ActionEvent e){

        int width, height, bombs;

        // If the beginner button is hit on the menu
        if (e.getActionCommand().equals("Beginner")){

            // Set the game size to 5/5/5 and create new game
            height = 5;
            width = 5;
            bombs = 5;

            dispose();

            Bomb B = new Bomb(width, height, bombs);
            B.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){System.exit(0);}
            });
        }
        // If the Intermediate button is hit on the menu
        else if (e.getActionCommand().equals("Intermediate")){

            // Set the game size to 8/8/15 and start new game
            height = 8;
            width = 8;
            bombs = 15;

            dispose();

            Bomb B = new Bomb(width, height, bombs);
            B.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){System.exit(0);}
            });
        }
        // If the Expert button is hit on the menu
        else if (e.getActionCommand().equals("Expert")){

            // Set game size to 10/10/30 and start new game
            height = 10;
            width = 10;
            bombs = 30;

            dispose();

            Bomb B = new Bomb(width, height, bombs);
            B.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){System.exit(0);}
            });
        }
        // If a custom game is requested
        else if (e.getActionCommand().equals("Custom")){

            String tall, wide, bomb_num;
            height = 0;
            width = 0;
            bombs = 0;

            // Keep the JOptionPane open asking for height until a valid height is given
            while (height < 5 || height > 10) {
                tall = JOptionPane.showInputDialog(Bomb.this, "Enter Height (5-10)");
                try {
                    height = Integer.valueOf(tall);
                }catch(NumberFormatException ignored){}
            }

            // Keep the JOptionPane open asking for width until a valid width is given
            while (width < 5 || width > 10) {
                wide = JOptionPane.showInputDialog(Bomb.this, "Enter Width (5-10)");
                try{
                    width = Integer.valueOf(wide);
                }catch(NumberFormatException ignored){}
            }
            int max = (height * width) / 2;

            // Keep the JOptionPane open asking for bombs until a valid number of bombs is given
            while (bombs < 1 || bombs > max) {
                bomb_num = JOptionPane.showInputDialog(Bomb.this, "Enter Bombs (" + max + " Max.)");

                try {
                    bombs = Integer.valueOf(bomb_num);
                }catch(NumberFormatException ignored){}
            }

            // Start a new game
            dispose();

            Bomb B = new Bomb(width, height, bombs);
            B.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){System.exit(0);}
            });
        }
        // If restart game is hit
        else if (e.getActionCommand().equals("Restart")){ restartGame(); }
        // If a tile is clicked
        else{

            // Get the source of the current tile clicked and start the timer
            TileFlip currTile = (TileFlip)e.getSource();
            gameTimer.start();

            // If the tile clicked is empty then perform the "clearing" function
            if (currTile.customName().equals("Empty")){

                board.clearEmptySquares(currTile.getXCoord(), currTile.getYCoord(), this, this);
            }
            // If the tile clicked is a bomb, stop the timer and show all the bombs
            else if (currTile.customName().equals("Bomb")){

                board.showBombs(currTile.getXCoord(), currTile.getYCoord(), this, this);
                gameTimer.stop();

                // Change the label at the top to inform user they lost
                labelView.remove(timerLabel);
                labelView.remove(flagLabel);

                JLabel loseLabel = new JLabel("YOU LOSE! ");

                labelView.add(loseLabel);
                labelView.revalidate();
                labelView.repaint();
            }
            // If a number tile is hit, show it and add to a counter
            else{

                counter1++;
                currTile.showFront();
                currTile.removeActionListener(this);
                currTile.removeMouseListener(this);
            }

            // get the counter from the clearings done and from numbers shown
            counter2 = board.getCounter();
            overallCount = counter1 + counter2;

            // if the total count of open squares equals the size of the board minus
            // the number of bombs you win
            if (overallCount == board.getSizeMinusBombs()){

                // Disable all the bombs if you win and stop the timer
                board.disableBombs(this, this);
                gameTimer.stop();

                // Change the label to the winning label
                labelView.remove(timerLabel);
                labelView.remove(flagLabel);

                winLabel = new JLabel("YOU WIN! Total Time: " + gameTime);

                labelView.add(winLabel);
                labelView.revalidate();
                labelView.repaint();
            }
        }
    }

    // Restarts the Game
    private void restartGame(){

        // If the game was won, but now restarted change labels back to flags and timer
        if (overallCount == board.getSizeMinusBombs()){

            labelView.remove(winLabel);
            labelView.add(flagLabel);
            labelView.add(timerLabel);
            labelView.revalidate();
            labelView.repaint();
        }

        // Reset all values to initial position
        overallCount = 0;
        counter1 = 0;
        counter2 = 0;
        gameTime = 0;

        // Set the labels
        flagLabel.setText("Flags: " + flags);
        timerLabel.setText("Timer: " + gameTime);

        // Create a new game of the same size as before
        dispose();

        Bomb B = new Bomb(board.getWidth(), board.getHeight(), board.getBombs());
        B.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }

    @Override // Get's a Right Click to set a Flag
    public void mouseClicked(MouseEvent e) {

        // Get the current tile that was clicked
        TileFlip currTile = (TileFlip)e.getSource();

        // If it was a right click (BUTTON3)
        if (e.getButton() == MouseEvent.BUTTON3) {

            // If there is a flag on the tile already remove it
            if (currTile.flagOnBack()){

                // Change the tile to the normal back and add action listeners back
                currTile.showBack();
                currTile.addActionListener(this);
                currTile.setFlagOnBack(false);

                // Add one back to the flag label
                flags++;
                flagLabel.setText("Flags: " + flags);
                flagLabel.repaint();
            }
            // If there is no flag on the tile and there are still flags left to put out, add one
            else if (!currTile.flagOnBack() && flags != 0){

                // Show the flag tile and remove action listeners so it can't be flipped
                currTile.showBackFlag();
                currTile.removeActionListener(this);
                currTile.setFlagOnBack(true);

                // Subtract from the flags label
                flags--;
                flagLabel.setText("Flags: " + flags);
                flagLabel.repaint();
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

    // Execute first game
    public static void main(String args[]){

        int width = 5;
        int height = 5;
        int bombs = 5;

        Bomb B = new Bomb(width, height, bombs);
        B.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });
    }
}
