package entity;

import main.Game;

public class Foxy extends NPC {
    public Foxy(Game game, int x, int y) {
        super(game);
        name = "foxy";
        direction = "right";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        speed = 2;
        loadImage("/npc/foxy/foxy_walk");
    }
}
