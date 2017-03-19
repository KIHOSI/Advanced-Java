//FinalQ1:資管三B 103403530 洪靖雯

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;






















import javax.security.auth.x500.X500Principal;
//import java.lang.Iterable;
import javax.swing.JPanel;

import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel; 
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinalQ1_103403530 extends JFrame{
	private JPanel controlBar = new JPanel(); //上方控制區
	private JPanel buttonPanel = new JPanel(); //按鈕區
	private JPanel showMessage = new JPanel(); //顯示狀態區
	private ImageIcon bg = new ImageIcon(getClass().getResource("bg.jpg")); //畫布背景
	private JLabel nowFunctionJLabel = new JLabel("目前功能:"); //顯示現在功能
	private JLabel nowFishNumJLabel = new JLabel("魚數量:"); //顯示魚的數目
	private JLabel fishingNumJLabel = new JLabel("已釣到的魚數量:"); //顯示已釣到多少魚
	//private JLabel emptyLabel1 = new JLabel(); //空白內容，為了讓自對齊
	//private JLabel emptyLabel2 = new JLabel(); //空白內容，為了讓自對齊
	//private JLabel emptyLabel3 = new JLabel(); //空白內容，為了讓自對齊
	//private JLabel emptyLabel4 = new JLabel(); //空白內容，為了讓自對齊
	//控制按鈕
	private JButton newFishButton = new JButton("新增魚");
	private JButton newTurtleButton = new JButton("放釣竿");
	private JButton deleteAnimalButton = new JButton("收釣竿");
	private JButton deleteAllButton = new JButton("移除全部");
	private JButton controlAnimalButton = new JButton("停止/繼續選取");
	private JButton controlAllButton = new JButton("停止/繼續全部");
	private Boolean stopAllFlag = false; //判斷暫停全部或是繼續全部，true是停止
	private Boolean stopFlag = false; //判斷暫停或是繼續，true是停止
	//private Boolean deleteFlag = false; //判斷是否要刪除，true是刪除
	private int buttonMode = 0; //判斷是按哪個按鈕
	private int fishingNum = 0; //已釣到多少隻魚
	//private List<Fish> fishList = new ArrayList<Fish>(); //儲存魚的thread
	//private List<FishRod> fishRodList = new ArrayList<FishRod>(); //儲存烏龜的thread
	private LinkedList<Fish> fishList = new LinkedList<Fish>();
	private LinkedList<FishRod> fishRodList = new LinkedList<FishRod>();
	
	//儲存魚和烏龜
	private Fish newFish;
	private FishRod newFishRod;
	//判斷魚和魚竿的位置
	private DetermineTask determinTask = new DetermineTask();
	private Timer timer = new Timer();
	private boolean fish_fishrod_exist = false; //true代表都有存在，false代表沒有(預設)
	//現在位置
	private int Now_X = 0;
	private int Now_Y = 0;
	private PaintPanel paintPanel = new PaintPanel(); //建立畫布區的物件
	
	//宣告畫布區
	class PaintPanel extends JPanel{
				 public PaintPanel(){} //建構子是空的，事件另外寫在mouseMoveHandler
				//清除畫布，畫圖
				 public void paintComponent( Graphics g ) //一定要繼承JPanel才可以super.paintComponent
				 {
					 super.paintComponent( g ); // clears drawing area
					 bg.paintIcon(this, g, 0, 0); //畫圖
				 }		  
	}
		
	
	//建構子
	public FinalQ1_103403530(){
		super("水族箱"); //視窗標題
		paintPanel.setLayout(null); //畫布的layout要設成null，預設為flowLayout，這樣在新增魚和烏龜時會出現錯誤
		setLayout(new BorderLayout()); //BorderLayout會把JFrame畫面分為「東」、「西」、「南」、「北」、「中」
		//設定控制區的介面
		controlBar.setLayout(new GridLayout(2,1)); //分成兩列
		//設定按鈕區位置
		buttonPanel.setLayout(new GridLayout(2,3)); //2列3行
		buttonPanel.add(newFishButton);
		buttonPanel.add(deleteAnimalButton);
		buttonPanel.add(controlAnimalButton);
		buttonPanel.add(newTurtleButton);
		buttonPanel.add(deleteAllButton);
		buttonPanel.add(controlAllButton);
		controlBar.add(buttonPanel); //控制區上方
		//設定顯示區位置
		showMessage.setBackground(Color.BLACK);
		showMessage.setLayout(new GridLayout(1,3)); //1列3行
		nowFunctionJLabel.setForeground(new Color(0, 255, 204)); //字體顏色
		nowFishNumJLabel.setForeground(new Color(0, 255, 204)); //字體顏色
		fishingNumJLabel.setForeground(new Color(0, 255, 204)); //字體顏色
		nowFunctionJLabel.setFont(new Font("Dialog",   1,   15)); //字體大小
		nowFishNumJLabel.setFont(new Font("Dialog",   1,   15));
		fishingNumJLabel.setFont(new Font("Dialog",   1,   15));
		showMessage.add(nowFunctionJLabel);
		showMessage.add(nowFishNumJLabel);
		showMessage.add(fishingNumJLabel);
		//showMessage.add(emptyLabel1); //空白，為了讓字對齊
		//showMessage.add(emptyLabel2); //空白，為了讓字對齊
		//showMessage.add(emptyLabel3); //空白，為了讓字對齊
		//showMessage.add(emptyLabel4); //空白，為了讓字對齊
		controlBar.add(showMessage); //控制區下方
       
		//設定位置
		add(controlBar,BorderLayout.NORTH);
		add(paintPanel,BorderLayout.CENTER);
		
		// create new ButtonHandler for button event handling 
		ButtonHandler buttonHandler = new ButtonHandler();
		newFishButton.addActionListener(buttonHandler);
		newTurtleButton.addActionListener(buttonHandler);
		deleteAnimalButton.addActionListener(buttonHandler);
		deleteAllButton.addActionListener(buttonHandler);
		controlAnimalButton.addActionListener(buttonHandler);
		controlAllButton.addActionListener(buttonHandler);
		
		//游標位置偵測
		paintPanel.addMouseListener(new MouseHandler()); 
		
		//每秒判斷魚和魚竿的移動
		timer.schedule(determinTask, 1,15); //delay0.01秒，每0.15秒執行
	}   
	// inner class for button event handling
			private class ButtonHandler implements ActionListener 
			   {
			      // handle button event
			      public void actionPerformed( ActionEvent event )
			      {
			    	 if(event.getActionCommand() == "新增魚" ){
			    		buttonMode = 1; //第一種:新增魚
			    		nowFunctionJLabel.setText("目前功能:新增魚");//將魚放到畫布裡
			    		stopFlag = false; //強制變成繼續模式
			    		stopAllFlag = false; //強制變成繼續模式
			    	 }
			    	 else if(event.getActionCommand() == "放釣竿" ){
			    		buttonMode = 2; //第二種:放釣竿
			    		nowFunctionJLabel.setText("目前功能:放釣竿"); //現在功能:放釣竿	
			    		stopFlag = false; //強制變成繼續模式
			    		stopAllFlag = false; //強制變成繼續模式
			    	 }
			    	 else if(event.getActionCommand() == "收釣竿" ){
			    		 buttonMode = 3; //第三種:收釣竿
			    		 stopAllFlag = false; //強制變成繼續模式
			    		 nowFunctionJLabel.setText("目前功能:收釣竿"); //現在功能:收釣竿
			    		 stopFlag = false; //強制變成繼續模式
			    	 }
			    	 else if(event.getActionCommand() == "移除全部" ){
			    		 buttonMode = 4; //第四種:移除全部
			    		 stopFlag = false; //強制變成繼續模式
			    		 stopAllFlag = false; //強制變成繼續模式
			    		 nowFunctionJLabel.setText("目前功能:移除全部"); //現在功能:移除全部
			    		 for(int i = 0; i<fishList.size();i++){ //把魚的list清掉
			    			 fishList.get(i).stopAll(); //停止魚的timer
			    			 paintPanel.remove(fishList.get(i)); //移除畫布中的魚
			    		 }
			    		 for(int j = 0 ; j<fishRodList.size();j++){ //把烏龜的list清掉
			    			 fishRodList.get(j).stopAll(); //停止烏龜的timer
			    			 paintPanel.remove(fishRodList.get(j)); //移除畫布中的烏龜
			    		 }
			    		 fishList.clear(); //清空fishList
			    		 fishRodList.clear(); //清空fishRodList
			    		 paintPanel.repaint(); //重畫
			    		 fishingNum = 0; //釣到的魚歸零
			    		 nowFishNumJLabel.setText("魚數量:"+fishList.size()); //顯示魚的數量
			    		 fishingNumJLabel.setText("已釣到的魚數量:"+fishingNum); //顯示釣到的魚數量
			    	 }
			    	 else if(event.getActionCommand() == "停止/繼續選取" ){
			    		 buttonMode = 5; //第五種:停止/繼續選取
			    		 stopAllFlag = false; //強制變成繼續模式
			    		 if(stopFlag == false){ //變成停止模式
			    			 stopFlag = true;
			    			 nowFunctionJLabel.setText("目前功能:停止選取"); //現在功能:停止選取
			    		 }
			    		 else{ //變成繼續模式
			    			 stopFlag = false;
			    			 nowFunctionJLabel.setText("目前功能:繼續選取"); //現在功能:繼續選取
			    		 }
			    	 }
			    	 else if(event.getActionCommand() == "停止/繼續全部" ){
			    		 buttonMode = 6; //第六種:停止/繼續全部
			    		 stopFlag = false; //強制變成繼續模式
			    		 if(stopAllFlag == false){ //停止
			    			 for(int i = 0; i<fishList.size();i++){  //魚
				    			 fishList.get(i).stopAll(); //停止
				    			 fishList.get(i).setFishStop(); //改變魚的狀態
				    		 }
			    			 /*for(int j = 0 ; j<fishRodList.size();j++){ //烏龜
				    			 fishRodList.get(j).stopAll(); //停止烏龜的timer
				    			 fishRodList.get(j).setTurtleStop(); //改變烏龜狀態
				    		 }*/
			    			 stopAllFlag = true;
			    			 nowFunctionJLabel.setText("目前功能:停止全部"); //現在功能:停止全部
			    		 }
			    		 else{ //繼續
			    			 for(int i = 0; i<fishList.size();i++){ //魚
				    			 fishList.get(i).update(); //繼續
				    			 fishList.get(i).setFishContinue(); //改變魚的狀態
				    		 }
			    			 /*for(int j = 0 ; j<fishRodList.size();j++){ //烏龜
				    			 fishRodList.get(j).update(); //停止烏龜的timer
				    			 fishRodList.get(j).setTurtleContinue(); //改變烏龜狀態
				    		 }*/
			    			 stopAllFlag = false;
			    			 nowFunctionJLabel.setText("目前功能:繼續全部"); //現在功能:繼續全部
			    		 }
			    	 }
			      }
			   }
	// inner class to handle mouse events
			private class MouseHandler extends MouseAdapter 
			{
				 public void mouseClicked(MouseEvent event){ //得取按下滑鼠時的那一點
					//現在位置
			         Now_X = event.getX();
					 Now_Y = event.getY();
					 if(buttonMode == 1){ //第一種:新增魚
						newFish = new Fish(Now_X,Now_Y); //新增魚到陣列
				    	fishList.add(newFish);
				    	paintPanel.add(fishList.get(fishList.size()-1));
				    	nowFishNumJLabel.setText("魚數量:"+fishList.size()); //顯示魚的數量
					 }
					 else if(buttonMode == 2){ //第二種:放釣竿
						newFishRod= new FishRod(Now_X,Now_Y);//新增釣竿到陣列
				    	fishRodList.add(newFishRod);
				    	paintPanel.add(fishRodList.get(fishRodList.size()-1)); //將烏龜放到畫布裡
					 }
					 else if(buttonMode == 3){ //第三種:收釣竿
						//判斷滑鼠點選的是否在圖案上面
						    for(int j = 0 ; j<fishRodList.size();j++){ //讀取每個魚竿
						    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())){
						    		//fishRodList.get(j).stop(); //停止該烏龜的timer
						    		fishRodList.get(j).stopAll(); //移除該魚竿的timer
						    		paintPanel.remove(fishRodList.get(j)); //移除畫布中的該魚
						    		fishRodList.remove(j);//刪除fishlist的該魚
					    		}
						    	for(int i = 0; i<fishList.size();i++){ //判斷此魚竿時，讀取每個魚，判斷是不是剛好在這魚竿上面
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())){
							    		//fishList.get(i).stop(); //停止該魚的timer
							    		fishList.get(i).stopAll(); //停止該魚的timer
							    		paintPanel.remove(fishList.get(i)); //移除畫布中的該魚
							    		fishList.remove(i);//刪除fishlist的該魚
							    		fishingNum++;//多釣一隻魚
						    		} 
							    	nowFishNumJLabel.setText("魚數量:"+fishList.size()); //顯示魚的數量
							    	fishingNumJLabel.setText("已釣到的魚數量:"+fishingNum); //顯示已釣到的魚數量
							    	//paintPanel.repaint(); //重畫
					    		 }
						    	paintPanel.repaint(); //重畫
						    }
					 } // buttonMode == 4 沒作用
					 else if(buttonMode == 5){ //第五種:停止/繼續選取
						 if(stopFlag == true){ //暫停模式
						    	//判斷滑鼠點選的是否在圖案上面，且魚或烏龜不是禁止狀態
							    for(int i = 0; i<fishList.size();i++){ //讀取每個魚
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())
							    			&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())
							    			&&(fishList.get(i).getFishState() == false)){
							    		fishList.get(i).stop(); //停止該魚的timer
							    		fishList.get(i).setFishStop(); //魚的狀態變成停止
						    		} 
					    		 }
							    /*for(int j = 0 ; j<fishRodList.size();j++){ //讀取每個烏龜
							    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())
							    			&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())
							    			&&(fishRodList.get(j).getTurtleState() == false)){
							    		fishRodList.get(j).stop(); //停止該烏龜的timer
							    		fishRodList.get(j).setTurtleStop();//烏龜的狀態變停止
						    		} 
							    }*/
						    }
					    else{ //判斷滑鼠點選的是否在圖案上面，且魚或烏龜不是繼續狀態
						    	for(int i = 0; i<fishList.size();i++){ //讀取每個魚
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())&&(fishList.get(i).getFishState() == true)){
							    		fishList.get(i).update(); //繼續該魚的timer
							    		fishList.get(i).setFishContinue(); //魚的狀態變成繼續
						    		} 
					    		 }
						    	 /*for(int j = 0 ; j<fishRodList.size();j++){ //讀取每個烏龜
								    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())&&(fishRodList.get(j).getTurtleState() == true)){
								    		fishRodList.get(j).update(); //繼續該烏龜的timer
								    		fishRodList.get(j).setTurtleContinue(); //烏龜狀態變成繼續
							    		} 
								    }*/
						}
					 }
					//buttomMode == 6 沒作用
				 }
			}
			class DetermineTask extends TimerTask{ //不斷判斷魚和魚竿
				public void run(){
					if((!fishList.isEmpty())&&(!fishRodList.isEmpty())){//先檢查是否有魚也有魚竿
						fish_fishrod_exist = true; //魚和魚竿都存在
					}
					else{
						fish_fishrod_exist = false;
					}
					if(fish_fishrod_exist == true){ //如果魚和魚竿都存在，才能判斷
						//判斷是否吃到魚餌
						for(int i = 0 ; i<fishList.size();i++){ //讀取每個魚
					    	for(int j = 0; j<fishRodList.size();j++){ //讀取每個魚竿，判斷魚是不是剛好在此範圍
						    	if((fishList.get(i).get_X() > fishRodList.get(j).get_X())&&(fishList.get(i).get_X() < fishRodList.get(j).get_size_X())&&(fishList.get(i).get_Y() > fishRodList.get(j).get_Y())&&(fishList.get(i).get_Y() < fishRodList.get(j).get_size_Y())
						    			&&(fishRodList.get(j).getFishRodState() == 0)){ //魚竿狀態也必須是有魚餌的
						    		
						    		fishRodList.get(j).changeFishRodState(); //改變魚竿圖片
						    		fishList.get(i).getBiger(); //魚變大
					    		} 
				    		 }
					    }
						
						//遠離沒有魚餌的魚竿
						for(int i = 0 ; i<fishList.size();i++){ //讀取每個魚
					    	for(int j = 0; j<fishRodList.size();j++){ //讀取每個魚竿，判斷魚是不是剛好在此範圍
						    	if((fishList.get(i).get_X() > fishRodList.get(j).get_X() - 30)&&(fishList.get(i).get_X() < fishRodList.get(j).get_size_X() + 30)&&(fishList.get(i).get_Y() > fishRodList.get(j).get_Y() + 30)&&(fishList.get(i).get_Y() < fishRodList.get(j).get_size_Y() +30)
						    			&&(fishRodList.get(j).getFishRodState() == 1)){ //魚竿狀態必須是沒魚餌的
						    		fishList.get(i).changeFishDirect(); //改變魚的方向
						    		//fishRodList.get(j).changeFishRodState(); //改變魚竿圖片
						    		//fishList.get(i).getBiger(); //魚變大
					    		} 
				    		 }
					    }
					
					}
					
				}
			}
	//主程式
		public static void main(String[] args){
			FinalQ1_103403530 f = new FinalQ1_103403530();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize( 775, 650); // set frame size
			f.setVisible( true ); // display frame
		}	   
}
