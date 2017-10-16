import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class Board{

    private TileFlip tiles[][];
    private int[][] idArray;
    private int[][] neighbors = { {-1, 1}, {1, 0}, {1, 0}, {0, -1}, {0, -1}, {-1, 0}, {-1, 0}, {0, 1} };
    private int overallWidth, overallHeight, overallBombs;
    private int counter = 0;
    private int SminusB;

    // Creates a new board of tiles
    Board(int width, int height, int bombs, ActionListener AL, MouseListener ML){

        ClassLoader loader = getClass().getClassLoader();
        tiles = new TileFlip[width][height];
        int[][] bombArray = new int[bombs][2];
        idArray = new int[width][height];
        int bombHere = 0;
        Random random = new Random();
        String number = " ";
        SminusB = (height * width) - bombs;
        overallWidth = width;
        overallHeight = height;
        overallBombs = bombs;

        // Generate random bomb positions
        // An array of 0's has been made
        while(bombHere < bombs){

            // Get a random X and Y coordinate to place the bomb
            int bombPointX = random.nextInt(width);
            int bombPointY = random.nextInt(height);

            // As long as there is no bomb here already, place the bomb at that location and create a list
            // of where each bomb is
            if (idArray[bombPointX][bombPointY] == 0) {

                idArray[bombPointX][bombPointY] = -1;
                bombArray[bombHere][0] = bombPointX;
                bombArray[bombHere][1] = bombPointY;
                bombHere++;
            }
        }

        // Get the X and Y value of where each bomb is
        for (int[] bArray : bombArray) {

            int bombPointX = bArray[0];
            int bombPointY = bArray[1];

            for (int[] neighbor : neighbors) {

                // For each bomb point check that bombs neighbor and add one to it
                bombPointX += neighbor[0];
                bombPointY += neighbor[1];

                // If multiple bombs share a neighbor it will make it 2 then 3... up to 8
                // Check the bounds of the array so it doesn't go out of bounds
                if (!(bombPointX < 0 || bombPointX >= height || bombPointY < 0 || bombPointY >= width ||
                        idArray[bombPointX][bombPointY] == -1)) {
                    idArray[bombPointX][bombPointY] += 1;
                }
            }
        }

        // Loop through the width and height to create the tiles array
        for (int j = 0; j < width; j++){
            for(int k = 0; k < height; k++){

                // Find each number of the number id array created set a string value for each number
                if (idArray[j][k] == -1) { number = "Bomb"; }
                else if (idArray[j][k] == 0){ number = "Empty"; }
                else if (idArray[j][k] == 1){ number = "One"; }
                else if (idArray[j][k] == 2){ number = "Two"; }
                else if (idArray[j][k] == 3){ number = "Three"; }
                else if (idArray[j][k] == 4){ number = "Four"; }
                else if (idArray[j][k] == 5){ number = "Five"; }
                else if (idArray[j][k] == 6){ number = "Six"; }
                else if (idArray[j][k] == 7){ number = "Seven"; }
                else if (idArray[j][k] == 8){ number = "Eight"; }

                // Get that string values picture
                String imgPathNumber = "res/" + number + ".jpg";
                ImageIcon imgNum = new ImageIcon(loader.getResource(imgPathNumber));

                // Create a new TileFlip object at position j, k and set it to tiles j, k
                TileFlip c2 = new TileFlip(j, k, imgNum);

                tiles[j][k] = c2;

                // Add all listeners and names and then hide the tile
                c2.addActionListener(AL);
                c2.addMouseListener(ML);
                c2.setCustomName(number);
                c2.showBack();
                c2.setFlagOnBack(false);
            }
        }
    }

    // Fills the board with tiles
    void fillBoardView(JPanel view){

        for (int i = 0; i < overallWidth; i++){
            for(int j = 0; j < overallHeight; j++){
                view.add(tiles[i][j]);
            }
        }
    }

    // Performs the Clearing if an empty tile is hit
    void clearEmptySquares(int x, int y, ActionListener AL, MouseListener ML){

        // If the tile it is checking next is out of bounds or has been visited then skip it
        if (x < 0 || x >= overallWidth || y < 0 || y >= overallHeight ||
                idArray[x][y] == -1 || tiles[x][y].visited) { return; }

        // If it has not been checked
        else {
            // Add to the counter and then reveal the front
            // remove any listeners from it
            counter++;
            tiles[x][y].showFront();
            tiles[x][y].removeActionListener(AL);
            tiles[x][y].removeMouseListener(ML);

            // If the tile is empty then loop through its neighbors and
            // recursively call the function again
            if (tiles[x][y].customName().equals("Empty")){
                for (int[] neighbor : neighbors) {
                    x += neighbor[0];
                    y += neighbor[1];
                    clearEmptySquares(x, y, AL, ML);
                }
            }
        }
    }

    // If a bomb is hit, show all the boms
    void showBombs(int x, int y, ActionListener AL, MouseListener ML){

        // Loop through the board to find all bombs and reveal them
        for (int i = 0; i < overallWidth; i++){
            for (int j = 0; j < overallHeight; j++) {

                tiles[i][j].removeActionListener(AL);
                tiles[i][j].removeMouseListener(ML);

                if (tiles[i][j].customName().equals("Bomb")) {
                    tiles[i][j].showFront();
                }
            }
        }

        // The initial bomb you click turns red
        tiles[x][y].showBombHit();
    }

    // Disable all bombs if the game is won
    void disableBombs(ActionListener AL, MouseListener ML){

        // Loop through board and disable all bombs
        for (int i = 0; i < overallWidth; i++){
            for (int j = 0; j < overallHeight; j++) {

                if (tiles[i][j].customName().equals("Bomb")){

                    tiles[i][j].removeActionListener(AL);
                    tiles[i][j].removeMouseListener(ML);
                }
            }
        }
    }

    // Return the counter, width, height, bombs, and size minus bombs numbers
    int getCounter(){ return counter; }
    int getSizeMinusBombs() { return SminusB; }
    int getWidth() { return overallWidth; }
    int getHeight() { return overallHeight; }
    int getBombs() { return overallBombs; }
}