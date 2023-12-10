package main;

import entity.NPC;
import entity.NPCManager;
import entity.Player;
import item.Item;
import item.ItemManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;

    int FPS = 60;

    public final Dimensions screen = new Dimensions(10, 6, this);
    public final Dimensions map = new Dimensions(46, 48, this);
    BufferedImage tempScreen;
    Graphics2D graphics2D;

    public UI ui = new UI(this);
    Thread thread = new Thread(this);
    Sound sbg = new Sound();
    Sound sfx = new Sound();
    TileManager tileManager = new TileManager(this);
    ItemManager itemManager = new ItemManager(this);
    NPCManager NPCManager = new NPCManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public Player player = new Player(this);
    public Item[] items;
    public NPC[] npc;
    public static String deathCause;

    public enum States { TITLE, PLAY, PAUSE, GAME_OVER }
    public States state;
    public Game() {
        System.out.println("Screen size: " + screen.cols * tileSize + " x " + screen.rows * tileSize);
        this.setPreferredSize(new Dimension(screen.width, screen.height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tempScreen = new BufferedImage(screen.width, screen.height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) tempScreen.getGraphics();
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
                draw();
                delta--;
            }
        }
    }

    public void setup() {
        state = States.TITLE;
        loopSound(3);
        itemManager.loadItems();
        NPCManager.loadNPC();
        setFullscreen();
    }

    public void setFullscreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(window);
    }

    public void update() {
        switch (state) {
            case PLAY -> {
                player.update();
                NPCManager.update();
            }
            case PAUSE -> {}
        }
    }

    public void draw() {
        if (state == States.TITLE) {
            ui.draw(graphics2D);
        } else {
            tileManager.draw(graphics2D);
            itemManager.draw(graphics2D);
            NPCManager.draw(graphics2D);
            player.draw(graphics2D);
            ui.draw(graphics2D);
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(tempScreen, 0, 0, window.getWidth(), window.getHeight(), null);
        graphics.dispose();
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

    public void changeState(States state) {
        this.state = state;
        switch (state) {
            case PLAY -> {
                stopSound();
                loopSound(0);
            }
            case PAUSE -> {
                stopSound();
            }
            case TITLE -> {
                restart();
                stopSound();
                loopSound(3);
            }
            case GAME_OVER -> {
                restart();
            }
        }
    }

    public void restart() {
        player.setDefaultValues();
        itemManager.loadItems();
        NPCManager.loadNPC();
    }

    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Five Nights at Freddy's 2D");
        window.setUndecorated(true);

        Game game = new Game();
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.thread.start();
        game.setup();
    }
}
