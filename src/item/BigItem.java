package item;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class BigItem extends Item {
    public Item[][] parts;
    public BigItem(String name, int mapX, int mapY, int width, int height, Game game) {
        super(mapX, mapY, game);
        this.name = name;
        collision = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/" + name + "_0_0.png")));
        } catch (Exception e) {
            System.err.println("Image for BigItem: " + this + " not found.");
        }
        parts = new Item[width][height];
        parts[0][0] = this;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) continue;
                Item item = new GenItem(name + "_" + j + "_" + i + ".png", name, mapX + j,  mapY + i, game);
                game.itemManager.addItem(item);
                parts[j][i] = item;
            }
        }
    }
}
