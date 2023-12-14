package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public Tile(String filename) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + filename)));
        } catch(Exception e) {
            System.err.println("Image[" + filename + "] for Tile not found.");
        }
    }

    public Tile(String filename, boolean collision) {
        this(filename);
        this.collision = collision;
    }
}
