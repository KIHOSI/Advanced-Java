//HW1:資管三B 103403530 洪靖雯

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ItemListener; //checkbox和radiobox會產生兩個Event,可用actionListener來作
import java.awt.event.ItemEvent; 
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel; 
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HW1_103403530 extends JFrame{
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
    //Layout
	private JPanel toolBar = new JPanel(); //工具列
	private JLabel mouseBar;//滑鼠狀態列	
	private JPanel paintBar = new JPanel();//畫布區
	//background color
	private Color toolBarColor = new Color(153,204,255); //工具列顏色
	
	//建構子
	public HW1_103403530(){
		super("小畫家"); //視窗標題
		setLayout(new BorderLayout()); //BorderLayout會把JFrame畫面分為「東」、「西」、「南」、「北」、「中」
		toolBar.setLayout(new GridLayout(11,1)); //將toolBar設成11列1行
		mouseBar = new JLabel("mouse position"); //顯示滑鼠位置
		toolBar.setBackground(toolBarColor); //工具列背景
		paintBar.setBackground(new Color(204,255,204)); //畫布背景
		mouseBar.setBackground(Color.LIGHT_GRAY); //滑鼠狀態列背景
		
		//2.create box 繪圖工具
		JLabel comboLabel = new JLabel("[繪圖工具]");
		toolComboBox = new JComboBox(tool);
		toolComboBox.setMaximumRowCount(3);
		toolBar.add(comboLabel);
		toolBar.add(toolComboBox);
		
		//register event for combo box
		toolComboBox.addItemListener(new ComboBoxButtonHandler());
		
		//3.create radio button 筆刷大小
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
	    
	    //4.create check box
	    fillView = new JCheckBox("填滿");
	    fillView.setBackground(toolBarColor);
	    toolBar.add(fillView);
	    
	    // register listeners for JCheckBoxes
	      fillView.addActionListener(new CheckBoxHandler());
	    
	    //5.create button
	    foreGroundColor = new JButton("前景色");
	    backGroundColor = new JButton("背景色");
	    removeColor = new JButton("清除畫面");
	    foreGroundColor.setBackground(toolBarColor);
	    backGroundColor.setBackground(Color.BLACK);
	    removeColor.setBackground(toolBarColor);
	    toolBar.add(foreGroundColor);
	    toolBar.add(backGroundColor);
	    toolBar.add(removeColor);
	    
	    // create new ButtonHandler for button event handling 
	      ButtonHandler buttonHandler = new ButtonHandler();
	      foreGroundColor.addActionListener(buttonHandler);
	      backGroundColor.addActionListener(buttonHandler);
	      removeColor.addActionListener(buttonHandler);
	   
	   // add these Bar to JFrame   
	   add(toolBar,BorderLayout.WEST);   
	   add(mouseBar,BorderLayout.SOUTH);
	   add(paintBar,BorderLayout.CENTER);
	   
	   //6.游標位置偵測
	   paintBar.addMouseMotionListener(new MouseMoveHandler()); 
	}
	
	// private inner class to handle radio button events
	   private class ComboBoxButtonHandler implements ItemListener 
	   {
	   // handle radio button events
		  public void itemStateChanged(ItemEvent event )
		  {
		     //顯示點選物件的資訊
			  if(event.getStateChange() == event.SELECTED){
				 JOptionPane.showMessageDialog(null, "你點選了: " + tool[toolComboBox.getSelectedIndex()], "訊息",JOptionPane.INFORMATION_MESSAGE);
			  }
		  }
		}
	
	// private inner class to handle radio button events
	   private class RadioButtonHandler implements ActionListener 
	   {
	   // handle radio button events
		  public void actionPerformed( ActionEvent event )
		  {
			  String s = event.getActionCommand();
		     //顯示點選物件的資訊
			  JOptionPane.showMessageDialog(null, "你點選了: " + s, "訊息",JOptionPane.INFORMATION_MESSAGE);
		  }  	
		}
	   
	// private inner class for ItemListener event handling
		private class CheckBoxHandler implements ActionListener 
		{
	    // respond to checkbox events
		   public void actionPerformed( ActionEvent event )
		   {
			  if(fillView.isSelected()){
				  JOptionPane.showMessageDialog(null, "你選擇了填滿", "訊息",JOptionPane.INFORMATION_MESSAGE);
			   }
		      else{
		    	  JOptionPane.showMessageDialog(null, "你取消了填滿", "訊息",JOptionPane.INFORMATION_MESSAGE);
			   }
		   }
		}
		
		// inner class for button event handling
		private class ButtonHandler implements ActionListener 
		   {
		      // handle button event
		      public void actionPerformed( ActionEvent event )
		      {
		    	  String s = event.getActionCommand();
		    	//顯示點選物件的資訊
		         JOptionPane.showMessageDialog( null, "你點選了: " + s,"訊息",JOptionPane.INFORMATION_MESSAGE );
		      }
		   }
		// inner class to handle mouse events
		   private class MouseMoveHandler extends MouseMotionAdapter 
		   {
		      // handle mouse-click event and determine which button was pressed
		      public void mouseMoved( MouseEvent event )
		      {
		    	  //mouseBar.setText("tst"); //顯示X,Y
		    	  mouseBar.setText(String.format("游標位置: (%d,%d)",event.getX(),event.getY())); //顯示X,Y
		      }
		   }	   
	//主程式
		public static void main(String[] args){
			HW1_103403530 hw1 = new HW1_103403530();
			hw1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			hw1.setSize( 700, 700 ); // set frame size
			hw1.setVisible( true ); // display frame
		}	   
}
