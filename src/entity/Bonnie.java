package entity;

import main.Game;

public class Bonnie extends NPC {
    public Bonnie(Game game, int x, int y) {
        super(game);
        name = "Bonnie";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 2;
        loadImage("/animatronics/bonnie/bonnie_walk");
    }
}
