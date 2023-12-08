package item;

import main.Game;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Mask extends Item {
    public Mask(int mapX, int mapY, Game game) {
        super(mapX, mapY, game);
        name = "Mask";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/mask.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
