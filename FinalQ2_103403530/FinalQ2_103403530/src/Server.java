//FinalQ2:103403530 洪靖雯
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Member;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class Server extends JFrame{
	//介面
	//private JPanel showStateJPanel = new JPanel(); //最上面的狀態列(分左、右)
	private JPanel stateLeftJPanel = new JPanel(); //狀態列左邊
	private JPanel stateRightJPanel = new JPanel(); //狀態列右邊
	private JPanel showChatRoomJPanel = new JPanel(); //顯示聊天室資訊(分左、右)
	private JPanel chatRoomJPanel = new JPanel(); //聊天室左邊資訊
	private JPanel chatMemberJPanel = new JPanel(); //聊天室右邊資訊
	//private JLabel showMemberNumJLabel = new JLabel("線上人數:"); //線上人數 
	//private JLabel broadcastJLabel = new JLabel("廣播:"); //廣播
	//private JTextField memberNumJTextField = new JTextField(15); //顯示線上人數有多少，設定寬度
	//private JTextField broadcastJTextField = new JTextField(15); //輸入廣播的內容，設定寬度
	private JTextArea chatRoomJTextArea = new JTextArea("<System>聊天室已經上線，等待來自port:32768的連線...\n",28, 40); //聊天室資訊，設定長寬
	private JTextArea chatMemberJTextArea = new JTextArea(28,30); //連線的client名稱，設定長寬
	private Member member;
	private ServerSocket serverSocket; //server socket
	//private ArrayList<Member> members = new ArrayList<Member>();
	private LinkedList<Member> members = new LinkedList<Member>(); //用ArrayList時，刪除會出現錯誤
	private boolean hadNamed = false;
	private ExecutorService runChatRoom; // will run members
	private int memberNum = 0; //有多少member在線上
	private int ID = 0; //給user的ID
	private final static int PLAYER_1 = 1; //第一位玩家
	private final static int PLAYER_2 = 2; //第二位玩家
	private int currentPlayer = 1; //決定誰先下
	private Lock gameLock;
	private Condition otherPlayerConnected; //等待別人連線的condition
	private Condition otherPlayerTurn; //換另一個player的condition
	
	private String speaker = "";
	private boolean close = false;
	//private int clientCounter = 0;
	
	public Server(){
		super("Chat Room Server");
		setLayout(new BorderLayout()); //主畫面設為北中南東
		
				
				//聊天室資訊
				showChatRoomJPanel.setLayout(new BorderLayout());
				 //聊天室左邊
				chatRoomJPanel.add(new JScrollPane(chatRoomJTextArea));
				 //聊天室右邊
				chatMemberJPanel.add(new JScrollPane(chatMemberJTextArea));
				//設置聊天室位置
				showChatRoomJPanel.add(chatRoomJPanel,BorderLayout.WEST);
				showChatRoomJPanel.add(chatMemberJPanel,BorderLayout.EAST);
				
				
				runChatRoom = Executors.newCachedThreadPool();//儲存Runnable跑thread
				gameLock = new ReentrantLock();
				otherPlayerConnected = gameLock.newCondition();
				otherPlayerTurn = gameLock.newCondition();
				
				//建立連線
				try{
					serverSocket = new ServerSocket(32768);
				}catch(IOException ioException){ //錯誤
					ioException.printStackTrace();
					System.exit(1);
				}
				//add(showStateJPanel,BorderLayout.NORTH);
				add(showChatRoomJPanel,BorderLayout.CENTER);
				
				setSize(807,570);
				setVisible(true);
	}
	public void execute(){
		while(true){
			try{
				member = new Member(serverSocket.accept(),ID,currentPlayer);
				runChatRoom.execute(member); //執行member的執行序
				members.add(member);
				ID++;
				currentPlayer++; //currentPlayer從1變2
			}catch(IOException e){
				displayMessage("socket建立失敗");
				e.printStackTrace();
			}
		}
	}
	// display message in MessageArea
		private void displayMessage( final String messageToDisplay )
		{
			// display message form event-dispatch thread of execution
			SwingUtilities.invokeLater(
					new Runnable()
					{
						public void run()
						{
							chatRoomJTextArea.append( messageToDisplay );
						} // end method run
					} // end inner class
			); // end call to SwingUtilities.invokeLater
		} // end method displayMessage
		public class Member implements Runnable{
			private Socket connectionSocket; // connection to client
			private ObjectOutputStream output; // output stream to client
		    private ObjectInputStream input; // input stream from client
		    private int idNum;
		    private int playerNum;
		    private boolean suspended = true; 
		    String name="";
		    
		    public Member(Socket socket,int id,int playerID){
		    	idNum = id;
		    	connectionSocket = socket;
		    	playerNum = playerID;
		    }
		    public void run(){
		    	try{
		    		getStreams();
					processNickName();
					memberNum++;
					//determinePlayer();
					//memberNumJTextField.setText(""+memberNum); //顯示線上人數
					processConnection();
		    	}catch(IOException ioException){
		    		displayMessage("伺服器斷線\n");
		    	}finally{
		    		closeConnection();
		    	}
		    }
		    private void determinePlayer(){ //判斷是第幾個玩家
	 	     	/*if(playerNum == PLAYER_1){ //第一位玩家
	 	     		
	 	     		gameLock.lock();
	 	     		try{
	 	     			while(suspended){
	 	     				otherPlayerConnected.await(); //等待另一個玩家
	 	     			}
	 	     		}catch(InterruptedException exception){
	 	     			exception.printStackTrace();
	 	     		}finally{
	 	     			gameLock.unlock();
	 	     		}
	 	     	}*//*else{ //第二個client
	 	     	
	 	     		
	 	     	}*/
		    	try{
			    	if(memberNum>=2){
			    		for(Member m : members){
			    			if(m.playerNum == PLAYER_1){ //第一個玩家
			    				m.output.writeObject("Your Turn");
			    				m.output.flush();
			    			}
			    		}
			    	}
		    	}catch(IOException ioException){
		    		ioException.printStackTrace();
		    	}
		    }
		    private void getStreams() throws IOException{  // 連接串流
			    output = new ObjectOutputStream( connectionSocket.getOutputStream() );
				output.flush();
				input = new ObjectInputStream( connectionSocket.getInputStream() );
		    }
		    private void processNickName() throws IOException{
		    	try{//處理來自client端的暱稱
		    		do{
		    			String message = (String) input.readObject();
		    			hadNamed = false;	// 先假設暱稱沒被使用過
		    			
		    			for(Member m : members){
		    				if(message.equals(m.name))	// 檢查有重複
							{
								output.writeObject("again");
								output.flush();
								hadNamed = true;	// 暱稱重複
							}	// end if
		    			}
		    			name = message;
		    		}while(hadNamed == true); //暱稱被使用過 重新取一個
		    		
		    	}catch(ClassNotFoundException classNotFoundException){
		    		displayMessage("<<System>> Server端收到未知的物件型態"+"\n"+"(在獲取暱稱過程中)");
		    	}
		    }
		    private void processConnection() throws IOException{
		    	String success = "client:'"+connectionSocket.getInetAddress()+" 連線建立，"
		    			+"ID:'"+idNum+"."; 
		    	displayMessage("<Server>"+success+"\n");
		    	//determinePlayer();
		    	//成功訊息指傳給成功連上的client
		    	try{
		    		output.writeObject("<Server>"+success+"\n");
		    		output.flush();
		    	}catch(IOException ioException){
		    		ioException.printStackTrace();
		    	}
		    	
		    	
		    	toClient();
		    	
		    	addWindowListener(new handler());	// 若關閉視窗則運作
		    	
		    	while(close == false){	// 接收訊息直到client關閉
		    		try
					{
		    			
						String message = (String) input.readObject();
						speaker = name;
						if(message.equals("exit"))
						{
							break;
						}
						sendData( message );
					}
					catch( ClassNotFoundException classNotFoundException )
					{
						displayMessage("<<System>> Server端收到未知的物件型態");
					}	// end catch
		    	}
		    }
		    private void sendData(String message){
		    	try	// 傳送訊息給Client
				{
					String talking = "";
					for(Member m : members)
					{
						talking = "[ " + speaker + " ]說 :" + message;
						m.output.writeObject( talking );
						m.output.flush();
					}

					displayMessage( talking + "\n");
				}
				catch(IOException e)
				{
					displayMessage("Error writing object.\n");
				} // end catch
		    }
		    private void toClient() throws IOException{	//處理初次進入聊天室的訊息

				for(Member m : members)
				{
					if(m.idNum == idNum)	//本身看到
					{
						m.output.writeObject("<Server> clientID: '"+(idNum)+"' 進入遊戲室" );

					}
					else if(m.idNum != idNum)	//他人看到
					{
						m.output.writeObject("<Server> clientID: '"+(idNum)+"' 進入遊戲室" );
					}	
					m.output.flush();
					showUser();
					showNum(memberNum);
				}
		    }
		    private void showUser(){//列出目前聊天室中的User

				chatMemberJTextArea.setText("");
				for(Member m : members)
				{
					chatMemberJTextArea.append(m.name + "( ID："+ m.idNum + " )\n");
				}
		    }
		    private void showNum(int num){ 	// 處理使用者可見到的線上人數
		    	for(Member m : members)
				{
					try 
					{
						m.output.writeObject("tellMeNumber"+num);
						output.flush();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
		    }
		    private void closeConnection(){
		    	displayMessage( "<Server> Client ID："+ idNum +" 已終止連線\n" );
		    	for(Member m : members)
				{
					if(idNum == m.idNum)
					{
						int clientLeft = members.indexOf(m);
						try
						{
							for(Member m2 : members)
							{
								String bye = "client：ID "+(m.idNum)+"已離開聊天室";
								m2.output.writeObject("<Server>" + bye);
								m2.output.flush();
							}
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
						members.remove( clientLeft );
						memberNum--;
						//chatMemberJTextArea.setText(""+memberNum);
						showUser();
						showNum(memberNum);
					}
				}
		    	try{
		    		output.close();
		    		input.close();
		    		connectionSocket.close();
		    	}catch(IOException e){
		    		e.printStackTrace();
		    	}
		    }
		    public class handler extends WindowAdapter{
				public void windowClosing(WindowEvent event)
				{
					close=true; 
					//JOptionPane.showMessageDialog(null, "離開聊天室");
					try{
						for(Member m :members){
							m.output.writeObject("Server Close"); //傳給client告知server關閉
						}
					}catch(IOException ioException){
						ioException.printStackTrace();
					}
					System.exit(1); //離開
					//closeConnection();
				}
			}
		}
		
		
	public static void main(String[] args){ //Server主程式
		Server application = new Server(); // create server
	    application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    application.execute(); // run server application
	}
}
