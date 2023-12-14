package task;

import item.TaskStarter;
import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WhackAFreddy extends Task implements Counter {
    private static final int GRID_SIZE = 6;
    private final JButton[][] buttons;
    private final JLabel scoreLabel;
    private Timer timer;
    private Font pixelFont;
    private final Random random;
    private int score = 0;
    private Cursor hammerCursor;
    private Cursor hammerCursorWhack;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WhackAFreddy(new Game()));
    }

    public WhackAFreddy(Game game) {
        super(game, "Whack A Freddy", 450, 460);
        setTitle("Whack-a-Freddy");
        setSize(450, 460);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        scoreLabel = new JLabel("Score: 0");
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.ORANGE);
        scorePanel.add(scoreLabel);
        FlowLayout layout = (FlowLayout)scorePanel.getLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        random = new Random();
        add(scorePanel, BorderLayout.NORTH);
        gameInitialize();
        gameTimer();
        setVisible(true);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        playMusic(23);

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/VCRosdNEUE.ttf");
            assert is != null;
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
        } catch (Exception e) {
            System.out.println("Font for WhackAFreddy not found.");
        }

        scoreLabel.setFont(pixelFont);

        try {
            Image hammerImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tasks/whack_a_freddy/hammer.png"));
            Image hammerWhack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/tasks/whack_a_freddy/hammerWhack.png"));
            hammerCursor = Toolkit.getDefaultToolkit().createCustomCursor(hammerImage, new Point(0, 0), "hammerCursor");
            hammerCursorWhack = Toolkit.getDefaultToolkit().createCustomCursor(hammerWhack, new Point(0, 0), "hammerCursorWhack");
        } catch (Exception e) {
            System.out.println("Image for WhackAFreddy hammer not found.");
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(hammerCursor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setCursor(hammerCursorWhack);
                Timer timer = new Timer(400, e1 -> {
                    setCursor(hammerCursor);
                    ((Timer) e1.getSource()).stop();
                });
                timer.start();
            }
        });
    }

    public void gameInitialize() {
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        grid.setBackground(Color.ORANGE);
        add(grid, BorderLayout.CENTER);
        ImageIcon icon = emptyWhack();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new JButton();
                grid.add(buttons[i][j]);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setIcon(icon);
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].addActionListener(new MoleListener(i, j));
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setCursor(hammerCursor);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursor(Cursor.getDefaultCursor());
                    }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        setCursor(hammerCursorWhack);
                        Timer timer = new Timer(400, e1 -> {
                            setCursor(hammerCursor);
                            ((Timer) e1.getSource()).stop();
                        });
                        timer.start();
                    }

                });
            }
        }
    }
    private ImageIcon emptyWhack() {
        try {
            Image img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tasks/whack_a_freddy/emptyWhack.png")));
            Image newImg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (Exception e) {
            System.out.println("Image for WhackAFreddy empty whack not found.");
            return null;
        }
    }

    public void gameTimer() {
        timer = new Timer(900, e -> {
            hideMoles();
            showRandomMole();
        });
        timer.start();
    }

    private void hideMoles() {
        ImageIcon icon = emptyWhack();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setIcon(icon);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setText("");
            }
        }
    }

    private void showRandomMole() {
        int row = random.nextInt(GRID_SIZE);
        int col = random.nextInt(GRID_SIZE);

        try{
            Image img;
            if(row % 2 == 0){
                img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tasks/whack_a_freddy/freddy.png")));
                buttons[row][col].setText("1");
            }else{
                img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tasks/whack_a_freddy/purpleGuy.png")));
                buttons[row][col].setText("0");
            }
            Image newImg = img.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            buttons[row][col].setIcon(new ImageIcon(newImg));
        }catch (Exception e){
            System.err.println("Images for moles in WhackAFreddy not found.");
        }

        if(score == 10){
            win();
        }

    }

    public void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(null, "You whacked Purple Man! Try again?");
        gameRestart();
    }

    public void gameRestart(){
        timer.start();
        hideMoles();
        gameInitialize();
        score = 0;
        scoreLabel.setText("Score: "+score);
    }

    public void win(){
        JOptionPane.showMessageDialog(WhackAFreddy.this, "Congratulations, great whacker!");
        timer.stop();
        close();
        removeTask();
        game.taskInfo = new ArrayList<>();
        game.taskInfo.add("Cool! The last task for your shift is");
        game.taskInfo.add("to fix the vents in the kitchen. Then, you");
        game.taskInfo.add("get to go home!");
        game.taskInfo.add("(as long as you don't get eaten out :D)");
        game.taskPaneHeight = ((game.tileSize * 5) / 2 ) + game.tileSize/3;
        game.itemManager.addItem(new TaskStarter("Fix Vents", 34, 36, game));
    }

    private class MoleListener implements ActionListener {
        private final int row;
        private final int col;

        public MoleListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("1")){
                playSE(21);
                ++score;
                scoreLabel.setText("Score: "+score);
            }else if(buttons[row][col].getText().equals("0")){
                gameOver();
            }else{
                playSE(22);
            }
        }
    }
}