package item;

import main.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TaskStarter extends Item {
    public TaskStarter(String name, int mapX, int mapY, Game game) {
        super(mapX, mapY, 48, 48, game.tileSize - 48, game.tileSize - 48, game);
        this.name = name;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/task.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
