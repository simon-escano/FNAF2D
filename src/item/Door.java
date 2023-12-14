package item;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Door extends Item {
    public boolean open;
    public Door(int mapX, int mapY, Game game) {
        super(mapX, mapY, game);
        solidArea = new Rectangle(game.tileSize/4, 0, game.tileSize/2, game.tileSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
        open = false;
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/door.png")));
        } catch (Exception e) {
            System.err.println("Image for Door not found.");
        }
    }
}
