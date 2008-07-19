import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Adder implements ActionListener {
    JFrame AdderFrame;
    JTextField TOprand1;		//被加数
    JTextField TOprand2;		//加数
    JLabel LAdd, LSum;		//加号，和
    JButton BAdd;			//Add！按钮
    int i=0;
    public Adder( ) {//建立图形界面的屏幕显示部分
	AdderFrame = new JFrame("AdderFrame");
	TOprand1 = new JTextField("0.0");
	TOprand2 = new JTextField("0.0");
	LAdd=new JLabel("+");
	LSum=new JLabel("=        ");
	BAdd=new JButton("Add!");
  	AdderFrame.setLayout(new FlowLayout( ));
	AdderFrame.add(TOprand1);
	AdderFrame.add(LAdd);
	AdderFrame.add(TOprand2);
  	AdderFrame.add(LSum);
  	AdderFrame.add(BAdd);
	AdderFrame.pack( );
	AdderFrame.setVisible(true);  
    AdderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
//为命令按钮添加事件监听器，监听器的实参是Adder类的对象
BAdd.addActionListener(this);
}
// Adder类的事件服务方法
public void actionPerformed(ActionEvent event) {  //事件服务代码
i=(i+1)%2;
         if(i==1){	//i为奇数，求和
             int sum= (int)(Double.valueOf(TOprand1.getText( )).doubleValue( )
                      +Double.valueOf(TOprand2.getText( )).doubleValue( ));
 LSum.setText("="+sum+"   ");
          }else{	//i为偶数，清零
                         TOprand1.setText("0.0");
                         TOprand2.setText("0.0");
                          LSum.setText("=   ");        };
      }
    
public static void main(String[ ] args) {
Adder adder = new Adder( );     
	}
}
