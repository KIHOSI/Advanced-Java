import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HW3_103403530 extends JFrame {
	private JPanel gameView = new JPanel();//�C���e���A��p���s���Ϥ�
	private JPanel buttonView = new JPanel();//���s�ާ@��m
	private JTextField nickName = new JTextField("Set your nickname"); //��J�W�r
	private JButton giveCandy = new JButton("Give Candy"); //���}�G���s
	private int nowCandy = 0; //�{�b�}�G
	private int needCandy = 25; //�ݭn�}�G
	private int needCandy1 = 0; //�Ĥ@���q�ݭn�}�G��
	private int needCandy2 = 0; //�ĤG���q�ݭn�}�G��
	private JButton openFile = new JButton("Open Game"); //���}���ɮ׫��s
	private JButton saveFile = new JButton("Save"); //�x�s�ɮ�(����)
	private JButton saveAnotherFile = new JButton("Save As");//�t�s�s��
	private JLabel candyState = new JLabel("0/25");//�w���}�G�P�ݦh�ֿ}�G�����A
	private JLabel fileState = new JLabel("New File");//�ɮת��A
	private BufferedImage pokePic;
	private String picName = "small.png"; //�Ϥ��W��
	private String picName1 = ""; //�Ĥ@���q�Ϥ�
	private String picName2 = ""; //�ĤG���q�Ϥ�
	private String picName3 = ""; //�ĤT���q�Ϥ�
	private JPanel buttonView1 = new JPanel(); //���O��|�����
	private JPanel buttonView2 = new JPanel();
	private JPanel buttonView3 = new JPanel();
	private JPanel buttonView4 = new JPanel();
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Scanner inputCondition;
	private String fileName = ""; //�o��ҿ��ɮ�
	private int countEvolve = 0; //�P�_�i�ƴX��
	private int chooseRight = 0; //�P�_�O�_����ɮ�
	private Icon[] dices = { //�s���l�Ϥ�
			new ImageIcon(getClass().getResource("DICE1_1.png")),
			new ImageIcon(getClass().getResource("DICE1_2.png")),
			new ImageIcon(getClass().getResource("DICE1_3.png")),
			new ImageIcon(getClass().getResource("DICE1_4.png")),
			new ImageIcon(getClass().getResource("DICE1_5.png")),
			new ImageIcon(getClass().getResource("DICE1_6.png"))};
	private Icon[] dices2 = { //�s���l�Ϥ�2
			new ImageIcon(getClass().getResource("DICE2_1.png")),
			new ImageIcon(getClass().getResource("DICE2_2.png")),
			new ImageIcon(getClass().getResource("DICE2_3.png")),
			new ImageIcon(getClass().getResource("DICE2_4.png")),
			new ImageIcon(getClass().getResource("DICE2_5.png")),
			new ImageIcon(getClass().getResource("DICE2_6.png"))};
	private JLabel diceDisplay = new JLabel(dices[0]); //��ܻ�l���a��A�w�]�Ĥ@�i��
	private JLabel diceDisplay2 = new JLabel(dices2[0]); //�ĤG����ܻ�l���a��A�w�]�Ĥ@�i��
	private JLabel explainDice = new JLabel("��X�ۦP�I�ƧY�|��o���I�ƭӿ}�G!"); //������l�C���W�h
	
	//��ܹC���Ϥ�
	private class GamePic extends JPanel{
		public GamePic(){
			try {      
		          pokePic = ImageIO.read(getClass().getResource(picName));
		       } catch (IOException ex) {
		            // handle exception...
		    	   System.err.println("Ū�Ϥ����~");
		       }
		}
		public void paintComponent( Graphics g ) //�N�Ϥ��e�X��
		{
			super.paintComponent(g);
			setBackground(Color.WHITE); //�I���]���զ�
			g.drawImage(pokePic,50,50,400,400,this); //�e����m�B�j�p
		}
	}
	
	public HW3_103403530(){//�غc�l
		super("Pokemon");//�������D
		
		//Ū�i�Ʊ�����
		try{
				inputCondition = new Scanner(new File("bin/pokemon.txt")); //Ū��
					picName1 = inputCondition.next(); //small.png
					needCandy1 = inputCondition.nextInt(); //25
					picName2 = inputCondition.next(); //medium.png
					needCandy2 = inputCondition.nextInt(); //100
					picName3 = inputCondition.next(); //large.png		
		}
		catch(FileNotFoundException fileNotFoundException){ //�䤣���ɮ�
			System.err.println("File cannot be found");
			System.exit(1);
		}
		finally{ //����
			if(inputCondition != null){
				inputCondition.close();
			}
		}
		//�N�Ĥ@���q������P�Ϥ��o��ó]�w
		//picName = picName1;  //�|�X�{�ýX
		needCandy = needCandy1;
		
		setLayout(new BorderLayout());//�e�������_���n
		GamePic gamePic = new GamePic(); 
		gamePic.setPreferredSize(new Dimension(490,490)); //�վ�C���e���j�p
		repaint(); //�e�X�Ϥ�
		gameView.setBackground(Color.WHITE); //�I�����զ�
		gameView.add(gamePic);
		
		//�\�]���s�ާ@��m
		buttonView.setLayout(new GridLayout(4,1)); //�����|�h
		buttonView1.add(nickName); //�Ĥ@�h
		nickName.setPreferredSize(new Dimension(650,30)); //nickName�����P�e
		buttonView2.add(giveCandy); //�ĤG�h
		buttonView2.add(candyState);
		diceDisplay.setPreferredSize(new Dimension(30,30)); //��l�j�p
		diceDisplay2.setPreferredSize(new Dimension(30,30)); //��l�j�p
		buttonView2.add(diceDisplay);
		buttonView2.add(diceDisplay2);
		buttonView2.add(explainDice);//������r
		buttonView3.add(openFile); //�ĤT�h
		buttonView3.add(saveFile);
		buttonView3.add(saveAnotherFile);
		buttonView4.setLayout(new FlowLayout(FlowLayout.LEFT));//�ĥ|�h
		buttonView4.add(fileState); 
		
		giveCandy.addActionListener( //���}�G
		         new ActionListener() 
		         {    
		            public void actionPerformed( ActionEvent event )
		            {
		            	Random random = new Random(); //�إ߶üƲ��;�
		            	int addCandy = random.nextInt(6)+1; //�H����X1~6�����
		            	int addCandy2 = random.nextInt(6)+1; //�H����X1~6�����	
		            	diceDisplay.setIcon(dices[addCandy-1]); //�ܴ���l�Ϯ�
		            	diceDisplay2.setIcon(dices2[addCandy2-1]); //�ܴ���l�Ϯ�
		            	if(addCandy == addCandy2){ //��X�P�˼Ʀr�~�|�W�[�}�G
		            		nowCandy += addCandy; //��X�h�ִN���h�ֿ}�G
		            	}
		            	candyState.setText(nowCandy+"/"+needCandy); //��ܥثe�}�G/�ݭn�}�G
		                if(nowCandy >= needCandy1 && countEvolve < 1){ //�Ĥ@���q����
		                	JOptionPane.showMessageDialog(null, "Your monster is evolved!!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	picName = picName2; //�����ĤG���q���Ϥ�
		                	GamePic gamePic = new GamePic(); //���Ϥ�
		                	repaint();
		                	nowCandy = 0;//��l��
		                	diceDisplay.setIcon(dices[0]); //��l�Ϯת�l��
		                	diceDisplay2.setIcon(dices2[0]); //��l�Ϯ�2��l��
		                	needCandy = needCandy2; //�ĤG���q�ݭn�}�G��
		                	candyState.setText(nowCandy+"/"+needCandy); //�����ĤG���q
		                	countEvolve = 1; //�i�Ƥ@��
		                }
		                if(nowCandy >= needCandy2 && countEvolve < 2){ //�ĤG���q����
		                	JOptionPane.showMessageDialog(null, "Your monster is evolved!!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	JOptionPane.showMessageDialog(null, "Congratuation!!Your monster has final evolved", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	picName = picName3; //�����ĤG���q���Ϥ�
		                	GamePic gamePic = new GamePic(); //���Ϥ�
		                	repaint();
		                	diceDisplay.setIcon(dices[0]); //��l�Ϯת�l��
		                	diceDisplay2.setIcon(dices2[0]); //��l�Ϯ�2��l��
		                	countEvolve = 2; //�i�ƨ⦸
		                }
		            } 
		         } 
		      ); 
		
		openFile.addActionListener( //�}�ɮ�
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					chooseRight = 0; //��l��
					openFile(); //���}�ɮ�
					if(chooseRight == 1){ return;} //�S������ɮסA���}JFileChooser
					readFile(); //Ū��
					closeFile(); //����
				}

				private void openFile() {
					// TODO �۰ʲ��ͪ���k Stub
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//�u����ɮ�
					fileChooser.setCurrentDirectory(new File(".")) ; //�w�]�b��e��Ƨ�
					int result = fileChooser.showOpenDialog(HW3_103403530.this);//Ū��
					try{
							if(result == JFileChooser.APPROVE_OPTION){ //�p�G���U�T�{��
								fileName = fileChooser.getSelectedFile().getAbsolutePath();//�s���s�b����m
								input = new ObjectInputStream(new FileInputStream(fileName)); //�|�Y�XIOException							
							}
							else if(result == JFileChooser.CANCEL_OPTION){ //�����{��
								chooseRight = 1; //�S������ɮ�
							}
					}
					catch(IOException ioException){
						System.err.println("Ū�ɿ��~");
						System.exit(1);
					}
						
					}
					private void readFile() {
					// TODO �۰ʲ��ͪ���k Stub
						PokeSerializable pokemon = new PokeSerializable();
						try{
							pokemon = (PokeSerializable) input.readObject(); //�N�ɮ�Ū���
							nickName.setText(pokemon.getNickname()); //�s���i��
							picName = pokemon.getMonster(); //�Ϥ�
							switch(picName){ //�P�_�O�ĴX���q
							 case "small.png": //�Ĥ@���q
								 needCandy = needCandy1;
								 countEvolve = 0;
								 break;
							 case "medium.png": //�ĤG���q
								 needCandy = needCandy2;
								 countEvolve = 1;
								 break;
							 case "large.png": //�ĤT���q
								 needCandy = needCandy2;
								 countEvolve = 2;
								 break;
							}	
							nowCandy = pokemon.getCandy(); //�ثe�}�G��
							diceDisplay.setIcon(dices[0]); //��l�Ϯת�l��
							diceDisplay2.setIcon(dices2[0]); //��l�Ϯ�2��l��
							candyState.setText(nowCandy+"/"+needCandy); //�ثe�}�G��/�ݭn�}�G��
							fileState.setText("File Load From: " + fileName); 
							GamePic gamePic = new GamePic(); //���e�Ϥ�
							repaint();
						}
						catch(ClassNotFoundException classNotFoundException){
							System.err.println("Unable to create object.");
							System.exit(1);
						}
						catch(IOException ioException){
							System.err.println("Ū�ɿ��~");
							System.exit(1);
						}
					
					}
					private void closeFile() {
					// TODO �۰ʲ��ͪ���k Stub
						try{
							if(input != null){ //����
								input.close();
							}
						}
						catch(IOException ioException){
							System.err.println("Error closing file.");
							System.exit(1);
						}
					}
			}
		);
		
		saveFile.addActionListener( //�s��
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						chooseRight = 0;//��l��
						openFile(); //����ɮ�
						if(chooseRight == 1){return;} //�S������ɮסA���}JFileChooser
						addPokeInfo();//�s��poke���
						closeFile(); //�����ɮ�
					}

					private void openFile() {
						// TODO �۰ʲ��ͪ���k Stub
						if(fileName == ""){ //�p�G���O����
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//�s�ɮ�
							fileChooser.setCurrentDirectory(new File(".")) ; //�w�]�s�b��e��Ƨ�
							int result = fileChooser.showSaveDialog(HW3_103403530.this);//�s��
							try{
									if(result == JFileChooser.APPROVE_OPTION){ //�p�G���U�T�{��
										fileName = fileChooser.getSelectedFile().getAbsolutePath();//�s���s�b����m
										output = new ObjectOutputStream(new FileOutputStream(fileName)); //�|�Y�XIOException
									}
									else if(result == JFileChooser.CANCEL_OPTION){ //�����{��
										chooseRight = 1;//�S������ɮ�
									}
							}
							catch(IOException ioException){
								System.err.println("�s�ɿ��~");
								System.exit(1);
							}
						}
						else{ //����
							try{
								output = new ObjectOutputStream(new FileOutputStream(fileName)); //�|�Y�XIOException
							}
							catch(IOException ioException){
								System.err.println("�s�ɿ��~");
								System.exit(1);
							}
						}
					}

					private void addPokeInfo() { //�s���ɮ׸�T
						// TODO �۰ʲ��ͪ���k Stub
						try{
							PokeSerializable pokemon; 
							String nickname = nickName.getText(); //�W��
							String monster = picName; //�Ϥ�
							int candy = nowCandy; //�ثe�}�G��
							pokemon = new PokeSerializable(nickname,monster,candy); //�s���W�١B�Ϥ��B�}�G��
							output.writeObject(pokemon); //�g�i�ɮ׸�
							fileState.setText("File Load From: " + fileName); //�ܧ�W��
						}
						catch(IOException ioException){
							System.err.println("�s�ɿ��~");
						}
					}

					private void closeFile() {
						// TODO �۰ʲ��ͪ���k Stub
						try{
							if(output != null){ //����
								output.close();
							}
						}
						catch(IOException ioException){
							System.err.println("Error closing file");
							System.exit(1);
						}
					}
				});
		
		saveAnotherFile.addActionListener( //�t�s�ɮ�
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
							chooseRight = 0; //��l��
							openFile(); //����ɮ�
							if(chooseRight == 1){return;} //�S������ɮסA���}JFileChooser
							addPokeInfo();//�s��poke���
							closeFile(); //�����ɮ�		
					}
					
					private void openFile() { //��ܦs�ɦ�m
						// TODO �۰ʲ��ͪ���k Stub
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//�s�ɮ�
						fileChooser.setCurrentDirectory(new File(".")) ; //�w�]�s�b��e��Ƨ�
						int result = fileChooser.showSaveDialog(HW3_103403530.this);//�s��	
						try{
							if(result == JFileChooser.APPROVE_OPTION){ //�p�G���U�T�{��
								fileName = fileChooser.getSelectedFile().getAbsolutePath();//�s���s�b����m
								output = new ObjectOutputStream(new FileOutputStream(fileName)); //�|�Y�XIOException
							}
								else if(result == JFileChooser.CANCEL_OPTION){ //�����{��
									chooseRight = 1; //�S������ɮ�
								}
						}
						catch(IOException ioException){
							System.err.println("�s�ɿ��~");
							System.exit(1);
						}
					}
					
					private void addPokeInfo() { //�N��Ʀs�i�h
						// TODO �۰ʲ��ͪ���k Stub
						try{
							PokeSerializable pokemon;
							String nickname = nickName.getText(); //�W��
							String monster = picName; //�Ϥ�
							int candy = nowCandy; //�{�b�}�G��
							pokemon = new PokeSerializable(nickname,monster,candy);
							output.writeObject(pokemon); //�g�i�ɮ׸�
							fileState.setText("File Load From: " + fileName); //�ܧ�W��
						}
						catch(IOException ioException){
							System.err.println("�s�ɿ��~");
							System.exit(1);
						}
					}
					
					private void closeFile() { //�����ɮ�
						// TODO �۰ʲ��ͪ���k Stub
						try{
							if(output != null){
								output.close(); //����
							}
						}
						catch(IOException ioException){
							System.err.println("Error closing file");
							System.exit(1);
						}
					}
				});
		
		buttonView.add(buttonView1); //�[�J���s����
		buttonView.add(buttonView2);
		buttonView.add(buttonView3);
		buttonView.add(buttonView4);

		//�\�]�e����m
		add(gameView,BorderLayout.NORTH); //�C���e���b�W��
		add(buttonView,BorderLayout.SOUTH); //���s�����b�U��
	}

	public static void main(String[] args){//�D�{��
		HW3_103403530 hw3 = new HW3_103403530();
		hw3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hw3.setSize( 700, 700 ); // set frame size
		hw3.setVisible( true ); // display frame
	}	
}
