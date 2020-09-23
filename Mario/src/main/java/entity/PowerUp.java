package entity;

import game.Game;
import game.Handler;
import game.Id;
import tile.Tile;

import java.awt.*;
import java.util.Random;

public class PowerUp extends Entity {

    public PowerUp(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        Random rand = new Random();
        int direction = rand.nextInt(2);
        if(direction == 1)
            setVelX(2);
        else
            setVelX(-2);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.getPowerUp().getSprite(), getX(), getY(), getWidth(), getHeight(), null);
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
                if(falling) falling = false;
            } else {
                if (!falling) {
                    setGravity(1);
                    setFalling(true);
                }
            }

            if (getBoundLeft().intersects(tile.getBounds())) {
                setVelX(2);
            }

            if (getBoundRight().intersects(tile.getBounds())) {
                setVelX(-2);
            }
        }

        if (falling) {
            gravity += 0.5;
            setVelY((int) gravity);
        }


    }
}
