package item;

import main.Game;

import java.awt.*;

public class TableAndChairs extends BigItem {

    public TableAndChairs(int mapX, int mapY, Game game) {
        super("tableandchairs", mapX, mapY, 6, 5, game);
        for (int i = 0; i < 6; i++) {
            parts[i][0].top = true;
            parts[i][0].setSolidArea(new Rectangle(0, game.tileSize - game.tileSize/16, game.tileSize, game.tileSize/16));
        }
    }
}
