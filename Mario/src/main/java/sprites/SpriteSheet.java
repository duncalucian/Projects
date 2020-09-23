package sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sprite;

    public SpriteSheet(String path) {
        try {
            sprite = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSpriteWalls(int x, int y) {
        return sprite.getSubimage(x * 32, y * 32, 32, 32);
    }

    public BufferedImage getSpriteCharacter(int x, int y) {
        return sprite.getSubimage(x * 50, y * 80, 50, 80);
    }

    public BufferedImage getJumpingSpriteRight(int x, int width) {
        return sprite.getSubimage(x * 42, 150, width, 60);
    }

    public BufferedImage getJumpingSpriteLeft(int x, int width) {
        return sprite.getSubimage(410 - x * 42, 285, width, 60);
    }

    public BufferedImage getSpriteMovingRight(int x, int width) {
        return sprite.getSubimage(x, 85, width, 55);
    }

    public BufferedImage getSpriteMovingLeft(int x, int width) {
        return sprite.getSubimage(x, 220, width, 60);
    }

    public BufferedImage getSpritePowerUp(int x, int y) {
        return sprite.getSubimage(x, y, 700, 700);
    }

    public BufferedImage getSpriteEnemy(int x, int y) {
        return sprite.getSubimage(x * 50, y * 75, 50, 60);
    }

    public BufferedImage getPowerUpBlock(int x, int y) {
        return sprite.getSubimage(x * 37, 70, 37, 30);
    }

    public BufferedImage setHitedSprite(int x, int width) {
        return sprite.getSubimage(x, 160, width, 55);
    }
}
