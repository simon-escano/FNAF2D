package task;

import item.TaskStarter;
import main.Game;
import main.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class Task extends JFrame {
    Game game;
    Sound sbg = new Sound();
    Sound sfx = new Sound();
    public abstract void gameInitialize();
    public abstract void gameOver();
    public abstract void win();
    public abstract void gameRestart();

    public void close() {
        stopMusic();
        dispose();
        game.changeState(Game.States.PLAY);
    }
    public Task(Game game, String title, int width, int height) {
        this.game = game;

        setTitle(title);
        setSize(width, height);
        setResizable(false);
        setBackground(Color.black);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        inputMap.put(escapeKey, "Escape");
        actionMap.put("Escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    public void playMusic(int i){
        sbg.setFile(i);
        sbg.play();
        sbg.loop();
    }

    public void stopMusic(){
        sbg.stop();
    }

    public void playSE(int i){
        sfx.setFile(i);
        sfx.play();
    }

    public void removeTask() {
        for (int i = 0; i < game.items.length; i++)
            if (game.items[i] != null && game.items[i] instanceof TaskStarter) game.items[i] = null;
    }
}
