//HW1:��ޤTB 103403530 �x�t��
//��int ��String

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
	private JPanel controlBar = new JPanel(); //�W�豱���
	private JLabel mouseBar; //�ƹ����A�C
	//private JPanel smallControl1 = new JPanel(); //����ϥ��b��
	//private JPanel smallControl2 = new JPanel(); //����ϥk�b��
	//�]�w����ϥ��b��
	private JLabel introduce1 = new JLabel("���W�y��:");
	private JTextField introduceText1 = new JTextField();
	private JTextField introduceText2 = new JTextField();
	private JLabel introduce2 = new JLabel("�ϧΪ��e:");
	private JTextField introduceText3 = new JTextField();
	private JTextField introduceText4 = new JTextField();
	private JButton drawButton = new JButton("Draw");
	//�]�w����ϥk�b��
	private JButton getPointButton = new JButton("���I");
	private JLabel introduce3 = new JLabel("�ꨤ���e:");
	private JTextField introduceText5 = new JTextField();
	private JTextField introduceText6 = new JTextField();
	private JButton deletePointButton = new JButton("�R���̷s�ϧ�");
	//�����
	private GridBagConstraints c1 = new GridBagConstraints(); //����introduce����
	private GridBagConstraints c2 = new GridBagConstraints(); //����TextField����
	private GridBagConstraints c3 = new GridBagConstraints(); //����button����
	//�e����
	private List<drawParent> shapeList= new ArrayList<drawParent>();//�O�s�ϧ�
	private Graphics2D g2;
	private int countPaint = 0; //�p��paintComponent�]�F�X��
	private int countUndo = 0; //�P�_�n���n�s�W�ƥ�
	private drawRoundRect roundRect;//�x�s�ҵe�ꨤ�x��
	private int getPoint = 0; //�P�_�O�_�n���I
	//���e��m
	private int Pre_X = 0;
	private int Pre_Y = 0;
	//�{�b��m
	private int Now_X = 0;
	private int Now_Y = 0;
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
					    	//float st = t.getStroke(); //�o��e��
					    	//g2.setStroke(new BasicStroke(st)); //�]�w�e��
					    	//Color co = t.getColor(); //�o���C��
					    	//g2.setColor(co); //�]�w�C��
					    	
					    	for(int i = 0 ; i < ret.length;i++){ //�N<Integer>�ন<int>
					    		ret[i] = tPosition.get(i);
					    	}
					    		
					    	/*if(name == "drawEraser"){ //�����
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
						    	}*/
					    	//�ꨤ�x��
						   // if(name == "drawRoundRect"){	
						    	g2.drawRoundRect(ret[0],ret[1],ret[2]-ret[0],ret[3]-ret[1],20,20);	
						   // 	}
					    }
					    
					    
					    //setBackground(backColor); //�]�w�I����		    
					    //g2.setStroke(new BasicStroke(stroke)); //�]�w����e��
					    //g2.setColor(foreColor); //�]�w�C��
					    				   
					    
					    //�P�_�O���O��e
					    countPaint++;
					    
						if(countUndo == 0){ //�b��ܤW�@�B��A���W�[����
							    if(countPaint>2){
							    	//�P�_��ܦ�ؤu��
							    	/*if(countEraser == 1){ //�����
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
								    }*/
							    	roundRect = new drawRoundRect();
							   }
						    }
					    countUndo = 0; //��l��
				   } 
			}
		
	
	//�غc�l
	public MidQ1(){
		super("�p�e�a"); //�������D
		PaintPanel paintPanel = new PaintPanel(); //�إߵe���Ϫ�����
		paintPanel.setBackground(Color.WHITE);
		setLayout(new BorderLayout()); //BorderLayout�|��JFrame�e�������u�F�v�B�u��v�B�u�n�v�B�u�_�v�B�u���v
		//�]�w����Ϫ�����
		controlBar.setLayout(new GridBagLayout());
		//smallControl1.setLayout(new GridBagLayout());
		//smallControl2.setLayout(new GridBagLayout());
		//�Ĥ@�ӱ����
		c1.gridx = 0;
		c1.gridy = 0;
		c1.gridwidth = 1;
		c1.gridheight = 1;
		c1.weightx = 0;
		c1.weighty = 0;
		c1.fill = GridBagConstraints.NONE;
	    c1.anchor = GridBagConstraints.WEST;
		//�ĤG�ӱ����
	    c2.gridx = 1;
	    c2.gridy = 0;
	    c2.gridwidth = 1;
		c2.gridheight = 1;
		c2.weightx = 1;
		c2.weighty = 0;
		c2.fill = GridBagConstraints.BOTH;
	    c2.anchor = GridBagConstraints.WEST;
		//�ĤT�ӱ����
		c3.gridx = 1;
		c3.gridy = 2;
		c3.gridwidth = 2;
        c3.gridheight = 1;
        c3.weightx = 0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.BOTH;
        c3.anchor = GridBagConstraints.CENTER;
       
	    
        //���b��
	    //introduce1
        controlBar.add(introduce1,c1);
	    //introduceText1
        
        //c2.weightx = 1; //�V�k����
        
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
	    
	    //�k�b��
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
	    
		mouseBar = new JLabel("mouse position"); //��ܷƹ���m
		//controlBar.setPreferredSize(new Dimension(490,490));
		//paintPanel.setPreferredSize(new Dimension(490,490));
		
		//�]�w��m
		add(controlBar,BorderLayout.NORTH);
		add(paintPanel,BorderLayout.CENTER);
		add(mouseBar,BorderLayout.SOUTH);
		
		// create new ButtonHandler for button event handling 
		ButtonHandler buttonHandler = new ButtonHandler();
		drawButton.addActionListener(buttonHandler);
		getPointButton.addActionListener(buttonHandler);
		deletePointButton.addActionListener(buttonHandler);
		
		//��Ц�m����
		paintPanel.addMouseListener(new MouseHandler()); 
		paintPanel.addMouseMotionListener(new MouseMoveHandler()); 
		
		   
	}   
	// inner class for button event handling
			private class ButtonHandler implements ActionListener 
			   {
			      // handle button event
			      public void actionPerformed( ActionEvent event )
			      {
			    	  //�e��
			    	  if(event.getActionCommand() == "Draw"){
			    		/*String num = introduceText1.getText();
			    		System.out.println(num);*/
			    		//int x = (int) introduceText1.getText();
			    		//g2.drawRoundRect(introduceText1.getText(),introduceText2.getText(), introduceText1.getText()+introduceText3.getText(),introduceText2.getText()+introduceText4.getText(), introduceText5.getText(), introduceText6.getText()); 
			    		//int num = introducedText1.getText(); 
			    	  }
			    	  //���I
			    	  else if(event.getActionCommand() == "���I"){
			    		 
			    		  JOptionPane.showMessageDialog(null,"�}�l���I","�T��",JOptionPane.INFORMATION_MESSAGE);
			    		  getPoint = 1;//���I
			    	  }
			    	  //�R���̷s�ϧ�
			    	  else if(event.getActionCommand() == "�R���̷s�ϧ�"){
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
	    	  if(getPoint == 1){
	    		  int getX = event.getX();
	    		  int getY = event.getY();
	    		  //String text = toString(1);
	    		  //introduceText1.setText();
	    		  getPoint = 0;
	    		  JOptionPane.showMessageDialog(null,"���I���\","�T��",JOptionPane.INFORMATION_MESSAGE);
	    	  }
	    	  else{
	    		  //�s������U������m
	    		  Pre_X = event.getX();
	    		  Pre_Y = event.getY();	  
	    	  }
	      }
	     public void mouseReleased(MouseEvent event){
	    	 
	    	 /*if(countEraser != 1){
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
	    	 }*/
	    	 shapeList.add(roundRect);
	    	 
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
		   //float s = stroke;  //�e��
		   
		   //Color c = foreColor; //�C��
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
		   /*public float getStroke(){ //�^�Ǽe��
			   return s;
		   }
		   public Color getColor(){  //�^���C��
			   return c;
		   }*/
	   }
	   //�ꨤ�x��
	   public class drawRoundRect extends drawParent{
		   public drawRoundRect(){
			   /*if(fillView.isSelected()){ //�p�G�Ŀ�񺡡A�񺡶ꨤ�x��
		    		g2.fillRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
		    		view = true;
		    	}
		    	else{ //���񺡶ꨤ�x��
		    		g2.drawRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,100,100);
		    		view = false;
		    	}*/
			   g2.drawRoundRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y,20,20);
			   
		   }
		   
	   }
	      
	   
	//�D�{��
		public static void main(String[] args){
			MidQ1 q1 = new MidQ1();
			q1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			q1.setSize( 700, 700 ); // set frame size
			q1.setVisible( true ); // display frame
			
		}	   
}