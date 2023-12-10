package entity;

import main.Game;

import java.util.Random;

public abstract class NPC extends Entity {
    public String name;
    public boolean chase = false;
    public NPC(Game game) {
        super(game);
    }

    public void setAction() {
        if (chase) {
            loadImage("/animatronics/freddy/freddy_chase");
            speed = 5;
            searchPath((game.player.mapX + game.player.solidArea.x) / game.tileSize, (game.player.mapY + game.player.solidArea.y) / game.tileSize);
        } else {
            loadImage("/animatronics/freddy/freddy_walk");
            speed = 2;
            actionLockCounter++;
            if (actionLockCounter < 120) return;
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) direction = "up";
            if (i > 25 && i <= 50) direction = "down";
            if (i > 50 && i <= 75) direction = "left";
            if (i > 75) direction = "right";
            actionLockCounter = 0;
        }
    }

    public void checkCollision() {
        collisionOn = false;
        game.collisionChecker.checkTile(this);
        game.collisionChecker.checkItem(this, false);
        if (game.collisionChecker.checkPlayer(this)) {
            Game.deathCause = "[insert " + name + " jumpscare]";
            game.playSound(4);
            game.changeState(Game.States.GAME_OVER);
        }
    }

    public void update() {
        setAction();
        checkCollision();
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
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
        }
        checkChase();
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (mapX + solidArea.x) / game.tileSize;
        int startRow = (mapY + solidArea.y) / game.tileSize;
        game.pathfinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (game.pathfinder.search()) {
            int nextX = game.pathfinder.pathList.get(0).col * game.tileSize;
            int nextY = game.pathfinder.pathList.get(0).row * game.tileSize;

            int entityLeftX = mapX + solidArea.x;
            int entityRightX = mapX + solidArea.x + solidArea.width;
            int entityTopY = mapY + solidArea.y;
            int entityBottomY = mapY + solidArea.y + solidArea.height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + game.tileSize) {
                direction = "up";
            } else if (entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + game.tileSize) {
                direction = "down";
            } else if (entityTopY >= nextY && entityBottomY < nextY + game.tileSize) {
                if (entityLeftX > nextX) {
                    direction = "left";
                }
                if (entityLeftX < nextX) {
                    direction = "right";
                }
            } else if (entityTopY > nextY && entityLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY > nextY && entityLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (entityTopY < nextY && entityLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (entityTopY < nextY && entityLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";
                }
            }
        }
    }

    public void checkChase() {
        int xDist = Math.abs(mapX - game.player.mapX);
        int yDist = Math.abs(mapY - game.player.mapY);
        int tileDistance = (xDist + yDist) / game.tileSize;

        if (!chase && tileDistance < 4) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                chase = true;
            }
        }
        if (chase && tileDistance > 6) {
            chase = false;
        }
    }
}
