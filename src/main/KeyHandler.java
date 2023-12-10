package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    Game game;

    public boolean W, S, A, D, SHIFT;

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
                        game.ui.command = 2;
                    }
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    game.playSound(5);
                    game.ui.command++;
                    if (game.ui.command > 2) {
                        game.ui.command = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    game.playSound(6);
                    switch (game.ui.command) {
                        case 0:
                            game.changeState(Game.States.PLAY);
                            break;
                        case 1:
                            break;
                        case 2:
                            System.exit(0);
                            break;
                    }
                }
            }
            case PLAY -> {
                if (code == KeyEvent.VK_W) W = true;
                if (code == KeyEvent.VK_A) A = true;
                if (code == KeyEvent.VK_S) S = true;
                if (code == KeyEvent.VK_D) D = true;
                if (code == KeyEvent.VK_SHIFT) SHIFT = true;
                if (code == KeyEvent.VK_P) game.changeState(Game.States.PAUSE);
            }
            case PAUSE -> {
                if (code == KeyEvent.VK_P) game.changeState(Game.States.PLAY);
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
    }
}
