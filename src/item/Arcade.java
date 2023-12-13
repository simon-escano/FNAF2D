package item;

import main.Game;

import java.awt.*;
import java.util.Arrays;

public class Arcade extends Item {
    Item[] parts = new Item[3];

    public Arcade(String color, int mapX, int mapY, Game game) {
        super(mapX, mapY, game);
        collision = true;
        parts[0] = new GenItem("arcade_" + color + "_1.png", "Arcade1", mapX, mapY, game);
        game.itemManager.addItem(parts[0]);
        parts[1] = new GenItem("arcade_" + color + "_2.png", "Arcade2", mapX, mapY - 1, game);
        game.itemManager.addItem(parts[1]);
        parts[2] = new GenItem("arcade_" + color + "_3.png", "Arcade3", mapX, mapY - 2, game);
        game.itemManager.addItem(parts[2]);
    }

    @Override
    public void draw(Graphics2D graphics2D, Game game) {
        for (Item part : parts) {
            part.draw(graphics2D, game);
        }
    }
}
