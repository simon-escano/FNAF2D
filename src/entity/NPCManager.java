package entity;

import main.Game;

import java.awt.*;

public class NPCManager {
    Game game;
    public NPCManager(Game game) {
        this.game = game;
    }

    public void loadNPC() {
        game.npc = new NPC[10];
        game.npc[0] = new Freddy(game, 21, 4);
    }

    public void update() {
        for (NPC npc : game.npc) {
            if (npc != null) {
                npc.update();
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (NPC npc : game.npc) {
            if (npc != null) {
                npc.draw(graphics2D);
            }
        }
    }
}
