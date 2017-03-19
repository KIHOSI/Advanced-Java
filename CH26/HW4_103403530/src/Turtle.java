//HW4:��ޤTB 103403530 �x�t��
import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


//�e��
public class Turtle extends JLabel{ //Timer�����N�|���ͤ@��thread�A���ΦAimplements Runnable
		private Random generator = new Random(); //�ü�
		private int turtle_X,turtle_Y,turtle_width,turtle_height,turtle_direct,turtle_move; //�Q�t��X��m,�Q�t��Y��m,�Q�t���e��,�Q�t������,�Q�t����V,�Q�t���ʶZ��
		private boolean rightFlag = true; //�O�_���k
		private boolean turtleStop = false; //�P�_�Q�t�O�_����Atrue������
		private ImageIcon[] turtleImg = {new ImageIcon(getClass().getResource("w.png")),new ImageIcon(getClass().getResource("w2.png"))};//�x�s�Q�t�Ϯ�
		private Timer timer = new Timer(); //�ɶ���
		private TurtleTask turtleTask = new TurtleTask(); //����Q�t���ʧ@������
		private ChangeTurtleTask changeTurtleTask = new ChangeTurtleTask(); //���ܯQ�t����V�P�t�ת�����
		public Turtle(int Now_X,int Now_Y) {
			// TODO �۰ʲ��ͪ��غc�l Stub
			turtle_X = Now_X - 50; //�Q�t��x��m
			turtle_Y = Now_Y - 50; //�Q�t��y��m
			turtle_direct = generator.nextInt(2); //0���k,1����
			turtle_move = generator.nextInt(5)+5; //�H�����ͯQ�t�����ʶZ��
			ImageIcon imageIcon = turtleImg[turtle_direct = generator.nextInt(2)]; //���ͬY��V�Q�t
			Image img = imageIcon.getImage();
			Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(newimg);
			this.setIcon(imageIcon); //���ͯQ�t
			if(turtle_direct == 1){
				rightFlag = false; //����
			}
			turtle_width = this.getIcon().getIconWidth(); //�����e��
			turtle_height = this.getIcon().getIconHeight(); //��������
			this.setBounds(turtle_X,turtle_Y,turtle_width,turtle_height); //�]�w������m,���e
			timer.schedule(turtleTask, 1,200); //delay0.01��A�C0.2�����
			timer.schedule(changeTurtleTask,200,5000);//delay0.2��A�C5�����
		}
		public void stop(){ //����ӥ���
			turtleTask.cancel();
			changeTurtleTask.cancel();
		}
		public void stopAll(){
			timer.cancel(); //����Ҧ��ɶ�
		}
		public void update(){ //�~�����
			turtleTask  = new TurtleTask(); //�s�W�s������
			changeTurtleTask = new ChangeTurtleTask();
			timer = new Timer(); //�s�W�s���ɶ����
			timer.schedule(turtleTask, 1,200); //delay0.01��A�C0.2�����
			timer.schedule(changeTurtleTask, 200,5000); //delay0.2��A�C5�����
		}
		public int get_X(){ //�o�쳽��X�y��
			return turtle_X;
		}
		public int get_Y(){ //�o�쳽��Y�y��
			return turtle_Y;
		}
		public int get_size_X(){ //�o�쳽��X�ج[
			return turtle_X+turtle_width;
		} 
		public int get_size_Y(){ //�o�쳽��Y�ج[
			return turtle_Y+turtle_height;
		}
		public void setTurtleStop(){ //���ܯQ�t�����A��stop
			turtleStop = true;
		}
		public void setTurtleContinue(){ //���ܯQ�t�����A��continue
			turtleStop = false;
		}
		public boolean getTurtleState(){ //�^�ǯQ�t���A
			return turtleStop;
		}
		//���泽���ʧ@
		class TurtleTask extends TimerTask{
			public void run(){
				if(turtle_Y<410){ //�p�G�Q�t�S���b�����A�쩳���~�४�k����
					turtle_Y += 10; //�C�����W�[1
				}
				else if(rightFlag == false){ //����
					if((turtle_X - turtle_move)>5){ //�����O�_���I�����:�S���A�~�򩹥�
						turtle_X -= turtle_move;
					}
					else{ //�����O�_���I�����:���A���k
						rightFlag = true;
						turtle_direct = 0;
						//���ܯQ�t��V
						ImageIcon imageIcon = turtleImg[turtle_direct];
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
						imageIcon = new ImageIcon(newimg);
						Turtle.this.setIcon(imageIcon);
						turtle_X += turtle_move;
					}
				}
				else{ //���k
					if((turtle_X+Turtle.this.getIcon().getIconWidth() < 765)) {//���k�O�_���I�����:�S���A�~�򩹥k
						turtle_X += turtle_move;
					}
					else{ //���k�O�_���I�����:���A����
						rightFlag = false;
						turtle_direct = 1;
						//���ܯQ�t��V
						ImageIcon imageIcon = turtleImg[turtle_direct];
						Image img = imageIcon.getImage();
						Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
						imageIcon = new ImageIcon(newimg);
						Turtle.this.setIcon(imageIcon);
						turtle_X -= turtle_move;
					}
				}
				Turtle.this.setLocation(turtle_X,turtle_Y); //�]�w��m
			}
		}
		//���ܯQ�t����V�P�t��
		class ChangeTurtleTask extends TimerTask{
			public void run(){
				turtle_move = generator.nextInt(10)+5; //���ܯQ�t�����ʶZ��
				turtle_direct = generator.nextInt(2); //���ܯQ�t�����ʤ�V
				if(turtle_direct == 1){//�V��
					rightFlag = false;
					//���ͯQ�t
					ImageIcon imageIcon = turtleImg[turtle_direct];
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					Turtle.this.setIcon(imageIcon);
				}
				else{ //�V�k
					rightFlag = true;
					//���ͯQ�t
					ImageIcon imageIcon = turtleImg[turtle_direct];
					Image img = imageIcon.getImage();
					Image newimg = img.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
					imageIcon = new ImageIcon(newimg);
					Turtle.this.setIcon(imageIcon);
				}
			}
		}
}
