package item;

import main.Game;

import java.awt.*;

public class WhackMachine extends BigItem {
    public WhackMachine(int mapX, int mapY, Game game) {
        super("whack", mapX, mapY, 1, 2, game);
        top = true;
        setSolidArea(new Rectangle(0, game.tileSize/4, game.tileSize, game.tileSize - game.tileSize/4));
    }
}
