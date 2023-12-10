package main;

import item.Mask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI {
    Game game;
    Graphics2D graphics2D;
    Font font;
    BufferedImage maskImage;
    public int command = 0;
    public UI(Game game) {
        this.game = game;
        font = new Font("Arial", Font.PLAIN, 40);
        Mask mask = new Mask(0, 0, game);
        maskImage = mask.image;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        graphics2D.setFont(font);
        graphics2D.setColor(Color.white);
        switch (game.state) {
            case TITLE -> drawTitle();
            case PLAY -> drawPlay();
            case PAUSE -> drawPause();
            case GAME_OVER -> drawGameOver();
        }
    }

    public void drawTitle() {
        try {
            graphics2D.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/freddybg.png"))), 0, 0, game.screen.width, game.screen.height, null);
        } catch (IOException ignored) {}
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 20));
        graphics2D.setColor(Color.white);
        int y = game.tileSize * 4;
        int x = game.tileSize + game.tileSize /2;
        graphics2D.drawString("New Game", x, y);
        if (command == 0) graphics2D.drawString(">", x * 3/4, y);
        y += game.tileSize / 2;
        graphics2D.drawString("Load Game", x, y);
        if (command == 1) graphics2D.drawString(">", x * 3/4, y);
        y += game.tileSize / 2;
        graphics2D.drawString("Quit", x, y);
        if (command == 2) graphics2D.drawString(">", x * 3/4, y);
    }

    public void drawPlay() {
        graphics2D.drawImage(maskImage, game.tileSize/2, game.tileSize/2, game.tileSize, game.tileSize, null);
        graphics2D.drawString("x " + game.player.numOfItems, 120, 100);
    }

    public void drawPause() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80));
        String text = "PAUSED";
        graphics2D.drawString(text, centerTextX(text), game.screen.height/2);
    }

    public void drawGameOver() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 30));
        graphics2D.setColor(Color.white);
        graphics2D.drawString(Game.deathCause, centerTextX(Game.deathCause), game.screen.height/2);
    }

    public int centerTextX(String text) {
        return game.screen.width/2 - (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth()/2;
    }
}
