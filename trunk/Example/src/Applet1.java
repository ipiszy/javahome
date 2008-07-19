import javax.swing.*;
import java.awt.*;

public class Applet1 extends JApplet implements Runnable
{    C t1=new C(this);
     public void init()  { t1.start();}
     public void paint(Graphics g)
     {   g.drawString("Hello,java",10,50);}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Hello!");
	}
 }
 class C extends Thread
 {  Applet1 a;
  C(Applet1 b)
{ a=b; }
public void run()
{ while(true)
   {  
a.repaint();
try{
  sleep(400000);
} catch(InterruptedException e){}
 }
   }
 }
