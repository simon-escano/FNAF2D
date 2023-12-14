package entity;

import main.Game;

public class Freddy extends NPC {
    public Freddy(Game game, int x, int y) {
        super(game);
        name = "freddy";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 2;
        loadImage("/npc/freddy/freddy_walk");
    }
}
