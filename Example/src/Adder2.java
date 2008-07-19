import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Adder2 implements ActionListener {
    JFrame AdderFrame;
    JTextField TOprand1;
    JTextField TOprand2;
    JLabel LAdd, LSum;
    JButton BAdd,BClear;
    JPanel panel1,panel2;
    public Adder2( ) {			  	// 建立顶层容器组件对象
	    ImageIcon face = new ImageIcon("flower.gif");
	    ImageIcon smile=new ImageIcon("smile.gif");
    	AdderFrame = new JFrame("AdderFrame");
	    TOprand1 = new JTextField("0.0");
	    TOprand2 = new JTextField("0.0");
	    LAdd=new JLabel("+");
	    LAdd.setSize(99, 99);
	    LSum=new JLabel("= 0.0 ");
	    BAdd=new JButton(face);
	    Dimension d=new Dimension(face.getIconWidth(),face.getIconHeight());
	    BAdd.setPreferredSize(d);
	    BAdd.setBounds(0, 0, 10000, 10000);
	    BAdd.setRolloverIcon(smile);
	    BClear=new JButton("Clear!");
     	    panel1=new JPanel( );
 	    panel2=new JPanel( );
 	  	//监听来自Badd的事件
	  	BAdd.addActionListener(this);
		//按钮Bclear的事件处理
	  	BClear.addActionListener(new ActionListener( ){
      		      public void actionPerformed(ActionEvent event){
          		      TOprand1.setText("0.0");
          		      TOprand2.setText("0.0");
          		      LSum.setText("= 0.0 ");
      		    }
    		  });		
	//建立中间容器面板panel1， panel1的布局管理器为FlowLayout
    		panel1.setLayout(new FlowLayout( ));
    	 	panel1.add(TOprand1);
	  	panel1.add(LAdd);
	 	panel1.add(TOprand2);
   		panel1.add(LSum);
   		//LAdd.setVisible(false);
	//建立中间容器面板panel2， panel2的布局管理器为FlowLayout
    		panel2.setLayout(new FlowLayout( ));
    		panel2.add(BAdd);
    		panel2.add(BClear);
    		//建立顶层容器ContentPane，ContentPane的布局管理器为BorderLayout
            AdderFrame.setLayout(new BorderLayout( ));
AdderFrame.add(panel1,BorderLayout.NORTH);
AdderFrame.add(panel2,BorderLayout.SOUTH);
//窗口关闭处理
               AdderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
AdderFrame.pack( );
AdderFrame.setVisible(true);
}
public void actionPerformed(ActionEvent event) {
int sum= (int)(Double.valueOf(TOprand1.getText( )).doubleValue( )
                 +Double.valueOf(TOprand2.getText( )).doubleValue( ));
LSum.setText("="+sum);
}
public static void main(String[ ] args) {
Adder2 adder2 = new Adder2( );
}
} 