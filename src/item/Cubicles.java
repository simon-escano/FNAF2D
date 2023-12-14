package item;

import main.Game;

import java.awt.*;

public class Cubicles extends BigItem {

    public Cubicles(int mapX, int mapY, Game game) {
        super("cubicle", mapX, mapY, 2, 7, game);
        for (int i = 0; i < 7; i++) {
            parts[0][i].setSolidArea(new Rectangle(game.tileSize/8, 0, game.tileSize - game.tileSize/8, game.tileSize));
        }
    }
}
