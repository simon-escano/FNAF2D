package item;

import main.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends Item {
    public boolean open;
    public Door(int mapX, int mapY, Game game) {
        super(mapX, mapY, game);
        collision = true;
        open = false;
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/door.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
