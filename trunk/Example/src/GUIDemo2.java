import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GUIDemo2{
public static void main(String[ ] args) {	 //ͼ�ν������Ļ��ʾ����
        JFrame frame = new JFrame("GUIDemo1");
        final JTextField Text= new JTextField("    ѧ �� Java �� �� �� ��    ");
       JButton Button = new JButton("��ť");
        frame.setLayout(new FlowLayout());//���ò��ֹ�����
        frame.add(Text);
        frame.add(Button);
        frame.pack( );
        frame.setVisible(true);//ͼ�ν�����¼�������     
        Button.addActionListener(new ActionListener( ){
   	public void actionPerformed(ActionEvent e){
	  	Text.setText("����JTextField��JButton��һ��ʾ��");
	  	
		   }
	   });
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         }
}