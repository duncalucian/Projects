package game;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import entity.PowerUp;
import input.Keyboard;
import sprites.Sprite;
import sprites.SpriteSheet;
import tile.Coin;
import tile.PowerUpBlock;
import tile.Tile;
import tile.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    private static final int HEIGHT = 640;
    private static final int WIDTH = 896;
    private static int numCoins;
    private boolean flag = false;
    public static Handler handler;
    private static Thread thread;
    private static SpriteSheet sheet = null;
    private static SpriteSheet characterSheet = null;
    private static SpriteSheet sheetPowerUp = null;
    private static SpriteSheet sheetEnemy = null;
    private static SpriteSheet sheetCoin = null;

    private static BufferedImage img = null;
    private static BufferedImage coinSprite = null;
    private static Sprite grass = null;
    private static Sprite noMovement[] = new Sprite[3];
    private static Sprite movementRight[] = new Sprite[11];
    private static Sprite movementLeft[] = new Sprite[11];
    private static Sprite jumpingRight[] = new Sprite[6];
    private static Sprite jumpingLeft[] = new Sprite[6];
    private static Sprite hitedLeft[] = new Sprite[4];
    private static Sprite hitedRight[] = new Sprite[4];
    private static Sprite powerUp = new Sprite();

    private static Sprite powerUpBlock[] = new Sprite[2];
    private static Sprite enemyMovementRight[] = new Sprite[8];
    private static Sprite enemyMovementLeft[] = new Sprite[8];
    int[] movXRight = {0, 45, 90, 150, 200, 250, 300, 365, 425, 500, 560};
    int[] movXLeft = {580, 535, 470, 420, 370, 320, 260, 210, 130, 60, 0};
    int[] movWidthRightLeft = {45, 45, 55, 45, 40, 50, 60, 55, 65, 60, 55};
    int[] movHited = {395, 460, 510, 550};
    int[] movHited2 = {595, 640, 680, 730};
    int[] movWidthHited = {65, 50, 40, 40};
    Camera cam;

    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void init() {

        handler = new Handler();
        cam = new Camera();
        addKeyListener(new Keyboard());
        try {
            sheet = new SpriteSheet("/sprites.png");
            characterSheet = new SpriteSheet("/luffy.png");
            sheetPowerUp = new SpriteSheet("/powerUp.png");
            sheetEnemy = new SpriteSheet("/kroko.png");


            img = ImageIO.read(getClass().getResource("/first.png"));

            int i;
            for (i = 0; i < jumpingRight.length; ++i) {
                jumpingRight[i] = new Sprite();
                jumpingRight[i].setJumpingSpriteRight(characterSheet, i, 40);
                jumpingLeft[i] = new Sprite();
                jumpingLeft[i].setJumpingSpriteLeft(characterSheet, i, 40);
            }
            for (i = 0; i < noMovement.length; ++i) {
                noMovement[i] = new Sprite();
                noMovement[i].setSpriteStandint(characterSheet, i, 0);
            }

            for (i = 0; i < movementRight.length; ++i) {
                movementRight[i] = new Sprite();
                movementRight[i].setSpriteMovingRight(characterSheet, movXRight[i], movWidthRightLeft[i]);
            }
            for (i = 0; i < movementLeft.length; ++i) {
                movementLeft[i] = new Sprite();
                movementLeft[i].setSpriteMovingLeft(characterSheet, movXLeft[i], movWidthRightLeft[i]);
            }
            for (i = 0; i < enemyMovementRight.length; ++i) {
                enemyMovementRight[i] = new Sprite();
                enemyMovementRight[i].setEnemySprite(sheetEnemy, i, 0);
                enemyMovementLeft[i] = new Sprite();
                enemyMovementLeft[i].setEnemySprite(sheetEnemy, i + 8, 0);
            }
            for (i = 0; i < movHited.length; ++i) {
                hitedLeft[i] = new Sprite();
                hitedLeft[i].setHitedSprite(characterSheet, movHited[i], movWidthHited[i]);
                hitedRight[i] = new Sprite();
                hitedRight[i].setHitedSprite(characterSheet, movHited2[i], movWidthHited[3-i]);
            }
            powerUp = new Sprite();
            powerUp.setPowerUpSprite(sheetPowerUp, 0, 0);

            coinSprite = ImageIO.read(getClass().getResource("/coin.png"));

            powerUpBlock[0] = new Sprite();
            powerUpBlock[1] = new Sprite();
            powerUpBlock[0].setPowerUpBlock(sheet, 0, 0);
            powerUpBlock[1].setPowerUpBlock(sheet, 1, 0);

            grass = new Sprite(sheet, 0, 0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        int i = 0, j = 0;
        for (i = 0; i < img.getHeight(); ++i)
            for (j = 0; j < img.getWidth(); ++j) {
                int rgb = img.getRGB(j, i);

                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                if (red == 0 && green == 255 && blue == 0)
                    handler.addTile(new Wall(j * 64, i * 64, 64, 64, true, Id.wall, handler));
                if (red == 0 && green == 0 && blue == 255)
                    handler.addTile(new PowerUpBlock(j * 64, i * 64, 64, 64, true, Id.powerUpBlock, handler));

                if (red == 255 && green == 0 && blue == 255)
                    handler.addTile(new Coin(j * 64, i * 64, 64, 64, false, Id.coin, handler));

                if (red == 255 && green == 0 && blue == 0)
                    handler.addEntity(new Player(j * 64 - 50, i * 64 - 50, 64, 64, Id.player, handler));
                if (red == 255 && green == 255 && blue == 0)
                    handler.addEntity(new Enemy(j * 64, i * 64, 64, 64, Id.enemy, handler));


            }

        // handler.addEntity(new PowerUp(200, 64, 64  , 64, true, Id.powerUp, handler));
        // handler.addTile(new Wall(200, 64, 64, 64, true, Id.wall, handler));
        // handler.addTile(new Wall(300, HEIGHT - 192, 400, 64, true, Id.wall, handler));

    }

    public synchronized void start() {
        if (flag == true)
            return;
        flag = true;
        thread = new Thread(this::run);
        thread.start();
    }


    public void stop() {
        if (flag != true)
            return;
        flag = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long last = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000 / 60.0;

        while (flag) {
            long now = System.nanoTime();
            delta += (now - last) / ns;
            last = now;
            while (delta > 1) {
                update();
                delta--;
            }
            renderer();

        }
        stop();
    }

    private void renderer() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(coinSprite, 30, 20, 90, 90, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD, 50));
        g.drawString("x " + numCoins, 130, 100);
        g.translate(cam.getX(), cam.getY());
        handler.render(g);
        g.dispose();
        bs.show();
    }

    public static Sprite getGrass() {
        return grass;
    }

    public static Sprite[] getNoMovement() {
        return noMovement;
    }

    private void update() {
        handler.update();

        for (Entity ent : handler.entity) {

            if (ent.getId() == Id.player)
                cam.update(ent);
        }
    }

    public static Sprite[] getMovementRight() {
        return movementRight;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static Sprite[] getMovementLeft() {
        return movementLeft;
    }

    public static Sprite[] getJumpingRight() {
        return jumpingRight;
    }

    public static void setJumpingRight(Sprite[] jumpingRight) {
        Game.jumpingRight = jumpingRight;
    }

    public static Sprite[] getJumpingLeft() {
        return jumpingLeft;
    }

    public static void setJumpingLeft(Sprite[] jumpingLeft) {
        Game.jumpingLeft = jumpingLeft;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static Sprite getPowerUp() {
        return powerUp;
    }

    public static Sprite[] getEnemyMovementRight() {
        return enemyMovementRight;
    }

    public static Sprite[] getEnemyMovementLeft() {
        return enemyMovementLeft;
    }


    public static void increaseNumCoins() {
        numCoins++;
    }

    public static void setPowerUp(Sprite powerUp) {
        Game.powerUp = powerUp;
    }

    public static Sprite[] getPowerUpBlock() {
        return powerUpBlock;
    }

    public static BufferedImage getCoinSprite() {
        return coinSprite;
    }

    public static Sprite[] getHitedRight() {
        return hitedRight;
    }

    public static Sprite[] getHitedLeft() {
        return hitedLeft;
    }
}
