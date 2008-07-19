import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Adder1 implements ActionListener {
    	JFrame AdderFrame;
    	JTextField TOprand1;
    	JTextField TOprand2;
    	JLabel LAdd, LSum;
    	JButton BAdd,BClear;
    	public Adder1( ) {  				// 建立主窗口和容器
		AdderFrame = new JFrame("AdderFrame");
		TOprand1 = new JTextField("0.0");
		TOprand2 = new JTextField("0.0");
		LAdd=new JLabel("+");
		LSum=new JLabel("=     ");
		BAdd=new JButton("Add!");
		BClear=new JButton("Clear!");		// 监听BAdd按钮的事件
		BAdd.addActionListener(this);		// Bclear事件处理
	  	BClear.addActionListener(new ActionListener( ){
     		        public void actionPerformed(ActionEvent event){
                      	     	TOprand1.setText("0.0");
          	                          	TOprand2.setText("0.0");
          		                     LSum.setText("=   ");	}
                                	});
		//设置布局管理器为FlowLayout
  		AdderFrame.setLayout(new FlowLayout( ));
		AdderFrame.add(TOprand1);
		AdderFrame.add(LAdd);
		AdderFrame.add(TOprand2);
    		AdderFrame.add(LSum);
    		AdderFrame.add(BAdd);
   		AdderFrame.add(BClear);
                                    AdderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AdderFrame.pack( );
		AdderFrame.setVisible(true);
    	}	
                                // Badd事件处理
    	public void actionPerformed(ActionEvent event) {
           	                  int sum= (int)(Double.valueOf(TOprand1.getText( )).doubleValue( )
	                                                   +Double.valueOf(TOprand2.getText( )).doubleValue( ));
                                     LSum.setText("="+sum);
    	}
                  public static void main(String[ ] args) {
	  Adder1 adder1 = new Adder1( );    }
  } 

