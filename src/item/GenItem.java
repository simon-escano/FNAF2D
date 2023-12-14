package item;

import main.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class GenItem extends Item {
    public GenItem(String image, String name, int mapX, int mapY, int saX, int saY, int saWidth, int saHeight, Game game) {
        super(mapX, mapY, saX, saY, saWidth, saHeight, game);
        collision = true;
        this.name = name;
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/" + image)));
        } catch (Exception e) {
            System.err.println("Image for GenItem: " + name + " not found.");
        }
    }

    public GenItem(String image, String name, int mapX, int mapY, Game game) {
        this(image, name, mapX, mapY, 0, 0, game.tileSize, game.tileSize, game);
    }
}
