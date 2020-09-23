package game;

import entity.Entity;

public class Camera {
    private int x;
    private int y;

    public void update(Entity player){
        x = Game.getWIDTH()/2 - player.getX();
        y = Game.getHEIGHT()/2 -player.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
