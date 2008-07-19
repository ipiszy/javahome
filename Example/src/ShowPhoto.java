import java.awt.*;
import java.awt.event.*;
//import java.applet.*;

import javax.swing.JApplet;
public class ShowPhoto extends JApplet{  
            /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int PHOTOTOTAL = 1;
	Image[ ] photos = new Image[PHOTOTOTAL];
	int photoNum = 0;	
	public void init( )	{
		addMouseListener(new MouseAdapter( ){
			public void mouseClicked(MouseEvent e){
		    if(++photoNum == PHOTOTOTAL)
		    	photoNum = 0;
		    repaint( );      //单击鼠标左键时显示下一幅图像
		    }
			});	//加载图象
		for(int i=1; i <= PHOTOTOTAL; i++)   
			photos[i-1] = getImage(getCodeBase(),"j"+i+".jpg");
		}
	  
	public void paint(Graphics g){
		if(photos[photoNum] != null)
			g.drawImage(photos[photoNum],0,0,this);    //显示图像		  
		} 
	
	
	} 
