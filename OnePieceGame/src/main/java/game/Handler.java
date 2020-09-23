package game;

import entity.Entity;
import tile.Tile;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Handler {
    public LinkedList<Entity> entity = new LinkedList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();
    boolean first = false;

    public void render(Graphics g) {
        for (Entity en : entity) {
            en.render(g);
        }

        for (Tile til : tile) {
            til.render(g);

        }
    }

    public void update() {
        try {
            for (Entity en : entity) {
                en.update();
            }
        } catch (ConcurrentModificationException e) {
        }
        for (Tile til : tile) {
            til.update();
        }
    }

    public void addEntity(Entity en) {
        entity.add(en);
    }

    public void removeEntity(Entity en) {
        entity.remove(en);
    }

    public void addTile(Tile til) {
        tile.add(til);
    }

    public void removeTile(Tile til) {
        tile.remove(til);
    }
}
