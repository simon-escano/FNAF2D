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
        game.items = new Item[50];
        addItem(new Mask(23, 9, game));
        addItem(new GenItem("sink.png", "Sink", 41, 18, game));
        addItem(new GenItem("toilet.png", "Toilet", 43, 20, game));
        addItem(new GenItem("sink.png", "Sink", 41, 12, game));
        addItem(new GenItem("toilet.png", "Toilet", 43, 14, game));
        addItem(new Door(18, 45, game));
        addItem(new Door(24, 45, game));
        addItem(new Door(14, 40, game));
        addItem(new Door(35, 22, game));
        addItem(new Door(35, 12, game));
        addItem(new Door(39, 14, game));
        addItem(new Door(39, 20, game));
        addItem(new Door(7, 10, game));
        addItem(new TaskStarter("Fix Bonnie", 12, 40, game));
    }
    
    public void addItem(Item item) {
        game.items[numOfItems] = item;
        numOfItems++;
    }

    public void draw(Graphics2D graphics2D) {
        for (Item item : game.items) {
            if (item != null) {
                item.draw(graphics2D, game);
            }
        }
    }
}
