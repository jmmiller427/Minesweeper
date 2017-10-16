import javax.swing.*;

class TileFlip extends JButton {

    private ClassLoader loader = getClass().getClassLoader();
    private String customName;
    private int x, y;
    private boolean flagOnBack;
    boolean visited;
    private Icon front;
    private Icon back = new ImageIcon(loader.getResource("res/Back.jpg"));
    private Icon backFlag = new ImageIcon(loader.getResource("res/Flag.jpg"));
    private Icon bombHit = new ImageIcon(loader.getResource("res/BombHit.jpg"));

    // Creates a new tile flip object
    TileFlip(int i, int j, ImageIcon frontImage){

        super();
        front = frontImage;
        super.setIcon(front);
        x = i;
        y = j;
    }

    // Show the front or the back image and the flag and red bomb
    void showFront() { super.setIcon(front); visited = true; }
    void showBack() { super.setIcon(back); visited = false; }
    void showBackFlag() { super.setIcon(backFlag); }
    void showBombHit() { super.setIcon(bombHit); }

    // Create a boolean to check if a flag is showing or not on a tile
    boolean flagOnBack() { return flagOnBack; }
    void setFlagOnBack(boolean f) { flagOnBack = f; }

    // Create a custom name for each tile
    String customName() { return customName; }
    void setCustomName(String s) { customName = s; }

    // Get the X and Y coordinates of each tile
    int getXCoord() { return x; }
    int getYCoord() { return y; }
}

