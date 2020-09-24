package input;

import entity.Entity;
import entity.Player;
import game.Game;
import game.Id;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private boolean isPressedA = false;
    private boolean isPressedD = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for (Entity en : Game.handler.entity) {
            if (en.getId() == Id.player) {
                switch (key) {
                    case KeyEvent.VK_W:
                        if (!en.isJumping()) {
                            en.setJumping(true);
                            en.setFalling(false);
                            en.setGravity(14.0);

                        }
                        break;

                    case KeyEvent.VK_A:
                        if(!((Player) en).isiGotHit()) {
                            isPressedA = true;
                            if (((Player) en).isStopedMovingRight())
                                ((Player) en).setStopedMovingRight(false);
                            if (((Player) en).isStopedMovingLeft())
                                ((Player) en).setStopedMovingLeft(false);
                            ((Player) en).setFacing(0);

                            en.setVelX(-5);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if(!((Player) en).isiGotHit()) {
                            isPressedD = true;
                            if (((Player) en).isStopedMovingRight())
                                ((Player) en).setStopedMovingRight(false);
                            if (((Player) en).isStopedMovingLeft())
                                ((Player) en).setStopedMovingLeft(false);
                            ((Player) en).setFacing(1);

                            en.setVelX(5);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for (Entity en : Game.handler.entity) {
            if (en.getId() == Id.player) {
                switch (key) {
                    case KeyEvent.VK_A:

                        if (!isPressedD && !((Player) en).isiGotHit())
                            if (!((Player) en).isStopedMovingLeft())
                                ((Player) en).setStopedMovingLeft(true);
                        isPressedA = false;
                        break;
                    case KeyEvent.VK_D:

                        if (!isPressedA && !((Player) en).isiGotHit())
                            if (!((Player) en).isStopedMovingRight())
                                ((Player) en).setStopedMovingRight(true);
                        isPressedD = false;
                        break;

                }
            }
        }
    }
}
