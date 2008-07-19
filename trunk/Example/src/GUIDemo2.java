import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GUIDemo2{
public static void main(String[ ] args) {	 //图形界面的屏幕显示部分
        JFrame frame = new JFrame("GUIDemo1");
        final JTextField Text= new JTextField("    学 好 Java 用 处 大 ！    ");
       JButton Button = new JButton("按钮");
        frame.setLayout(new FlowLayout());//设置布局管理器
        frame.add(Text);
        frame.add(Button);
        frame.pack( );
        frame.setVisible(true);//图形界面的事件处理部分     
        Button.addActionListener(new ActionListener( ){
   	public void actionPerformed(ActionEvent e){
	  	Text.setText("这是JTextField和JButton的一个示例");
	  	
		   }
	   });
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
}