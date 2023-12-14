package task;

import item.TaskStarter;
import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.net.URL;

public class BalloonPop extends Task implements Counter {
    private static final int NUM_BALLOONS = 10;
    private static final int MAX_ALLOWED_POPS = 5;
    private static final int BALLOON_SIZE = 50;
    private static final int SPACING = 20;
    private static final int TIMER_DELAY = 25; // milliseconds
    private int popsCount = 0;
    private Timer timer;
    private List<JButton> balloons;

    public BalloonPop(Game game) {
        super(game, "Balloon Pop game", 650, 400);
        gameInitialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BalloonPop(new Game()));
    }

    public void gameInitialize() {
        playMusic(16);
        String backgroundImagePath = "/tasks/balloon_pop/placeholderBG.gif";

        try {
            ImageIcon backgroundImageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(backgroundImagePath)));
            JLabel backgroundLabel = new JLabel(backgroundImageIcon);
            setContentPane(backgroundLabel);
            setLayout(null);

            balloons = new ArrayList<>();

            for (int i = 0; i < NUM_BALLOONS; i++) {
                JButton balloon = createBalloonButton();
                balloons.add(balloon);
                add(balloon);
            }

            arrangeBalloons();

            timer = new Timer(TIMER_DELAY, e -> moveBalloons());
            timer.start();
        } catch (Exception e) {
            System.err.println("Image for BalloonPop background not found.");
        }
    }

    @Override
    public void gameTimer() {}

    @Override
    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game over! You hit Balloon Boy.");
    }

    private JButton createBalloonButton() {
        JButton balloon = new JButton();
        balloon.setSize(BALLOON_SIZE, BALLOON_SIZE);
        balloon.setContentAreaFilled(false);
        balloon.setBorderPainted(false);
        ImageIcon balloonIcon = getRandomBalloonImage();
        balloon.setIcon(balloonIcon);

        balloon.addActionListener(e -> {
            playSE(15);
            popsCount++;
            if (popsCount == MAX_ALLOWED_POPS) {
                win();
            }
            balloon.setVisible(false);
        });

        String forbiddenImagePath = "\\tasks\\balloon_pop\\balloon\\balloonBoy.gif";
        if (balloonIcon.getDescription().endsWith(forbiddenImagePath)) {
            balloon.addActionListener(e -> gameRestart());
        }
        return balloon;
    }

    private ImageIcon getRandomBalloonImage() {
        String imagePath = "/tasks/balloon_pop/balloon";
        URL resource = getClass().getResource(imagePath);

        if (resource != null) {
            File folder = new File(resource.getFile());
            File[] files = folder.listFiles();

            if (files != null && files.length > 0) {
                Random rand = new Random();
                int randomIndex = rand.nextInt(files.length);
                return new ImageIcon(files[randomIndex].getAbsolutePath());
            }
        }
        return new ImageIcon();
    }

    private void arrangeBalloons() {
        int x = SPACING;
        int y = getHeight() / 2 - BALLOON_SIZE / 2;

        for (JButton balloon : balloons) {
            balloon.setLocation(x, y);
            x += BALLOON_SIZE + SPACING;
        }
    }

    private void moveBalloons() {
        for (JButton balloon : balloons) {
            Point location = balloon.getLocation();
            if (location.x < getWidth()) {
                balloon.setLocation(location.x + 8, location.y);
            } else {
                balloon.setLocation(10 - BALLOON_SIZE, location.y);
            }
        }
    }

    public void gameRestart() {
        gameOver();
        timer.stop();
        stopMusic();
        for (JButton balloon : balloons) {
            remove(balloon);
        }
        getContentPane().removeAll();
        popsCount = 0;
        gameInitialize();
        revalidate();
        repaint();
    }

    public void win(){
        JOptionPane.showMessageDialog(BalloonPop.this, "Congratulations! You win!");
        timer.stop();
        close();
        removeTask();
        game.playSound(24);
        game.lightsOff = true;
        try {
            game.player.fov = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fov_lights_out.png")));
        } catch (Exception e) {
            System.err.println("Player lights out fov image not found.");
        }
        game.taskInfo = new ArrayList<>();
        game.taskInfo.add("(Uh oh... they've noticed you.)");
        game.taskInfo.add("Check the backstage to fix the wiring");
        game.taskInfo.add("in the junction box.");
        game.taskInfo.add("(press F to use your flashlight)");
        game.taskPaneHeight += game.tileSize/3;
        game.itemManager.addItem(new TaskStarter("Fix Lights", 1, 9, game));
    }
}