import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class FishRod extends JLabel {
	private Random generator = new Random(); //�ü�
	private int fishrod_X,fishrod_Y,fishrod_width,fishrod_height,fishrod_move; //����X��m,Y��m,�e��,����,��V,���ʶZ��
	private int fishrod_up_down; //�H���P�_�n���n���W�ΤU
	private int origin_Y; //�貣�ͳ���Y��m
	//private boolean rightFlag = true; //�O�_���k
	//private boolean fishrodStop = false; //�P�_����O�_����Atrue������
	private ImageIcon[] fishrodImg = {new ImageIcon(getClass().getResource("fishing_1.png")),new ImageIcon(getClass().getResource("fishing.png"))};//�x�s�Q�t�Ϯ�
	private Timer timer = new Timer(); //�ɶ���
	private FishRodTask fishrodTask = new FishRodTask(); //���泽�񪺰ʧ@������
	private ChangeFishRodTask changeFishRodTask = new ChangeFishRodTask(); //���ܳ��񪺤�V�P�t�ת�����
	private boolean upFlag = true; //�O�_���W
	private int fishrodState = 0; //0��������A1���S������(�w�]�O��)
	public FishRod(int Now_X,int Now_Y){ //�غc�l
		fishrod_X = Now_X - 50; //����x��m
		fishrod_Y = Now_Y - 50; //����y��m
		//origin_X = fishrod_X;
		origin_Y = fishrod_Y;
		fishrod_move = generator.nextInt(5)+1; //����_�T��10(Y��V)�A�C���W�[1~5
		fishrod_up_down = generator.nextInt(2); //0���W�A1���U
		ImageIcon imageIcon = fishrodImg[fishrodState]; //���ͬY��V�Q�t
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon); //���ͳ���
		if(fishrod_up_down == 1){
			upFlag = false; //���U
		}
		fishrod_width = this.getIcon().getIconWidth(); //���񪺼e��
		fishrod_height = this.getIcon().getIconHeight(); //���񪺰���
		this.setBounds(fishrod_X,fishrod_Y,fishrod_width,fishrod_height); //�]�w���񪺦�m�Ϊ��e
		timer.schedule(fishrodTask, 1000,2000); //delay0.01���A�C0.2������
		timer.schedule(changeFishRodTask,2000,5000);//delay0.2���A�C5������
	}
	/*public void stop(){ //����ӥ���
		fishrodTask.cancel();
		changeFishRodTask.cancel();
	}*/
	public void stopAll(){
		timer.cancel(); //����Ҧ��ɶ�
	}
	/*public void update(){ //�~�����
		fishrodTask  = new FishRodTask(); //�s�W�s������
		changeFishRodTask = new ChangeFishRodTask();
		timer = new Timer(); //�s�W�s���ɶ����
		timer.schedule(fishrodTask, 1,200); //delay0.01���A�C0.2������
		timer.schedule(changeFishRodTask, 200,5000); //delay0.2���A�C5������
	}*/
	public int get_X(){ //�o�쳽��X�y��
		return fishrod_X;
	}
	public int get_Y(){ //�o�쳽��Y�y��
		return fishrod_Y;
	}
	public int get_size_X(){ //�o�쳽��X�ج[
		return fishrod_X+fishrod_width;
	} 
	public int get_size_Y(){ //�o�쳽��Y�ج[
		return fishrod_Y+fishrod_height;
	}
	public int getFishRodState(){ //�P�_�{�b�O�_������
		return fishrodState;
	}
	public void changeFishRodState(){ //����Q�Y���F�A�令�L���窺��
		fishrodState = 1;
		ImageIcon imageIcon = fishrodImg[fishrodState]; //���ͬY��V�Q�t
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg);
		this.setIcon(imageIcon); //���ͳ���
	}
	/*public void setTurtleStop(){ //���ܳ��񪺪��A��stop
		turtleStop = true;
	}
	public void setTurtleContinue(){ //���ܳ��񪺪��A��continue
		turtleStop = false;
	}*/
	
	class FishRodTask extends TimerTask{ //���泽��ʧ@
		public void run(){
			if(upFlag == false){ //���U
				if(fishrod_Y+fishrod_move < origin_Y-10){  //���U�O�_���I�����:�S���A�~�򩹤U
					fishrod_Y += fishrod_move;
				}
				else{ //���U�O�_���I�����:���A���W
					upFlag = true;
					fishrod_up_down = 0;
					fishrod_Y -= fishrod_move;
				}
			}
			else{ //���W
				if((fishrod_Y-fishrod_move)>origin_Y+10){ //���W�O�_���I�����:�S���A�~�򩹤W
					fishrod_Y -= fishrod_move;
				}
				else{  //���W�O�_���I�����:���A���U
					upFlag = false;
					fishrod_up_down = 1;
					fishrod_Y += fishrod_move;
				}
			}
			FishRod.this.setLocation(fishrod_X,fishrod_Y); //�]�w��m
		}
	}
	class ChangeFishRodTask extends TimerTask{ //���ܳ��_��V�P�_�T
		public void run(){
			fishrod_move = generator.nextInt(10)+1; //�����ͤ@�����ʶZ��
			fishrod_up_down = generator.nextInt(2); //���ܳ��񪺤W�U���ʤ�V
			if(fishrod_up_down == 1){ //���U
				upFlag = false;
			}
			else{ //���W
				upFlag = true;
			}
		}
		
	}
}