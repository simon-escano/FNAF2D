package tile;

import main.Game;

import java.awt.*;
import java.io.*;

public class TileManager {
    Game game;
    public Tile[] tiles;
    public int[][] mapTileNumber;

    public TileManager(Game game) {
        this.game = game;
        tiles = new Tile[50];
        mapTileNumber = new int[game.map.cols][game.map.rows];
        loadTiles();
        loadMap("map.txt");
    }

    public void loadTiles() {
        tiles[0] = new Tile("air.png");
        tiles[1] = new Tile("wall01.png", true);
        tiles[2] = new Tile("wall02.png", true);
        tiles[3] = new Tile("wall_edge_up.png", true);
        tiles[4] = new Tile("wall_edge_down.png", true);
        tiles[5] = new Tile("wall_l_01.png", true);
        tiles[6] = new Tile("wall_l_04.png", true);
        tiles[7] = new Tile("wall_l_02.png", true);
        tiles[8] = new Tile("wall_l_03.png", true);
        tiles[9] = new Tile("wall_t_down.png", true);
        tiles[10] = new Tile("wall_t_up.png", true);
        tiles[11] = new Tile("wall_t_left.png", true);
        tiles[12] = new Tile("wall_t_right.png", true);
        tiles[13] = new Tile("wall_front01.png", true);
        tiles[14] = new Tile("wall_front02.png", true);
        tiles[15] = new Tile("wall_front03.png", true);
        tiles[16] = new Tile("tile_white.png");
        tiles[17] = new Tile("tile_black.png");
        tiles[18] = new Tile("wood.png");
        tiles[19] = new Tile("tile_red.png");
        tiles[20] = new Tile("tile_purple.png");
        tiles[21] = new Tile("platform01.png", true);
        tiles[22] = new Tile("platform_left.png");
        tiles[23] = new Tile("platform_right.png");
        tiles[24] = new Tile("platform_l_01.png", true);
        tiles[25] = new Tile("platform_l_02.png", true);
        tiles[26] = new Tile("stairs.png");
        tiles[27] = new Tile("stairs_edge_01.png");
        tiles[28] = new Tile("stairs_edge_02.png");
    }

    public void loadMap(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/" + fileName);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < game.map.rows; i++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for (int j = 0; j < game.map.cols; j++) {
                    int num;
                    try {
                        num = Integer.parseInt(numbers[j]);
                    } catch (NumberFormatException n) {
                        num = 0;
                    }
                    mapTileNumber[j][i] = num;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < game.map.rows; i++) {
            for (int j = 0; j < game.map.cols; j++) {
                int k = mapTileNumber[j][i];
                int mapX = j * game.tileSize;
                int mapY = i * game.tileSize;
                int screenX = mapX - game.player.mapX + game.player.screenX;
                int screenY = mapY - game.player.mapY + game.player.screenY;

                if (mapX + game.tileSize > game.player.mapX - game.player.screenX &&
                        mapX - game.tileSize < game.player.mapX + game.player.screenX &&
                        mapY + game.tileSize > game.player.mapY - game.player.screenY &&
                        mapY - game.tileSize < game.player.mapY + game.player.screenY) {
                    graphics2D.drawImage(tiles[k].image, screenX, screenY, game.tileSize, game.tileSize, null);
                }
            }
        }
    }
}
