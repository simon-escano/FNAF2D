package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    Game game;
    Graphics2D graphics2D;
    Font font;
    public int command = 0;
    public UI(Game game) {
        this.game = game;
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/VCRosdNEUE.ttf");
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        graphics2D.setFont(font);
        switch (game.state) {
            case TITLE -> drawTitle();
            case OPTIONS -> drawOptions();
            case PLAY -> drawPlay();
            case PAUSE -> drawPause();
            case GAME_OVER -> drawGameOver();
            case TASK -> drawTask();
        }
    }

    public void drawTitle() {
        image("/ui/main-menu.png", 0, 0, game.screen.width, game.screen.height);
        int y = game.tileSize * 6;
        int x = game.tileSize * 2;
        text("Play Game", x, y, 30, Color.white);
        if (command == 0) text(">", x - game.tileSize/2, y, 30, Color.white);
        y += game.tileSize / 2;
        text("Quit", x, y, 30, Color.white);
        if (command == 1) text(">", x - game.tileSize/2, y, 30, Color.white);

        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
    }

    public void drawOptions() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        int y = game.tileSize * 3;
        text("OPTIONS", y, 30, Color.white);
        y += game.tileSize;
        text("Fullscreen", y, 30, Color.white);
        int x = centerText("Fullscreen") + (game.tileSize * 13) / 6;
        graphics2D.setColor(Color.black);
        graphics2D.drawRect(x + 3, (y - game.tileSize/4) + 3, game.tileSize / 4, game.tileSize / 4);
        graphics2D.setColor(Color.white);
        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.drawRect(x, y - game.tileSize/4, game.tileSize / 4, game.tileSize / 4);
        if (game.fullscreen) graphics2D.fillRect(x, y - game.tileSize / 4, game.tileSize / 4, game.tileSize / 4);
        if (command == 0) text(">", centerText("Fullscreen") - game.tileSize/2, y, 30, Color.white);
        y += game.tileSize / 2;
        text("Exit to Main Menu", y, 30, Color.white);
        if (command == 1) text(">", centerText("Exit to Main Menu") - game.tileSize/2, y, 30, Color.white);
        y += game.tileSize / 2;
        text("Quit Game", y, 30, Color.white);
        if (command == 2) text(">", centerText("Quit Game") - game.tileSize/2, y, 30, Color.white);
        y += game.tileSize;
        text("Back", y, 30, Color.white);
        if (command == 3) text(">", centerText("Back") - game.tileSize/2, y, 30, Color.white);
    }

    public void drawPlay() {
    }

    public void drawPause() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        text("PAUSED", game.screen.height/2, 80, Color.white);
    }

    public void drawGameOver() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        text(Game.deathCause, game.screen.height/2, 30, Color.white);
    }

    public void drawTask() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        text("TASK ONGOING", game.screen.height/2, 80, Color.white);
        text("(alt + tab) to switch to task", game.screen.height/2 + game.tileSize/2, 30, Color.white);
    }

    public int centerText(String string) {
        return game.screen.width/2 - (int) graphics2D.getFontMetrics().getStringBounds(string, graphics2D).getWidth()/2;
    }

    public void text(String string, int x, int y, int size, Color color) {
        graphics2D.setColor(Color.black);
        graphics2D.drawString(string, x + 3, y + 3);
        graphics2D.setColor(color);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, size));
        graphics2D.drawString(string, x, y);
    }

    public void text(String string, int y, int size, Color color) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, size));
        text(string, centerText(string), y, size, color);
    }

    public void image(String filepath, int x, int y, int width, int height) {
        try {
            graphics2D.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filepath))), x, y, width, height, null);
        } catch (IOException ignored) {}
    }

    public void pane(int x, int y, int width, int height) {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(x + 3, y + 3, width - 6, height - 6, 25, 25);
    }

    public void pane(int width, int height) {
        int x = game.screen.width / 2 - width / 2;
        int y = game.screen.height / 2 - height / 2;
        pane (x, y, width, height);
    }
}
