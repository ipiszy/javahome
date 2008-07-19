import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Minest {
	int height=9,weight=9,mineNum=10;
	int mineArray[][]=new int[height][weight];
	int mineStatus[][]=new int [height][weight]; //0:没有点开；1：标记；-1：点开；
	//int x=0,y=0;
	ImageIcon face,mine,smile,flag,ask;
	JFrame mineFrame;
	JPanel mineJPanel, statusJPanel, statusJPanelHelp;
	JButton[][] mineButton;
	JButton centerButton;
	JLabel label1,label2;
	String str="看谁最雷", str1="剩余雷数", str2="已用时间";
	
	
	class MyMouseListener implements MouseListener{
		int x,y,rightClick=0;

		public MyMouseListener(int i,int j){
			x=i;y=j;
		}
		
		public void mineView(int i,int j){
			if ((i<0) || (i>=height) || (j<0) || (j>=weight))
				return;
			if (mineStatus[i][j]!=0)
				return;
			if (mineArray[i][j]==-1)
				return;
			
			if (mineArray[i][j]>0){
				mineButton[i][j].setIcon(null);
				mineButton[i][j].setText(mineArray[i][j]+"");
				mineButton[i][j].setFont(new Font("Arial",Font.ITALIC,10));
				mineStatus[i][j]=-1;
				return;
			}
			if (mineArray[i][j]==0){
				mineButton[i][j].setIcon(null);
				mineStatus[i][j]=-1;
				mineView(i-1,j-1);
				mineView(i-1,j);
				mineView(i-1,j+1);
				mineView(i,j-1);
				mineView(i,j+1);
				mineView(i+1,j-1);
				mineView(i+1,j);
				mineView(i+1,j+1);
			}
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getButton()==MouseEvent.BUTTON1){
				if (mineArray[x][y]==-1){
					for (int i=0;i<height;i++)
						for (int j=0;j<weight;j++)
							if (mineArray[i][j]==-1)
								mineButton[i][j].setIcon(mine);
							else{
								mineButton[i][j].setIcon(null);
								mineButton[i][j].setText(mineArray[i][j]+"");
								mineButton[i][j].setFont(new Font("Arial",Font.ITALIC,10));							
							}	
				}
					
				else{
					/*mineButton[x][y].setIcon(null);
					mineButton[x][y].setText(mineArray[x][y]+"");
					mineButton[x][y].setFont(new Font("Arial",Font.ITALIC,10));*/
					mineView(x,y);
				}
			}
			if (e.getButton()==MouseEvent.BUTTON3){
				if (rightClick==0){
				    mineStatus[x][y]=1;
				    mineButton[x][y].setIcon(flag);
				    rightClick++;
				}
				else if(rightClick==1){
					mineButton[x][y].setIcon(ask);
					rightClick++;
				}
				else{
					mineStatus[x][y]=0;
					mineButton[x][y].setIcon(face);
					rightClick=0;
				}
			}
	
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public Minest(){
		face=new ImageIcon("haha.gif");
		mine=new ImageIcon("mine.gif");
		smile=new ImageIcon("smile.gif");
		flag=new ImageIcon("flag.gif");
		ask=new ImageIcon("ask.gif");
		mineFrame=new JFrame(str);
		mineJPanel=new JPanel();
		statusJPanel=new JPanel();
		statusJPanelHelp=new JPanel();
		centerButton=new JButton();
		label1=new JLabel(str1);
		label2=new JLabel(str2);
		Dimension d1=new Dimension(smile.getIconWidth(),smile.getIconHeight());
		centerButton=new JButton(smile);
		centerButton.setPreferredSize(d1);
		mineButton=new JButton[height][weight];
		Dimension d=new Dimension(face.getIconWidth(),face.getIconHeight());
		for (int i=0;i<height;i++)
			for (int j=0;j<weight;j++){
			//	System.out.println(x+"  "+y);
				mineButton[i][j]=new JButton(face);
				mineButton[i][j].setPreferredSize(d);
//				mineButton[i][j].setFont(font)
				mineButton[i][j].addMouseListener(new MyMouseListener(i,j));
		}
		
		mineJPanel.setLayout(new GridLayout(height,weight,3,3));
		
		for (int i=0;i<height;i++)
			for (int j=0;j<weight;j++)
				mineJPanel.add(mineButton[i][j]);
		
		mineJPanel.setVisible(true);
		
		
		centerButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				for (int i=0;i<height;i++)
					for (int j=0;j<weight;j++){
						mineButton[i][j].setText(null);
						mineButton[i][j].setIcon(face);
					}
				getMine();				
			}
			
		});
		label1.setFont(new Font("宋体",Font.BOLD,15));
		label2.setFont(new Font("宋体",Font.BOLD,15));
		
		statusJPanelHelp.setLayout(new FlowLayout());
		statusJPanelHelp.add(label1);
		statusJPanelHelp.add(centerButton);
		statusJPanelHelp.add(label2);
		
		statusJPanel.setLayout(new BorderLayout());
		statusJPanel.add(statusJPanelHelp,BorderLayout.CENTER);
		
		mineFrame.setLayout(new BorderLayout());
		mineFrame.add(statusJPanel,BorderLayout.NORTH);
		mineFrame.add(mineJPanel,BorderLayout.SOUTH);
		
		mineFrame.setVisible(true);
		mineFrame.pack();
		mineFrame.setResizable(true);
		
		mineFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	public void getMine(){
		Random rand=new Random();
		for (int i=0;i<height;i++)
			for (int j=0;j<weight;j++){
				mineArray[i][j]=0;
				mineStatus[i][j]=0;
			}
		
		int count=0;
		while (count<mineNum){
			int xTemp=rand.nextInt(height);
			int yTemp=rand.nextInt(weight);
			if (mineArray[xTemp][yTemp]==0){
				mineArray[xTemp][yTemp]=-1;
				count++;
			}				
		}
		
		for (int i=0;i<height;i++)
			for (int j=0;j<weight;j++){
				if (mineArray[i][j]==-1){
					if ((i>0) && (mineArray[i-1][j]!=-1))
						mineArray[i-1][j]++;
					if ((i<height-1) && (mineArray[i+1][j]!=-1))
						mineArray[i+1][j]++;
					if ((j>0) && (mineArray[i][j-1]!=-1))
						mineArray[i][j-1]++;
					if ((j<weight-1) && (mineArray[i][j+1]!=-1))
						mineArray[i][j+1]++;
					if ((i>0) && (j>0) && (mineArray[i-1][j-1]!=-1))
						mineArray[i-1][j-1]++;
					if ((i<height-1) && (j>0) && (mineArray[i+1][j-1]!=-1))
						mineArray[i+1][j-1]++;
					if ((i>0) && (j<weight-1) && (mineArray[i-1][j+1]!=-1))
						mineArray[i-1][j+1]++;
					if ((i<height-1) && (j<weight-1) && (mineArray[i+1][j+1]!=-1))
						mineArray[i+1][j+1]++;
					}
			}
		
		for (int i=0;i<height;i++){
			for (int j=0;j<weight;j++)
				System.out.print(mineArray[i][j]+"\t\t");
			System.out.println();
		}
		
		//System.out.println(mineArray);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Minest myMine=new Minest();
		myMine.getMine();
		

	}

}
