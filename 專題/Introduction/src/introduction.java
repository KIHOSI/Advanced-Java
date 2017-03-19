import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class introduction extends JFrame 
{
	
	private int flag=0;
	private ImageIcon image1 = new ImageIcon("src/HOSI.png");
	private ImageIcon image2 = new ImageIcon("src/Cher.png");
	private ImageIcon image3 = new ImageIcon("src/Hiene.png");
	private ImageIcon image4 = new ImageIcon("src/Cathy.png");
	private ImageIcon image5 = new ImageIcon("src/treasure.png");
	JPanel leftPanel = new JPanel();                                   
    JPanel rightPanel = new JPanel();
    
    JLabel picture = new JLabel(image1);
    JLabel name = new JLabel(" 洪靖雯");
    JLabel grade = new JLabel(" ● 系級:資管三B");
    JTextArea characteristic = new JTextArea(" ● 個人特質:"+"\n"+" 喜歡新鮮的事物，願意學習新知，嘗試平常人不敢做的事，對事物接受度"+"\n"+" 大，有責任心，感知力強");
    JTextArea goodat = new JTextArea(" ● 專長:"+"\n"+" 1.程式能力(已碰過語言：C,Java,HTML5,CSS,JS,PHP)"+"\n"+" 2.碰過ㄧ些協助開發工具(ex.Laravel、phpMyAdmin、github)，有MVC架構的概念");
    JTextArea expeirience = new JTextArea(" ● 經歷: 1.參加過學校的新生知訊網程設組，有網頁開發相關經驗"+"\n"+" 2.社團方面有參加創遊社，學習操作遊戲引擎(NVL,RPG Maker,C2,Unity)，"+"\n"+"    對遊戲開發有相當程度的認識"+"\n"+" 3.目前為大氣系網管，管理後台程式"+"\n");
    JTextArea future = new JTextArea(" ● 未來規劃:"+"\n"+" 目前打算參加系上新辦之Android學生團隊，學習App開發相關經驗，大三下"+"\n"+" 後想修一些資工課程；大四規劃去網路公司實習，同時也會準備推甄研究所");
    JTextArea classcondi = new JTextArea(" ● 修課情況:輔系資工");
    
    Box buttonbox;
    JButton HOSIButton = new JButton("洪靖雯");
    JButton CherButton = new JButton("陳奕瑄");
    JButton HeineButton = new JButton("朱浚銘");
    JButton CathyButton = new JButton("陳姿穎");
    JButton SurpriseButton = new JButton(image5);
    
    
	public introduction()
	{
		super("關於我們 About Us");
		setLayout(new BorderLayout());
		
		leftPanel.setLayout(new GridLayout(1, 1));
		leftPanel.setPreferredSize(new Dimension(400, 160));           
		leftPanel.setBackground(Color.WHITE);
		rightPanel.setLayout(new GridLayout(8, 1));
		rightPanel.setBackground(Color.decode("#FFEBCD")); 
		
		add(leftPanel,BorderLayout.WEST);  
		add(rightPanel,BorderLayout.CENTER); 
		
		name.setFont(new Font("新細明體", Font.BOLD, 40));
		grade.setFont(new Font("新細明體", Font.BOLD, 20));
		characteristic.setFont(new Font("新細明體", Font.BOLD, 20));
		goodat.setFont(new Font("新細明體", Font.BOLD, 20));
		expeirience.setFont(new Font("新細明體", Font.BOLD, 20));
		future.setFont(new Font("新細明體", Font.BOLD, 20));
		classcondi.setFont(new Font("新細明體", Font.BOLD, 20));
		

		characteristic.setEditable(false); 
		goodat.setEditable(false); 
		expeirience.setEditable(false); 
		future.setEditable(false); 
		classcondi.setEditable(false); 
		
		name.setBackground(Color.decode("#FFEBCD")); 
		grade.setBackground(Color.decode("#FFEBCD")); 
		characteristic.setBackground(Color.decode("#FFEBCD")); 
		goodat.setBackground(Color.decode("#FFEBCD")); 
		expeirience.setBackground(Color.decode("#FFEBCD")); 
		future.setBackground(Color.decode("#FFEBCD")); 
		classcondi.setBackground(Color.decode("#FFEBCD"));
		
		HOSIButton.setBackground(Color.decode("#F8F8FF"));
		CherButton.setBackground(Color.decode("#F8F8FF"));
		HeineButton.setBackground(Color.decode("#F8F8FF"));
		CathyButton.setBackground(Color.decode("#F8F8FF"));
		SurpriseButton.setBackground(Color.decode("#FFEBCD"));
	    
		HOSIButton.setFont(new Font("新細明體", Font.BOLD, 23));
		CherButton.setFont(new Font("新細明體", Font.BOLD, 23));;
		HeineButton.setFont(new Font("新細明體", Font.BOLD, 23));
		CathyButton.setFont(new Font("新細明體", Font.BOLD, 23));
		SurpriseButton.setFont(new Font("新細明體", Font.BOLD, 23));
		SurpriseButton.setToolTipText("自介都看完再點我喔: )");
		
		buttonbox=Box.createHorizontalBox();
		buttonbox.add(Box.createHorizontalStrut(20));
		buttonbox.add(HOSIButton);
		buttonbox.add(Box.createHorizontalStrut(20));
		buttonbox.add(CherButton);
		buttonbox.add(Box.createHorizontalStrut(20));
		buttonbox.add(HeineButton);
		buttonbox.add(Box.createHorizontalStrut(20));
		buttonbox.add(CathyButton);
		buttonbox.add(Box.createHorizontalStrut(20));
		buttonbox.add(SurpriseButton);
		
		leftPanel.add(picture);
		rightPanel.add(buttonbox);
		rightPanel.add(name);
		rightPanel.add(grade);
		rightPanel.add(characteristic);
		rightPanel.add(goodat);
		rightPanel.add(expeirience);
		rightPanel.add(future);
		rightPanel.add(classcondi);
		
		ButtonHandler handler = new ButtonHandler();                           
		HOSIButton.addActionListener(handler);                                     
		CherButton.addActionListener(handler);                                     
		HeineButton.addActionListener(handler);                                     
		CathyButton.addActionListener(handler);
		SurpriseButton.addActionListener(handler);
		
	}
	
	private class ButtonHandler implements ActionListener                        
	{
		public void actionPerformed( ActionEvent event )                                  
		{
			
			if(event.getActionCommand() == "洪靖雯")
			{
				picture.setIcon(image1);
                name.setText(" 洪靖雯");
                grade.setText(" ● 系級:資管三B");
                characteristic.setText(" ● 個人特質:"+"\n"+" 喜歡新鮮的事物，願意學習新知，嘗試平常人不敢做的事，對事物接受度"+"\n"+" 大，有責任心，感知力強");
                goodat.setText(" ● 專長:"+"\n"+" 1.程式能力(已碰過語言：C,Java,HTML5,CSS,JS,PHP)"+"\n"+" 2.碰過ㄧ些協助開發工具(ex.Laravel、phpMyAdmin、github)，有MVC架構的概念");
                expeirience.setText(" ● 經歷: 1.參加過學校的新生知訊網程設組，有網頁開發相關經驗"+"\n"+" 2.社團方面有參加創遊社，學習操作遊戲引擎(NVL,RPG Maker,C2,Unity)，"+"\n"+"    對遊戲開發有相當程度的認識"+"\n"+" 3.目前為大氣系網管，管理後台程式"+"\n");
                future.setText(" ● 未來規劃:"+"\n"+" 目前打算參加系上新辦之Android學生團隊，學習App開發相關經驗，大三下"+"\n"+" 後想修一些資工課程；大四規劃去網路公司實習，同時也會準備推甄研究所");
                classcondi.setText(" ● 修課情況:輔系資工");
			}
			
			if(event.getActionCommand() == "陳奕瑄")
			{
				flag++;
				picture.setIcon(image2);
				name.setText(" 陳奕瑄");
                grade.setText(" ● 系級:資管三A");
                characteristic.setText(" ● 個人特質:"+"\n"+" Open-minded，樂意學習新技能，對於想完成的事有一定的執著但不會"+"\n"+" 太過，認真負責，喜歡和一群人聊天然後蹦出新想法的感覺！");
                goodat.setText(" ● 專長:"+"\n"+" 網路相關、影片剪輯、 Photoshop、illustrator"+"\n"+" 對於C,Java,HTML5,CSS,JS,PHP有基礎的了解");
                expeirience.setText(" ● 經歷:  1.管院電算中心工讀生，協助處理網路問題與伺服器管理"+"\n"+" 2.體育室網管，協助維護網路以及管理更新體育室網頁、校運會網頁"+"\n"+" 3.彰化校友會社團幹部-攝影長，擅長攝影與影片製作"+"\n"+" 4.曾擔任彰化校友會返鄉服務晚會組組長，擅長進度規劃與溝通統整");
                future.setText(" ● 未來規劃:"+"\n"+" 目前打算報考資管所，將來以資訊為主財金為輔，朝金融科技這個領域邁進。"+"\n"+" 大三下後想修習一些資工的課程補足我目前程式能力的不足，也期待自己能在"+"\n"+ "專題中精進程式能力");
                classcondi.setText(" ● 修課情況:雙主修財金系");
				
			}
			
			
			if(event.getActionCommand() == "朱浚銘")
			{
				flag++;
				picture.setIcon(image3);
				name.setText(" 朱浚銘");
                grade.setText(" ● 系級:資管三A");
                characteristic.setText(" ● 個人特質:"+"\n"+" 較隨和，但仍會堅持自己的原則。喜歡與長輩及前輩談論其工作經驗，"+"\n"+" 並從中發掘道理與學習");
                goodat.setText(" ● 專長:"+"\n"+" 1.觀察個人與群體的行為，並加以預測、分析"+"\n"+" 2.影片編輯、SAI、Java、JavaScript");
                expeirience.setText(" ● 經歷:"+"\n"+" 1.轉學考(原輔大資管)"+"\n"+" 2.協辦中央電競比賽(直播、選手接洽...等)"+"\n"+" 3.參與有機農業實務"+"\n");
                future.setText(" ● 未來規劃:"+"\n"+" 在大三期間找到與興趣相符的領域發展(大數據、區塊鏈、醫療資訊...)，"+"\n"+" 大四預計考取研究所");
                classcondi.setText("");
			}
			
			if(event.getActionCommand() == "陳姿穎")
			{
				flag++;
				picture.setIcon(image4);
				name.setText(" 陳姿穎");
                grade.setText(" ● 系級:資管三B");
                characteristic.setText(" ● 個人特質:"+"\n"+" 個性較文靜隨和，喜歡幫助別人。樂於學習，認真負責，喜歡有挑戰性的任"+"\n"+" 務。另外如果事情沒做好不會輕易放棄");
                goodat.setText(" ● 專長:"+"\n"+" 1.有修ERP相關課程、實際操作過ERP軟體，對於商管方面知識有一定了解"+"\n"+" 2.C、JAVA、SQL語言、ASP、Photoshop、illustrator");
                expeirience.setText(" ● 經歷:"+"\n"+" 1.資管營課程組組員，自學3D動畫軟體，有自編教材、上台教課的經驗"+"\n"+" 2.資管迎新廣宣組組員， 設計巨型海報、能做基礎美工"+"\n"+" 3.嘉年華器材組組員，有架設、管理小型區域網路的經驗"+"\n");
                future.setText(" ● 未來規劃:"+"\n"+" 畢業後規劃直接就業，大四會安排實習，累積職場經驗。"+"\n"+" 目前有加入系上剛成立的APP學生團隊，希望未來可以從中學到系統程式設計"+"\n"+" 等技能，以及如何有效經營一個team，並且將所學應用在專題上");
                classcondi.setText(" ● 修課情況:輔系資工系、ERP學分學程");
			}
			
			if(event.getSource() == SurpriseButton )
			{
				if(flag>=3)
				{
					surprise surpriseframe = new surprise();
					surpriseframe.setSize(1160,900);                                   
			   	    surpriseframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
			   	    surpriseframe.setVisible(true);  
			   	    
					
				}
				else
				{
					JOptionPane.showMessageDialog(introduction.this,"要先看完每個人的自介才能看surprise喔!");
					
				}
                
			}
			
		}
		
		
	}
	
	public class surprise extends JFrame
	{
		JPanel northPanel = new JPanel();                                   
	    JPanel southPanel = new JPanel();
	    JLabel word1 = new JLabel("                                         感謝老師耐心看完!");
	    JLabel word2 = new JLabel("             相信老師對我們有一定的了解了~老師一定要選我們喔!! ^_^");
	    private ImageIcon image6 = new ImageIcon("src/AllofUs.png");
	    JLabel ourpicture = new JLabel(image6);
	    
		public surprise()
		{
			super("驚喜!!!");
			setLayout(new BorderLayout());
			
			northPanel.setBackground(Color.WHITE);
			northPanel.setPreferredSize(new Dimension(400, 700)); 
			southPanel.setLayout(new GridLayout(3, 1));
			southPanel.setBackground(Color.decode("#FFEBCD")); 
			
			word1.setFont(new Font("新細明體", Font.BOLD, 30));
			word2.setFont(new Font("新細明體", Font.BOLD, 30));
			
			word1.setBackground(Color.BLUE);
			word2.setBackground(Color.BLUE);
			
			add(northPanel,BorderLayout.NORTH);
			add(southPanel,BorderLayout.CENTER);
			
			southPanel.add(word1);
			southPanel.add(word2);
			northPanel.add(ourpicture);
			
			
		}
	}
	
	public static void main( String[] args)
    {
		introduction introframe = new introduction();
		
   	    introframe.setSize(1160,900);                                   
   	    introframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
   	    introframe.setVisible(true);                                    
   	      	
    }
	
	
}
