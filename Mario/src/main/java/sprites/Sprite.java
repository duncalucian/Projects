package sprites;

import javax.imageio.ImageIO;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {

    private BufferedImage sprite;

    public Sprite(){}
    public Sprite(SpriteSheet sheet, int x, int y) {
        sprite =  sheet.getSpriteWalls(x,y);
    }

    public void setSpriteStandint(SpriteSheet sheet, int x, int y){
        sprite = sheet.getSpriteCharacter(x, y);

    }
    public void setSpriteMovingRight(SpriteSheet sheet, int x, int width){
        sprite = sheet.getSpriteMovingRight(x, width);
    }

    public void setSpriteMovingLeft(SpriteSheet sheet, int x, int width){
        sprite = sheet.getSpriteMovingLeft(x, width);

    }

    public void setJumpingSpriteRight(SpriteSheet sheet, int x, int width){
        sprite = sheet.getJumpingSpriteRight(x, width);
    }

    public void setJumpingSpriteLeft(SpriteSheet sheet, int x, int width){
        sprite = sheet.getJumpingSpriteLeft(x, width);
    }

    public void setPowerUpSprite(SpriteSheet sheet, int x, int y){
        sprite = sheet.getSpritePowerUp(x, y);
    }

    public void setEnemySprite(SpriteSheet sheet, int x, int y){
        sprite = sheet.getSpriteEnemy(x, y);
    }

    public void setPowerUpBlock(SpriteSheet sheet, int x, int y){
        sprite = sheet.getPowerUpBlock(x, y);
    }

    public void setHitedSprite(SpriteSheet sheet, int x, int width){
        sprite = sheet.setHitedSprite(x, width);
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
