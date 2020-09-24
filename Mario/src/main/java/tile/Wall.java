package tile;

import game.Game;
import game.Handler;
import game.Id;

import java.awt.*;

public class Wall extends Tile{

    public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Game.getGrass().getSprite(), getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void update() {

    }
}
