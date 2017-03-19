//FinalQ2:103403530 �x�t��
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
	//����
	//private JPanel showStateJPanel = new JPanel(); //�̤W�������A�C(�����B�k)
	private JPanel stateLeftJPanel = new JPanel(); //���A�C����
	private JPanel stateRightJPanel = new JPanel(); //���A�C�k��
	private JPanel showChatRoomJPanel = new JPanel(); //��ܲ�ѫǸ�T(�����B�k)
	private JPanel chatRoomJPanel = new JPanel(); //��ѫǥ����T
	private JPanel chatMemberJPanel = new JPanel(); //��ѫǥk���T
	//private JLabel showMemberNumJLabel = new JLabel("�u�W�H��:"); //�u�W�H�� 
	//private JLabel broadcastJLabel = new JLabel("�s��:"); //�s��
	//private JTextField memberNumJTextField = new JTextField(15); //��ܽu�W�H�Ʀ��h�֡A�]�w�e��
	//private JTextField broadcastJTextField = new JTextField(15); //��J�s�������e�A�]�w�e��
	private JTextArea chatRoomJTextArea = new JTextArea("<System>��ѫǤw�g�W�u�A���ݨӦ�port:32768���s�u...\n",28, 40); //��ѫǸ�T�A�]�w���e
	private JTextArea chatMemberJTextArea = new JTextArea(28,30); //�s�u��client�W�١A�]�w���e
	private Member member;
	private ServerSocket serverSocket; //server socket
	//private ArrayList<Member> members = new ArrayList<Member>();
	private LinkedList<Member> members = new LinkedList<Member>(); //��ArrayList�ɡA�R���|�X�{���~
	private boolean hadNamed = false;
	private ExecutorService runChatRoom; // will run members
	private int memberNum = 0; //���h��member�b�u�W
	private int ID = 0; //��user��ID
	private final static int PLAYER_1 = 1; //�Ĥ@�쪱�a
	private final static int PLAYER_2 = 2; //�ĤG�쪱�a
	private int currentPlayer = 1; //�M�w�֥��U
	private Lock gameLock;
	private Condition otherPlayerConnected; //���ݧO�H�s�u��condition
	private Condition otherPlayerTurn; //���t�@��player��condition
	
	private String speaker = "";
	private boolean close = false;
	//private int clientCounter = 0;
	
	public Server(){
		super("Chat Room Server");
		setLayout(new BorderLayout()); //�D�e���]���_���n�F
		
				
				//��ѫǸ�T
				showChatRoomJPanel.setLayout(new BorderLayout());
				 //��ѫǥ���
				chatRoomJPanel.add(new JScrollPane(chatRoomJTextArea));
				 //��ѫǥk��
				chatMemberJPanel.add(new JScrollPane(chatMemberJTextArea));
				//�]�m��ѫǦ�m
				showChatRoomJPanel.add(chatRoomJPanel,BorderLayout.WEST);
				showChatRoomJPanel.add(chatMemberJPanel,BorderLayout.EAST);
				
				
				runChatRoom = Executors.newCachedThreadPool();//�x�sRunnable�]thread
				gameLock = new ReentrantLock();
				otherPlayerConnected = gameLock.newCondition();
				otherPlayerTurn = gameLock.newCondition();
				
				//�إ߳s�u
				try{
					serverSocket = new ServerSocket(32768);
				}catch(IOException ioException){ //���~
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
				runChatRoom.execute(member); //����member�������
				members.add(member);
				ID++;
				currentPlayer++; //currentPlayer�q1��2
			}catch(IOException e){
				displayMessage("socket�إߥ���");
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
					//memberNumJTextField.setText(""+memberNum); //��ܽu�W�H��
					processConnection();
		    	}catch(IOException ioException){
		    		displayMessage("���A���_�u\n");
		    	}finally{
		    		closeConnection();
		    	}
		    }
		    private void determinePlayer(){ //�P�_�O�ĴX�Ӫ��a
	 	     	/*if(playerNum == PLAYER_1){ //�Ĥ@�쪱�a
	 	     		
	 	     		gameLock.lock();
	 	     		try{
	 	     			while(suspended){
	 	     				otherPlayerConnected.await(); //���ݥt�@�Ӫ��a
	 	     			}
	 	     		}catch(InterruptedException exception){
	 	     			exception.printStackTrace();
	 	     		}finally{
	 	     			gameLock.unlock();
	 	     		}
	 	     	}*//*else{ //�ĤG��client
	 	     	
	 	     		
	 	     	}*/
		    	try{
			    	if(memberNum>=2){
			    		for(Member m : members){
			    			if(m.playerNum == PLAYER_1){ //�Ĥ@�Ӫ��a
			    				m.output.writeObject("Your Turn");
			    				m.output.flush();
			    			}
			    		}
			    	}
		    	}catch(IOException ioException){
		    		ioException.printStackTrace();
		    	}
		    }
		    private void getStreams() throws IOException{  // �s����y
			    output = new ObjectOutputStream( connectionSocket.getOutputStream() );
				output.flush();
				input = new ObjectInputStream( connectionSocket.getInputStream() );
		    }
		    private void processNickName() throws IOException{
		    	try{//�B�z�Ӧ�client�ݪ��ʺ�
		    		do{
		    			String message = (String) input.readObject();
		    			hadNamed = false;	// �����]�ʺ٨S�Q�ϥιL
		    			
		    			for(Member m : members){
		    				if(message.equals(m.name))	// �ˬd������
							{
								output.writeObject("again");
								output.flush();
								hadNamed = true;	// �ʺ٭���
							}	// end if
		    			}
		    			name = message;
		    		}while(hadNamed == true); //�ʺٳQ�ϥιL ���s���@��
		    		
		    	}catch(ClassNotFoundException classNotFoundException){
		    		displayMessage("<<System>> Server�ݦ��쥼�������󫬺A"+"\n"+"(�b����ʺٹL�{��)");
		    	}
		    }
		    private void processConnection() throws IOException{
		    	String success = "client:'"+connectionSocket.getInetAddress()+" �s�u�إߡA"
		    			+"ID:'"+idNum+"."; 
		    	displayMessage("<Server>"+success+"\n");
		    	//determinePlayer();
		    	//���\�T�����ǵ����\�s�W��client
		    	try{
		    		output.writeObject("<Server>"+success+"\n");
		    		output.flush();
		    	}catch(IOException ioException){
		    		ioException.printStackTrace();
		    	}
		    	
		    	
		    	toClient();
		    	
		    	addWindowListener(new handler());	// �Y���������h�B�@
		    	
		    	while(close == false){	// �����T������client����
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
						displayMessage("<<System>> Server�ݦ��쥼�������󫬺A");
					}	// end catch
		    	}
		    }
		    private void sendData(String message){
		    	try	// �ǰe�T����Client
				{
					String talking = "";
					for(Member m : members)
					{
						talking = "[ " + speaker + " ]�� :" + message;
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
		    private void toClient() throws IOException{	//�B�z�즸�i�J��ѫǪ��T��

				for(Member m : members)
				{
					if(m.idNum == idNum)	//�����ݨ�
					{
						m.output.writeObject("<Server> clientID: '"+(idNum)+"' �i�J�C����" );

					}
					else if(m.idNum != idNum)	//�L�H�ݨ�
					{
						m.output.writeObject("<Server> clientID: '"+(idNum)+"' �i�J�C����" );
					}	
					m.output.flush();
					showUser();
					showNum(memberNum);
				}
		    }
		    private void showUser(){//�C�X�ثe��ѫǤ���User

				chatMemberJTextArea.setText("");
				for(Member m : members)
				{
					chatMemberJTextArea.append(m.name + "( ID�G"+ m.idNum + " )\n");
				}
		    }
		    private void showNum(int num){ 	// �B�z�ϥΪ̥i���쪺�u�W�H��
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
		    	displayMessage( "<Server> Client ID�G"+ idNum +" �w�פ�s�u\n" );
		    	for(Member m : members)
				{
					if(idNum == m.idNum)
					{
						int clientLeft = members.indexOf(m);
						try
						{
							for(Member m2 : members)
							{
								String bye = "client�GID "+(m.idNum)+"�w���}��ѫ�";
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
					//JOptionPane.showMessageDialog(null, "���}��ѫ�");
					try{
						for(Member m :members){
							m.output.writeObject("Server Close"); //�ǵ�client�i��server����
						}
					}catch(IOException ioException){
						ioException.printStackTrace();
					}
					System.exit(1); //���}
					//closeConnection();
				}
			}
		}
		
		
	public static void main(String[] args){ //Server�D�{��
		Server application = new Server(); // create server
	    application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    application.execute(); // run server application
	}
}