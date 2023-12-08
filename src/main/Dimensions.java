package main;

public class Dimensions {
    public int cols, rows, width, height;
    public Dimensions(int cols, int rows, Game game) {
        this.cols = cols;
        this.rows = rows;
        this.width = game.tileSize * cols;
        this.height = game.tileSize * rows;
    }
}
