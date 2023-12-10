package entity;

import main.Game;

import java.util.Random;

public abstract class NPC extends Entity {
    public String name;
    public NPC(Game game) {
        super(game);
    }

    public void setAction() {
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

    public void update() {
        setAction();

        collisionOn = false;
        game.collisionChecker.checkTile(this);
        game.collisionChecker.checkItem(this, false);
        game.collisionChecker.checkPlayer(this);

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
    }
}
