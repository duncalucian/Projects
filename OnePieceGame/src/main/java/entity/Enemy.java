package entity;

import game.Game;
import game.Handler;
import game.Id;
import sprites.Sprite;
import tile.Tile;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
    private int facing = 1; // 1 - right, 0 left
    private int frameMovement = 0;
    private int frameDelay = 0;

    public Enemy(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        Random rand = new Random();
        int direction = rand.nextInt(2);
        if (direction == 1) {
            facing = 1;
            setVelX(2);
        } else {
            facing = 0;
            setVelX(-2);
        }
    }

    @Override
    public void render(Graphics g) {
        Sprite movementRight[] = Game.getEnemyMovementRight();
        Sprite movementLeft[] = Game.getEnemyMovementLeft();
        if (facing == 1)
            g.drawImage(movementRight[(frameMovement)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        else
            g.drawImage(movementLeft[(frameMovement)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void update() {
        setX(getX() + (int) getVelX());
        setY(getY() + (int) getVelY());


        for (Tile tile : getHandler().tile) {
            if (getBoundBottom().intersects(tile.getBounds())) {
                setVelY(0);
                gravity = 0;
                setY(tile.getY() - getHeight());
                if (falling) falling = false;
            } else {
                if (!falling) {
                    setGravity(1);
                    setFalling(true);
                }
            }

            if (getBoundLeft().intersects(tile.getBounds())) {
                setVelX(2);
                facing = 1;
            }

            if (getBoundRight().intersects(tile.getBounds())) {
                setVelX(-2);
                facing = 0;
            }
        }


        if (velX > 0 || velX < 0) {             //aninmatie alergare
            frameDelay++;
            if (frameDelay > 20) {
                frameMovement = (frameMovement + 1) % 8;
                frameDelay = 0;
            }
        }

        if (falling) {
            gravity += 0.5;
            setVelY((int) gravity);
        }
    }


}
