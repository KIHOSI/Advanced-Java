//HW1:��ޤTB 103403530 �x�t��

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

import java.awt.event.ItemListener; //checkbox�Mradiobox�|���ͨ��Event,�i��actionListener�ӧ@
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
	//ø�Ϥu��
	private JComboBox toolComboBox;
	private static final String tool[] = {"����","���u","����","�x��","�ꨤ�x��"};
	//����j�B���B�p
	private JRadioButton largeRadioButton; //select large
	private JRadioButton mediumRadioButton; //select medium
	private JRadioButton smallRadioButton; //select small
	private ButtonGroup radioGroup; 
	//�O�_��
	private JCheckBox fillView;
	//�e����B�I����B�M���e��
	private JButton foreGroundColor; //�e����
	private JButton backGroundColor; //�I����
	private JButton removeColor; //�M��
	private Icon eraserImg = new ImageIcon(getClass().getResource("eraser.png")); //������Ϧ�
	private JButton eraserButton = new JButton("�����",eraserImg); //�����
	private JButton undoButton = new JButton("�W�@�B"); //�W�@�B
	//Layout
	private JPanel toolBar = new JPanel(); //�u��C
	private JPanel menuBar = new JPanel(); //�\��C
	private JLabel mouseBar;//�ƹ����A�C	
	//background color
	private Color toolBarColor = new Color(153,204,255); //�u��C�C��
	//�e��
	private int countPoint = 0; //count numbers of point
	//���e��m
	private int Pre_X = 0;
	private int Pre_Y = 0;
	//�{�b��m
	private int Now_X = 0;
	private int Now_Y = 0;
	//����j�p
	private float stroke = 5;
	
	private List<drawParent> shapeList= new ArrayList<drawParent>();//�O�s�ϧ�
	private Graphics2D g2;
	private Color foreColor = new Color(0,0,0); //�e����ҿ��C��
	private Color backColor = Color.white; //�I����ҿ��C��
	private int countPaint = 0; //�p��paintComponent�]�F�X��
	private int countEraser = 0; //�P�_�n���n�ϥ�eraser
	private int countUndo = 0; //�P�_�n���n�s�W�ƥ�
	private float[] dashPattern = {10F, 20F, 10F, 20F, 10F, 20F, 10F, 20F}; //�]�w��u�M�ť�
	
	private drawLine line;//�x�s�ҵe���u
	private drawOval oval;//�x�s�ҵe���
	private drawRect rect;//�x�s�ҵe�x��
	private drawRoundRect roundRect;//�x�s�ҵe�ꨤ�x��
	
	//�ŧi�e����
		public class PaintPanel extends JPanel{
			 public PaintPanel(){
			 } //�غc�l�O�Ū��A�ƥ�t�~�g�bmouseMoveHandler
			 
			//�M���e���A�e��
			  public void paintComponent( Graphics g ) //�@�w�n�~��JPanel�~�i�Hsuper.paintComponent
			   {
				    super.paintComponent( g ); // clears drawing area
				    g2 = (Graphics2D) g ; //�إߤ@��graphics2D
				     			    
				    //�e�X�Ҧs�ϧ�
				    for (drawParent t : shapeList) {
				    	List<Integer> tPosition = new ArrayList<Integer>();
				    	tPosition = t.getPosition();//�o���m
				    	int [] ret = new int[tPosition.size()]; //�qList<Integer>�নint
				    	String name = t.getName(); //�o��W��
				    	Boolean view = t.getView(); //�o��񺡪��P�_
				    	float st = t.getStroke(); //�o��e��
				    	g2.setStroke(new BasicStroke(st)); //�]�w�e��
				    	Color co = t.getColor(); //�o���C��
				    	g2.setColor(co); //�]�w�C��
				    	
				    	for(int i = 0 ; i < ret.length;i++){ //�N<Integer>�ন<int>
				    		ret[i] = tPosition.get(i);
				    	}
				    		
				    	if(name == "drawEraser"){ //�����
				    		g2.fillOval(ret[2]-20, ret[3]-20, 50,50);
				    	}
				    	//����
				    	else if(name == "drawPoint"){
					    			 //�P�_����j�p
					    			if(st == 5){ //�p����
					    				g2.fillOval(ret[2],ret[3],10,10);
					    			}
					    			else if(st == 10){ //������
					    				g2.fillOval(ret[2],ret[3],30,30);
					    			}
					    			else if(st == 15){ //�j����
					    				g2.fillOval(ret[2],ret[3],50,50);
					    			}
					    	}
					    //���u
					    else if(name == "drawLine"){
					    		if(view){ //��u
					    			g2.drawLine(ret[0], ret[1], ret[2], ret[3]);
					    		}
					    		else{ //��u
					    			g2.setStroke(new BasicStroke(st,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dashPattern,0));
					    			g2.drawLine(ret[0], ret[1], ret[2], ret[3]);
					    		}
					    		
					    	}
					    //���
					    else if(name == "drawOval"){
					    		if(view){ //�p�G����
					    			g2.fillOval(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
					    		}
					    		else{ //�S����
					    			g2.drawOval(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
					    		}
					    	}
					    //�x��
					    else if(name == "drawRect"){
					    		if(view){ //�p�G����
					    			g2.fillRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
					    		}
					    		else{ //�S����
					    			g2.drawRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1]);
					    		}
					    	}
					    else if(name == "drawRoundRect"){
					    		if(view){ //�p�G����
					    			g2.fillRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],100,100);
					    		}
					    		else{ //�S����
					    			g2.drawRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],100,100);
					    		}
					    	}
				    }
				    
				    
				    setBackground(backColor); //�]�w�I����		    
				    g2.setStroke(new BasicStroke(stroke)); //�]�w����e��
				    g2.setColor(foreColor); //�]�w�C��
				    				   
				    
				    //�P�_�O���O��e
				    countPaint++;
				    
					if(countUndo == 0){ //�b��ܤW�@�B��A���W�[����
						    if(countPaint>2){
						    	//�P�_��ܦ�ؤu��
						    	if(countEraser == 1){ //�����
						    		shapeList.add(new drawEraser());	
						    	}
						    	else if(toolComboBox.getSelectedIndex() == 0){ //����
						    		shapeList.add(new drawPoint());
						    	}
						    
						    	else if(toolComboBox.getSelectedIndex() == 1){  //���u
							    	line = new drawLine(); //�bmouseDraged���ɭԴN���e���u�A����released�A�s��}�C
							    }
							    
							    else if(toolComboBox.getSelectedIndex() == 2){ //����
							    	oval = new drawOval();
							    }
							    
							    else if(toolComboBox.getSelectedIndex() == 3){ //�x��
							    	rect = new drawRect();
							    }
							    
							    else if(toolComboBox.getSelectedIndex() == 4){ //�ꨤ�x��
							    	roundRect = new drawRoundRect();
							    }
						   }
					    }
				    countUndo = 0; //��l��
			   } 
		}
	
		
	//�غc�l
	public HW2_103403530(){
		super("�p�e�a"); //�������D
		PaintPanel paintPanel = new PaintPanel(); //�إߵe���Ϫ�����
		setLayout(new BorderLayout()); //BorderLayout�|��JFrame�e�������u�F�v�B�u��v�B�u�n�v�B�u�_�v�B�u���v
		toolBar.setLayout(new GridLayout(12,1)); //�NtoolBar�]��11�C1��
		mouseBar = new JLabel("mouse position"); //��ܷƹ���m
		toolBar.setBackground(toolBarColor); //�u��C�I��
		mouseBar.setBackground(Color.LIGHT_GRAY); //�ƹ����A�C�I��
		
		//create box ø�Ϥu��
		JLabel comboLabel = new JLabel("[ø�Ϥu��]");
		toolComboBox = new JComboBox(tool);
		toolComboBox.setMaximumRowCount(3);
		toolBar.add(comboLabel);
		toolBar.add(toolComboBox);
		
		//�����
		toolBar.add(eraserButton);
		//�W�@�B
		undoButton.setBackground(new Color(204,204,255));
		toolBar.add(undoButton);
		
		//create radio button ����j�p
		JLabel radioLabel = new JLabel("[����j�p]");
		smallRadioButton = new JRadioButton("�p",true);
		mediumRadioButton = new JRadioButton("��",false);
		largeRadioButton = new JRadioButton("�j",false);
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
	    fillView = new JCheckBox("��");
	    fillView.setBackground(toolBarColor);
	    toolBar.add(fillView);
	    
	    //create button
	    foreGroundColor = new JButton("�e����");
	    backGroundColor = new JButton("�I����");
	    removeColor = new JButton("�M���e��");
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
	   
	   //��Ц�m����
	   paintPanel.addMouseListener(new MouseHandler()); 
	   paintPanel.addMouseMotionListener(new MouseMoveHandler()); 
	}
	
	
	// private inner class to handle radio button events
	   private class RadioButtonHandler implements ActionListener 
	   {
	   // handle radio button events
		  public void actionPerformed( ActionEvent event )
		  {
			 if(event.getActionCommand() == "�p"){ //��ܤp����
				 stroke = 5;
			 }
			  else if(event.getActionCommand() == "��"){ //��ܤ�����
				  stroke = 10;
			  }
			  else if(event.getActionCommand() == "�j"){ //��ܤj����
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
		    	  if(event.getActionCommand() == "�e����"){
		    		  foreColor = JColorChooser.showDialog(null,"Choose Color",foreColor);
		    		  foreGroundColor.setBackground(foreColor); //�N�I���]������C��
		    		  
		    	  }
		    	  else if(event.getActionCommand() == "�I����"){
		    		  backColor = JColorChooser.showDialog(null,"Choose Color",backColor);
		    		  backGroundColor.setBackground(backColor); //�N�I���]������C��
		    		  repaint();
		    	  }
		    	  else if(event.getActionCommand() == "�M���e��"){
		    		  shapeList.clear(); //�M���e��
		    		//��l��
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
		    	  
		    	  if(event.getActionCommand() == "�����"){
		    		  if(countEraser == 0){ //�Ĥ@���I�����
		    			  countEraser = 1; //�ϥ�eraser
		    			  eraserButton.setBackground(new Color(204,204,255));
		    		  }
		    		  else{
		    			  countEraser = 0; //���������
		    			  eraserButton.setBackground(null);
		    		  }
		    	  }
		    	  
		    	  if(event.getActionCommand() == "�W�@�B"){
		    		  if(shapeList.isEmpty()){
		    			  JOptionPane.showMessageDialog(null,"�e���W�S���F��!","���~",JOptionPane.ERROR_MESSAGE);
		    		  }
		    		  else{
		    			  shapeList.remove(shapeList.size()-1); //�����̫�index
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
		    	  //�s������U������m
		    	  Pre_X = event.getX();
		    	  Pre_Y = event.getY();	  
		      }
		     public void mouseReleased(MouseEvent event){
		    	 
		    	 if(countEraser != 1){
			    	 if(toolComboBox.getSelectedIndex() == 1){ //���u
					    	shapeList.add(line); //�s��}�C
					    }
			    	 else if(toolComboBox.getSelectedIndex() == 2){ //����
					    	shapeList.add(oval);
					    }
			    	 else if(toolComboBox.getSelectedIndex() == 3){ //�x��
					    	shapeList.add(rect);
					    }
			    	 else if(toolComboBox.getSelectedIndex() == 4){ //�ꨤ�x��
					    	shapeList.add(roundRect);
					    }
		    	 }
		    	 
		      }
		      public void mouseClicked(MouseEvent event){
		    	//�{�b��m
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
		    	  mouseBar.setText(String.format("��Ц�m: (%d,%d)",event.getX(),event.getY())); //���X,Y
		      }
		   // store drag coordinates and repaint
	            public void mouseDragged( MouseEvent event )
	            {
	            	//�{�b��m
	            	Now_X = event.getX();
			    	Now_Y = event.getY();	
	            	repaint();
	            	
	            }
	
	            
		   }
	//ø�ϡB�����O
		   public class drawParent{
			   List<Integer> position = new ArrayList<Integer>(); //�x�s��m
			   String name = ""; //�x�s�W�r�H�P�_
			   Boolean view = false; //�O�_��
			   float s = stroke;  //�e��
			   
			   Color c = foreColor; //�C��
 			   public drawParent(){
 				   //�[�iposition
				   position.add(Pre_X);
				   position.add(Pre_Y);
				   position.add(Now_X);
				   position.add(Now_Y);  
			   }
			   public List<Integer> getPosition(){
				   return position;
			   }
			   public String getName(){ //�^�ǦW�r
					  return name;
				   }
			   public Boolean getView(){ //�^�ǬO�_�ݶ�
				   return view;
			   }
			   public float getStroke(){ //�^�Ǽe��
				   return s;
			   }
			   public Color getColor(){  //�^���C��
				   return c;
			   }
		   }
		   //����
		   public class drawPoint extends drawParent{
			   public drawPoint(){
					   //�P�_����j�p
					   if(stroke == 5){ //�p����
						  g2.fillOval(Now_X,Now_Y,10,10);
					   }
					   else if(stroke == 10){ //������
						   g2.fillOval(Now_X,Now_Y,30,30);
					   }
					   else if(stroke == 15){ //�j����
						   g2.fillOval(Now_X,Now_Y,50,50);
					   }
				   name="drawPoint";
				   
			   }
		   }
		   //���u
		   public class drawLine extends drawParent{
			   public drawLine(){ //��u
				   if(fillView.isSelected()){
					  g2.drawLine(Pre_X,Pre_Y,Now_X,Now_Y);
					  view = true;
				   }
				   else{ //��u
					  g2.setStroke(new BasicStroke(s,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f,dashPattern,0));
					  g2.drawLine(Pre_X,Pre_Y,Now_X,Now_Y);
					  view = false;
				   }
				   
				   name = "drawLine";
			   }
		   }
		   //����
		   public class drawOval extends drawParent{
			   public drawOval(){
				   if(fillView.isSelected()){ //�p�G�Ŀ�񺡡A�񺡾��	
					   	g2.fillOval(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = true;
			    	}
			    	else{ //���񺡾��
			    		g2.drawOval(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = false;
			    	}
				   name = "drawOval";
			   }
		   }
		   //�x��
		   public class drawRect extends drawParent{
			   public drawRect(){
				   if(fillView.isSelected()){ //�p�G�Ŀ�񺡡A�񺡯x��
			    		g2.fillRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = true;
			    	}
			    	else{ //���񺡯x��
			    		g2.drawRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
			    		view = false;
			    	}
				   name = "drawRect";
			   }
		   }
		 //�ꨤ�x��
		   public class drawRoundRect extends drawParent{
			   public drawRoundRect(){
				   if(fillView.isSelected()){ //�p�G�Ŀ�񺡡A�񺡶ꨤ�x��
			    		g2.fillRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
			    		view = true;
			    	}
			    	else{ //���񺡶ꨤ�x��
			    		g2.drawRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
			    		view = false;
			    	}
				   name = "drawRoundRect";
			   }
		   }
		   //�����
		   public class drawEraser extends drawParent{
			   public drawEraser(){
				  c = backColor; //�C��]���I���C��
				  g2.setColor(c); 
				  g2.fillOval(Now_X-20,Now_Y-20,50,50); 
				  name = "drawEraser";
				  
				  //��l��
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
		   
		   
	//�D�{��
		public static void main(String[] args){
			HW2_103403530 hw2 = new HW2_103403530();
			hw2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			hw2.setSize( 700, 700 ); // set frame size
			hw2.setVisible( true ); // display frame
			
		}	   
}