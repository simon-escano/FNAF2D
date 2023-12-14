package entity;

import item.*;
import main.Game;
import task.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    public BufferedImage fov;
    public int numOfItems;
    public Item[] items;

    public Player(Game game) {
        super(game);
        screenX = game.screen.width/2 - game.tileSize/2;
        screenY = game.screen.height/2 - game.tileSize/2;

        solidArea = new Rectangle(game.tileSize / 6, (game.tileSize / 3) - 1, game.tileSize * 2/3, game.tileSize * 2/3);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        loadImage("/player/guard");
        try {
            fov = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fov.png")));
        } catch (Exception e) {
            System.err.println("Image for Player.fov not found.");
        }
    }

    public void setDefaultValues() {
        numOfItems = 0;
        items = new Item[50];
        mapX = game.tileSize * 21;
        mapY = (game.tileSize * 43) + game.tileSize/4;
        speed = 3;
        direction = "up";
    }

    public void update() {
        speed = game.keyHandler.SHIFT ? 5 : 3;
        if (game.keyHandler.W || game.keyHandler.S || game.keyHandler.A || game.keyHandler.D) {
            if (game.keyHandler.W) direction = "up";
            if (game.keyHandler.S) direction = "down";
            if (game.keyHandler.A) direction = "left";
            if (game.keyHandler.D) direction = "right";

            collisionOn = false;
            game.collisionChecker.checkTile(this);
            game.collisionChecker.checkItem(this, true);
            game.collisionChecker.checkEntity(this, game.npc);

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
        Item item = game.items[i];
        if (item instanceof Mask) {
            items[numOfItems] = item;
            game.items[i] = null;
            numOfItems++;
            game.playSound(7);
        } else if (item instanceof TaskStarter) {
            game.changeState(Game.States.TASK);
            game.playSound(7);
            switch (item.name) {
                case "Fix Bonnie" -> game.task = new FixBonnie(game);
                case "Balloon Pop" -> game.task = new BalloonPop(game);
                case "Fix Lights" -> game.task = new FixLights("/tasks/fix_lights/dot1.txt", game);
                case "Foxy Run" -> game.task = new FoxyRun(game);
                case "Whack A Freddy" -> game.task = new WhackAFreddy(game);
                case "Fix Vents" -> game.task = new FixVents(game);
            }
        } else if (item instanceof Door) {
            if (new Rectangle(solidArea.x + mapX, solidArea.y + mapY, solidArea.width, solidArea.height)
                    .intersects(new Rectangle(item.solidArea.x + item.mapX, item.solidArea.y + item.mapY, item.solidArea.width, item.solidArea.height)))
                return;
            game.playSound(8);
            ((Door) item).open = !((Door) item).open;
            item.collision = !((Door) item).open;
            if (((Door) item).open) {
                try {
                    item.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/air.png")));
                } catch (Exception e) {
                    System.err.println("Image for Air not found.");
                }
            } else {
                try {
                    item.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/door.png")));
                } catch (Exception e) {
                    System.err.println("Image for Door not found.");
                }
            }
        }
    }

    public void interactItem() {
        Rectangle nextTile = switch (game.player.direction) {
            case "up" ->
                    new Rectangle(game.player.mapX, (game.player.mapY) - game.tileSize, game.tileSize, game.tileSize);
            case "down" ->
                    new Rectangle(game.player.mapX, (game.player.mapY) + game.tileSize, game.tileSize, game.tileSize);
            case "left" ->
                    new Rectangle((game.player.mapX) - game.tileSize, game.player.mapY, game.tileSize, game.tileSize);
            case "right" ->
                    new Rectangle((game.player.mapX) + game.tileSize, game.player.mapY, game.tileSize, game.tileSize);
            default -> null;
        };

        for (int i = 0; i < game.items.length; i++) {
            Item item = game.items[i];
            assert nextTile != null;
            if (item != null) {
                Rectangle itemTile = new Rectangle(item.mapX, item.mapY, game.tileSize, game.tileSize);
                if (itemTile.intersects(nextTile) || new Rectangle(mapX, mapY, game.tileSize, game.tileSize).intersects(itemTile)) {
                    pickUpItem(i);
                }
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        if (!game.keyHandler.W && !game.keyHandler.S && !game.keyHandler.A && !game.keyHandler.D)
            spriteNum = 0;
        super.draw(graphics2D);
    }
}
