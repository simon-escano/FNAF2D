package main;

import item.Mask;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class UI {
    Game game;
    Font font;
    BufferedImage maskImage;
    public UI(Game game) {
        this.game = game;
        font = new Font("Arial", Font.PLAIN, 40);
        Mask mask = new Mask(0, 0, game);
        maskImage = mask.image;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(font);
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(maskImage, game.tileSize/2, game.tileSize/2, game.tileSize, game.tileSize, null);
        graphics2D.drawString("x " + game.player.numOfItems, 120, 100);
    }
}
