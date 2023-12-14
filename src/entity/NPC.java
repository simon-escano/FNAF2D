package entity;

import main.Game;
import main.Sound;

import java.awt.*;
import java.util.Random;

public abstract class NPC extends Entity {
    public String name;
    public boolean chase = false;
    public boolean idle = true;
    Sound sound = new Sound();
    public NPC(Game game) {
        super(game);
    }
    public void setAction() {
        if (chase) {
            searchPath((game.player.mapX + game.player.solidArea.x) / game.tileSize, (game.player.mapY + game.player.solidArea.y) / game.tileSize);
        } else {
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
            game.killer = this;
            game.changeState(Game.States.GAME_OVER);
        }
    }

    public void update() {
        checkCollision();
        if (idle) return;
        setAction();
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
                playSound(10);
                spriteNum = 2;
            } else {
                playSound(11);
                spriteNum = 1;
            }
        }
        checkChase();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (idle) spriteNum = 0;
        super.draw(graphics2D);
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
        if (!chase && playerDistance() < 4) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                chase = true;
                speed = 3;
                if (this instanceof Foxy) {
                    speed = 4;
                }
                playSound(12);
                loadImage("/npc/" + name + "/" + name +  "_chase");
            }
        }
        if (chase && playerDistance() > 8) {
            if (this instanceof Foxy) {
                chase = false;
                speed = 2;
                loadImage("/npc/" + name + "/" + name +  "_walk");
            } else if (playerDistance() > 10) {
                chase = false;
                speed = 2;
                loadImage("/npc/" + name + "/" + name +  "_walk");
            }
        }
    }

    public void playSound(int i) {
        sound.setFile(i);
        surroundSound();
        sound.play();
    }

    public void loopSound(int i ) {
        sound.setFile(i);
        surroundSound();
        sound.play();
        sound.loop();
    }

    public void surroundSound() {
        float volume = (float) -(Math.pow(playerDistance(), 2)/3) + 6;
        if (volume < -80) volume = -80;
        sound.changeVolume(volume);
    }

    public int playerDistance() {
        return (Math.abs(mapX - game.player.mapX) + Math.abs(mapY - game.player.mapY)) / game.tileSize;
    }
}
