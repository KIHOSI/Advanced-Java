//HW1:資管三B 103403530 洪靖雯

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Graphics;
import java.awt.Graphics2D;
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class HW2_103403530 extends JFrame{
	//繪圖工具
	private JComboBox toolComboBox;
	private static final String tool[] = {"筆刷","直線","橢圓形","矩形","圓角矩形"};
	//筆刷大、中、小
	private JRadioButton largeRadioButton; //select large
	private JRadioButton mediumRadioButton; //select medium
	private JRadioButton smallRadioButton; //select small
	private ButtonGroup radioGroup; 
	//是否填滿
	private JCheckBox fillView;
	//前景色、背景色、清除畫面
	private JButton foreGroundColor; //前景色
	private JButton backGroundColor; //背景色
	private JButton removeColor; //清除
	private Icon eraserImg = new ImageIcon(getClass().getResource("eraser.png")); //橡皮擦圖式
	private JButton eraserButton = new JButton("橡皮擦",eraserImg); //橡皮擦
	private JButton undoButton = new JButton("上一步"); //上一步
	//Layout
	private JPanel toolBar = new JPanel(); //工具列
	private JPanel menuBar = new JPanel(); //功能列
	private JLabel mouseBar;//滑鼠狀態列	
	//background color
	private Color toolBarColor = new Color(153,204,255); //工具列顏色
	//畫圖
	private int countPoint = 0; //count numbers of point
	//之前位置
	private int Pre_X = 0;
	private int Pre_Y = 0;
	//現在位置
	private int Now_X = 0;
	private int Now_Y = 0;
	//筆刷大小
	private float stroke = 5;
	
	private List<drawParent> shapeList= new ArrayList<drawParent>();//保存圖形
	private Graphics2D g2;
	private Color foreColor = new Color(0,0,0); //前景色所選顏色
	private Color backColor = Color.white; //背景色所選顏色
	private int countPaint = 0; //計算paintComponent跑了幾次
	private int countEraser = 0; //判斷要不要使用eraser
	private int countUndo = 0; //判斷要不要新增事件
	private float[] dashPattern = {10F, 20F, 10F, 20F, 10F, 20F, 10F, 20F}; //設定實線和空白
	
	private drawLine line;//儲存所畫直線
	private drawOval oval;//儲存所畫圓形
	private drawRect rect;//儲存所畫矩形
	private drawRoundRect roundRect;//儲存所畫圓角矩形
	
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
				    for (drawParent t : shapeList) {
				    	List<Integer> tPosition = new ArrayList<Integer>();
				    	tPosition = t.getPosition();//得到位置
				    	int [] ret = new int[tPosition.size()]; //從List<Integer>轉成int
				    	String name = t.getName(); //得到名稱
				    	Boolean view = t.getView(); //得到填滿的判斷
				    	float st = t.getStroke(); //得到寬度
				    	g2.setStroke(new BasicStroke(st)); //設定寬度
				    	Color co = t.getColor(); //得到顏色
				    	g2.setColor(co); //設定顏色
				    	
				    	for(int i = 0 ; i < ret.length;i++){ //將<Integer>轉成<int>
				    		ret[i] = tPosition.get(i);
				    	}
				    		
				    	if(name == "drawEraser"){ //橡皮擦
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
					    	}
					    else if(name == "drawRoundRect"){
					    		if(view){ //如果有填滿
					    			g2.fillRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],100,100);
					    		}
					    		else{ //沒有填滿
					    			g2.drawRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],100,100);
					    		}
					    	}
				    }
				    
				    
				    setBackground(backColor); //設定背景色		    
				    g2.setStroke(new BasicStroke(stroke)); //設定筆刷寬度
				    g2.setColor(foreColor); //設定顏色
				    				   
				    
				    //判斷是不是能畫
				    countPaint++;
				    
					if(countUndo == 0){ //在選擇上一步後，不增加筆刷
						    if(countPaint>2){
						    	//判斷選擇何種工具
						    	if(countEraser == 1){ //橡皮擦
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
							    }
						   }
					    }
				    countUndo = 0; //初始化
			   } 
		}
	
		
	//建構子
	public HW2_103403530(){
		super("小畫家"); //視窗標題
		PaintPanel paintPanel = new PaintPanel(); //建立畫布區的物件
		setLayout(new BorderLayout()); //BorderLayout會把JFrame畫面分為「東」、「西」、「南」、「北」、「中」
		toolBar.setLayout(new GridLayout(12,1)); //將toolBar設成11列1行
		mouseBar = new JLabel("mouse position"); //顯示滑鼠位置
		toolBar.setBackground(toolBarColor); //工具列背景
		mouseBar.setBackground(Color.LIGHT_GRAY); //滑鼠狀態列背景
		
		//create box 繪圖工具
		JLabel comboLabel = new JLabel("[繪圖工具]");
		toolComboBox = new JComboBox(tool);
		toolComboBox.setMaximumRowCount(3);
		toolBar.add(comboLabel);
		toolBar.add(toolComboBox);
		
		//橡皮擦
		toolBar.add(eraserButton);
		//上一步
		undoButton.setBackground(new Color(204,204,255));
		toolBar.add(undoButton);
		
		//create radio button 筆刷大小
		JLabel radioLabel = new JLabel("[筆刷大小]");
		smallRadioButton = new JRadioButton("小",true);
		mediumRadioButton = new JRadioButton("中",false);
		largeRadioButton = new JRadioButton("大",false);
		smallRadioButton.setBackground(toolBarColor);
		mediumRadioButton.setBackground(toolBarColor);
		largeRadioButton.setBackground(toolBarColor);
		toolBar.add(radioLabel);
		toolBar.add(smallRadioButton);
		toolBar.add(mediumRadioButton);
		toolBar.add(largeRadioButton);
		
		// create logical relationship between JRadioButtons
	    radioGroup = new ButtonGroup(); // create ButtonGroup
	    radioGroup.add( smallRadioButton ); // add plain to group
	    radioGroup.add( mediumRadioButton ); // add bold to group
	    radioGroup.add( largeRadioButton ); // add italic to group
	    
	    // register events for JRadioButtons
	    RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
	    smallRadioButton.addActionListener(radioButtonHandler);
	    mediumRadioButton.addActionListener(radioButtonHandler);
	    largeRadioButton.addActionListener(radioButtonHandler);
	    
	    //create check box
	    fillView = new JCheckBox("填滿");
	    fillView.setBackground(toolBarColor);
	    toolBar.add(fillView);
	    
	    //create button
	    foreGroundColor = new JButton("前景色");
	    backGroundColor = new JButton("背景色");
	    removeColor = new JButton("清除畫面");
	    foreGroundColor.setBackground(toolBarColor);
	    backGroundColor.setBackground(backColor);
	    removeColor.setBackground(new Color(204,204,255));
	    toolBar.add(foreGroundColor);
	    toolBar.add(backGroundColor);
	    toolBar.add(removeColor);
	    
	    // create new ButtonHandler for button event handling 
	      ButtonHandler buttonHandler = new ButtonHandler();
	      foreGroundColor.addActionListener(buttonHandler);
	      backGroundColor.addActionListener(buttonHandler);
	      removeColor.addActionListener(buttonHandler);
	      eraserButton.addActionListener(buttonHandler);
	      undoButton.addActionListener(buttonHandler);
	   
	   // add these Bar to JFrame   
	   add(toolBar,BorderLayout.WEST);   
	   add(mouseBar,BorderLayout.SOUTH);
	   add(paintPanel,BorderLayout.CENTER);
	   
	   //游標位置偵測
	   paintPanel.addMouseListener(new MouseHandler()); 
	   paintPanel.addMouseMotionListener(new MouseMoveHandler()); 
	}
	
	
	// private inner class to handle radio button events
	   private class RadioButtonHandler implements ActionListener 
	   {
	   // handle radio button events
		  public void actionPerformed( ActionEvent event )
		  {
			 if(event.getActionCommand() == "小"){ //選擇小筆刷
				 stroke = 5;
			 }
			  else if(event.getActionCommand() == "中"){ //選擇中筆刷
				  stroke = 10;
			  }
			  else if(event.getActionCommand() == "大"){ //選擇大筆刷
				  stroke = 15;
			  }
		  }  	
		}   
		
		// inner class for button event handling
		private class ButtonHandler implements ActionListener 
		   {
		      // handle button event
		      public void actionPerformed( ActionEvent event )
		      {
		    	  if(event.getActionCommand() == "前景色"){
		    		  foreColor = JColorChooser.showDialog(null,"Choose Color",foreColor);
		    		  foreGroundColor.setBackground(foreColor); //將背景設為選擇顏色
		    		  
		    	  }
		    	  else if(event.getActionCommand() == "背景色"){
		    		  backColor = JColorChooser.showDialog(null,"Choose Color",backColor);
		    		  backGroundColor.setBackground(backColor); //將背景設為選擇顏色
		    		  repaint();
		    	  }
		    	  else if(event.getActionCommand() == "清除畫面"){
		    		  shapeList.clear(); //清除畫面
		    		//初始化
		    		  countPaint = 1; 
		    		  Pre_X = 0;
		    		  Pre_Y = 0;
		    		  Now_X = 0;
		    		  Now_Y = 0;
		    		  line = new drawLine();
		    		  oval = new drawOval();
		    		  rect = new drawRect();
		    		  roundRect = new drawRoundRect();
		    		  
		    		  repaint();
		    	  }
		    	  
		    	  if(event.getActionCommand() == "橡皮擦"){
		    		  if(countEraser == 0){ //第一次點橡皮擦
		    			  countEraser = 1; //使用eraser
		    			  eraserButton.setBackground(new Color(204,204,255));
		    		  }
		    		  else{
		    			  countEraser = 0; //取消橡皮擦
		    			  eraserButton.setBackground(null);
		    		  }
		    	  }
		    	  
		    	  if(event.getActionCommand() == "上一步"){
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
		    	  //存取剛按下取的位置
		    	  Pre_X = event.getX();
		    	  Pre_Y = event.getY();	  
		      }
		     public void mouseReleased(MouseEvent event){
		    	 
		    	 if(countEraser != 1){
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
		    	 }
		    	 
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
		   public class drawParent{
			   List<Integer> position = new ArrayList<Integer>(); //儲存位置
			   String name = ""; //儲存名字以判斷
			   Boolean view = false; //是否填滿
			   float s = stroke;  //寬度
			   
			   Color c = foreColor; //顏色
 			   public drawParent(){
 				   //加進position
				   position.add(Pre_X);
				   position.add(Pre_Y);
				   position.add(Now_X);
				   position.add(Now_Y);  
			   }
			   public List<Integer> getPosition(){
				   return position;
			   }
			   public String getName(){ //回傳名字
					  return name;
				   }
			   public Boolean getView(){ //回傳是否需填滿
				   return view;
			   }
			   public float getStroke(){ //回傳寬度
				   return s;
			   }
			   public Color getColor(){  //回傳顏色
				   return c;
			   }
		   }
		   //筆刷
		   public class drawPoint extends drawParent{
			   public drawPoint(){
					   //判斷筆刷大小
					   if(stroke == 5){ //小筆刷
						  g2.fillOval(Now_X,Now_Y,10,10);
					   }
					   else if(stroke == 10){ //中筆刷
						   g2.fillOval(Now_X,Now_Y,30,30);
					   }
					   else if(stroke == 15){ //大筆刷
						   g2.fillOval(Now_X,Now_Y,50,50);
					   }
				   name="drawPoint";
				   
			   }
		   }
		   //直線
		   public class drawLine extends drawParent{
			   public drawLine(){ //實線
				   if(fillView.isSelected()){
					  g2.drawLine(Pre_X,Pre_Y,Now_X,Now_Y);
					  view = true;
				   }
				   else{ //虛線
					  g2.setStroke(new BasicStroke(s,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dashPattern,0));
					  g2.drawLine(Pre_X,Pre_Y,Now_X,Now_Y);
					  view = false;
				   }
				   
				   name = "drawLine";
			   }
		   }
		   //橢圓形
		   public class drawOval extends drawParent{
			   public drawOval(){
				   if(fillView.isSelected()){ //如果勾選填滿，填滿橢圓	
					   	g2.fillOval(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = true;
			    	}
			    	else{ //不填滿橢圓
			    		g2.drawOval(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = false;
			    	}
				   name = "drawOval";
			   }
		   }
		   //矩形
		   public class drawRect extends drawParent{
			   public drawRect(){
				   if(fillView.isSelected()){ //如果勾選填滿，填滿矩形
			    		g2.fillRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = true;
			    	}
			    	else{ //不填滿矩形
			    		g2.drawRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = false;
			    	}
				   name = "drawRect";
			   }
		   }
		 //圓角矩形
		   public class drawRoundRect extends drawParent{
			   public drawRoundRect(){
				   if(fillView.isSelected()){ //如果勾選填滿，填滿圓角矩形
			    		g2.fillRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
			    		view = true;
			    	}
			    	else{ //不填滿圓角矩形
			    		g2.drawRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
			    		view = false;
			    	}
				   name = "drawRoundRect";
			   }
		   }
		   //橡皮擦
		   public class drawEraser extends drawParent{
			   public drawEraser(){
				  c = backColor; //顏色設為背景顏色
				  g2.setColor(c); 
				  g2.fillOval(Now_X-20,Now_Y-20,50,50); 
				  name = "drawEraser";
				  
				  //初始化
				  Pre_X = 0;
	    		  Pre_Y = 0;
	    		  Now_X = 0;
	    		  Now_Y = 0;
	    		  line = new drawLine();
	    		  oval = new drawOval();
	    		  rect = new drawRect();
	    		  roundRect = new drawRoundRect();
			   }
			   
		   }
		   
		   
	//主程式
		public static void main(String[] args){
			HW2_103403530 hw2 = new HW2_103403530();
			hw2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			hw2.setSize( 700, 700 ); // set frame size
			hw2.setVisible( true ); // display frame
			
		}	   
}
