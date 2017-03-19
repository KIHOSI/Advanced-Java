import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class FishRod extends JLabel {
	private Random generator = new Random(); //亂數
	private int fishrod_X,fishrod_Y,fishrod_width,fishrod_height,fishrod_move; //魚竿的X位置,Y位置,寬度,高度,方向,移動距離
	private int fishrod_up_down; //隨機判斷要不要往上或下
	private int origin_Y; //剛產生魚竿的Y位置
	//private boolean rightFlag = true; //是否往右
	//private boolean fishrodStop = false; //判斷魚竿是否停止，true為停止
	private ImageIcon[] fishrodImg = {new ImageIcon(getClass().getResource("fishing_1.png")),new ImageIcon(getClass().getResource("fishing.png"))};//儲存烏龜圖案
	private Timer timer = new Timer(); //時間器
	private FishRodTask fishrodTask = new FishRodTask(); //執行魚竿的動作的任務
	private ChangeFishRodTask changeFishRodTask = new ChangeFishRodTask(); //改變魚竿的方向與速度的任務
	private boolean upFlag = true; //是否往上
	private int fishrodState = 0; //0為有魚餌，1為沒有魚餌(預設是有)
	public FishRod(int Now_X,int Now_Y){ //建構子
		fishrod_X = Now_X - 50; //魚竿的x位置
		fishrod_Y = Now_Y - 50; //魚竿的y位置
		//origin_X = fishrod_X;
		origin_Y = fishrod_Y;
		fishrod_move = generator.nextInt(5)+1; //魚竿震幅為10(Y方向)，每次增加1~5
		fishrod_up_down = generator.nextInt(2); //0為上，1為下
		ImageIcon imageIcon = fishrodImg[fishrodState]; //產生某方向烏龜
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon); //產生魚竿
		if(fishrod_up_down == 1){
			upFlag = false; //往下
		}
		fishrod_width = this.getIcon().getIconWidth(); //魚竿的寬度
		fishrod_height = this.getIcon().getIconHeight(); //魚竿的高度
		this.setBounds(fishrod_X,fishrod_Y,fishrod_width,fishrod_height); //設定魚竿的位置及長寬
		timer.schedule(fishrodTask, 1000,2000); //delay0.01秒，每0.2秒執行
		timer.schedule(changeFishRodTask,2000,5000);//delay0.2秒，每5秒執行
	}
	/*public void stop(){ //停止該任務
		fishrodTask.cancel();
		changeFishRodTask.cancel();
	}*/
	public void stopAll(){
		timer.cancel(); //停止所有時間
	}
	/*public void update(){ //繼續執行
		fishrodTask  = new FishRodTask(); //新增新的任務
		changeFishRodTask = new ChangeFishRodTask();
		timer = new Timer(); //新增新的時間控制器
		timer.schedule(fishrodTask, 1,200); //delay0.01秒，每0.2秒執行
		timer.schedule(changeFishRodTask, 200,5000); //delay0.2秒，每5秒執行
	}*/
	public int get_X(){ //得到魚竿的X座標
		return fishrod_X;
	}
	public int get_Y(){ //得到魚竿的Y座標
		return fishrod_Y;
	}
	public int get_size_X(){ //得到魚竿的X框架
		return fishrod_X+fishrod_width;
	} 
	public int get_size_Y(){ //得到魚竿的Y框架
		return fishrod_Y+fishrod_height;
	}
	public int getFishRodState(){ //判斷現在是否有魚餌
		return fishrodState;
	}
	public void changeFishRodState(){ //魚餌被吃掉了，改成無魚餌的圖
		fishrodState = 1;
		ImageIcon imageIcon = fishrodImg[fishrodState]; //產生某方向烏龜
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon); //產生魚竿
	}
	/*public void setTurtleStop(){ //改變魚竿的狀態為stop
		turtleStop = true;
	}
	public void setTurtleContinue(){ //改變魚竿的狀態為continue
		turtleStop = false;
	}*/
	
	class FishRodTask extends TimerTask{ //執行魚竿動作
		public void run(){
			if(upFlag == false){ //往下
				if(fishrod_Y+fishrod_move < origin_Y-10){  //往下是否有碰到邊界:沒有，繼續往下
					fishrod_Y += fishrod_move;
				}
				else{ //往下是否有碰到邊界:有，往上
					upFlag = true;
					fishrod_up_down = 0;
					fishrod_Y -= fishrod_move;
				}
			}
			else{ //往上
				if((fishrod_Y-fishrod_move)>origin_Y+10){ //往上是否有碰到邊界:沒有，繼續往上
					fishrod_Y -= fishrod_move;
				}
				else{  //往上是否有碰到邊界:有，往下
					upFlag = false;
					fishrod_up_down = 1;
					fishrod_Y += fishrod_move;
				}
			}
			FishRod.this.setLocation(fishrod_X,fishrod_Y); //設定位置
		}
	}
	class ChangeFishRodTask extends TimerTask{ //改變魚鉤方向與震幅
		public void run(){
			fishrod_move = generator.nextInt(10)+1; //重產生一次移動距離
			fishrod_up_down = generator.nextInt(2); //改變魚竿的上下移動方向
			if(fishrod_up_down == 1){ //往下
				upFlag = false;
			}
			else{ //往上
				upFlag = true;
			}
		}
		
	}
}
