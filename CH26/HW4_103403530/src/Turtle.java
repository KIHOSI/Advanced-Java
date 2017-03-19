//HW4:資管三B 103403530 洪靖雯
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


//畫魚
public class Turtle extends JLabel{ //Timer本身就會產生一個thread，不用再implements Runnable
		private Random generator = new Random(); //亂數
		private int turtle_X,turtle_Y,turtle_width,turtle_height,turtle_direct,turtle_move; //烏龜的X位置,烏龜的Y位置,烏龜的寬度,烏龜的高度,烏龜的方向,烏龜移動距離
		private boolean rightFlag = true; //是否往右
		private boolean turtleStop = false; //判斷烏龜是否停止，true為停止
		private ImageIcon[] turtleImg = {new ImageIcon(getClass().getResource("w.png")),new ImageIcon(getClass().getResource("w2.png"))};//儲存烏龜圖案
		private Timer timer = new Timer(); //時間器
		private TurtleTask turtleTask = new TurtleTask(); //執行烏龜的動作的任務
		private ChangeTurtleTask changeTurtleTask = new ChangeTurtleTask(); //改變烏龜的方向與速度的任務
		public Turtle(int Now_X,int Now_Y) {
			// TODO 自動產生的建構子 Stub
			turtle_X = Now_X - 50; //烏龜的x位置
			turtle_Y = Now_Y - 50; //烏龜的y位置
			turtle_direct = generator.nextInt(2); //0為右,1為左
			turtle_move = generator.nextInt(5)+5; //隨機產生烏龜的移動距離
			ImageIcon imageIcon = turtleImg[turtle_direct = generator.nextInt(2)]; //產生某方向烏龜
			Image img = imageIcon.getImage();
			Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(newimg);
			this.setIcon(imageIcon); //產生烏龜
			if(turtle_direct == 1){
				rightFlag = false; //往左
			}
			turtle_width = this.getIcon().getIconWidth(); //魚的寬度
			turtle_height = this.getIcon().getIconHeight(); //魚的高度
			this.setBounds(turtle_X,turtle_Y,turtle_width,turtle_height); //設定魚的位置,長寬
			timer.schedule(turtleTask, 1,200); //delay0.01秒，每0.2秒執行
			timer.schedule(changeTurtleTask,200,5000);//delay0.2秒，每5秒執行
		}
		public void stop(){ //停止該任務
			turtleTask.cancel();
			changeTurtleTask.cancel();
		}
		public void stopAll(){
			timer.cancel(); //停止所有時間
		}
		public void update(){ //繼續執行
			turtleTask  = new TurtleTask(); //新增新的任務
			changeTurtleTask = new ChangeTurtleTask();
			timer = new Timer(); //新增新的時間控制器
			timer.schedule(turtleTask, 1,200); //delay0.01秒，每0.2秒執行
			timer.schedule(changeTurtleTask, 200,5000); //delay0.2秒，每5秒執行
		}
		public int get_X(){ //得到魚的X座標
			return turtle_X;
		}
		public int get_Y(){ //得到魚的Y座標
			return turtle_Y;
		}
		public int get_size_X(){ //得到魚的X框架
			return turtle_X+turtle_width;
		} 
		public int get_size_Y(){ //得到魚的Y框架
			return turtle_Y+turtle_height;
		}
		public void setTurtleStop(){ //改變烏龜的狀態為stop
			turtleStop = true;
		}
		public void setTurtleContinue(){ //改變烏龜的狀態為continue
			turtleStop = false;
		}
		public boolean getTurtleState(){ //回傳烏龜狀態
			return turtleStop;
		}
		//執行魚的動作
		class TurtleTask extends TimerTask{
			public void run(){
				if(turtle_Y<410){ //如果烏龜沒有在底部，到底部才能左右移動
					turtle_Y += 10; //每次都增加1
				}
				else if(rightFlag == false){ //往左
					if((turtle_X - turtle_move)>5){ //往左是否有碰到邊界:沒有，繼續往左
						turtle_X -= turtle_move;
					}
					else{ //往左是否有碰到邊界:有，往右
						rightFlag = true;
						turtle_direct = 0;
						//改變烏龜方向
						ImageIcon imageIcon = turtleImg[turtle_direct];
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
						imageIcon = new ImageIcon(newimg);
						Turtle.this.setIcon(imageIcon);
						turtle_X += turtle_move;
					}
				}
				else{ //往右
					if((turtle_X+Turtle.this.getIcon().getIconWidth() < 765)) {//往右是否有碰到邊界:沒有，繼續往右
						turtle_X += turtle_move;
					}
					else{ //往右是否有碰到邊界:有，往左
						rightFlag = false;
						turtle_direct = 1;
						//改變烏龜方向
						ImageIcon imageIcon = turtleImg[turtle_direct];
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
						imageIcon = new ImageIcon(newimg);
						Turtle.this.setIcon(imageIcon);
						turtle_X -= turtle_move;
					}
				}
				Turtle.this.setLocation(turtle_X,turtle_Y); //設定位置
			}
		}
		//改變烏龜的方向與速度
		class ChangeTurtleTask extends TimerTask{
			public void run(){
				turtle_move = generator.nextInt(10)+5; //改變烏龜的移動距離
				turtle_direct = generator.nextInt(2); //改變烏龜的移動方向
				if(turtle_direct == 1){//向左
					rightFlag = false;
					//產生烏龜
					ImageIcon imageIcon = turtleImg[turtle_direct];
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					Turtle.this.setIcon(imageIcon);
				}
				else{ //向右
					rightFlag = true;
					//產生烏龜
					ImageIcon imageIcon = turtleImg[turtle_direct];
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					Turtle.this.setIcon(imageIcon);
				}
			}
		}
}
