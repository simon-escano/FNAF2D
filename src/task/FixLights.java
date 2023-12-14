package task;

import item.TaskStarter;
import main.Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.*;

class Dimension {
    int x,y;
}

public class FixLights extends Task implements MouseMotionListener, MouseListener {
    boolean flag = true, winFlag = false;
    boolean win;
    int noOfColors, y = 0, cellPos;
    int cellSize;
    JFrame frame;
    boolean[] winCond;
    boolean[] cellVisible;
    boolean[] emptyCell;
    final JDialog dialog;
    ArrayList<Integer>[] color_select;
    int[][] color;
    int[] pos_color;
    Dimension q = new Dimension();
    int activeCell = 1;

    public static void main(String[] args){
        String level = "/tasks/fix_lights/dot1.txt";
        SwingUtilities.invokeLater(() -> new FixLights(level, new Game()));
    }
    Dimension operate(int activeCell){
        if(activeCell % cellSize == 0){
            q.x = 100 * cellSize;
            q.y = 100 + 100 * ((activeCell/cellSize) - 1);
        }else{
            q.x = 100 + 100 * ((activeCell) % cellSize - 1);
            q.y = 100 + 100 * (activeCell /cellSize);
        }
        return q;
    }
    public FixLights(String level, Game game){
        super(game, "Fix Lights", 750, 750);
        setBackground(Color.decode("#0D0D0D"));
        playMusic(18);

        dialog = new JDialog();
        dialog.setAlwaysOnTop(true);

        frame = new JFrame();

        try {
            InputStream is = getClass().getResourceAsStream(level);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            cellSize = Integer.parseInt(line);
            line = br.readLine();
            noOfColors = Integer.parseInt(line);
            pos_color = new int[noOfColors * 2];
            winCond = new boolean[cellSize * cellSize];
            cellVisible = new boolean[cellSize * cellSize];
            emptyCell = new boolean[noOfColors];
            color = new int[cellSize][cellSize];
            for (int i = 0; i < noOfColors * 2; i++) {
                line = br.readLine();
                pos_color[i] = Integer.parseInt(line);
            }
        } catch (Exception e) {
            System.err.println("Level .txt for FixLights not found.");
        }

        setSize( 200 + 100 * cellSize, 200 + 100 * cellSize );
        setVisible(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        color_select = new ArrayList[noOfColors];
        for (int i = 0; i < noOfColors; i++) {
            color_select[i] = new ArrayList<>();
        }
    }
    @Override
    public void paint( Graphics g ){
        g.setColor(Color.decode("#0D0D0D"));
        g.fillRect(0,0,200+100*cellSize,200+100*cellSize);
        g.setColor(Color.WHITE);


        for ( int x = 100; x < 100+100*cellSize; x += 100 ){
            for ( int y = 100; y <100+100*cellSize; y += 100 ){
                g.drawRect( x, y, 100, 100 );
            }
        }

        for (int m = 0; m < noOfColors * 2; m++) {
            if (m < 2) {
                activeCell = pos_color[m];
                g.setColor(Color.CYAN);
                q = operate(activeCell);
                cellVisible[activeCell - 1] = true;
                g.fillOval(q.x + 20, q.y + 20, 60, 60);
                color[(q.y / 100) - 1][(q.x / 100) - 1] = 5;
            } else if (m < 4) {
                activeCell = pos_color[m];
                g.setColor(Color.YELLOW);
                q = operate(activeCell);
                cellVisible[activeCell - 1] = true;
                g.fillOval(q.x + 20, q.y + 20, 60, 60);
                color[(q.y / 100) - 1][(q.x / 100) - 1] = 6;
            } else if (m < 6) {
                activeCell = pos_color[m];
                g.setColor(Color.RED);
                q = operate(activeCell);
                cellVisible[activeCell - 1] = true;
                g.fillOval(q.x + 20, q.y + 20, 60, 60);
                color[(q.y / 100) - 1][(q.x / 100) - 1] = 7;
            } else if (m < 8) {
                activeCell = pos_color[m];
                g.setColor(Color.MAGENTA);
                q = operate(activeCell);
                cellVisible[activeCell - 1] = true;
                g.fillOval(q.x + 20, q.y + 20, 60, 60);
                color[(q.y / 100) - 1][(q.x / 100) - 1] = 8;
            } else if (m < 16) {
                activeCell = pos_color[m];
                g.setColor(Color.PINK);
                q = operate(activeCell);
                cellVisible[activeCell - 1] = true;
                g.fillOval(q.x + 20, q.y + 20, 60, 60);
                color[(q.y / 100) - 1][(q.x / 100) - 1] = 12;
            }
        }

        Dimension prevXY;
        for (int i = 0; i < noOfColors; i++) {
            if (i == 0) g.setColor(Color.CYAN);
            else if (i == 1) g.setColor(Color.YELLOW);
            else if (i == 2) g.setColor(Color.RED);
            else if (i == 3) g.setColor(Color.MAGENTA);
            else if (i == 7) g.setColor(Color.PINK);

            for (int j = 1; j < color_select[i].size(); j++) {
                int dx, dy;
                int ox, oy, px, py;

                Dimension draw;
                draw = operate(color_select[i].get(j));

                draw.x += 50;
                draw.y += 50;
                dx = draw.x;
                dy = draw.y;
                prevXY = operate(color_select[i].get(j - 1));

                prevXY.x += 50;
                prevXY.y += 50;
                px = prevXY.x;
                py = prevXY.y;

                int height, width;
                if (dx == px) {
                    if (py > dy) {
                        ox = dx;
                        oy = dy;
                    } else {
                        ox = px;
                        oy = py;
                    }
                    width = 10;
                    height = 100;
                } else {
                    if (px > dx) {
                        ox = dx;
                        oy = dy;
                    } else {
                        ox = px;
                        oy = py;
                    }
                    height = 10;
                    width = 100;
                }
                g.fillRect(ox, oy, width, height);
            }
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int a, b;
        a = (e.getX()) / 100;
        b = e.getY() / 100;

        if (e.getX() <= 100 || e.getX() >= 100 + 100 * cellSize || e.getY() <= 100 || e.getY() >= 100 + 100 * cellSize) {
            return;
        } else if (a > 100 || a < 100 + 100 * cellSize || b > 100 || b < 100 + 100 * cellSize) {
            if (flag) {
                if (cellVisible[(b - 1) * cellSize + a - 1]) {
                    y = color[b - 1][a - 1];
                    flag = false;
                }
            } else {
                cellPos = (b - 1) * cellSize + a - 1;
                if (cellVisible[cellPos]) {
                    if (color_select[y - 5].isEmpty() && y == color[b - 1][a - 1]) {
                        color_select[y - 5].add(cellPos + 1);
                        winCond[cellPos] = true;
                    }
                    if (color_select[y - 5].get(color_select[y - 5].size() - 1) != (b - 1) * cellSize + a && y == color[b - 1][a - 1]) {
                        if (color_select[y - 5].get(0) == cellPos + 1) {
                            for (int i = 0; i < noOfColors; i++) {
                                for (int j = 0; j < color_select[i].size(); j++) {
                                    if (color_select[i].get(j) == cellPos + 1) {
                                        for (int k = 0; k < color_select[i].size(); k++) {
                                            int p = color_select[i].get(k);
                                            winCond[p - 1] = false;
                                            if (p % cellSize == 0) {
                                                color[p / cellSize - 1][3] = 0;
                                            } else {
                                                color[p / cellSize][p % cellSize - 1] = 0;
                                            }
                                        }
                                        color_select[i].clear();
                                    }
                                }
                            }
                        } else {
                            color_select[y - 5].add(cellPos + 1);
                            winCond[cellPos] = true;
                        }
                    }
                } else if (color[b - 1][a - 1] == y && color_select[y - 5].size() > 2) {
                    for (int i = 0; i < noOfColors; i++) {
                        for (int j = 0; j < color_select[i].size() - 1; j++) {
                            if (color_select[i].get(j) == cellPos + 1) {
                                for (int k = 0; k < color_select[i].size(); k++) {
                                    int p = color_select[i].get(k);
                                    winCond[p - 1] = false;
                                    if (p % cellSize == 0) {
                                        color[p / cellSize - 1][3] = 0;
                                    } else {
                                        color[p / cellSize][p % cellSize - 1] = 0;
                                    }
                                }
                                color_select[i].clear();
                                emptyCell[i] = true;
                            }
                        }
                    }
                } else if (color[b - 1][a - 1] != y && !cellVisible[cellPos]) {
                    for (int i = 0; i < noOfColors; i++) {
                        for (int j = 0; j < color_select[i].size(); j++) {
                            if (color_select[i].get(j) == cellPos + 1) {
                                for (int k = 0; k < color_select[i].size(); k++) {
                                    int p = color_select[i].get(k);
                                    winCond[p - 1] = false;
                                    if (p % cellSize == 0) {
                                        color[p / cellSize - 1][3] = 0;
                                    } else {
                                        color[p / cellSize][p % cellSize - 1] = 0;
                                    }
                                }
                                color_select[i].clear();
                            }
                        }
                    }
                    color_select[y - 5].add(cellPos + 1);
                    winCond[cellPos] = true;
                    color[b - 1][a - 1] = y;
                } else {
                    color[b - 1][a - 1] = y;
                    if (color_select[y - 5].isEmpty()) {
                        color_select[y - 5].add(cellPos + 1);
                        winCond[cellPos] = true;
                    } else {
                        if (color_select[y - 5].get(color_select[y - 5].size() - 1) != cellPos + 1) {
                            color_select[y - 5].add(cellPos + 1);
                            winCond[cellPos] = true;
                        }
                    }
                }
            }
            repaint();
        }

        for (int i = 0; i < noOfColors; i++) {
            win = true;
            for (int k = 0; k < cellSize * cellSize; k++) {
                if (!winCond[k]) {
                    win = false;
                    break;
                }
            }
            if (win) {
                winFlag = true;
            }
        }

        if (winFlag) {
            win();
        }
    }

    @Override
    public void gameInitialize() {}

    @Override
    public void gameOver() {}

    public void win(){
        JOptionPane.showMessageDialog(FixLights.this,"Successfully fixed lights!");
        close();
        removeTask();
        game.itemManager.addItem(new TaskStarter("Foxy Run", 25, 31, game));
    }

    @Override
    public void gameRestart() {}

    @Override
    public void mouseReleased(MouseEvent e) {
        playSE(17);

        for (int i = 0; i < noOfColors; i++) {
            if (emptyCell[i]) {
                for (int k = 0; k < color_select[i].size(); k++) {
                    int p = color_select[i].get(k);
                    winCond[p - 1] = false;
                    if (p % cellSize == 0) {
                        color[p / cellSize - 1][3] = 0;
                    } else {
                        color[p / cellSize][p % cellSize - 1] = 0;
                    }
                }
                color_select[i].clear();
                emptyCell[i] = false;
            }
        }
        flag = true;
        y = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
}