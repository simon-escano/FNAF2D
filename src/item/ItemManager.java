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
        game.items = new Item[100];
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

        addItem(new Arcade("green", 30, 30, game));
        addItem(new Arcade("red", 29, 30, game));
        addItem(new Arcade("yellow", 28, 30, game));
        addItem(new Arcade("blue", 27, 30, game));

        addItem(new Arcade("blue", 30, 26, game));
        addItem(new Arcade("red", 28, 26, game));
        addItem(new Arcade("yellow", 27, 26, game));

        addItem(new Arcade("yellow", 25, 30, game));
        addItem(new Arcade("blue", 23, 30, game));
        addItem(new Arcade("green", 22, 30, game));

        addItem(new Arcade("green", 25, 26, game));
        addItem(new Arcade("blue", 24, 26, game));
        addItem(new Arcade("red", 23, 26, game));
        addItem(new Arcade("green", 22, 26, game));

        addItem(new GenItem("whack_2.png", "Whack Machine", 20, 27, game));
        addItem(new GenItem("whack_1.png", "Whack Machine", 20, 28, game));
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
