package main;

import entity.Player;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;

    int FPS = 60;

    public final Dimensions screen = new Dimensions(12, 9, this);
    public final Dimensions map = new Dimensions(46, 48, this);

    Thread thread = new Thread(this);
    Sound sound = new Sound();
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this);
    public Game() {
        this.setPreferredSize(new Dimension(screen.width, screen.height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void setup() {
        loopSound(0);
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        player.draw(graphics2D);
        graphics2D.dispose();
    }

    public void loopSound(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopSound() {
        sound.stop();
    }

    public void playSound(int i) {
        sound.setFile(i);
        sound.play();
    }
}
