//HW1:��ޤTB 103403530 �x�t��

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.awt.event.ItemListener; //checkbox�Mradiobox�|���ͨ��Event,�i��actionListener�ӧ@
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
    //Layout
	private JPanel toolBar = new JPanel(); //�u��C
	private JLabel mouseBar;//�ƹ����A�C	
	private JPanel paintBar = new JPanel();//�e����
	//background color
	private Color toolBarColor = new Color(153,204,255); //�u��C�C��
	
	//�غc�l
	public HW1_103403530(){
		super("�p�e�a"); //�������D
		setLayout(new BorderLayout()); //BorderLayout�|��JFrame�e�������u�F�v�B�u��v�B�u�n�v�B�u�_�v�B�u���v
		toolBar.setLayout(new GridLayout(11,1)); //�NtoolBar�]��11�C1��
		mouseBar = new JLabel("mouse position"); //��ܷƹ���m
		toolBar.setBackground(toolBarColor); //�u��C�I��
		paintBar.setBackground(new Color(204,255,204)); //�e���I��
		mouseBar.setBackground(Color.LIGHT_GRAY); //�ƹ����A�C�I��
		
		//2.create box ø�Ϥu��
		JLabel comboLabel = new JLabel("[ø�Ϥu��]");
		toolComboBox = new JComboBox(tool);
		toolComboBox.setMaximumRowCount(3);
		toolBar.add(comboLabel);
		toolBar.add(toolComboBox);
		
		//register event for combo box
		toolComboBox.addItemListener(new ComboBoxButtonHandler());
		
		//3.create radio button ����j�p
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
	    
	    //4.create check box
	    fillView = new JCheckBox("��");
	    fillView.setBackground(toolBarColor);
	    toolBar.add(fillView);
	    
	    // register listeners for JCheckBoxes
	      fillView.addActionListener(new CheckBoxHandler());
	    
	    //5.create button
	    foreGroundColor = new JButton("�e����");
	    backGroundColor = new JButton("�I����");
	    removeColor = new JButton("�M���e��");
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
	   
	   //6.��Ц�m����
	   paintBar.addMouseMotionListener(new MouseMoveHandler()); 
	}
	
	// private inner class to handle radio button events
	   private class ComboBoxButtonHandler implements ItemListener 
	   {
	   // handle radio button events
		  public void itemStateChanged(ItemEvent event )
		  {
		     //����I�磌�󪺸�T
			  if(event.getStateChange() == event.SELECTED){
				 JOptionPane.showMessageDialog(null, "�A�I��F: " + tool[toolComboBox.getSelectedIndex()], "�T��",JOptionPane.INFORMATION_MESSAGE);
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
		     //����I�磌�󪺸�T
			  JOptionPane.showMessageDialog(null, "�A�I��F: " + s, "�T��",JOptionPane.INFORMATION_MESSAGE);
		  }  	
		}
	   
	// private inner class for ItemListener event handling
		private class CheckBoxHandler implements ActionListener 
		{
	    // respond to checkbox events
		   public void actionPerformed( ActionEvent event )
		   {
			  if(fillView.isSelected()){
				  JOptionPane.showMessageDialog(null, "�A��ܤF��", "�T��",JOptionPane.INFORMATION_MESSAGE);
			   }
		      else{
		    	  JOptionPane.showMessageDialog(null, "�A�����F��", "�T��",JOptionPane.INFORMATION_MESSAGE);
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
		    	//����I�磌�󪺸�T
		         JOptionPane.showMessageDialog( null, "�A�I��F: " + s,"�T��",JOptionPane.INFORMATION_MESSAGE );
		      }
		   }
		// inner class to handle mouse events
		   private class MouseMoveHandler extends MouseMotionAdapter 
		   {
		      // handle mouse-click event and determine which button was pressed
		      public void mouseMoved( MouseEvent event )
		      {
		    	  //mouseBar.setText("tst"); //���X,Y
		    	  mouseBar.setText(String.format("��Ц�m: (%d,%d)",event.getX(),event.getY())); //���X,Y
		      }
		   }	   
	//�D�{��
		public static void main(String[] args){
			HW1_103403530 hw1 = new HW1_103403530();
			hw1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			hw1.setSize( 700, 700 ); // set frame size
			hw1.setVisible( true ); // display frame
		}	   
}
