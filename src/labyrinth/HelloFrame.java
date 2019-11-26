package labyrinth;
import javax.swing.*;
import java.awt.event.*;
import labyrinth.MainFrame;

public class HelloFrame extends JFrame{
	 private static JLabel helloLogo = new JLabel();// logg pic
	 private static JLabel backJLabel = new JLabel();//back pic
     private static ImageIcon logoIcon = new ImageIcon("pic/logo.png");	   //logo pic
     private static ImageIcon BackIcon = new ImageIcon("pic/back.jpg");	   //back
     private static ImageIcon type1Icon = new ImageIcon("pic/type1.png");	   //light model
     private static ImageIcon type2Icon= new ImageIcon("pic/type2.png");	   //props
     
     private static JButton chose1=new JButton();	   
     private static JButton chose2=new JButton();      
     
	 public HelloFrame(){
		    
			initBasic();
			
			initHelloJLabel();
			
			initFrameBackGround();

			setVisible(true);
	 }
	 private  void initBasic()
		{
			this.setTitle("puzzle");	
			this.setSize(825, 850);		
			this.setLocation(700, 30);	
			this.setLayout(null);		
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//
		}	
	 private void initHelloJLabel()
		{
		helloLogo.setIcon(logoIcon);
		helloLogo.setBounds(120, 150, 540, 200);
		this.add(helloLogo);
		chose1.setOpaque(false); 
		chose2.setOpaque(false); 
		chose1.setContentAreaFilled(false);
		chose2.setContentAreaFilled(false);  
		chose1.setFocusPainted(false);
		chose2.setFocusPainted(false);
		chose1.setBorder(null); 
		chose2.setBorder(null); 
		chose1.setIcon(type1Icon);
		chose2.setIcon(type2Icon);
		chose1.setBounds(0, 500, 400, 110);
		chose2.setBounds(400, 500, 400, 110);
		chose1.addMouseListener(new MyMouseListenner1());
		chose2.addMouseListener(new MyMouseListenner2());
		this.add(chose1);
		this.add(chose2);
		}
	 private void initFrameBackGround()
		{
			backJLabel = new JLabel(BackIcon);
			backJLabel.setBounds(0, 0, 800, 800);
			this.add(backJLabel);
		}
	 //mouse
	 private class MyMouseListenner1 extends MouseAdapter{
			public void mouseClicked(MouseEvent e)
		    {
				MainFrame newmain = new MainFrame(1);
		    }
		}
	 private class MyMouseListenner2  extends MouseAdapter{
			public void mouseClicked(MouseEvent e)
		    {
				MainFrame newmain = new MainFrame(2);
		    }	
	}
}
