package tile;

import entity.PowerUp;
import game.Game;
import game.Handler;
import game.Id;
import sprites.Sprite;

import java.awt.*;

public class PowerUpBlock extends Tile {

    private boolean isPopped = false;

    private int spriteY = getY();

    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);

    }

    @Override
    public void render(Graphics g) {
        if (!isPopped) g.drawImage(Game.getPowerUp().getSprite(), getX(), spriteY, getWidth(), getHeight(), null);

        if (!isActivated())
            g.drawImage(Game.getPowerUpBlock()[0].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        else g.drawImage(Game.getPowerUpBlock()[1].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void update() {
        if (isActivated() && !isPopped) {
            spriteY--;
            if (spriteY <= getY() - getHeight()) {
                handler.addEntity(new PowerUp(getX(), spriteY, 64, 64, Id.powerUp, handler));
                isPopped = true;
            }
        }

    }
}
