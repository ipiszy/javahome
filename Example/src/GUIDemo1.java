import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
public class GUIDemo1{
    public static void main(String[ ] args) {
       JFrame frame = new JFrame("GUIDemo1");//�����Ӵ�����
       JLabel label = new JLabel(" ѧ �� Java �� �� ��\n\n\n\n");//������ǩ����
       JLabel label1=new JLabel("AD is SB!");
       frame.setLayout(new FlowLayout()); 
       frame.getContentPane( ).add(label);//����ǩ������ӵ��Ӵ������������
        frame.getContentPane().add(label1);
      //�����¼�����
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
        frame.pack( );//�����Ӵ��ߴ�
        //frame.setResizable(false);
        frame.setVisible(true);//���Ӵ�����Ϊ�ɼ�
    }
}