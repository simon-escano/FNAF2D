package entity;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class BurgerKingGuy extends NPC {
    public BurgerKingGuy(Game game, int x, int y) {
        super(game);
        name = "Burger King Guy";
        direction = "down";
        mapX = x * game.tileSize;
        mapY = y * game.tileSize;
        idle = false;
        speed = 1;
        loopSound(27);
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter < 160) return;
        direction = Objects.equals(direction, "left") ? "right" : "left";
        actionLockCounter = 0;
    }

    @Override
    public void update() {
        checkCollision();
        surroundSound();
        setAction();
        if (!collisionOn && spriteCtr % 2 == 0) {
            if (direction.equals("left")) mapX -= speed;
            if (direction.equals("right")) mapX += speed;
        }
        spriteCtr++;
        if (spriteCtr >= 7) {
            spriteCtr = 0;
            spriteNum++;
            if (spriteNum == 7) spriteNum = 1;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/npc/burgerkingguy/burgerkingguy" + spriteNum + ".png")));
        } catch (Exception e) {
            System.err.println("Image for BurgerKingGuy not found.");
        }
        int screenX = mapX - game.player.mapX + game.player.screenX;
        int screenY = mapY - game.player.mapY + game.player.screenY;

        if (mapX + game.tileSize > game.player.mapX - game.player.screenX &&
                mapX - game.tileSize < game.player.mapX + game.player.screenX &&
                mapY + game.tileSize > game.player.mapY - game.player.screenY &&
                mapY - game.tileSize < game.player.mapY + game.player.screenY) {
            graphics2D.drawImage(image, screenX, screenY, game.tileSize, game.tileSize, null);
        }
        graphics2D.drawImage(image, screenX, screenY, game.tileSize, game.tileSize, null);
    }

    @Override
    public void checkCollision() {
        collisionOn = false;
        game.collisionChecker.checkTile(this);
        game.collisionChecker.checkItem(this, false);
        game.collisionChecker.checkPlayer(this);
    }
}
