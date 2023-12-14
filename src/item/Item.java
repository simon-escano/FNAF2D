package item;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public boolean top = false;
    public int mapX, mapY;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public Item(int mapX, int mapY, int saX, int saY, int saWidth, int saHeight, Game game) {
        this.mapX = mapX * game.tileSize;
        this.mapY = mapY * game.tileSize;
        solidArea = new Rectangle(saX, saY, saWidth, saHeight);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public Item(int mapX, int mapY, Game game) {
        this(mapX, mapY, 0, 0, game.tileSize, game.tileSize, game);
    }

    public Item setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        return this;
    }

    public void draw(Graphics2D graphics2D, Game game) {
        int screenX = mapX - game.player.mapX + game.player.screenX;
        int screenY = mapY - game.player.mapY + game.player.screenY;

        if (mapX + game.tileSize > game.player.mapX - game.player.screenX &&
                mapX - game.tileSize < game.player.mapX + game.player.screenX &&
                mapY + game.tileSize > game.player.mapY - game.player.screenY &&
                mapY - game.tileSize < game.player.mapY + game.player.screenY) {
            graphics2D.drawImage(image, screenX, screenY, game.tileSize, game.tileSize, null);
        }
    }
}
