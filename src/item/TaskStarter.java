package item;

import main.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TaskStarter extends Item {
    public TaskStarter(String name, int mapX, int mapY, Game game) {
        super(mapX, mapY, 0, 0, game.tileSize, game.tileSize, game);
        this.name = name;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/task.png")));
        } catch (Exception e) {
            System.err.println("Image for TaskStarter not found");
        }
    }
}
