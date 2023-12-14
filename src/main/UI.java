package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    Game game;
    Graphics2D graphics2D;
    Font font;
    public int command = 0;
    float alpha = 0;
    boolean blackScreenDone = false;
    int jumpscareNum = 0;
    private boolean blinkVisible = true;
    Timer timer = new Timer(400, e1 -> {
        blinkVisible = !blinkVisible;
        if (!blinkVisible) {
            ((Timer) e1.getSource()).stop();
        }
    });
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
        graphics2D.drawImage(game.player.fov, 0, 0, game.screen.width, game.screen.height, null);
        switch (game.state) {
            case TITLE -> drawTitle();
            case OPTIONS -> drawOptions();
            case PLAY -> {
                drawPlay();
                if (game.keyHandler.T) {
                    drawLocation();
                }
            }
            case PAUSE -> drawPause();
            case GAME_OVER -> drawGameOver();
            case TASK -> drawTask();
            case ENDING -> drawEnding();
        }
    }

    public void drawTitle() {
        if (blinkVisible) {
            image("/ui/main-menu.png", 0, 0, game.screen.width, game.screen.height);
        } else {
            image("/ui/main-blink.png", 0, 0, game.screen.width, game.screen.height);
        }

        int y = game.tileSize * 6;
        int x = game.tileSize * 2;
        text("Play Game", x, y, 30, Color.white);
        if (command == 0) text(">", x - game.tileSize/2, y, 30, Color.white);
        y += game.tileSize / 2;
        text("Quit", x, y, 30, Color.white);
        if (command == 1) text(">", x - game.tileSize/2, y, 30, Color.white);
        if (!timer.isRunning()) {
            timer.start();
        }
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
        pane(game.tileSize/2, game.tileSize/2, game.tileSize * 5 + game.tileSize/2, game.taskPaneHeight);
        int x = game.tileSize;
        int y = game.tileSize + game.tileSize/4;
        text("TASK:", x, y, 26, new Color(161, 141, 214));
        y += game.tileSize/2 - game.tileSize/16;
        for (String line : game.taskInfo) {
            if (line.startsWith("(")) {
                text(line, x, y, 20, new Color(83, 91, 100));
            } else {
                text(line, x, y, 20, Color.white);
            }
            y += game.tileSize/3;
        }
    }

    public void drawLocation() {
        text("Location: (" + game.player.mapX / game.tileSize + ", " + game.player.mapY / game.tileSize + ")", game.tileSize/2, game.screen.height - game.tileSize, 20, Color.white);
    }

    public void drawPause() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        text("PAUSED", game.screen.height/2, 80, Color.white);
    }

    public void drawGameOver() {
        if (!blackScreenDone) {
            alpha += 0.005f;
            if (alpha > 1f) {
                alpha = 1f;
            }
            drawBlackScreen(alpha);
            if (alpha == 1f) {
                alpha = 0;
                blackScreenDone = true;
            }
        } else {
            if (jumpscareNum == 39) {
                jumpscareNum = 0;
                blackScreenDone = false;
                game.changeState(Game.States.PLAY);
                game.restart();
                return;
            }
            if (jumpscareNum == 0) {
                game.stopSound();
                game.loopSound(9);
                game.playSound(4);
            }
            String nameUpper = game.killer.name.substring(0, 1).toUpperCase() + game.killer.name.substring(1);
            image("/jumpscares/" + nameUpper + "/" + nameUpper + "Scare (" + (jumpscareNum + 1) + ").png", 0, 0, game.screen.width, game.screen.height);
            jumpscareNum++;
        }
    }

    public void drawTask() {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        image("/ui/static.png", 0, 0, game.screen.width, game.screen.height);
        text("TASK ONGOING", game.screen.height/2, 80, Color.white);
        text("(alt + tab) to switch to task", game.screen.height/2 + game.tileSize/2, 30, Color.white);
    }

    public void drawEnding() {
        alpha += 0.003f;
        if (alpha > 1f) {
            alpha = 1f;
        }
        drawBlackScreen(alpha);
        if (alpha == 1f) {
            game.changeState(Game.States.TITLE);
        }
    }

    public void drawBlackScreen(float alpha) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0, 0, game.screen.width, game.screen.height);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public int centerText(String string) {
        return game.screen.width/2 - (int) graphics2D.getFontMetrics().getStringBounds(string, graphics2D).getWidth()/2;
    }

    public void text(String string, int x, int y, int size, Color color) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, size));
        graphics2D.setColor(Color.black);
        graphics2D.drawString(string, x + 3, y + 3);
        graphics2D.setColor(color);
        graphics2D.drawString(string, x, y);
    }

    public void text(String string, int y, int size, Color color) {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, size));
        text(string, centerText(string), y, size, color);
    }

    public void image(String filepath, int x, int y, int width, int height) {
        try {
            graphics2D.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filepath))), x, y, width, height, null);
        } catch (Exception e) {
            System.err.println("Image [" + filepath + "] not found.");
        }
    }

    public void pane(int x, int y, int width, int height) {
        graphics2D.setColor(new Color(0, 0, 0, 150));
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);
        graphics2D.setColor(new Color(255, 255, 255));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.drawRoundRect(x + 3, y + 3, width - 6, height - 6, 25, 25);
    }
}
