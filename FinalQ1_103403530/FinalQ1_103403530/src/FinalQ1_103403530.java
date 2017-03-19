//FinalQ1:��ޤTB 103403530 �x�t��

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
	private JPanel controlBar = new JPanel(); //�W�豱���
	private JPanel buttonPanel = new JPanel(); //���s��
	private JPanel showMessage = new JPanel(); //��ܪ��A��
	private ImageIcon bg = new ImageIcon(getClass().getResource("bg.jpg")); //�e���I��
	private JLabel nowFunctionJLabel = new JLabel("�ثe�\��:"); //��ܲ{�b�\��
	private JLabel nowFishNumJLabel = new JLabel("���ƶq:"); //��ܳ����ƥ�
	private JLabel fishingNumJLabel = new JLabel("�w���쪺���ƶq:"); //��ܤw����h�ֳ�
	//private JLabel emptyLabel1 = new JLabel(); //�ťդ��e�A���F���۹��
	//private JLabel emptyLabel2 = new JLabel(); //�ťդ��e�A���F���۹��
	//private JLabel emptyLabel3 = new JLabel(); //�ťդ��e�A���F���۹��
	//private JLabel emptyLabel4 = new JLabel(); //�ťդ��e�A���F���۹��
	//������s
	private JButton newFishButton = new JButton("�s�W��");
	private JButton newTurtleButton = new JButton("�񳨬�");
	private JButton deleteAnimalButton = new JButton("������");
	private JButton deleteAllButton = new JButton("��������");
	private JButton controlAnimalButton = new JButton("����/�~����");
	private JButton controlAllButton = new JButton("����/�~�����");
	private Boolean stopAllFlag = false; //�P�_�Ȱ������άO�~������Atrue�O����
	private Boolean stopFlag = false; //�P�_�Ȱ��άO�~��Atrue�O����
	//private Boolean deleteFlag = false; //�P�_�O�_�n�R���Atrue�O�R��
	private int buttonMode = 0; //�P�_�O�����ӫ��s
	private int fishingNum = 0; //�w����h�ְ���
	//private List<Fish> fishList = new ArrayList<Fish>(); //�x�s����thread
	//private List<FishRod> fishRodList = new ArrayList<FishRod>(); //�x�s�Q�t��thread
	private LinkedList<Fish> fishList = new LinkedList<Fish>();
	private LinkedList<FishRod> fishRodList = new LinkedList<FishRod>();
	
	//�x�s���M�Q�t
	private Fish newFish;
	private FishRod newFishRod;
	//�P�_���M���񪺦�m
	private DetermineTask determinTask = new DetermineTask();
	private Timer timer = new Timer();
	private boolean fish_fishrod_exist = false; //true�N�������s�b�Afalse�N���S��(�w�])
	//�{�b��m
	private int Now_X = 0;
	private int Now_Y = 0;
	private PaintPanel paintPanel = new PaintPanel(); //�إߵe���Ϫ�����
	
	//�ŧi�e����
	class PaintPanel extends JPanel{
				 public PaintPanel(){} //�غc�l�O�Ū��A�ƥ�t�~�g�bmouseMoveHandler
				//�M���e���A�e��
				 public void paintComponent( Graphics g ) //�@�w�n�~��JPanel�~�i�Hsuper.paintComponent
				 {
					 super.paintComponent( g ); // clears drawing area
					 bg.paintIcon(this, g, 0, 0); //�e��
				 }		  
	}
		
	
	//�غc�l
	public FinalQ1_103403530(){
		super("���ڽc"); //�������D
		paintPanel.setLayout(null); //�e����layout�n�]��null�A�w�]��flowLayout�A�o�˦b�s�W���M�Q�t�ɷ|�X�{���~
		setLayout(new BorderLayout()); //BorderLayout�|��JFrame�e�������u�F�v�B�u��v�B�u�n�v�B�u�_�v�B�u���v
		//�]�w����Ϫ�����
		controlBar.setLayout(new GridLayout(2,1)); //������C
		//�]�w���s�Ϧ�m
		buttonPanel.setLayout(new GridLayout(2,3)); //2�C3��
		buttonPanel.add(newFishButton);
		buttonPanel.add(deleteAnimalButton);
		buttonPanel.add(controlAnimalButton);
		buttonPanel.add(newTurtleButton);
		buttonPanel.add(deleteAllButton);
		buttonPanel.add(controlAllButton);
		controlBar.add(buttonPanel); //����ϤW��
		//�]�w��ܰϦ�m
		showMessage.setBackground(Color.BLACK);
		showMessage.setLayout(new GridLayout(1,3)); //1�C3��
		nowFunctionJLabel.setForeground(new Color(0, 255, 204)); //�r���C��
		nowFishNumJLabel.setForeground(new Color(0, 255, 204)); //�r���C��
		fishingNumJLabel.setForeground(new Color(0, 255, 204)); //�r���C��
		nowFunctionJLabel.setFont(new Font("Dialog",   1,   15)); //�r��j�p
		nowFishNumJLabel.setFont(new Font("Dialog",   1,   15));
		fishingNumJLabel.setFont(new Font("Dialog",   1,   15));
		showMessage.add(nowFunctionJLabel);
		showMessage.add(nowFishNumJLabel);
		showMessage.add(fishingNumJLabel);
		//showMessage.add(emptyLabel1); //�ťաA���F���r���
		//showMessage.add(emptyLabel2); //�ťաA���F���r���
		//showMessage.add(emptyLabel3); //�ťաA���F���r���
		//showMessage.add(emptyLabel4); //�ťաA���F���r���
		controlBar.add(showMessage); //����ϤU��
       
		//�]�w��m
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
		
		//��Ц�m����
		paintPanel.addMouseListener(new MouseHandler()); 
		
		//�C���P�_���M���񪺲���
		timer.schedule(determinTask, 1,15); //delay0.01���A�C0.15������
	}   
	// inner class for button event handling
			private class ButtonHandler implements ActionListener 
			   {
			      // handle button event
			      public void actionPerformed( ActionEvent event )
			      {
			    	 if(event.getActionCommand() == "�s�W��" ){
			    		buttonMode = 1; //�Ĥ@��:�s�W��
			    		nowFunctionJLabel.setText("�ثe�\��:�s�W��");//�N�����e����
			    		stopFlag = false; //�j���ܦ��~��Ҧ�
			    		stopAllFlag = false; //�j���ܦ��~��Ҧ�
			    	 }
			    	 else if(event.getActionCommand() == "�񳨬�" ){
			    		buttonMode = 2; //�ĤG��:�񳨬�
			    		nowFunctionJLabel.setText("�ثe�\��:�񳨬�"); //�{�b�\��:�񳨬�	
			    		stopFlag = false; //�j���ܦ��~��Ҧ�
			    		stopAllFlag = false; //�j���ܦ��~��Ҧ�
			    	 }
			    	 else if(event.getActionCommand() == "������" ){
			    		 buttonMode = 3; //�ĤT��:������
			    		 stopAllFlag = false; //�j���ܦ��~��Ҧ�
			    		 nowFunctionJLabel.setText("�ثe�\��:������"); //�{�b�\��:������
			    		 stopFlag = false; //�j���ܦ��~��Ҧ�
			    	 }
			    	 else if(event.getActionCommand() == "��������" ){
			    		 buttonMode = 4; //�ĥ|��:��������
			    		 stopFlag = false; //�j���ܦ��~��Ҧ�
			    		 stopAllFlag = false; //�j���ܦ��~��Ҧ�
			    		 nowFunctionJLabel.setText("�ثe�\��:��������"); //�{�b�\��:��������
			    		 for(int i = 0; i<fishList.size();i++){ //�⳽��list�M��
			    			 fishList.get(i).stopAll(); //�����timer
			    			 paintPanel.remove(fishList.get(i)); //�����e��������
			    		 }
			    		 for(int j = 0 ; j<fishRodList.size();j++){ //��Q�t��list�M��
			    			 fishRodList.get(j).stopAll(); //����Q�t��timer
			    			 paintPanel.remove(fishRodList.get(j)); //�����e�������Q�t
			    		 }
			    		 fishList.clear(); //�M��fishList
			    		 fishRodList.clear(); //�M��fishRodList
			    		 paintPanel.repaint(); //���e
			    		 fishingNum = 0; //���쪺���k�s
			    		 nowFishNumJLabel.setText("���ƶq:"+fishList.size()); //��ܳ����ƶq
			    		 fishingNumJLabel.setText("�w���쪺���ƶq:"+fishingNum); //��ܳ��쪺���ƶq
			    	 }
			    	 else if(event.getActionCommand() == "����/�~����" ){
			    		 buttonMode = 5; //�Ĥ���:����/�~����
			    		 stopAllFlag = false; //�j���ܦ��~��Ҧ�
			    		 if(stopFlag == false){ //�ܦ�����Ҧ�
			    			 stopFlag = true;
			    			 nowFunctionJLabel.setText("�ثe�\��:������"); //�{�b�\��:������
			    		 }
			    		 else{ //�ܦ��~��Ҧ�
			    			 stopFlag = false;
			    			 nowFunctionJLabel.setText("�ثe�\��:�~����"); //�{�b�\��:�~����
			    		 }
			    	 }
			    	 else if(event.getActionCommand() == "����/�~�����" ){
			    		 buttonMode = 6; //�Ĥ���:����/�~�����
			    		 stopFlag = false; //�j���ܦ��~��Ҧ�
			    		 if(stopAllFlag == false){ //����
			    			 for(int i = 0; i<fishList.size();i++){  //��
				    			 fishList.get(i).stopAll(); //����
				    			 fishList.get(i).setFishStop(); //���ܳ������A
				    		 }
			    			 /*for(int j = 0 ; j<fishRodList.size();j++){ //�Q�t
				    			 fishRodList.get(j).stopAll(); //����Q�t��timer
				    			 fishRodList.get(j).setTurtleStop(); //���ܯQ�t���A
				    		 }*/
			    			 stopAllFlag = true;
			    			 nowFunctionJLabel.setText("�ثe�\��:�������"); //�{�b�\��:�������
			    		 }
			    		 else{ //�~��
			    			 for(int i = 0; i<fishList.size();i++){ //��
				    			 fishList.get(i).update(); //�~��
				    			 fishList.get(i).setFishContinue(); //���ܳ������A
				    		 }
			    			 /*for(int j = 0 ; j<fishRodList.size();j++){ //�Q�t
				    			 fishRodList.get(j).update(); //����Q�t��timer
				    			 fishRodList.get(j).setTurtleContinue(); //���ܯQ�t���A
				    		 }*/
			    			 stopAllFlag = false;
			    			 nowFunctionJLabel.setText("�ثe�\��:�~�����"); //�{�b�\��:�~�����
			    		 }
			    	 }
			      }
			   }
	// inner class to handle mouse events
			private class MouseHandler extends MouseAdapter 
			{
				 public void mouseClicked(MouseEvent event){ //�o�����U�ƹ��ɪ����@�I
					//�{�b��m
			         Now_X = event.getX();
					 Now_Y = event.getY();
					 if(buttonMode == 1){ //�Ĥ@��:�s�W��
						newFish = new Fish(Now_X,Now_Y); //�s�W����}�C
				    	fishList.add(newFish);
				    	paintPanel.add(fishList.get(fishList.size()-1));
				    	nowFishNumJLabel.setText("���ƶq:"+fishList.size()); //��ܳ����ƶq
					 }
					 else if(buttonMode == 2){ //�ĤG��:�񳨬�
						newFishRod= new FishRod(Now_X,Now_Y);//�s�W�����}�C
				    	fishRodList.add(newFishRod);
				    	paintPanel.add(fishRodList.get(fishRodList.size()-1)); //�N�Q�t���e����
					 }
					 else if(buttonMode == 3){ //�ĤT��:������
						//�P�_�ƹ��I�諸�O�_�b�ϮפW��
						    for(int j = 0 ; j<fishRodList.size();j++){ //Ū���C�ӳ���
						    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())){
						    		//fishRodList.get(j).stop(); //����ӯQ�t��timer
						    		fishRodList.get(j).stopAll(); //�����ӳ���timer
						    		paintPanel.remove(fishRodList.get(j)); //�����e�������ӳ�
						    		fishRodList.remove(j);//�R��fishlist���ӳ�
					    		}
						    	for(int i = 0; i<fishList.size();i++){ //�P�_������ɡAŪ���C�ӳ��A�P�_�O���O��n�b�o����W��
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())){
							    		//fishList.get(i).stop(); //����ӳ���timer
							    		fishList.get(i).stopAll(); //����ӳ���timer
							    		paintPanel.remove(fishList.get(i)); //�����e�������ӳ�
							    		fishList.remove(i);//�R��fishlist���ӳ�
							    		fishingNum++;//�h���@����
						    		} 
							    	nowFishNumJLabel.setText("���ƶq:"+fishList.size()); //��ܳ����ƶq
							    	fishingNumJLabel.setText("�w���쪺���ƶq:"+fishingNum); //��ܤw���쪺���ƶq
							    	//paintPanel.repaint(); //���e
					    		 }
						    	paintPanel.repaint(); //���e
						    }
					 } // buttonMode == 4 �S�@��
					 else if(buttonMode == 5){ //�Ĥ���:����/�~����
						 if(stopFlag == true){ //�Ȱ��Ҧ�
						    	//�P�_�ƹ��I�諸�O�_�b�ϮפW���A�B���ίQ�t���O�T��A
							    for(int i = 0; i<fishList.size();i++){ //Ū���C�ӳ�
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())
							    			&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())
							    			&&(fishList.get(i).getFishState() == false)){
							    		fishList.get(i).stop(); //����ӳ���timer
							    		fishList.get(i).setFishStop(); //�������A�ܦ�����
						    		} 
					    		 }
							    /*for(int j = 0 ; j<fishRodList.size();j++){ //Ū���C�ӯQ�t
							    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())
							    			&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())
							    			&&(fishRodList.get(j).getTurtleState() == false)){
							    		fishRodList.get(j).stop(); //����ӯQ�t��timer
							    		fishRodList.get(j).setTurtleStop();//�Q�t�����A�ܰ���
						    		} 
							    }*/
						    }
					    else{ //�P�_�ƹ��I�諸�O�_�b�ϮפW���A�B���ίQ�t���O�~�򪬺A
						    	for(int i = 0; i<fishList.size();i++){ //Ū���C�ӳ�
							    	if((Now_X > fishList.get(i).get_X())&&(Now_X < fishList.get(i).get_size_X())&&(Now_Y > fishList.get(i).get_Y())&&(Now_Y < fishList.get(i).get_size_Y())&&(fishList.get(i).getFishState() == true)){
							    		fishList.get(i).update(); //�~��ӳ���timer
							    		fishList.get(i).setFishContinue(); //�������A�ܦ��~��
						    		} 
					    		 }
						    	 /*for(int j = 0 ; j<fishRodList.size();j++){ //Ū���C�ӯQ�t
								    	if((Now_X > fishRodList.get(j).get_X())&&(Now_X < fishRodList.get(j).get_size_X())&&(Now_Y > fishRodList.get(j).get_Y())&&(Now_Y < fishRodList.get(j).get_size_Y())&&(fishRodList.get(j).getTurtleState() == true)){
								    		fishRodList.get(j).update(); //�~��ӯQ�t��timer
								    		fishRodList.get(j).setTurtleContinue(); //�Q�t���A�ܦ��~��
							    		} 
								    }*/
						}
					 }
					//buttomMode == 6 �S�@��
				 }
			}
			class DetermineTask extends TimerTask{ //���_�P�_���M����
				public void run(){
					if((!fishList.isEmpty())&&(!fishRodList.isEmpty())){//���ˬd�O�_�����]������
						fish_fishrod_exist = true; //���M���񳣦s�b
					}
					else{
						fish_fishrod_exist = false;
					}
					if(fish_fishrod_exist == true){ //�p�G���M���񳣦s�b�A�~��P�_
						//�P�_�O�_�Y�쳽��
						for(int i = 0 ; i<fishList.size();i++){ //Ū���C�ӳ�
					    	for(int j = 0; j<fishRodList.size();j++){ //Ū���C�ӳ���A�P�_���O���O��n�b���d��
						    	if((fishList.get(i).get_X() > fishRodList.get(j).get_X())&&(fishList.get(i).get_X() < fishRodList.get(j).get_size_X())&&(fishList.get(i).get_Y() > fishRodList.get(j).get_Y())&&(fishList.get(i).get_Y() < fishRodList.get(j).get_size_Y())
						    			&&(fishRodList.get(j).getFishRodState() == 0)){ //���񪬺A�]�����O�����窺
						    		
						    		fishRodList.get(j).changeFishRodState(); //���ܳ���Ϥ�
						    		fishList.get(i).getBiger(); //���ܤj
					    		} 
				    		 }
					    }
						
						//�����S�����窺����
						for(int i = 0 ; i<fishList.size();i++){ //Ū���C�ӳ�
					    	for(int j = 0; j<fishRodList.size();j++){ //Ū���C�ӳ���A�P�_���O���O��n�b���d��
						    	if((fishList.get(i).get_X() > fishRodList.get(j).get_X() - 30)&&(fishList.get(i).get_X() < fishRodList.get(j).get_size_X() + 30)&&(fishList.get(i).get_Y() > fishRodList.get(j).get_Y() + 30)&&(fishList.get(i).get_Y() < fishRodList.get(j).get_size_Y() +30)
						    			&&(fishRodList.get(j).getFishRodState() == 1)){ //���񪬺A�����O�S���窺
						    		fishList.get(i).changeFishDirect(); //���ܳ�����V
						    		//fishRodList.get(j).changeFishRodState(); //���ܳ���Ϥ�
						    		//fishList.get(i).getBiger(); //���ܤj
					    		} 
				    		 }
					    }
					
					}
					
				}
			}
	//�D�{��
		public static void main(String[] args){
			FinalQ1_103403530 f = new FinalQ1_103403530();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize( 775, 650); // set frame size
			f.setVisible( true ); // display frame
		}	   
}