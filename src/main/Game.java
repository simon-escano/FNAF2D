package main;

import entity.Player;
import item.Item;
import item.ItemManager;
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

    public UI ui = new UI(this);
    Thread thread = new Thread(this);
    Sound sbg = new Sound();
    Sound sfx = new Sound();
    TileManager tileManager = new TileManager(this);
    ItemManager itemManager = new ItemManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this);
    public Item[] items = new Item[50];
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
        itemManager.loadItems();
        loopSound(0);
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        tileManager.draw(graphics2D);
        itemManager.draw(graphics2D);
        player.draw(graphics2D);

        ui.draw(graphics2D);
        graphics2D.dispose();
    }

    public void loopSound(int i) {
        sbg.setFile(i);
        sbg.play();
        sbg.loop();
    }

    public void stopSound() {
        sbg.stop();
    }

    public void playSound(int i) {
        sfx.setFile(i);
        sfx.play();
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Five Nights at Freddy's 2D");

        Game game = new Game();
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.thread.start();
        game.setup();
    }
}
