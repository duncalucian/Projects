package tile;

import game.Handler;
import game.Id;

import java.awt.*;

public abstract class Tile {
    private int x, y;
    private int width, height;
    public boolean solid;
    private boolean activated = false;
    int velX, velY;
    public Id id;
    Handler handler;
    public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void render(Graphics g);
    public abstract void update();


    public Id getId() {
        return id;
    }
    public void die(){
        handler.removeTile(this);
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
