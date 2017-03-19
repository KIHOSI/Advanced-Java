//HW4:��ޤTB 103403530 �x�t��
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


//�e��
public class Fish extends JLabel { //Timer�����N�|���ͤ@��thread�A���ΦAimplements Runnable
		private Random generator = new Random(); //�ü�
		private int fish_X,fish_Y,fish_type,fish_width,fish_height,fish_direct,size_x,size_y,fish_move_horizontal,fish_move_vertical,fish_up_down; //����X��m,����Y��m,��������,�����e��,��������,������V��V,����������V,�Ϥ����׻P�e��,�������ʶZ��,���O�_���W
		private boolean rightFlag = true; //�O�_���k
		private boolean upFlag = true; //�O�_���W
		private boolean fishStop = false; //�P�_���O���O����A�Atrue������
		private ImageIcon[][] fishImg = {{new ImageIcon(getClass().getResource("1.png")),new ImageIcon(getClass().getResource("3.png")),new ImageIcon(getClass().getResource("5.png"))},
					{new ImageIcon(getClass().getResource("2.png")),new ImageIcon(getClass().getResource("4.png")),new ImageIcon(getClass().getResource("6.png"))}};//�G���}�C,�Ĥ@�h�O���k����,�ĤG�h�O��������
		private Timer timer = new Timer();
		private FishTask fishTask = new FishTask();//���泽���ʧ@������
		private ChangeFishTask changeFishTask = new ChangeFishTask();//���ܳ�����V�P�t�ת�����
		public Fish(int Now_X,int Now_Y) {
			// TODO �۰ʲ��ͪ��غc�l Stub
			fish_X = Now_X - 50; //����x��m
			fish_Y = Now_Y - 50; //����y��m
			fish_direct = generator.nextInt(2); //0���k,1����
			fish_up_down = generator.nextInt(2); //0���W�A1���U
			size_x = generator.nextInt(50)+70; //�H�����ͮج[�e
			size_y = generator.nextInt(50)+70; //�H�����ͮج[��
			fish_move_vertical = generator.nextInt(15); //�H�����ͫ������ʶZ��
			fish_move_horizontal = generator.nextInt(10)+20; //�H�����ͤ������ʶZ��
			ImageIcon imageIcon = fishImg[fish_direct][fish_type= generator.nextInt(3)]; //���ͬY�س�
			Image img = imageIcon.getImage();
			Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//���ܳ����j�p
			imageIcon = new ImageIcon(newimg);
			if(fish_direct == 1){
				rightFlag = false; //����
			}
			if(fish_up_down == 1){
				upFlag = false; //���U
			}
			this.setIcon(imageIcon); //���ͳ���
			fish_width = this.getIcon().getIconWidth(); //�����e��
			fish_height = this.getIcon().getIconHeight(); //��������
			this.setBounds(fish_X,fish_Y,fish_width,fish_height); //�]�w������m,���e
			timer.schedule(fishTask, 1,150); //delay0.01��A�C0.15�����
			timer.schedule(changeFishTask, 200,5000); //delay0.2��A�C5�����
		}
		public void stop(){ //����ӥ���
			fishTask.cancel();
			changeFishTask.cancel();
		}
		public void stopAll(){
			timer.cancel(); //����Ҧ��ɶ�
		}
		public void update(){ //�~�����
			fishTask = new FishTask(); //�s�W�s������
			changeFishTask = new ChangeFishTask();
			timer = new Timer(); //�s�W�s���ɶ����
			timer.schedule(fishTask, 1,150); //delay0.01��A�C0.15�����
			timer.schedule(changeFishTask, 200,5000); //delay0.2��A�C5�����
		}
		
		public int get_X(){ //�o�쳽��X�y��
			return fish_X;
		}
		public int get_Y(){ //�o�쳽��Y�y��
			return fish_Y;
		}
		public int get_size_X(){ //�o�쳽��X�ج[
			return fish_X+size_x;
		} 
		public int get_size_Y(){ //�o�쳽��Y�ج[
			return fish_Y+size_y;
		}
		public void setFishStop(){ //���ܳ������A��stop
			fishStop = true;
		}
		public void setFishContinue(){ //���ܳ������A��continue
			fishStop = false;
		}
		public boolean getFishState(){ //�^�ǳ������A
			return fishStop; 
		}
		//���泽���ʧ@
		class FishTask extends TimerTask{
			public void run(){
				if(rightFlag == false){ //����
					if((fish_X - fish_move_horizontal)>5){//�����O�_���I�����:�S���A�~�򩹥�
						fish_X -= fish_move_horizontal;
					}
					else{  //�����O�_���I�����:���A���k
						rightFlag = true;
						fish_direct = 0;
						//���ܳ�����V�A���s�s�@��
						ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //���ͬY�س�
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//���ܳ����j�p
						imageIcon = new ImageIcon(newimg);
						Fish.this.setIcon(imageIcon);
						fish_X += fish_move_horizontal;
					}
				}
				else{ //���k
					if((fish_X+Fish.this.getIcon().getIconWidth() < 765)) {//���k�O�_���I�����:�S���A�~�򩹥k
						fish_X += fish_move_horizontal;
					}
					else{  //���k�O�_���I�����:���A����
						rightFlag = false;
						fish_direct = 1;
						//���ܳ�����V�A���s�s�@��
						ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //���ͬY�س�
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//���ܳ����j�p
						imageIcon = new ImageIcon(newimg);
						Fish.this.setIcon(imageIcon);
						fish_X -= fish_move_horizontal;
					}
				}
				if(upFlag == false){ //���U
					if(fish_Y+fish_move_vertical < 410){  //���U�O�_���I�����:�S���A�~�򩹤U
						fish_Y += fish_move_vertical;
					}
					else{ //���U�O�_���I�����:���A���W
						upFlag = true;
						fish_up_down = 0;
						fish_Y -= fish_move_vertical;
					}
				}
				else{ //���W
					if((fish_Y-fish_move_vertical)>0){ //���W�O�_���I�����:�S���A�~�򩹤W
						fish_Y -= fish_move_vertical;
					}
					else{  //���W�O�_���I�����:���A���U
						upFlag = false;
						fish_up_down = 1;
						fish_Y += fish_move_vertical;
					}
				}
				Fish.this.setLocation(fish_X,fish_Y); //�]�w��m
			}
		}	
		
		//���ܳ�����V�P�t��
		class ChangeFishTask extends TimerTask{
			public void run(){
				fish_move_horizontal = generator.nextInt(15)+5; //���ܳ������ʳt��
				fish_move_vertical = generator.nextInt(15); //�H�����ͫ������ʶZ��
				fish_direct = generator.nextInt(2); //���ܳ������ʤ�V
				fish_up_down = generator.nextInt(2); //���ܳ����W�U���ʤ�V
				if(fish_direct == 1){ //����
					rightFlag = false;
					ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //���ͬY�س�
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//���ܳ����j�p
					imageIcon = new ImageIcon(newimg);
					Fish.this.setIcon(imageIcon); //���s��ܹϤ�
				}
				else{ //���k
					rightFlag = true;
					ImageIcon imageIcon = fishImg[fish_direct][fish_type]; //���ͬY�س�
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(size_x, size_y, java.awt.Image.SCALE_SMOOTH);//���ܳ����j�p
					imageIcon = new ImageIcon(newimg);
					Fish.this.setIcon(imageIcon); //���s��ܹϤ�
				}
				if(fish_up_down == 1){ //���U
					upFlag = false;
				}
				else{ //���W
					upFlag = true;
				}
			}
		}
}
