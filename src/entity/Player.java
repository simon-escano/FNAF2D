package entity;

import item.Item;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    BufferedImage fov;
    public int numOfItems = 0;
    public Item[] items = new Item[50];

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
        mapX = game.tileSize * 21;
        mapY = game.tileSize * 45;
        speed = 4;
        direction = "up";
    }

    public void update() {
        if (game.keyHandler.shiftPressed) {
            speed = 6;
        } else {
            speed = 4;
        }
        if (game.keyHandler.upPressed || game.keyHandler.downPressed || game.keyHandler.leftPressed || game.keyHandler.rightPressed) {
            if (game.keyHandler.upPressed) direction = "up";
            if (game.keyHandler.downPressed) direction = "down";
            if (game.keyHandler.leftPressed) direction = "left";
            if (game.keyHandler.rightPressed) direction = "right";

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
        if (i != -1) {
            String itemName = game.items[i].name;
            switch (itemName) {
                case "Mask":
                    items[numOfItems] = game.items[i];
                    numOfItems++;
                    game.items[i] = null;
                    break;
                case "Door":
                    game.items[i] = null;
                    break;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        if (!game.keyHandler.upPressed && !game.keyHandler.downPressed && !game.keyHandler.leftPressed && !game.keyHandler.rightPressed)
            spriteNum = 0;
        super.draw(graphics2D);
        graphics2D.drawImage(fov, (screenX - game.screen.width/2) + game.tileSize/2, (screenY - game.screen.height/2) + game.tileSize/2, game.screen.width, game.screen.height, null);
    }
}
