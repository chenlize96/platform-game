package labyrinth;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class MainFrame extends JFrame {
	 private static JLabel mainBackJLabel;//mianback 
	 private static JLabel typeShow23;//props steps
	 
	 private static ImageIcon mainBack;	   //mainback picture;
     private static ImageIcon smalltype1Icon = new ImageIcon("pic/smallType1.png");	//small logo
     private static ImageIcon smalltype2Icon = new ImageIcon("pic/smallType2.png");	//
     private static ImageIcon typeInfor1Icon = new ImageIcon("pic/typeInfor1.png");	//info
     private static ImageIcon typeInfor2Icon = new ImageIcon("pic/typeInfor2.png");	//
     private static ImageIcon wall;	   //Boundary picture
     private static ImageIcon characterType;
     private static ImageIcon stone;//people picture  
     private static ImageIcon win;//end picture 
     private static ImageIcon light = new ImageIcon("pic/light.png");	//light model ppt
     private static ImageIcon step = new ImageIcon("pic/step.png");	//light model
     private static int[][] labyrinthData = new int[47][37];//logic maze
     private static JLabel[][] labyrinthLable = new JLabel[37][47];	//图像显示上的迷宫(maze in picture)
     private static int[][] newData=new int[47][37];  //for moving record）     
     private static int steps=100;// count down
     private static int lightchances=3;//numbers of light props
	public MainFrame(int x) {
		//location, size,title....
		initBasic();
		
		//init logic
		initMapData();
		
		//init view
		initMapFrame();
		
		//1 is light model  2 is props model)）
		switch(x) {
		case 1:initType1Frame();break;
		case 2:initType2Frame();break;
		}
		  upDateUIpri(labyrinthData,x);
		//press
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent event)
			{   
				int k =event.getKeyCode();
			//暂时先存好移动前的数组(record before moving)
			   for(int m=0; m<47; m++)
			      {  for(int n=0; n<37; n++)
				     {newData[m][n]=labyrinthData[m][n];}
			      }
				switch(k)
				{//1 is left; 2 is right; 3 is up; 4 is down//32 is space
				case 37:move(1); break;
				case 38:move(3); break;
				case 39:move(2);break;
				case 40:move(4); break;
				case 32:if(x==1&&lightchances>0){light();lightchances--;break;}
				}//switch
				yesornoMove();
				boolean havemove =yesornoMove();
				if(havemove==true)//update
				{MainFrame.upDateUIpub(labyrinthData);
				upDateUIpri(labyrinthData,x);
				gamestate();
				if(x==2)
				steps--;
				}
			}
		});	
		initMainFrameBack();
		
		setVisible(true);
	}
	
	
	private  void initBasic()
	{
		this.setTitle("puzzle");	
		this.setSize(1000, 1060);		
		this.setLocation(600, 0);	
		this.setLayout(null);		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//
	}	
	//back pic
	private void initMainFrameBack()
	{
		 Random random1 = new Random();
		 mainBack= new ImageIcon("pic/mainback"+(random1.nextInt(3)+1)+".jpg");	
		mainBackJLabel = new JLabel(mainBack);
		mainBackJLabel.setBounds(0, 0, 1000, 1060);
		this.add(mainBackJLabel);
	}
	//
	private void initMapData() {
		//从文件中读取数据保存到labyrinthData数组中
		//i（37）j（47）
    	String[] line=new String [47];
    	int m=0;
    	
        try { 
            BufferedReader br = new BufferedReader(
            		new  InputStreamReader ( 
    	                    new FileInputStream("src/labyrinth/piao.txt")));
            
            String line1 ;
            while ((line1= br.readLine()) != null) {
                //System.out.println(line1);//每行输出	
            	line[m]=line1 ;
            	m++;
        	}//while
        } catch (Exception e) {
            e.printStackTrace();
        }
       // change String ， put in labyrinthData
        for(int j=0;j<47;j++) {
        for(int i=0;i<37;i++){
          String s2 = line[j].substring(i, i+1);
          labyrinthData[j][i] = Integer.parseInt(s2);
          labyrinthData[j][i] = Integer.valueOf(s2).intValue();
          }
        }   
        Random random = new Random();
	     //random start and end）
	     int x,y;
	     for(int i=0;i<2;i++) {
	       do
	       {
	         x=random.nextInt(47);
		     y=random.nextInt(37);
	       }while(labyrinthData[x][y]==1);//start
	         if(i==0)  labyrinthData[x][y]=2;//2 is people data   
	         if(i==1)  labyrinthData[x][y]=5;//5 is out data   
	     }//for 	
	}
	
	private  void initMapFrame() {
		Random random = new Random();//random
		//（frame中纵横与数组相反）
		
		for(int i=0;i<47;i++) {
			for(int j=0;j<37;j++) {		
				if(labyrinthData[i][j]==0)
				{
					labyrinthLable[j][i]=new JLabel();
					labyrinthLable[j][i].setIcon(null);
					labyrinthLable[j][i].setBounds(20+j*20, 50+i*20, 20, 20);
				    this.add(labyrinthLable[j][i]);
				}
					if(labyrinthData[i][j]==1) {					
				    wall= new ImageIcon("pic/wallType1"+(random.nextInt(4)+1)+".png");
				    labyrinthLable[j][i]=new JLabel(wall);
				    labyrinthLable[j][i].setBounds(20+j*20, 50+i*20, 20, 20);
				    this.add(labyrinthLable[j][i]);
					}
					if(labyrinthData[i][j]==2)
				       {characterType=new ImageIcon("pic/characterType"+(random.nextInt(4)+1)+".png");//人物图片初始化;//迷宫人物图片     
						    labyrinthLable[j][i]=new JLabel(characterType);
						    labyrinthLable[j][i].setBounds(20+j*20, 50+i*20, 20, 20);
						    this.add(labyrinthLable[j][i]);						    				 
	                   }
				//end
					if(labyrinthData[i][j]==5) {
						win = new ImageIcon("pic/win"+(random.nextInt(3)+1)+".png");
						labyrinthLable[j][i]=new JLabel(win);
					    labyrinthLable[j][i].setBounds(20+j*20, 50+i*20, 20, 20);
					    this.add(labyrinthLable[j][i]);
					}
			}//	
		}
	}
	//set（initType1Frame, initType2Frame）
	
	private  void initType1Frame()
	{  
	  //迷雾模式 == light model
	  JLabel typeShow11 =new  JLabel();
	  typeShow11.setIcon(smalltype1Icon);
	  typeShow11.setBounds(280,0,240,60);
	  this.add(typeShow11);
	  
	  // 道具模式 == props model
	  JLabel typeShow12 =new  JLabel();
	  typeShow12.setIcon(typeInfor1Icon);
	  typeShow12.setBounds(760,60,220,150);
	  this.add(typeShow12);
	}
	
	private  void initType2Frame()
	{//props logo
	  JLabel typeShow21 =new  JLabel();
	  typeShow21.setIcon(smalltype2Icon);
	  typeShow21.setBounds(280,0,240,60);
	  this.add(typeShow21);
	  //model intro
	  JLabel typeShow22 =new  JLabel();
	  typeShow22.setIcon(typeInfor2Icon);
	  typeShow22.setBounds(760,60,260,200);
	  this.add(typeShow22);
	//model intro
	  JLabel typeShow24 =new  JLabel();
	  typeShow24.setIcon(step);
	  typeShow24.setBounds(760,210,140,100);
	  this.add(typeShow24);
	  //count down steps 
	  typeShow23=new JLabel("");
	  Font font = new Font("Verdana",Font.BOLD , 40);      
	  typeShow23.setFont(font);      
	  typeShow23.setForeground(Color.darkGray);       
	  typeShow23.setBounds(760,230,150,150);
	  this.add(typeShow23);
	 //init props 
	  Random random = new Random();
	     int x,y;
	     //5random props blue and red）
	     for(int i=0;i<5;i++) {
	       do
	       {
	         x=random.nextInt(47);
		     y=random.nextInt(37);
	       }while(labyrinthData[x][y]==1);//
	     labyrinthData[x][y]=random.nextInt(2)+3;//3 is blue props，4 is red props)  
	     } 
	     
	   //3 is blue props :stone1,4 is red props :stone2)	    
	     for(int i=0;i<47;i++) {
				for(int j=0;j<37;j++) {
					      if(labyrinthData[i][j]==3||labyrinthData[i][j]==4)
					      {
					    	  switch(labyrinthData[i][j]) {
					    	  case 3:stone=new ImageIcon("pic/stone1.png");break;
					    	  case 4:stone=new ImageIcon("pic/stone2.png");break;
					    	  }//switch					    	  
							    labyrinthLable[j][i]=new JLabel(stone);
							    labyrinthLable[j][i].setBounds(20+j*20, 50+i*20, 20, 20);
							    this.add(labyrinthLable[j][i]);
					      }     					      
					}//
				}//
	     
	     
	}
	
	//update people move
	public static void upDateUIpub(int[][] labyrinthData)
	{
		 Random random = new Random();
		//update people move      in frame i and j is backwords
		for(int i=0;i<47;i++) {
			for(int j=0;j<37;j++) {					
				    if(labyrinthData[i][j]==2)
				       {
				    	labyrinthLable[j][i].setIcon(characterType);   
	                   }
				    else if(labyrinthData[i][j]==0)
				    {
				    	labyrinthLable[j][i].setIcon(null);   
	                 }
				    else if(labyrinthData[i][j]==6)
				    {
				    	labyrinthLable[j][i].setIcon(light);   
	                 }
			}// in for
		}//out for
	}
	//update more
	public  static void  upDateUIpri(int[][] labyrinthData,int type)
	{Random random = new Random();		
		if(type==1) 
		{
			for(int m=0;m<47;m++) {
			      for(int n=0;n<37;n++) {
    	        if(labyrinthData[m][n]==1)
    	  { labyrinthLable[n][m].setVisible(false);}}}//
			for(int i=0;i<47;i++) {
				for(int j=0;j<37;j++) {	
					if(labyrinthData[i][j]==2||labyrinthData[i][j]==6||labyrinthData[i][j]==5)		
					{ 
						for(int temp=0; temp<=120;temp++) {
						int x,y;
						do
					       {
					         x=random.nextInt(5)-2;//-2,-1,0,1,2	
					         y=random.nextInt(5)-2;//-2,-1,0,1,2					        
					       }while((i+x<0||i+x>=47)||(j+y<0||j+y>=37)||labyrinthData[i+x][j+y]!=1);
						labyrinthLable[j+y][i+x].setVisible(true);
						}
					}				
				}//
			}
      }//type1
      if(type==2) {
    	  typeShow23.setText(steps+"");
      }//type2
   }
	//in game
	public  void gamestate()
	{
		int flag=0;//0: no win or lose. 1 win 2 lose    in data 7 is win
		for(int i=0; i<47; i++)
		{
			for(int j=0; j<37; j++)
			{//移动函数中到达终点将数据赋值为人物数据2
				// set data when people get the end and give it value 2
				if(labyrinthData[i][j]==7) {flag=1;}
			}
		}
		if(steps==0)
		{   flag=2;  }
		if(flag==1) {
		//press "Yes"or "No"
		int result = JOptionPane.showConfirmDialog(null, "win! back main？", "result",JOptionPane.YES_NO_OPTION);
		//yes == new game
		if(result == 0)
		{    
			this.dispose();
			HelloFrame newgame = new HelloFrame();
		}
		//no: get out
		else   {System.exit(0);}
		}
	
	//fail
	if(flag==2)
	{
		int result = JOptionPane.showConfirmDialog(null, "lose！back main？", "result", JOptionPane.YES_NO_OPTION);
		if(result == 0)
		{
			HelloFrame newgame = new HelloFrame();
		}
		else
		{System.exit(0);}
	}
  }//
	
	//move data（1left 2right 3up 4down）
	public static  void move(int direction) {
		piao:for(int i=0;i<47;i++) {
			for(int j=0;j<37;j++) {	
				if(labyrinthData[i][j]==2) {
				switch(direction) {
				case 1:{ 
					switch(labyrinthData[i][j-1]){
					case 3:steps+=20;break;//plus20 steps
					case 4:steps+=10;break;//plus20 steps
					case 5:labyrinthData[0][0]=7;break;//win
					}
                    if(labyrinthData[i][j-1]!=1&&labyrinthData[i][j-1]!=6)
				    {labyrinthData[i][j-1]=2;labyrinthData[i][j]=0;}					
					break piao;}
				case 2:{
					switch(labyrinthData[i][j+1]){
				          case 3:steps+=20;break;
				          case 4:steps+=10;break;
				          case 5:labyrinthData[0][0]=7;break;
				         }					
					if(labyrinthData[i][j+1]!=1&&labyrinthData[i][j+1]!=6)
				    {labyrinthData[i][j+1]=2;labyrinthData[i][j]=0;}
					break piao;}
				case 3:{
					switch(labyrinthData[i-1][j]){
			          case 3:steps+=20;break;//
			          case 4:steps+=10;break;//
			          case 5:labyrinthData[0][0]=7;break;//
			         }		
					 if(labyrinthData[i-1][j]!=1&&labyrinthData[i-1][j]!=6)
			        {labyrinthData[i-1][j]=2;labyrinthData[i][j]=0;}
				    break piao;}
				case 4:{
					switch(labyrinthData[i+1][j]){
			          case 3:steps+=20;break;//
			          case 4:steps+=10;break;//
			          case 5:labyrinthData[0][0]=7;break;//
			         }		
					if(labyrinthData[i+1][j]!=1&&labyrinthData[i+1][j]!=6)
				    {labyrinthData[i+1][j]=2;labyrinthData[i][j]=0;}
					break piao;}
				}//switch(direction)
				}//if(labyrinthData[i][j]==2) 
			}//
		}//
	}
	private static boolean yesornoMove()
	{
		boolean flag = false;//0 means no move
		for(int m=0; m<47; m++)
	      {  for(int n=0; n<37; n++)
		     {
	    	  if(newData[m][n]!=labyrinthData[m][n])
	    		  flag = true;
	    	  }
	      }
		return flag;
	}
	public static void light() {
		
		Random random = new Random();	
		for(int i=0;i<47;i++) {
			for(int j=0;j<37;j++) {	
				if(labyrinthData[i][j]==2)//find people location
				{ 
					int x,y;
					do
				       {
				         x=random.nextInt(3)-1;//-1,0,1	
				         y=random.nextInt(3)-1;//-1,0,1	
				       }while(labyrinthData[i+x][j+y]!=1);//put light
					labyrinthData[i+x][j+y]=6;//6 is light
					labyrinthLable[j+y][i+x].setIcon(light);
				}				
			}//
		}//
	}
}
