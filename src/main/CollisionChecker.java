package main;

import entity.Entity;

public class CollisionChecker {
    Game game;
    public CollisionChecker(Game game) {
        this.game = game;
    }

    public void checkTile(Entity entity) {
        int entityLeftMapX = entity.mapX + entity.solidArea.x;
        int entityRightMapX = entity.mapX + entity.solidArea.x + entity.solidArea.width;
        int entityTopMapY = entity.mapY + entity.solidArea.y;
        int entityBottomMapY = entity.mapY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftMapX / game.tileSize;
        int entityRightCol = entityRightMapX / game.tileSize;
        int entityTopRow = entityTopMapY / game.tileSize;
        int entityBottomRow = entityBottomMapY / game.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopMapY - entity.speed) / game.tileSize;
                tileNum1 = game.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = game.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                if (game.tileManager.tiles[tileNum1].collision || game.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomMapY - entity.speed) / game.tileSize;
                tileNum1 = game.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = game.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (game.tileManager.tiles[tileNum1].collision || game.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftMapX - entity.speed) / game.tileSize;
                tileNum1 = game.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = game.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if (game.tileManager.tiles[tileNum1].collision || game.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightMapX - entity.speed) / game.tileSize;
                tileNum1 = game.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = game.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (game.tileManager.tiles[tileNum1].collision || game.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
