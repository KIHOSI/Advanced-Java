import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//import MidQ1.PaintPanel;




public class MidQ3 extends JFrame {
	private JButton runButton = new JButton("Run");
	private JPanel paintPanel = new JPanel();
	//private drawRect rect;//�x�s�ҵe�x��
	/*private JPanel small_1 = new JPanel();
	private JPanel small_2 = new JPanel();
	private JPanel small_3 = new JPanel();
	private JPanel small_4 = new JPanel();
	private JPanel small_5 = new JPanel();
	private JPanel small_6 = new JPanel();
	private JPanel small_7 = new JPanel();
	private JPanel small_8 = new JPanel();
	private JPanel small_9 = new JPanel();
	private JPanel small_10 = new JPanel();
	private JPanel small1_11 = new JPanel();*/
	
	private GridBagConstraints c = new GridBagConstraints(); //����introduce����
	private Graphics2D g2;
	
	
	public class PaintPanel extends JPanel{
		 public PaintPanel(){
		 } //�غc�l�O�Ū��A�ƥ�t�~�g�bmouseMoveHandler
		 
		//�M���e���A�e��
		 public void paintComponent( Graphics g ) //�@�w�n�~��JPanel�~�i�Hsuper.paintComponent
		   {
			  super.paintComponent( g ); // clears drawing area
			  
		   } 
	}
	public MidQ3(){
		super("���g�c");
		setLayout(new BorderLayout()); //BorderLayout�|��JFrame�e�������u�F�v�B�u��v�B�u�n�v�B�u�_�v�B�u���v
		//PaintPanel paintPanel = new PaintPanel(); //�إߵe���Ϫ�����
		paintPanel.setBackground(Color.WHITE);
		paintPanel.setPreferredSize(new Dimension(490,490));
		
		/*paintPanel.setLayout(new GridBagLayout());
		//(1,2)
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.WEST;
	    small_1.setBackground(Color.BLACK);
	    paintPanel.add(small_1,c);
	    //(1,4)
	    c.gridx = 4;
	    c.gridy = 1;
	    small_2.setBackground(Color.BLACK);
	    paintPanel.add(small_2,c);
	    */
		//������m
		add(paintPanel,BorderLayout.NORTH);
		add(runButton,BorderLayout.SOUTH);
		
	}
	
	//ø�ϡB�����O
	   public class drawParent{
		   List<Integer> position = new ArrayList<Integer>(); //�x�s��m
		   //String name = ""; //�x�s�W�r�H�P�_
		  // Boolean view = false; //�O�_��
		  // float s = stroke;  //�e��
		   
		  Color c = Color.BLACK; //�C��
		 
		   /*public drawParent(){
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
		   }*/
		   /*public Color getColor(){  //�^���C��
			   return c;
		   }*/
	  }
	 //�x��
	  public class drawRect extends drawParent{
		   public drawRect(int posX,int posY){
			   //if(fillView.isSelected()){ //�p�G�Ŀ�񺡡A�񺡯x��
		    	//	g2.fillRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
		    	//	view = true;
		    	//}
		    	//else{ //���񺡯x��
		    	//	g2.drawRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
		    		//view = false;
		    	//}
			   //name = "drawRect";
			   g2.setColor(c);
			   g2.fillRect(posX,posY,5,5);
		   }
	   }
	
	//�D�{��
			public static void main(String[] args){
				MidQ3 q3 = new MidQ3();
				q3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				q3.setSize( 700, 700 ); // set frame size
				q3.setVisible( true ); // display frame
				
			}	

}
   
