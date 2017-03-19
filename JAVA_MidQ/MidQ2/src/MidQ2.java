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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MidQ2 extends JFrame {
	private JPanel gameView = new JPanel();//遊戲畫面，放小火龍的圖片
	private JPanel buttonView = new JPanel();//按鈕操作位置
	private JTextField nickName = new JTextField("Set your nickname"); //輸入名字
	private JButton giveCandy = new JButton("Give Candy"); //給糖果按鈕
	private int nowCandy = 0; //現在糖果
	private int needCandy = 25; //需要糖果
	private int needCandy1 = 0; //第一階段需要糖果數
	private int needCandy2 = 0; //第二階段需要糖果數
	private JButton openFile = new JButton("Open Game"); //打開舊檔案按鈕
	private JButton saveFile = new JButton("Save"); //儲存檔案(舊檔)
	private JButton saveAnotherFile = new JButton("Save As");//另存新檔
	private JLabel candyState = new JLabel("0/25");//已給糖果與需多少糖果的狀態
	private JLabel fileState = new JLabel("Status:New File");//檔案狀態
	private BufferedImage pokePic;
	private String picName = "small.png"; //圖片名稱
	private String picName1 = ""; //第一階段圖片
	private String picName2 = ""; //第二階段圖片
	private String picName3 = ""; //第三階段圖片
	private JPanel buttonView1 = new JPanel(); //分別當四個欄位
	private JPanel buttonView2 = new JPanel();
	private JPanel buttonView3 = new JPanel();
	private JPanel buttonView4 = new JPanel();
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ObjectInputStream inputGameManage;
	private ObjectOutputStream outputGameManage;
	private Scanner inputCondition;
	private String filePath = ""; //得到所選檔案路徑
	private String fileName = ""; //得到所選檔案名稱
	private int countEvolve = 0; //判斷進化幾次
	private int chooseRight = 0; //判斷是否選擇檔案
	private static String gameFile[] = {"-未選取-"};
	private JComboBox gameComboBox = new JComboBox(gameFile);
	
	//顯示遊戲圖片
	private class GamePic extends JPanel{
		public GamePic(){
			try {      
		          pokePic = ImageIO.read(getClass().getResource(picName));
		       } catch (IOException ex) {
		            // handle exception...
		    	   System.err.println("讀圖片錯誤");
		       }
		}
		public void paintComponent( Graphics g ) //將圖片畫出來
		{
			super.paintComponent(g);
			setBackground(Color.WHITE); //背景設為白色
			g.drawImage(pokePic,50,50,400,400,this); //畫的位置、大小
		}
	}
	
	public MidQ2(){//建構子
		super("Pokemon");//視窗標題
		
		//讀進化條件檔
		try{
				inputCondition = new Scanner(new File("bin/pokemon.txt")); //讀檔
					picName1 = inputCondition.next(); //small.png
					needCandy1 = inputCondition.nextInt(); //25
					picName2 = inputCondition.next(); //medium.png
					needCandy2 = inputCondition.nextInt(); //100
					picName3 = inputCondition.next(); //large.png		
		}
		catch(FileNotFoundException fileNotFoundException){ //找不到檔案
			System.err.println("File cannot be found");
			System.exit(1);
		}
		finally{ //關檔
			if(inputCondition != null){
				inputCondition.close();
			}
		}
		//將第一階段的條件與圖片得到並設定
		//picName = picName1;  //會出現亂碼
		needCandy = needCandy1;
		
		/*//讀遊戲進度檔
		try{
			inputGameManage = new ObjectInputStream(new FileInputStream("saved_game.ser")); //會擲出IOException
		}
		catch(IOException ioException){ //找不到檔案
			System.err.println("File cannot be found");
			System.exit(1);
		}
		finally{ //關檔
			try{
				if(inputGameManage != null){
					inputGameManage.close();
				}
			}
			catch(IOException ioException){
				System.err.println("Error closing file.");
				System.exit(1);
			}
		}*/
		
		setLayout(new BorderLayout());//畫面分為北中南
		GamePic gamePic = new GamePic(); 
		gamePic.setPreferredSize(new Dimension(490,490)); //調整遊戲畫面大小
		repaint(); //畫出圖片
		gameView.setBackground(Color.WHITE); //背景為白色
		gameView.add(gamePic);
		
		//擺設按鈕操作位置
		buttonView.setLayout(new GridLayout(4,1)); //分成四層
		buttonView1.add(nickName); //第一層
		nickName.setPreferredSize(new Dimension(650,30)); //nickName的長與寬
		buttonView2.add(giveCandy); //第二層
		buttonView2.add(candyState);
		
		buttonView3.add(saveFile); //第三層
		buttonView3.add(saveAnotherFile);
		
		buttonView4.add(gameComboBox);//第四層
		buttonView4.add(openFile);
		//buttonView4.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonView4.add(fileState); 
		
		giveCandy.addActionListener( //給糖果
		         new ActionListener() 
		         {    
		            public void actionPerformed( ActionEvent event )
		            {
		            	nowCandy++;//每次加一次糖果
		            	candyState.setText(nowCandy+"/"+needCandy); //顯示目前糖果/需要糖果
		                if(nowCandy >= needCandy1 && countEvolve < 1){ //第一階段完成
		                	JOptionPane.showMessageDialog(null, "Your monster is evolved!!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	picName = picName2; //換成第二階段的圖片
		                	GamePic gamePic = new GamePic(); //換圖片
		                	repaint();
		                	nowCandy = 0;//初始化
		                	
		                	needCandy = needCandy2; //第二階段需要糖果數
		                	candyState.setText(nowCandy+"/"+needCandy); //換成第二階段
		                	countEvolve = 1; //進化一次
		                }
		                if(nowCandy >= needCandy2 && countEvolve < 2){ //第二階段完成
		                	JOptionPane.showMessageDialog(null, "Your monster is evolved!!", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	JOptionPane.showMessageDialog(null, "Congratuation!!Your monster has final evolved", "Notice", JOptionPane.INFORMATION_MESSAGE);
		                	picName = picName3; //換成第二階段的圖片
		                	GamePic gamePic = new GamePic(); //換圖片
		                	repaint();
		                	
		                	countEvolve = 2; //進化兩次
		                }
		            } 
		         } 
		      ); 
		
		openFile.addActionListener( //開檔案
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					chooseRight = 0; //初始化
					openFile(); //打開檔案
					//if(chooseRight == 1){ return;} //沒有選擇檔案，離開JFileChooser
					readFile(); //讀檔
					
					closeFile(); //關檔
				}

				private void openFile() {
					// TODO 自動產生的方法 Stub
					//JFileChooser fileChooser = new JFileChooser();
					//fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//只能選檔案
					//fileChooser.setCurrentDirectory(new File(".")) ; //預設在當前資料夾
					//int result = fileChooser.showOpenDialog(MidQ2.this);//讀檔
					try{
							inputGameManage = new ObjectInputStream(new FileInputStream("saved_game.ser")); //會擲出IOException
							//if(result == JFileChooser.APPROVE_OPTION){ //如果按下確認鍵
							//	filePath = fileChooser.getSelectedFile().getAbsolutePath();//存取存在的位置
							//	input = new ObjectInputStream(new FileInputStream(filePath)); //會擲出IOException							
							//}
							//else if(result == JFileChooser.CANCEL_OPTION){ //關閉程式
								//chooseRight = 1; //沒有選擇檔案
							//}
					}
					catch(IOException ioException){
						System.err.println("讀檔錯誤");
						System.exit(1);
					}
						
					}
					private void readFile() {
					// TODO 自動產生的方法 Stub
						PokeSerializable pokemon = new PokeSerializable();
						try{
							pokemon = (PokeSerializable) input.readObject(); //將檔案讀近來
							nickName.setText(pokemon.getNickname()); //存取進度
							picName = pokemon.getMonster(); //圖片
							switch(picName){ //判斷是第幾階段
							 case "small.png": //第一階段
								 needCandy = needCandy1;
								 countEvolve = 0;
								 break;
							 case "medium.png": //第二階段
								 needCandy = needCandy2;
								 countEvolve = 1;
								 break;
							 case "large.png": //第三階段
								 needCandy = needCandy2;
								 countEvolve = 2;
								 break;
							}	
							nowCandy = pokemon.getCandy(); //目前糖果數
							
							candyState.setText(nowCandy+"/"+needCandy); //目前糖果數/需要糖果數
							fileState.setText("File Load From: " + filePath); 
							GamePic gamePic = new GamePic(); //重畫圖片
							repaint();
						}
						catch(ClassNotFoundException classNotFoundException){
							System.err.println("Unable to create object.");
							System.exit(1);
						}
						catch(IOException ioException){
							System.err.println("讀檔錯誤");
							System.exit(1);
						}
					
					}
					private void closeFile() {
					// TODO 自動產生的方法 Stub
						try{
							if(input != null){ //關檔
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
		
		saveFile.addActionListener( //存檔
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						chooseRight = 0;//初始化
						openFile(); //選擇檔案
						if(chooseRight == 1){return;} //沒有選擇檔案，離開JFileChooser
						addPokeInfo();//存取poke資料
						changeGameComboBox(); //改變combobox狀態
						closeFile(); //關閉檔案
					}

					

					private void openFile() {
						// TODO 自動產生的方法 Stub
						if(filePath == ""){ //如果不是舊檔
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//存檔案
							fileChooser.setCurrentDirectory(new File(".")) ; //預設存在當前資料夾
							int result = fileChooser.showSaveDialog(MidQ2.this);//存檔
							try{
									if(result == JFileChooser.APPROVE_OPTION){ //如果按下確認鍵
										filePath = fileChooser.getSelectedFile().getAbsolutePath();//存取存在的位置
										fileName = fileChooser.getSelectedFile().getName(); //存取檔案名稱
										output = new ObjectOutputStream(new FileOutputStream(filePath)); //會擲出IOException
									}
									else if(result == JFileChooser.CANCEL_OPTION){ //關閉程式
										chooseRight = 1;//沒有選擇檔案
									}
									outputGameManage = new ObjectOutputStream(new FileOutputStream("saved_game.ser"));//開啟遊戲進度檔
							}
							catch(IOException ioException){
								System.err.println("存檔錯誤");
								System.exit(1);
							}
						}
						else{ //舊檔
							try{
								output = new ObjectOutputStream(new FileOutputStream(filePath)); //會擲出IOException
								outputGameManage = new ObjectOutputStream(new FileOutputStream("saved_game.ser"));//開啟遊戲進度檔
							}
							catch(IOException ioException){
								System.err.println("存檔錯誤");
								System.exit(1);
							}
						}
					}

					private void addPokeInfo() { //存取檔案資訊
						// TODO 自動產生的方法 Stub
						try{
							PokeSerializable pokemon; 
							String nickname = nickName.getText(); //名稱
							String monster = picName; //圖片
							int candy = nowCandy; //目前糖果數
							pokemon = new PokeSerializable(nickname,monster,candy); //存取名稱、圖片、糖果數
							output.writeObject(pokemon); //寫進檔案裡
							fileState.setText("File Load From: " + filePath); //變更名稱
							GameSerializable gameManage = new GameSerializable(fileName,filePath);//儲存遊戲紀錄檔
							outputGameManage.writeObject(gameManage);//寫進紀錄檔
							
						}
						catch(IOException ioException){
							System.err.println("存檔錯誤");
						}
					}
					
					

					private void closeFile() {
						// TODO 自動產生的方法 Stub
						try{
							if(output != null || outputGameManage != null){ //關檔
								output.close();
								outputGameManage.close();
							}
						}
						catch(IOException ioException){
							System.err.println("Error closing file");
							System.exit(1);
						}
					}
				});
		
		saveAnotherFile.addActionListener( //另存檔案
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
							chooseRight = 0; //初始化
							openFile(); //選擇檔案
							if(chooseRight == 1){return;} //沒有選擇檔案，離開JFileChooser
							addPokeInfo();//存取poke資料
							changeGameComboBox(); //改變combobox狀態
							closeFile(); //關閉檔案		
					}
					
					private void openFile() { //選擇存檔位置
						// TODO 自動產生的方法 Stub
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);//存檔案
						fileChooser.setCurrentDirectory(new File(".")) ; //預設存在當前資料夾
						int result = fileChooser.showSaveDialog(MidQ2.this);//存檔	
						try{
							if(result == JFileChooser.APPROVE_OPTION){ //如果按下確認鍵
								filePath = fileChooser.getSelectedFile().getAbsolutePath();//存取存在的位置
								output = new ObjectOutputStream(new FileOutputStream(filePath)); //會擲出IOException
							}
								else if(result == JFileChooser.CANCEL_OPTION){ //關閉程式
									chooseRight = 1; //沒有選擇檔案
							}
							outputGameManage = new ObjectOutputStream(new FileOutputStream("saved_game.ser"));//開啟遊戲進度檔
						}
						catch(IOException ioException){
							System.err.println("存檔錯誤");
							System.exit(1);
						}
					}
					
					private void addPokeInfo() { //將資料存進去
						// TODO 自動產生的方法 Stub
						try{
							PokeSerializable pokemon;
							String nickname = nickName.getText(); //名稱
							String monster = picName; //圖片
							int candy = nowCandy; //現在糖果數
							pokemon = new PokeSerializable(nickname,monster,candy);
							output.writeObject(pokemon); //寫進檔案裡
							fileState.setText("File Load From: " + filePath); //變更名稱
							GameSerializable gameManage = new GameSerializable(fileName,filePath);//儲存遊戲紀錄檔
							outputGameManage.writeObject(gameManage);//寫進紀錄檔
						}
						catch(IOException ioException){
							System.err.println("存檔錯誤");
							System.exit(1);
						}
					}
					
					private void closeFile() { //關閉檔案
						// TODO 自動產生的方法 Stub
						try{
							if(output != null || outputGameManage != null){
								output.close(); //關檔
								outputGameManage.close();
							}
						}
						catch(IOException ioException){
							System.err.println("Error closing file");
							System.exit(1);
						}
					}
				});
		
		buttonView.add(buttonView1); //加入按鈕介面
		buttonView.add(buttonView2);
		buttonView.add(buttonView3);
		buttonView.add(buttonView4);

		//擺設畫面位置
		add(gameView,BorderLayout.NORTH); //遊戲畫面在上面
		add(buttonView,BorderLayout.SOUTH); //按鈕介面在下面
	}
	
	public void changeGameComboBox(){ //讀取進度檔，改變GameComboBox狀態
		//讀遊戲進度檔
				try{
					inputGameManage = new ObjectInputStream(new FileInputStream("saved_game.ser")); //會擲出IOException
				}
				catch(IOException ioException){ //找不到檔案
					System.err.println("File cannot be found");
					System.exit(1);
				}
				/*finally{ //關檔
					try{
						if(inputGameManage != null){
							inputGameManage.close();
						}
					}
					catch(IOException ioException){
						System.err.println("Error closing file.");
						System.exit(1);
					}
				}*/
		
		//拿取進度檔資料
		GameSerializable gameManage = new GameSerializable();//儲存遊戲紀錄檔
		ArrayList<String> gameName = new ArrayList<String>();
		ArrayList<String> gamePath = new ArrayList<String>();
		try {
			while(true){
				gameManage = (GameSerializable) inputGameManage.readObject();
				gameName.add(gameManage.getGameName()); //得到檔名
				gamePath.add(gameManage.getGamePath()); //得到路徑
				//System.out.printf("gameName : %d,gamePath: %d",gameManage.getGameName(),gameManage.getGamePath());
			}
		}
		catch(EOFException endOfFileException){ //結束
			gameFile = gameName.toArray(new String[gameName.size()]);
			gameComboBox.addItem(gameFile);
			/*for(int i = 0;i<gameFile.length;i++){
				System.out.printf("%s ",gameFile[i]);
			}*/
			return;
		} catch (ClassNotFoundException e) {
			// TODO 自動產生的 catch 區塊
			System.err.println("Unable to create object");
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			System.err.println("Error during read from file.");
		}
		
	}

	public static void main(String[] args){//主程式
		MidQ2 q2 = new MidQ2();
		q2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		q2.setSize( 700, 700 ); // set frame size
		q2.setVisible( true ); // display frame
	}	
}
