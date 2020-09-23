package entity;

import game.Handler;
import game.Id;
import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class Entity {
    private int x, y;
    private int width, height;
    boolean jumping = false;
    boolean falling = true;
    double gravity = 0.0;
    protected double velX, velY;
    private Handler handler;
    public Id id;

    public Entity(int x, int y, int width, int height, Id id, Handler handler) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public abstract void render(Graphics g);
    public abstract void update();

    public void die(){
        handler.removeEntity(this);
    }
    public Id getId() {
        return id;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
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



    public double getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width -30, height-30);
    }

    public Rectangle getBoundsTop(){
        return new Rectangle(getX() + 10, getY(), width -20, 5);
    }

    public Rectangle getBoundBottom(){
        return new Rectangle(getX() + 10, getY() + height -5 , width -20 , 5);
    }

    public Rectangle getBoundLeft(){
        return new Rectangle(getX(), getY() + 10, 5, height - 20);
    }

    public Rectangle getBoundRight(){
        return new Rectangle(getX() + width-5, getY() + 10, 5, height - 20);
    }
}
