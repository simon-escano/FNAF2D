package entity;

import main.Game;

public class Foxy extends NPC {
    public Foxy(Game game, int x, int y) {
        super(game);
        name = "Foxy";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 4;
        loadImage("/animatronics/foxy/foxy_walk");
    }
}
