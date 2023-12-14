package item;

import main.Game;

import java.awt.*;

public class ItemManager {
    Game game;
    int numOfItems;
    public ItemManager(Game game) {
        this.game = game;
    }

    public void loadItems() {
        numOfItems = 0;
        game.items = new Item[500];
        addItem(new GenItem("sink.png", "Sink", 40, 20, game));
        addItem(new GenItem("sink.png", "Sink", 41, 20, game));
        addItem(new GenItem("sink.png", "Sink", 42, 20, game));
        addItem(new GenItem("sink.png", "Sink", 43, 20, game));
        addItem(new Cubicles(45, 20, game));

        addItem(new GenItem("sink.png", "Sink", 40, 7, game));
        addItem(new GenItem("sink.png", "Sink", 41, 7, game));
        addItem(new GenItem("sink.png", "Sink", 42, 7, game));
        addItem(new GenItem("sink.png", "Sink", 43, 7, game));
        addItem(new Cubicles(45, 7, game));

        addItem(new Door(18, 43, game));
        addItem(new Door(24, 43, game));
        addItem(new Door(14, 40, game));
        addItem(new Door(35, 22, game));
        addItem(new Door(35, 12, game));
        addItem(new Door(39, 12, game));
        addItem(new Door(39, 25, game));
        addItem(new Door(7, 10, game));
        addItem(new TaskStarter("Fix Bonnie", 12, 40, game));

        addItem(new Arcade("green", 30, 28, game));
        addItem(new Arcade("red", 29, 28, game));
        addItem(new Arcade("yellow", 28, 28, game));
        addItem(new Arcade("blue", 27, 28, game));

        addItem(new Arcade("blue", 30, 24, game));
        addItem(new Arcade("red", 28, 24, game));
        addItem(new Arcade("yellow", 27, 24, game));

        addItem(new Arcade("yellow", 25, 28, game));
        addItem(new Arcade("blue", 23, 28, game));
        addItem(new Arcade("green", 22, 28, game));

        addItem(new Arcade("green", 25, 24, game));
        addItem(new Arcade("blue", 24, 24, game));
        addItem(new Arcade("red", 23, 24, game));
        addItem(new Arcade("green", 22, 24, game));

        addItem(new WhackMachine(20, 27, game));

        addItem(new Seat(21, 43, game));

        addItem(new TableAndChairs(11, 10, game));
        addItem(new TableAndChairs(11, 16, game));
        addItem(new TableAndChairs(11, 22, game));

        addItem(new TableAndChairs(19, 10, game));
        addItem(new TableAndChairs(19, 16, game));

        addItem(new TableAndChairs(27, 10, game));
        addItem(new TableAndChairs(27, 16, game));

        addItem(new BigItem("storagerack", 10, 37, 1, 5, game));
        addItem(new BigItem("broom", 13, 37, 1, 3, game));

        addItem(new BigItem("guardtable", 19, 39, 5, 2, game));

        addItem(new BigItem("backstagecabinet", 1, 11, 1, 5, game));
        addItem(new BigItem("backstagetable", 3, 10, 2, 5, game));

        addItem(new BigItem("kitchencabinets1", 29, 35, 1, 7, game));
        BigItem kitchenCabinets2 = addItem(new BigItem("kitchencabinets2", 30, 40, 2, 2, game));
        kitchenCabinets2.parts[0][0].setSolidArea(new Rectangle(0, game.tileSize/2, game.tileSize, game.tileSize/2));
        kitchenCabinets2.parts[1][0].setSolidArea(new Rectangle(0, game.tileSize/2, game.tileSize, game.tileSize/2));
        kitchenCabinets2.parts[0][0].top = true;
        kitchenCabinets2.parts[1][0].top = true;
        addItem(new BigItem("refrigerator", 32, 39, 2, 3, game));
        addItem(new BigItem("kitchencabinets3", 34, 38, 1, 4, game));
    }
    
    public BigItem addItem(Item item) {
        game.items[numOfItems] = item;
        numOfItems++;
        if (item instanceof BigItem) return (BigItem) item;
        return null;
    }

    public void draw(Graphics2D graphics2D) {
        for (Item item : game.items) {
            if (item != null && !item.top) {
                item.draw(graphics2D, game);
            }
        }
    }

    public void drawTop(Graphics2D graphics2D) {
        for (Item item : game.items) {
            if (item != null && item.top) {
                item.draw(graphics2D, game);
            }
        }
    }
}
