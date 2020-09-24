package entity;

import game.Game;
import game.Handler;
import game.Id;
import sprites.Sprite;
import tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;

public class Player extends Entity {
    private int frameStanding = 0;
    private int frameMovement = 0;
    private int framJumping = 0;
    private int frameDelay = 0;
    private int frameGround = 0;
    private int frameHit = 0;
    private int frameDelayJumping = 0;
    int facing = 1; //1 - right, 0- left
    private boolean checkFalling;
    private boolean meetingTheGround = true;
    private boolean stopedMovingRight = false;
    private boolean stopedMovingLeft = false;
    boolean iGotHit = false;
    private int bonus = 1;

    public Player(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        ;
    }

    @Override
    public void render(Graphics g) {
        Sprite player[] = Game.getNoMovement();
        Sprite movementRight[] = Game.getMovementRight();
        Sprite movementLeft[] = Game.getMovementLeft();
        Sprite jumpRight[] = Game.getJumpingRight();
        Sprite jumpLeft[] = Game.getJumpingLeft();
        Sprite hitLeft[] = Game.getHitedLeft();
        Sprite hitRight[] = Game.getHitedRight();
        if (iGotHit) {
            if (facing == 1)
                g.drawImage(hitLeft[(frameHit)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
            else
                g.drawImage(hitRight[(frameHit)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        } else if (isJumping() || checkFalling || meetingTheGround) {
            if (facing == 1)
                g.drawImage(jumpRight[(framJumping)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
            else
                g.drawImage(jumpLeft[(framJumping)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        } else if (velX != 0) {
            if (facing == 1)
                g.drawImage(movementRight[(frameMovement)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
            else
                g.drawImage(movementLeft[(frameMovement)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
        } else
            g.drawImage(player[(frameStanding)].getSprite(), getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void update() {
        setX(getX() + (int) getVelX());
        setY(getY() + (int) getVelY());

        for (Tile tile : getHandler().tile) {
            if (tile.solid) {
                if (getBoundsTop().intersects(tile.getBounds()) && tile.getId() != Id.coin) {
                    setVelY(0);
                    if (jumping) {
                        setJumping(false);
                        setGravity(1);
                        setFalling(true);
                        setCheckFalling(true);
                    }
                    if (tile.getId() == Id.powerUpBlock) {
                        tile.setActivated(true);
                    }
                }

                if (getBoundBottom().intersects(tile.getBounds()) && tile.getId() != Id.coin && !jumping) {
                    setVelY(0);
                    gravity = 0;
                    setY(tile.getY() - getHeight());
                    framJumping = 5;
                    if (checkFalling)
                        meetingTheGround = true;
                    setCheckFalling(false);
                } else {
                    if (!falling && !jumping) {
                        setGravity(1);
                        setFalling(true);
                    }
                }

                if (getBoundLeft().intersects(tile.getBounds()) && tile.getId() != Id.coin) {
                    setVelX(0);
                    setX(tile.getX() + tile.getWidth());
                }

                if (getBoundRight().intersects(tile.getBounds()) && tile.getId() != Id.coin) {
                    setVelX(0);
                    setX(tile.getX() - getWidth());
                }


                try {
                    for (int i = 0; i < getHandler().entity.size(); ++i) {
                        Entity en = getHandler().entity.get(i);

                        if (en.getId() == Id.powerUp) {
                            if (getBounds().intersects(en.getBounds())) {
                                setWidth((int) (getWidth() * 1.3));
                                setHeight((int) (getHeight() * 1.3));
                                bonus++;
                                en.die();
                            }
                        } else if (en.getId() == Id.enemy) {
                            if (getBoundBottom().intersects(en.getBoundsTop())) {
                                en.die();
                                getHandler().entity.remove(en);
                            } else if (getBounds().intersects((en.getBounds()))) {
                                if (bonus <= 0) {
                                    JFrame frame = new JFrame("Notice");
                                    JOptionPane.showMessageDialog(frame,
                                            "GAME OVER");
                                    this.die();
                                    System.exit(0);

                                } else {
                                    bonus--;
                                    iGotHit = true;

                                    setWidth((int) (getWidth() * (1.0 / 1.3)));
                                    setHeight((int) (getHeight() * (1.0 / 1.3)));

                                    if (facing == 0) {
                                        setX(getX() + 30);
                                        setVelX(8);
                                    } else {
                                        setX(getX() - 10);
                                        setVelX(-8);
                                    }
                                }


                            }

                        }
                    }
                } catch (ConcurrentModificationException e) {

                    System.out.println(e.getMessage());
                }

                if (jumping) {
                    gravity -= 0.004;

                    setVelY(((int) -gravity));
                    if (gravity <= 0) {
                        jumping = false;
                        checkFalling = true;
                        falling = true;
                    }
                }
                if (falling) {
                    gravity += 0.007;
                    setVelY((int) gravity);
                }

                if (velX == 0 && !meetingTheGround) {           //animatie caracter fara viteza
                    frameDelay++;
                    if (frameDelay > 2000) {
                        frameStanding = (frameStanding + 1) % 2;
                        frameDelay = 0;
                    }
                } else {
                    frameGround++;                  //animatie atingere pamant (dupa cadere)
                    if (frameGround > 1500) {
                        frameGround = 0;
                        meetingTheGround = false;
                    }
                }


                if (velX > 0 || velX < 0) {             //aninmatie alergare
                    frameDelay++;
                    if (frameDelay > 500) {
                        frameMovement = (frameMovement + 1) % 8;
                        frameDelay = 0;
                    }
                }

                if (stopedMovingRight && !iGotHit) {
                    velX -= 0.0015;                 //animatie de oprire din miscarea catre dreapta (x axis)
                    if (velX < 2)
                        frameMovement = 10;

                    if (velX <= 0) {
                        velX = 0;
                        stopedMovingRight = false;
                    }
                }

                if (stopedMovingLeft && !iGotHit) {
                    velX += 0.0015;             //animatie de oprire din miscarea catre stanga (x axis)
                    if (velX > -2)
                        frameMovement = 10;

                    if (velX >= 0) {
                        velX = 0;
                        stopedMovingLeft = false;
                    }
                }

                if (isJumping()) {
                    if (gravity > 8)           //iterare prin frame-urile animatiei de salt
                        framJumping = 0;
                    else if (gravity > 4)
                        framJumping = 1;
                    else if (gravity > 0)
                        framJumping = 2;
                }

                if (isCheckFalling()) {
                    if (gravity > -3)       //iterare prin frame-urile animatiei de cadere
                        framJumping = 3;
                    else framJumping = 4;
                }

                if (iGotHit) {
                    if (facing == 0) {
                        velX -= 0.002;
                        if (velX <= 0) {
                            velX = 0;
                            iGotHit = false;
                        }
                        if (velX > 6) {
                            frameHit = 3;

                        } else if (velX > 4)
                            frameHit = 2;
                        else if (velX > 2)
                            frameHit = 1;
                        else if (velX > 0)
                            frameHit = 0;
                    } else {
                        velX += 0.002;
                        if (velX >= 0) {
                            velX = 0;
                            iGotHit = false;
                        }
                        if (velX < -6) {
                            frameHit = 3;

                        } else if (velX < -4)
                            frameHit = 2;
                        else if (velX < -2)
                            frameHit = 1;
                        else if (velX < 0)
                            frameHit = 0;
                    }
                }
            } else {
                if (getBounds().intersects(tile.getBounds())) {
                    if (tile.getId() == Id.flag) {
                        System.out.println("luci");
                        Game.increaseLevel();
                    }
                }
                if (tile.id == Id.coin) {
                    if (getBounds().intersects(tile.getBounds())) {
                        tile.die();
                        Game.increaseNumCoins();
                    }
                }
            }
        }
    }

    public boolean isCheckFalling() {
        return checkFalling;
    }

    public void setCheckFalling(boolean checkFalling) {
        this.checkFalling = checkFalling;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isStopedMovingRight() {
        return stopedMovingRight;
    }

    public boolean isStopedMovingLeft() {
        return stopedMovingLeft;
    }

    public boolean isiGotHit() {
        return iGotHit;
    }

    public void setStopedMovingLeft(boolean stopedMovingLeft) {
        this.stopedMovingLeft = stopedMovingLeft;
    }

    public void setStopedMovingRight(boolean stopedMovingRight) {
        this.stopedMovingRight = stopedMovingRight;
    }
}
