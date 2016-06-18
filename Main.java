import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 3D graphing Software for Stuff. It still needs some patches
 * Rory O'Dwyer with the aid of Chris Kjellqvist. Math concepts borrowed from desmos calculator project 12.6.15
 * Please reference all the creators of this project if code is borrowed.
 * 6/14/15
 */
public class Main
{
    public static final int UP = KeyEvent.VK_UP;
    public static final int DOWN = KeyEvent.VK_DOWN;
    public static final int RIGHT = KeyEvent.VK_RIGHT;
    public static final int LEFT = KeyEvent.VK_LEFT;
    public static final int SPACE = KeyEvent.VK_SPACE;
    public static final int ONE = KeyEvent.VK_1;

    public static void main(String [] args)
    {
        // initialise instance variables
        //The Key for a Rotation is a,b,or c, and to switch their directions its O"+"\nThe Keys for Changing the Light Source are WER for XYZ respectively
        JFrame frame = new JFrame("3D Grapher");
        Display display = new Display();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(dim);
        display.setPreferredSize(dim);
        frame.add(display);
                frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                boolean changed = false;
                //Every time a key is pressed, the program looks right here
                //the program knows what key is pressed, it is stored as e (above)
                //switch(...) means you can take the ... and depending on what it does,
                //do different things for every case that it can be.
                //e.getKeyCode() returns the number that is associated with the key

                switch (e.getKeyCode()) {
                    case UP:
                        display.yTranslation -= display.scale * 15;//You subtract here to go up because java goes 0 down for the y axis
                        changed = true;
                        break;
                    case DOWN:
                        display.yTranslation += display.scale * 15;
                        changed = true;
                        break;
                    case RIGHT:
                        display.xTranslation += display.scale * 15;
                        changed = true;
                        break;
                    case LEFT:
                        display.xTranslation -= display.scale * 15;
                        changed = true;
                        break;
                    case KeyEvent.VK_EQUALS:
                        display.zoom(.9);//multiplys the scale by .9 to make smaller
                        changed = true;
                        break;
                    case KeyEvent.VK_MINUS:
                        display.zoom(1.1);
                        changed = true;
                        break;
                    case KeyEvent.VK_A:
                        display.aRotation+=(display.multiplier*.01);
                        changed = true;
                        break;
                    case KeyEvent.VK_B:
                        display.bRotation+=(display.multiplier*.01);
                        changed = true;
                        break;
                    case KeyEvent.VK_C:
                        display.cRotation+=(display.multiplier*.01);
                        changed = true;
                        break;
                    case KeyEvent.VK_O:
                        display.multiplier*=-1;
                        changed = true;
                        break;
                    case KeyEvent.VK_Q:
                        display.cubeVision+=1*(display.multiplier);
                        changed = true;
                        break;
                    case KeyEvent.VK_X:
                        display.sX+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case KeyEvent.VK_Y:
                        display.sY+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case KeyEvent.VK_Z:
                        display.sZ+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case KeyEvent.VK_W:
                        display.lightX+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case KeyEvent.VK_E:
                        display.lightY+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case KeyEvent.VK_R:
                        display.lightZ+=(display.multiplier*display.scale*5);
                        changed = true;
                        break;
                    case SPACE:
                        if(display.multiplier<0){display.pointSpace*= .1;}
                        else{display.pointSpace*=10;}
                        changed = true;
                        break;
                }
                if (changed) {
                    display.repaint();
                    changed = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        display.repaint();
    }

}
