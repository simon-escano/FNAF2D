package item;

import main.Game;

import java.awt.*;
import java.util.Arrays;

public class Arcade extends BigItem {
    public Arcade(String color, int mapX, int mapY, Game game) {
        super("arcade_" + color, mapX, mapY, 1, 3, game);
        parts[0][0].top = true;
        parts[0][0].setSolidArea(new Rectangle(0, game.tileSize/2, game.tileSize, game.tileSize/2));
    }
}
