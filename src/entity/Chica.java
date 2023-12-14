package entity;

import main.Game;

public class Chica extends NPC {
    public Chica(Game game, int x, int y) {
        super(game);
        name = "chica";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 2;
        loadImage("/npc/chica/chica_walk");
    }
}
