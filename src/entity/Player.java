package entity;

import item.Item;
import main.Game;
import task.SlidingPuzzle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    BufferedImage fov;
    public int numOfItems;
    public Item[] items;

    public Player(Game game) {
        super(game);
        screenX = game.screen.width/2 - game.tileSize/2;
        screenY = game.screen.height/2 - game.tileSize/2;

        solidArea = new Rectangle(game.tileSize / 6, (game.tileSize / 3) - 1, game.tileSize * 2/3, game.tileSize * 2/3);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        System.out.println(solidArea);

        setDefaultValues();
        loadImage("/player/guard");
        try {
            fov = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fov.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultValues() {
        numOfItems = 0;
        items = new Item[50];
        mapX = game.tileSize * 21;
        mapY = game.tileSize * 45;
        speed = 3;
        direction = "up";
    }

    public void update() {
        if (game.keyHandler.SHIFT) {
            speed = 5;
        } else {
            speed = 3;
        }
        if (game.keyHandler.W || game.keyHandler.S || game.keyHandler.A || game.keyHandler.D) {
            if (game.keyHandler.W) direction = "up";
            if (game.keyHandler.S) direction = "down";
            if (game.keyHandler.A) direction = "left";
            if (game.keyHandler.D) direction = "right";

            collisionOn = false;
            game.collisionChecker.checkTile(this);

            pickUpItem(game.collisionChecker.checkItem(this, true));

            if (!collisionOn) {
                if (direction.equals("up")) mapY -= speed;
                if (direction.equals("down")) mapY += speed;
                if (direction.equals("left")) mapX -= speed;
                if (direction.equals("right")) mapX += speed;
            }

            spriteCtr++;
            if (spriteCtr >= 32 - (speed * 3)) {
                spriteCtr = 0;
                if (spriteNum == 1) {
                    game.playSound(1);
                    spriteNum = 2;
                } else {
                    game.playSound(2);
                    spriteNum = 1;
                }
            }
        }
    }

    public void pickUpItem(int i) {
        if (i == -1) return;
        switch (game.items[i].name) {
            case "Mask":
                items[numOfItems] = game.items[i];
                numOfItems++;
                game.items[i] = null;
                game.playSound(7);
                break;
            case "Fix Bonnie":
                game.changeState(Game.States.TASK);
                SwingUtilities.invokeLater(() -> new SlidingPuzzle(game));
                game.playSound(7);
                break;
            case "Door":
                game.items[i] = null;
                game.playSound(8);
                break;
        }
    }

    public void draw(Graphics2D graphics2D) {
        if (!game.keyHandler.W && !game.keyHandler.S && !game.keyHandler.A && !game.keyHandler.D)
            spriteNum = 0;
        super.draw(graphics2D);
        graphics2D.drawImage(fov, (screenX - game.screen.width/2) + game.tileSize/2, (screenY - game.screen.height/2) + game.tileSize/2, game.screen.width, game.screen.height, null);
    }
}
