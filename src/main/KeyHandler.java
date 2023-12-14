package main;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {
    Game game;

    public boolean W, S, A, D, E, T = false, F = false, SHIFT;

    public KeyHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (game.state) {
            case TITLE -> {
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    game.playSound(5);
                    game.ui.command--;
                    if (game.ui.command < 0) {
                        game.ui.command = 1;
                    }
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    game.playSound(5);
                    game.ui.command++;
                    if (game.ui.command > 1) {
                        game.ui.command = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    game.playSound(6);
                    switch (game.ui.command) {
                        case 0 -> game.changeState(Game.States.PLAY);
                        case 1 -> System.exit(0);
                    }
                }
            }
            case OPTIONS -> {
                if (code == KeyEvent.VK_ESCAPE) game.changeState(Game.States.PLAY);
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    game.playSound(5);
                    game.ui.command--;
                    if (game.ui.command < 0) {
                        game.ui.command = 3;
                    }
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    game.playSound(5);
                    game.ui.command++;
                    if (game.ui.command > 3) {
                        game.ui.command = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    game.playSound(6);
                    switch (game.ui.command) {
                        case 0 -> {
                            game.fullscreen = !game.fullscreen;
                            if (game.fullscreen) {
                                game.setFullscreen();
                            } else {
                                game.exitFullScreen();
                            }
                        }
                        case 1 -> game.changeState(Game.States.TITLE);
                        case 2 -> System.exit(0);
                        case 3 -> game.changeState(Game.States.PLAY);
                    }
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    game.playSound(6);
                    game.changeState(Game.States.PLAY);
                }
            }
            case PLAY -> {
                if (code == KeyEvent.VK_W) W = true;
                if (code == KeyEvent.VK_A) A = true;
                if (code == KeyEvent.VK_S) S = true;
                if (code == KeyEvent.VK_D) D = true;
                if (code == KeyEvent.VK_SHIFT) SHIFT = true;
                if (code == KeyEvent.VK_P) game.changeState(Game.States.PAUSE);
                if (code == KeyEvent.VK_T) {
                    T = !T;
                    game.playSound(5);
                }
                if (code == KeyEvent.VK_F) {
                    if (!game.lightsOff) return;
                    F = !F;
                    game.playSound(25);
                    if (F) {
                        try {
                            game.player.fov = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fov_flashlight.png")));
                        } catch (Exception f) {
                            System.err.println("Player flashlight fov image not found.");
                        }
                    } else {
                        try {
                            game.player.fov = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/fov_lights_out.png")));
                        } catch (Exception f) {
                            System.err.println("Player fov image not found.");
                        }
                    }
                }
                if (code == KeyEvent.VK_ESCAPE) game.changeState(Game.States.OPTIONS);
                if (code == KeyEvent.VK_E) {
                    game.player.interactItem();
                }
            }
            case PAUSE -> {
                if (code == KeyEvent.VK_P) {
                    game.playSound(6);
                    game.changeState(Game.States.PLAY);
                }
            }
            case GAME_OVER -> {
                if (code == KeyEvent.VK_ENTER) game.changeState(Game.States.PLAY);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) W = false;
        if (code == KeyEvent.VK_A) A = false;
        if (code == KeyEvent.VK_S) S = false;
        if (code == KeyEvent.VK_D) D = false;
        if (code == KeyEvent.VK_SHIFT) SHIFT = false;
        if (code == KeyEvent.VK_E) E = false;
    }
}
