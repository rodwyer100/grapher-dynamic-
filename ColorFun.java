import javax.swing.*;
import java.awt.*;
public class ColorFun
/** Rory O'Dwyer with the aid of Chris Kjellqvist. Math concepts borrowed from desmos calculator project 12.6.15
 * Please reference all the creators of this project if code is borrowed.
 */
{
    public static int numberOfColors = 10000;
    public static float[] color1 = Color.RGBtoHSB(0, 0, 255, null);
    public float[] color2 = Color.RGBtoHSB(0, 0, 0, null);
    public Color[] colorArray = getColorPallete(numberOfColors, color1, color2);
    public ColorFun(){
    }
    public Color[] getColorPallete(int n, float[] c1, float[] c2){
            Color[] ar = new Color[n];
        for (int i = 0; i < n; i++) {
            //How much of color1 and how much of color2? Depends on the ratio
                       //you tell the combineHSB method to mix them. In this case, it is
            // (float) i/n.
            float[] temp = combineHSB(c1, c2, (float) i / n);
            //Convert the HSB value back into RGB so you can use it.
            ar[i] = new Color(Color.HSBtoRGB(temp[0], temp[1], temp[2]));
        }
        return ar;
    }
        private static float[] combineHSB(float[] c1, float[] c2, float ratio) {
        float[] toReturn = new float[3];
        for (int i = 0; i < 3; i++) {
            toReturn[i] = c1[i] * (1 - ratio) + c2[i] * (ratio);
        }
        return toReturn;
    }
}
