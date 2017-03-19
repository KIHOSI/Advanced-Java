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
	//private drawRect rect;//儲存所畫矩形
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
	
	private GridBagConstraints c = new GridBagConstraints(); //控制introduce元件
	private Graphics2D g2;
	
	
	public class PaintPanel extends JPanel{
		 public PaintPanel(){
		 } //建構子是空的，事件另外寫在mouseMoveHandler
		 
		//清除畫布，畫圖
		 public void paintComponent( Graphics g ) //一定要繼承JPanel才可以super.paintComponent
		   {
			  super.paintComponent( g ); // clears drawing area
			  
		   } 
	}
	public MidQ3(){
		super("走迷宮");
		setLayout(new BorderLayout()); //BorderLayout會把JFrame畫面分為「東」、「西」、「南」、「北」、「中」
		//PaintPanel paintPanel = new PaintPanel(); //建立畫布區的物件
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
		//介面位置
		add(paintPanel,BorderLayout.NORTH);
		add(runButton,BorderLayout.SOUTH);
		
	}
	
	//繪圖、父類別
	   public class drawParent{
		   List<Integer> position = new ArrayList<Integer>(); //儲存位置
		   //String name = ""; //儲存名字以判斷
		  // Boolean view = false; //是否填滿
		  // float s = stroke;  //寬度
		   
		  Color c = Color.BLACK; //顏色
		 
		   /*public drawParent(){
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
		   }*/
		   /*public Color getColor(){  //回傳顏色
			   return c;
		   }*/
	  }
	 //矩形
	  public class drawRect extends drawParent{
		   public drawRect(int posX,int posY){
			   //if(fillView.isSelected()){ //如果勾選填滿，填滿矩形
		    	//	g2.fillRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
		    	//	view = true;
		    	//}
		    	//else{ //不填滿矩形
		    	//	g2.drawRect(Pre_X,Pre_Y,Now_X-Pre_X,Now_Y-Pre_Y);
		    		//view = false;
		    	//}
			   //name = "drawRect";
			   g2.setColor(c);
			   g2.fillRect(posX,posY,5,5);
		   }
	   }
	
	//主程式
			public static void main(String[] args){
				MidQ3 q3 = new MidQ3();
				q3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				q3.setSize( 700, 700 ); // set frame size
				q3.setVisible( true ); // display frame
				
			}	

}
   
