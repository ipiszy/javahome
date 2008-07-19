import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
public class GUIDemo1{
    public static void main(String[ ] args) {
       JFrame frame = new JFrame("GUIDemo1");//创建视窗对象
       JLabel label = new JLabel(" 学 好 Java 用 处 大！\n\n\n\n");//创建标签对象
       JLabel label1=new JLabel("AD is SB!");
       frame.setLayout(new FlowLayout()); 
       frame.getContentPane( ).add(label);//将标签对象添加到视窗对象的容器中
        frame.getContentPane().add(label1);
      //定义事件处理
        frame.addWindowListener(new WindowAdapter( ){
        	        public void windowActivated(WindowEvent e){
        	        	System.out.println("AD is SB!");
        	        	//getContentPane().add(label1);
        	        }
                    public void windowClosing(WindowEvent e) {
                    System.out.println("Exit!");
        			System.exit(0); 
                    }
             });
        frame.pack( );//调整视窗尺寸
        //frame.setResizable(false);
        frame.setVisible(true);//将视窗设置为可见
    }
}