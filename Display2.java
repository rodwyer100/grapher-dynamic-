import javax.swing.*;
import javax.tools.*;
import java.awt.*;
import java.util.*;
import java.util.regex.*;
/**
 *  Rory O'Dwyer with the aid of Chris Kjellqvist. Math concepts borrowed from desmos calculator project 12.6.15
 *  Please reference all the creators of this project if code is borrowed.
 */
public class Display2 extends JPanel
{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//picels on screen
    String instructions = "Math.cos(y*x)";
    String reply = "";
    String working = "";
    double answer = -1;
    int upperOrLower = 1;
    boolean negProtocol = false;
    double x = 0;
    double y = 0;
    
    public Display2(){
    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);//clears the screen;
      g.setColor(Color.black);
      int h = (int)screenSize.getHeight();
      int w = (int)screenSize.getWidth();
      g.drawString(instructions, 50,50);
      reply = PEMDAS(instructions);
      if(reply.equals("Sorry, I dont Understand This")){working = reply;}else{working = "Is Calculating ...";}
      g.drawString(working,700, 50);
      //Now for any point in 3D, the point on this plane is composed of those transformations times the actual point
    }
    public String PEMDAS(String instr){
      try{
      String MathComplete = variableReplace(instr);
      MathComplete = mathHandler(MathComplete);
      if(negProtocol){MathComplete = negHandler(MathComplete);}
      MathComplete = parenthesis(MathComplete);
      MathComplete = MultDiv(MathComplete);
      if(MathComplete.substring(0,2).equals("+-")){MathComplete = MathComplete.substring(1,MathComplete.length());}
      return AddSub(MathComplete);
    }
      catch(Exception e){return "Sorry, I dont Understand This";}
    }
    public String mathHandler(String instr){
      Matcher m = Pattern.compile("Math\\.\\w{1,5}\\([^\\(]{1,7}\\)").matcher(instr);
      ArrayList<String> allMatches = new ArrayList<String>();
      while(m.find()){
        String replacement = ""+findOperation(m.group());
        instr = m.replaceFirst(replacement);
        m = Pattern.compile("Math\\.\\w{1,5}\\([^\\(]{1,7}\\)").matcher(instr);
      }
      return instr;
     //if doesn't contain any math, return inst
     //Find instances of "Math.)", find respective double and return it
     }
    public String parenthesis(String instr){
      Matcher m = Pattern.compile("\\([^\\(\\)]*\\)").matcher(instr);
      while(m.find()){
      String replacement = m.group();
      replacement = PEMDAS(replacement.substring(1,replacement.length()-1));
      instr = instr.substring(0, m.start())+replacement+instr.substring(m.end(), instr.length());
      m = Pattern.compile("\\([^\\(\\)]*\\)").matcher(instr);
      }
      return instr;
    }
    public String MultDiv(String instr){
      Matcher m = Pattern.compile("\\-{0,1}\\d{0,100}\\.{0,1}\\d{1,100}\\*\\-{0,1}\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      while(m.find()){
       String replacement = m.group();
       int start = m.start(); int end = m.end();
       int index = replacement.indexOf("*");
       replacement = ""+Double.parseDouble(replacement.substring(0,index))*Double.parseDouble(replacement.substring(index+1, replacement.length()));
       instr = instr.substring(0,start)+replacement+instr.substring(end, instr.length());
       m = Pattern.compile("\\-{0,1}\\d{0,100}\\.{0,1}\\d{1,100}\\*\\-{0,1}\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      }
      m = Pattern.compile("\\-{0,1}\\d{0,100}\\.{0,1}\\d{1,100}\\/\\-{0,1}\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
       while(m.find()){
       String replacement = m.group();
       int start = m.start(); int end = m.end();
       int index = replacement.indexOf("/");
       replacement = ""+Double.parseDouble(replacement.substring(0,index))/Double.parseDouble(replacement.substring(index+1, replacement.length()));
       instr = instr.substring(0,start)+replacement+instr.substring(end, instr.length());
       m = Pattern.compile("\\-{0,1}\\d{0,100}\\.{0,1}\\d{1,100}\\/\\-{0,1}\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      }
      return "+" + instr;
    }
    public String AddSub(String instr){
      Matcher m = Pattern.compile("\\-\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      double negSum= 0;

      while(m.find()){
          String replacement = m.group();
          negSum += Double.parseDouble(replacement);
          instr = instr.substring(0, m.start())+instr.substring(m.end(),instr.length());
          m = Pattern.compile("\\-\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      }
      m = Pattern.compile("\\d{0,100}\\.{0,1}\\d{1,100}\\+\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
       while(m.find()){
       String replacement = m.group();
       int start = m.start(); int end = m.end();
       int index = replacement.indexOf("+");
       replacement = ""+(Double.parseDouble(replacement.substring(0,index))+Double.parseDouble(replacement.substring(index+1, replacement.length())));
       instr = instr.substring(0,start)+replacement+instr.substring(end, instr.length());
       m = Pattern.compile("\\d{0,100}\\.{0,1}\\d{1,100}\\+\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      }
      if(instr.equals("")){instr="0";}
      return ""+(Double.parseDouble(instr) + negSum);  
    }
    public double findOperation(String s){
    Matcher m = Pattern.compile("Math\\.\\w{1,5}\\(").matcher(s);
    m.find(); String query = m.group();
    negProtocol = false;
    double repl = 0;
    switch(query){
        case "Math.sin(":
        repl = Math.sin(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
        case "Math.abs(":
        repl = Math.abs(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
        case "Math.acos(":
        repl = Math.acos(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
        case "Math.asin(":
        repl = Math.asin(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
        case "Math.atan(":
        repl = Math.atan(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
        case "Math.cos(":
        repl = Math.cos(Double.parseDouble(PEMDAS(s.substring(query.length(), s.length()-1))));
        if(repl<0){negProtocol = true; return repl;}else{return repl;}
    }
    return -10000;
    }
    public String negHandler(String instr){
      Matcher m = Pattern.compile("\\d{0,100}\\.{0,1}\\d{1,100}\\+\\-\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      while(m.find()){
        String word = m.group();
        int index = word.indexOf("+-");
        instr = instr.substring(0,index) + instr.substring(index+1, instr.length());
        m = Pattern.compile("\\d{0,100}\\.{0,1}\\d{1,100}\\+\\-\\d{0,100}\\.{0,1}\\d{0,100}").matcher(instr);
      }
      return instr;
    }
    public String variableReplace(String instr){
      Matcher m = Pattern.compile("[x-y]{1}").matcher(instr);
      while(m.find()){
        String word = m.group();
        double replacement = (word.equals("x")? x:y);
        int index = instr.indexOf(word);
        instr = instr.substring(0,index) + replacement + instr.substring(index+1, instr.length());
         m = Pattern.compile("[x-y]{1}").matcher(instr);
      }
      return instr;
    }
    public double getReply(double i, double j){
    x = i;
    y = j;
    return Double.parseDouble(PEMDAS(instructions));
    }
}
