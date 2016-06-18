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
public class Calculator
{
    Display2 dis = new Display2();
    public Calculator()
    {
        // initialise instance variables
        JFrame frame = new JFrame("3D Grapher");
        frame.setFocusable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(dim);
        dis.setPreferredSize(dim);
        frame.add(dis);
                frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                boolean changed = false;
                int num = e.getKeyCode();
                switch(num){
                case 8:
                if(dis.instructions.length()==0){changed = true; break;}
                dis.instructions = dis.instructions.substring(0,dis.instructions.length()-1);
                changed = true;
                break;
                case 20:
                dis.upperOrLower *= -1;
                changed = true;
                break;
                case KeyEvent.VK_PERIOD:
                dis.instructions += ".";
                changed = true;
                break;
                case KeyEvent.VK_MINUS:
                dis.instructions += "-";
                changed = true;
                break;
                case KeyEvent.VK_EQUALS:
                if(dis.upperOrLower == 1){dis.instructions+= "+";}
                else{dis.instructions+= "=";}
                changed = true;
                break;
                case KeyEvent.VK_8:
                if(dis.upperOrLower == 1){dis.instructions+= "*";}
                changed = true;
                break;
                case KeyEvent.VK_COMMA:
                dis.instructions+= ",";
                changed = true;
                break;
                }
                boolean helper =((num<91&&num>=48)||num == 32);
                if((!changed)&&helper){
                String holder = Character.toString((char)num);
                if(dis.upperOrLower<0){if(num!=57&&num!=48){holder = holder.toLowerCase();}else{holder = ((num == 57? "(":")"));}}
                dis.instructions+= holder;
                changed = true;}
                if(changed){dis.repaint();}
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        dis.repaint();
    }

}
