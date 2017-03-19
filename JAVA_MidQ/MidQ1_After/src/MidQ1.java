//HW1:資管三B 103403530 洪靖雯
//轉int 轉String

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Shape;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Graphics;








//import java.lang.Iterable;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ItemListener; //checkbox和radiobox會產生兩個Event,可用actionListener來作
import java.awt.event.ItemEvent; 
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel; 
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MidQ1 extends JFrame{
	private JPanel controlBar = new JPanel(); //上方控制區
	private JLabel mouseBar; //滑鼠狀態列
	//private JPanel smallControl1 = new JPanel(); //控制區左半邊
	//private JPanel smallControl2 = new JPanel(); //控制區右半邊
	//設定控制區左半部
	private JLabel introduce1 = new JLabel("左上座標:");
	private JTextField introduceText1 = new JTextField();
	private JTextField introduceText2 = new JTextField();
	private JLabel introduce2 = new JLabel("圖形長寬:");
	private JTextField introduceText3 = new JTextField();
	private JTextField introduceText4 = new JTextField();
	private JButton drawButton = new JButton("Draw");
	//設定控制區右半部
	private JButton getPointButton = new JButton("取點");
	private JLabel introduce3 = new JLabel("圓角長寬:");
	private JTextField introduceText5 = new JTextField();
	private JTextField introduceText6 = new JTextField();
	private JButton deletePointButton = new JButton("刪除最新圖形");
	//控制元件
	private GridBagConstraints c1 = new GridBagConstraints(); //控制introduce元件
	private GridBagConstraints c2 = new GridBagConstraints(); //控制TextField元件
	private GridBagConstraints c3 = new GridBagConstraints(); //控制button元件
	//畫布區
	private List<drawRoundRect> shapeList= new ArrayList<drawRoundRect>();//保存圖形
	private Graphics2D g2;
	private int countPaint = 0; //計算paintComponent跑了幾次
	private int countUndo = 0; //判斷要不要新增事件
	private drawRoundRect roundRect;//儲存所畫圓角矩形
	private int getPoint = 0; //判斷是否要取點
	//之前位置
	private int Pre_X = 0;
	private int Pre_Y = 0;
	//現在位置
	private int Now_X = 0;
	private int Now_Y = 0;
	//讀取左上座標、圖形長寬、圓角長寬
	private int getX = 0;
	private int getY = 0;
	private int getPicWidth = 0;
	private int getPicHeight = 0;
	private int getRoundRectWidth = 0;
	private int getRoundRectHeight = 0;
	//宣告畫布區
	public class PaintPanel extends JPanel{
				 public PaintPanel(){
				 } //建構子是空的，事件另外寫在mouseMoveHandler
				 
				//清除畫布，畫圖
				 public void paintComponent( Graphics g ) //一定要繼承JPanel才可以super.paintComponent
				   {
					  super.paintComponent( g ); // clears drawing area
					  g2 = (Graphics2D) g ; //建立一個graphics2D
					     			    
					    //畫出所存圖形
					    for (drawRoundRect t : shapeList) {
					    	List<Integer> tPosition = new ArrayList<Integer>();
					    	tPosition = t.getPosition();//得到位置
					    	int [] ret = new int[tPosition.size()]; //從List<Integer>轉成int
					    	//String name = t.getName(); //得到名稱
					    	//Boolean view = t.getView(); //得到填滿的判斷
					    	//float st = t.getStroke(); //得到寬度
					    	//g2.setStroke(new BasicStroke(st)); //設定寬度
					    	//Color co = t.getColor(); //得到顏色
					    	//g2.setColor(co); //設定顏色
					    	
					    	for(int i = 0 ; i < ret.length;i++){ //將<Integer>轉成<int>
					    		ret[i] = tPosition.get(i);
					    	}
					    		
					    	/*if(name == "drawEraser"){ //橡皮擦
					    		g2.fillOval(ret[2]-20, ret[3]-20, 50,50);
					    	}
					    	//筆刷
					    	else if(name == "drawPoint"){
						    			 //判斷筆刷大小
						    			if(st == 5){ //小筆刷
						    				g2.fillOval(ret[2],ret[3],10,10);
						    			}
						    			else if(st == 10){ //中筆刷
						    				g2.fillOval(ret[2],ret[3],30,30);
						    			}
						    			else if(st == 15){ //大筆刷
						    				g2.fillOval(ret[2],ret[3],50,50);
						    			}
						    	}
						    //直線
						    else if(name == "drawLine"){
						    		if(view){ //實線
						    			g2.drawLine(ret[0], ret[1], ret[2], ret[3]);
						    		}
						    		else{ //虛線
						    			g2.setStroke(new BasicStroke(st,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dashPattern,0));
						    			g2.drawLine(ret[0], ret[1], ret[2], ret[3]);
						    		}
						    		
						    	}
						    //橢圓
						    else if(name == "drawOval"){
						    		if(view){ //如果有填滿
						    			g2.fillOval(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
						    		}
						    		else{ //沒有填滿
						    			g2.drawOval(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
						    		}
						    	}
						    //矩形
						    else if(name == "drawRect"){
						    		if(view){ //如果有填滿
						    			g2.fillRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
						    		}
						    		else{ //沒有填滿
						    			g2.drawRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
						    		}
						    	}*/
					    	//圓角矩形
						   // if(name == "drawRoundRect"){	
						    	g2.drawRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],20,20);	
						   // 	}
					    }
					    
					    
					    //setBackground(backColor); //設定背景色		    
					    //g2.setStroke(new BasicStroke(stroke)); //設定筆刷寬度
					    //g2.setColor(foreColor); //設定顏色
					    				   
					    
					    //判斷是不是能畫
					    countPaint++;
					    
						if(countUndo == 0){ //在選擇上一步後，不增加筆刷
							    if(countPaint>2){
							    	//判斷選擇何種工具
							    	/*if(countEraser == 1){ //橡皮擦
							    		shapeList.add(new drawEraser());	
							    	}
							    	else if(toolComboBox.getSelectedIndex() == 0){ //筆刷
							    		shapeList.add(new drawPoint());
							    	}
							    
							    	else if(toolComboBox.getSelectedIndex() == 1){  //直線
								    	line = new drawLine(); //在mouseDraged的時候就先畫直線，之後released再存近陣列
								    }
								    
								    else if(toolComboBox.getSelectedIndex() == 2){ //橢圓形
								    	oval = new drawOval();
								    }
								    
								    else if(toolComboBox.getSelectedIndex() == 3){ //矩形
								    	rect = new drawRect();
								    }
								    
								    else if(toolComboBox.getSelectedIndex() == 4){ //圓角矩形
								    	roundRect = new drawRoundRect();
								    }*/
							    	roundRect = new drawRoundRect(Pre_X,Pre_Y,Now_X,Now_Y,20,20); //畫一個圓角矩形
							   }
						    }
					    countUndo = 0; //初始化
				   } 
			}
		
	
	//建構子
	public MidQ1(){
		super("小畫家"); //視窗標題
		PaintPanel paintPanel = new PaintPanel(); //建立畫布區的物件
		paintPanel.setBackground(Color.WHITE);
		setLayout(new BorderLayout()); //BorderLayout會把JFrame畫面分為「東」、「西」、「南」、「北」、「中」
		//設定控制區的介面
		controlBar.setLayout(new GridBagLayout());
		//smallControl1.setLayout(new GridBagLayout());
		//smallControl2.setLayout(new GridBagLayout());
		//第一個控制元件
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c1.weightx = 0;
		c1.weighty = 0;
		c1.fill = GridBagConstraints.NONE;
	    c1.anchor = GridBagConstraints.WEST;
		//第二個控制元件
	    c2.gridx = 1;
	    c2.gridy = 0;
	    c2.gridwidth = 1;
		c2.gridheight = 1;
		c2.weightx = 1;
		c2.weighty = 0;
		c2.fill = GridBagConstraints.BOTH;
	    c2.anchor = GridBagConstraints.WEST;
		//第三個控制元件
		c3.gridx = 1;
		c3.gridy = 2;
		c3.gridwidth = 2;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.CENTER;
       
	    
        //左半部
	    //introduce1
        controlBar.add(introduce1,c1);
	    //introduceText1
        
        //c2.weightx = 1; //向右延伸
        
        controlBar.add(introduceText1,c2);
	    //introduceText2
	    c2.gridx = 2;
	    c2.gridy = 0;
	    
	    controlBar.add(introduceText2,c2);
	    
	    //introduce2
	    c1.gridx = 0;
	    c1.gridy = 1;
	    controlBar.add(introduce2,c1);	      
	    //introduceText3
	    c2.gridx = 1;
	    c2.gridy = 1;
	    controlBar.add(introduceText3,c2);    
	    //introduceText4
	    c2.gridx = 2;
	    c2.gridy = 1;
	    controlBar.add(introduceText4,c2);
	    //drawButton
	    controlBar.add(drawButton,c3);
	    
	    //右半部
	    //getPointButton
	    c3.gridx = 5;
	    c3.gridy = 0;
	    controlBar.add(getPointButton,c3);
	    //introduce3
	    c1.gridx = 4;
	    c1.gridy = 1;
	    controlBar.add(introduce3,c1);
	    //introduceText5
	    c2.gridx = 5;
	    c2.gridy = 1;
	    controlBar.add(introduceText5,c2);
	    //introduceText6
	    c2.gridx = 6;
	    c2.gridy = 1;
	    controlBar.add(introduceText6,c2);
	    //deletePointButton
	    c3.gridx = 5;
	    c3.gridy = 2;
	    controlBar.add(deletePointButton,c3);
	    
	    /*controlBar.add(smallControl1,BorderLayout.WEST);
	    controlBar.add(smallControl2,BorderLayout.EAST);*/
	    
		mouseBar = new JLabel("mouse position"); //顯示滑鼠位置
		//controlBar.setPreferredSize(new Dimension(490,490));
		//paintPanel.setPreferredSize(new Dimension(490,490));
		
		//設定位置
		add(controlBar,BorderLayout.NORTH);
		add(paintPanel,BorderLayout.CENTER);
		add(mouseBar,BorderLayout.SOUTH);
		
		// create new ButtonHandler for button event handling 
		ButtonHandler buttonHandler = new ButtonHandler();
		drawButton.addActionListener(buttonHandler);
		getPointButton.addActionListener(buttonHandler);
		deletePointButton.addActionListener(buttonHandler);
		
		//游標位置偵測
		paintPanel.addMouseListener(new MouseHandler()); 
		paintPanel.addMouseMotionListener(new MouseMoveHandler()); 
		
		   
	}   
	// inner class for button event handling
			private class ButtonHandler implements ActionListener 
			   {
			      // handle button event
			      public void actionPerformed( ActionEvent event )
			      {
			    	  //畫圖
			    	  if(event.getActionCommand() == "Draw"){
			    		  //try{
			    			  getPicHeight = Integer.parseInt(introduceText3.getText()); //String to int
			    			  getPicWidth = Integer.parseInt(introduceText4.getText());
			    			  getRoundRectHeight = Integer.parseInt(introduceText5.getText());
			    			  getRoundRectWidth = Integer.parseInt(introduceText6.getText());
			    			  drawRoundRect drawRect = new drawRoundRect(getX,getY,getX+getPicWidth,getY+getPicHeight,getRoundRectWidth,getRoundRectHeight); //畫一個圓角矩形
			    			  
			    			  
			    			  //}
			    		  //catch(NumberFormatException){
			    			  
			    		  //}
			    	  }
			    	  //取點
			    	  else if(event.getActionCommand() == "取點"){
			    		 
			    		  JOptionPane.showMessageDialog(null,"開始取點","訊息",JOptionPane.INFORMATION_MESSAGE);
			    		  getPoint = 1;//取點
			    	  }
			    	  //刪除最新圖形
			    	  else if(event.getActionCommand() == "刪除最新圖形"){
			    		  if(shapeList.isEmpty()){
			    			  JOptionPane.showMessageDialog(null,"畫布上沒有東西!","錯誤",JOptionPane.ERROR_MESSAGE);
			    		  }
			    		  else{
			    			  shapeList.remove(shapeList.size()-1); //移除最後index
			    			  countUndo = 1; //undo 
			    			  repaint();
			    		  }
			    	  }
			      }
			   }
	// inner class to handle mouse events
	   private class MouseHandler extends MouseAdapter 
	   {
	      // handle mouse-click event and determine which button was pressed
	      public void mousePressed( MouseEvent event )
	      {
	    	  if(getPoint == 1){
	    		  getX = event.getX(); //取得滑鼠點選的X座標
	    		  getY = event.getY(); //取得滑鼠點選的Y座標
	    		  introduceText1.setText(Integer.toString(getX)); //從int轉String
	    		  introduceText2.setText(Integer.toString(getY)); //從int轉String
	    		  //String text = toString(1);
	    		  //introduceText1.setText();
	    		  getPoint = 0;
	    		  JOptionPane.showMessageDialog(null,"取點成功","訊息",JOptionPane.INFORMATION_MESSAGE);
	    	  }
	    	  else{
	    		  //存取剛按下取的位置
	    		  Pre_X = event.getX();
	    		  Pre_Y = event.getY();	  
	    	  }
	      }
	     public void mouseReleased(MouseEvent event){
	    	 
	    	 /*if(countEraser != 1){
		    	 if(toolComboBox.getSelectedIndex() == 1){ //直線
				    	shapeList.add(line); //存近陣列
				    }
		    	 else if(toolComboBox.getSelectedIndex() == 2){ //橢圓形
				    	shapeList.add(oval);
				    }
		    	 else if(toolComboBox.getSelectedIndex() == 3){ //矩形
				    	shapeList.add(rect);
				    }
		    	 else if(toolComboBox.getSelectedIndex() == 4){ //圓角矩形
				    	shapeList.add(roundRect);
				    }
	    	 }*/
	    	 shapeList.add(roundRect);
	    	 
	      }
	      public void mouseClicked(MouseEvent event){
	    	//現在位置
         	Now_X = event.getX();
		    Now_Y = event.getY();
		    repaint();
	    	  
	      }
         
	   }
	// inner class to handle mouse events
	   private class MouseMoveHandler extends MouseMotionAdapter 
	   {
	      // handle mouse-click event and determine which button was pressed
	      public void mouseMoved( MouseEvent event )
	      {
	    	  mouseBar.setText(String.format("游標位置: (%d,%d)",event.getX(),event.getY())); //顯示X,Y
	      }
	   // store drag coordinates and repaint
         public void mouseDragged( MouseEvent event )
         {
         	//現在位置
         	Now_X = event.getX();
		    Now_Y = event.getY();	
         	repaint();
         }

         
	   }
	   
	 //繪圖、父類別
	   //public class drawParent{
		 //  List<Integer> position = new ArrayList<Integer>(); //儲存位置
		   //String name = ""; //儲存名字以判斷
		   //Boolean view = false; //是否填滿
			//   public drawParent(){
				   //加進position
			/*   position.add(Pre_X);
			   position.add(Pre_Y);
			   position.add(Now_X);
			   position.add(Now_Y); */ 
		  // }
		  // public List<Integer> getPosition(){
			//   return position;
		  // }
		  /* public String getName(){ //回傳名字
				  return name;
			   }*/
		   /*public Boolean getView(){ //回傳是否需填滿
			   return view;
		   }*/
	   //}
	   //圓角矩形
	   public class drawRoundRect{
		   List<Integer> position = new ArrayList<Integer>(); //儲存位置
		   public drawRoundRect(int start_X,int start_Y,int end_X,int end_Y,int roundRectWidth,int roundRectHeight){
			   //g2.drawRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,20,20);
			   g2.drawRoundRect(start_X,start_Y, end_X, end_Y, roundRectWidth, roundRectHeight);
			   position.add(start_X);
			   position.add(start_Y);
			   position.add(end_X);
			   position.add(end_X);
		   }
		   public List<Integer> getPosition(){
			   return position;
		   }
		   
	   }
	      
	   
	//主程式
		public static void main(String[] args){
			MidQ1 q1 = new MidQ1();
			q1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			q1.setSize( 700, 700 ); // set frame size
			q1.setVisible( true ); // display frame
			
		}	   
}
