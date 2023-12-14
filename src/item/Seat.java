package item;

import main.Game;

import java.awt.*;

public class Seat extends BigItem {
    public Seat(int mapX, int mapY, Game game) {
        super("seat", mapX, mapY, 1, 2, game);
        top = true;
        collision = false;
        parts[0][1].setSolidArea(new Rectangle(0, game.tileSize/4, game.tileSize, game.tileSize - game.tileSize/4));
    }
}
