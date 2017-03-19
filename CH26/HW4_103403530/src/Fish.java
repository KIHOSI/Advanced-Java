//HW4:資管三B 103403530 洪靖雯
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


//畫魚
public class Fish extends JLabel { //Timer本身就會產生一個thread，不用再implements Runnable
		private Random generator = new Random(); //亂數
		private int fish_X,fish_Y,fish_type,fish_width,fish_height,fish_direct,size_x,size_y,fish_move_horizontal,fish_move_vertical,fish_up_down; //魚的X位置,魚的Y位置,魚的種類,魚的寬度,魚的高度,魚的橫向方向,魚的垂直方向,圖片高度與寬度,魚的移動距離,魚是否往上
		private boolean rightFlag = true; //是否往右
		private boolean upFlag = true; //是否往上
		private boolean fishStop = false; //判斷魚是不是停止狀態，true為停止
		private ImageIcon[][] fishImg = {{new ImageIcon(getClass().getResource("1.png")),new ImageIcon(getClass().getResource("3.png")),new ImageIcon(getClass().getResource("5.png"))},
					{new ImageIcon(getClass().getResource("2.png")),new ImageIcon(getClass().getResource("4.png")),new ImageIcon(getClass().getResource("6.png"))}};//二元陣列,第一層是往右的魚,第二層是往左的魚
		private Timer timer = new Timer();
		private FishTask fishTask = new FishTask();//執行魚的動作的任務
		private ChangeFishTask changeFishTask = new ChangeFishTask();//改變魚的方向與速度的任務
		public Fish(int Now_X,int Now_Y) {
			// TODO 自動產生的建構子 Stub
			fish_X = Now_X - 50; //魚的x位置
			fish_Y = Now_Y - 50; //魚的y位置
			fish_direct = generator.nextInt(2); //0為右,1為左
			fish_up_down = generator.nextInt(2); //0為上，1為下
			size_x = generator.nextInt(50)+70; //隨機產生框架寬
			size_y = generator.nextInt(50)+70; //隨機產生框架高
			fish_move_vertical = generator.nextInt(15); //隨機產生垂直移動距離
			fish_move_horizontal = generator.nextInt(10)+20; //隨機產生水平移動距離
			ImageIcon imageIcon = fishImg[fish_direct][fish_type= generator.nextInt(3)]; //產生某種魚
			Image img = imageIcon.getImage();
			Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//改變魚的大小
			imageIcon = new ImageIcon(newimg);
			if(fish_direct == 1){
				rightFlag = false; //往左
			}
			if(fish_up_down == 1){
				upFlag = false; //往下
			}
			this.setIcon(imageIcon); //產生魚種
			fish_width = this.getIcon().getIconWidth(); //魚的寬度
			fish_height = this.getIcon().getIconHeight(); //魚的高度
			this.setBounds(fish_X,fish_Y,fish_width,fish_height); //設定魚的位置,長寬
			timer.schedule(fishTask, 1,150); //delay0.01秒，每0.15秒執行
			timer.schedule(changeFishTask, 200,5000); //delay0.2秒，每5秒執行
		}
		public void stop(){ //停止該任務
			fishTask.cancel();
			changeFishTask.cancel();
		}
		public void stopAll(){
			timer.cancel(); //停止所有時間
		}
		public void update(){ //繼續執行
			fishTask = new FishTask(); //新增新的任務
			changeFishTask = new ChangeFishTask();
			timer = new Timer(); //新增新的時間控制器
			timer.schedule(fishTask, 1,150); //delay0.01秒，每0.15秒執行
			timer.schedule(changeFishTask, 200,5000); //delay0.2秒，每5秒執行
		}
		
		public int get_X(){ //得到魚的X座標
			return fish_X;
		}
		public int get_Y(){ //得到魚的Y座標
			return fish_Y;
		}
		public int get_size_X(){ //得到魚的X框架
			return fish_X+size_x;
		} 
		public int get_size_Y(){ //得到魚的Y框架
			return fish_Y+size_y;
		}
		public void setFishStop(){ //改變魚的狀態為stop
			fishStop = true;
		}
		public void setFishContinue(){ //改變魚的狀態為continue
			fishStop = false;
		}
		public boolean getFishState(){ //回傳魚的狀態
			return fishStop; 
		}
		//執行魚的動作
		class FishTask extends TimerTask{
			public void run(){
				if(rightFlag == false){ //往左
					if((fish_X - fish_move_horizontal)>5){//往左是否有碰到邊界:沒有，繼續往左
						fish_X -= fish_move_horizontal;
					}
					else{  //往左是否有碰到邊界:有，往右
						rightFlag = true;
						fish_direct = 0;
						//改變魚的方向，重新存一次
						ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //產生某種魚
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//改變魚的大小
						imageIcon = new ImageIcon(newimg);
						Fish.this.setIcon(imageIcon);
						fish_X += fish_move_horizontal;
					}
				}
				else{ //往右
					if((fish_X+Fish.this.getIcon().getIconWidth() < 765)) {//往右是否有碰到邊界:沒有，繼續往右
						fish_X += fish_move_horizontal;
					}
					else{  //往右是否有碰到邊界:有，往左
						rightFlag = false;
						fish_direct = 1;
						//改變魚的方向，重新存一次
						ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //產生某種魚
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//改變魚的大小
						imageIcon = new ImageIcon(newimg);
						Fish.this.setIcon(imageIcon);
						fish_X -= fish_move_horizontal;
					}
				}
				if(upFlag == false){ //往下
					if(fish_Y+fish_move_vertical < 410){  //往下是否有碰到邊界:沒有，繼續往下
						fish_Y += fish_move_vertical;
					}
					else{ //往下是否有碰到邊界:有，往上
						upFlag = true;
						fish_up_down = 0;
						fish_Y -= fish_move_vertical;
					}
				}
				else{ //往上
					if((fish_Y-fish_move_vertical)>0){ //往上是否有碰到邊界:沒有，繼續往上
						fish_Y -= fish_move_vertical;
					}
					else{  //往上是否有碰到邊界:有，往下
						upFlag = false;
						fish_up_down = 1;
						fish_Y += fish_move_vertical;
					}
				}
				Fish.this.setLocation(fish_X,fish_Y); //設定位置
			}
		}	
		
		//改變魚的方向與速度
		class ChangeFishTask extends TimerTask{
			public void run(){
				fish_move_horizontal = generator.nextInt(15)+5; //改變魚的移動速度
				fish_move_vertical = generator.nextInt(15); //隨機產生垂直移動距離
				fish_direct = generator.nextInt(2); //改變魚的移動方向
				fish_up_down = generator.nextInt(2); //改變魚的上下移動方向
				if(fish_direct == 1){ //往左
					rightFlag = false;
					ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //產生某種魚
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//改變魚的大小
					imageIcon = new ImageIcon(newimg);
					Fish.this.setIcon(imageIcon); //重新顯示圖片
				}
				else{ //往右
					rightFlag = true;
					ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //產生某種魚
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//改變魚的大小
					imageIcon = new ImageIcon(newimg);
					Fish.this.setIcon(imageIcon); //重新顯示圖片
				}
				if(fish_up_down == 1){ //往下
					upFlag = false;
				}
				else{ //往上
					upFlag = true;
				}
			}
		}
}
