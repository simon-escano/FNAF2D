package entity;

import main.Game;

public class Freddy extends NPC {
    public Freddy(Game game, int x, int y) {
        super(game);
        name = "Freddy";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 2;
        loadImage("/animatronics/freddy/freddy_walk");
    }
}
