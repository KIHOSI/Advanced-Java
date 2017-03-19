//FinalQ2:103403530 �x�t��
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ConnectException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Client extends JFrame {
	//����
	private JPanel showStateJPanel = new JPanel(); //�̤W�ƪ��A�C
	private JPanel showCenterInfoJPanel = new JPanel();//�������϶�(��l�ϮסB��ܻ�l�ϮסB��ѫ�)
	private JPanel sendMsgJPanel = new JPanel(); //�ǰe�T�����a��
	//�W
	private JLabel nickNameJLabel = new JLabel("�п�J�z���ʺ�:"); //�ڪ��ʺ�
	private JTextField nickNameJTextField = new JTextField(15); //��ܿ�J�W��
	private JLabel showPlayerState = new JLabel("�ʺ٭���!"); //�i�D���a�{�b���A
	private JLabel emptyJLabel = new JLabel(); //�Ū��A���F�ƪ�
	//��1
	private JPanel dicePicJPanel = new JPanel();//���l�Ϯ�
	private Icon[] diceImg = {
			new ImageIcon(getClass().getResource("1.png")),
			new ImageIcon(getClass().getResource("2.png")),
			new ImageIcon(getClass().getResource("3.png")),
			new ImageIcon(getClass().getResource("4.png")),
			new ImageIcon(getClass().getResource("5.png")),
			new ImageIcon(getClass().getResource("6.png"))}; //��l1���ϧ�
	private JLabel diceJLabel1 = new JLabel(); //�Ĥ@����l
	private JLabel diceJLabel2 = new JLabel(); //�ĤG����l
	private JLabel diceJLabel3 = new JLabel(); //�ĤT����l
	private JLabel diceJLabel4 = new JLabel(); //�ĥ|����l
	private JLabel diceJLabel5 = new JLabel(); //�Ĥ�����l
	private JLabel diceJLabel6 = new JLabel(); //�Ĥ�����l
	//��2
	private JPanel chooseDiceJPanel = new JPanel(); //��ܻ�l
	private JLabel diceNumJLabel= new JLabel("�ƶq:"); //�ƶq
	private JComboBox diceNumJComboBox;
	private static final String diceNum[] = {"2","3","4","5","6","7","8","9","10"};
	private JLabel diceJLabel = new JLabel("��l�s��:");
	private JComboBox diceJComboBox;
	private static final String dice[] = {"2","3","4","5","6"};
	private JButton yellJButton = new JButton("��");
	private JButton catchJButton = new JButton("��");
	//��3
	private JPanel chatRoomJPanel = new JPanel(); //��ѫ�
	private JTextArea showChatRoomJTextArea = new JTextArea(15,61); //��ܲ�ѫǸ�T�A�]�w����
	//�U
	private JLabel sendMsgJLabel = new JLabel("�ǰe�T��:"); //�ǰe�T��
	private JTextField sendMsgJTextField = new JTextField(); //��J�ǰe�T���A�]�w����
	
	private String clientHost; //client��host
	private Socket connection;
	private int memberNum = 0; //�o�����h��player�b�u�W
	private ObjectOutputStream output; // output stream to client
    private ObjectInputStream input; // input stream from client
    private String message = "";
	private String Name = "";
	private boolean close = false;
	private boolean myTurn; //�P�_�O���O���ڤU
	private Random generator = new Random();
	
	public Client(String host){
		super("Chat Room Client");
		clientHost = host;
		setLayout(new BorderLayout());//���_���n�F
		//�W�����A�C
		showStateJPanel.setLayout(new GridLayout(1,4)); //���|�C
		showStateJPanel.add(nickNameJLabel);
		showStateJPanel.add(nickNameJTextField);
		showPlayerState.setVisible(false); //�w�]�����
		showStateJPanel.add(showPlayerState);
		showStateJPanel.add(emptyJLabel);
		nickNameJTextField.addActionListener( //���UenterŪ��nickname
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO �۰ʲ��ͪ���k Stub
						//�ǰe�W�r
						Name = nickNameJTextField.getText();
						//enterNickNameJTextField.setText("");
						try{
							output.writeObject(Name); //�N�W�r�ǰe�L�h
							output.flush();
						}catch(IOException ioException){
							ioException.printStackTrace();
						}
					}
				}
					
			);
		//����
		showCenterInfoJPanel.setLayout(new GridLayout(3,1)); //�T�C�@��
			//��1
		dicePicJPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); //�a����
		dicePicJPanel.add(diceJLabel1);
		dicePicJPanel.add(diceJLabel2);
		dicePicJPanel.add(diceJLabel3);
		dicePicJPanel.add(diceJLabel4);
		dicePicJPanel.add(diceJLabel5);
		dicePicJPanel.add(diceJLabel6);
			//��2
		chooseDiceJPanel.setLayout(new GridLayout(1,6)); //1�C����
		//diceNumJButton.setBackground(null);
		chooseDiceJPanel.add(diceNumJLabel);
		diceNumJComboBox = new JComboBox(diceNum);
		chooseDiceJPanel.add(diceNumJComboBox);
		//diceJButton.setBackground(null);
		chooseDiceJPanel.add(diceJLabel);
		diceJComboBox = new JComboBox(dice);
		chooseDiceJPanel.add(diceJComboBox);
		chooseDiceJPanel.add(yellJButton);
		chooseDiceJPanel.add(catchJButton);
			//��3:��ѫ�
		showChatRoomJTextArea.setEditable(false); //�����J
		chatRoomJPanel.add(new JScrollPane(showChatRoomJTextArea));
		
		showCenterInfoJPanel.add(dicePicJPanel);
		showCenterInfoJPanel.add(chooseDiceJPanel);
		showCenterInfoJPanel.add(showChatRoomJTextArea);
		
		//�U���ǰe�T���C
		sendMsgJPanel.setVisible(false);
		sendMsgJPanel.setLayout(new GridLayout(1,2)); //�G��@�C
		
		sendMsgJTextField.addActionListener(
				new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO �۰ʲ��ͪ���k Stub
				sendData(event.getActionCommand());
				sendMsgJTextField.setText("");
			}
		});
		sendMsgJPanel.add(sendMsgJLabel);
		sendMsgJPanel.add(sendMsgJTextField);
		//�@�}�l�����
		showCenterInfoJPanel.setVisible(false);
		sendMsgJPanel.setVisible(false);
		
		add(showStateJPanel,BorderLayout.NORTH);
		add(showCenterInfoJPanel,BorderLayout.CENTER);
		add(sendMsgJPanel,BorderLayout.SOUTH);
		
		setSize(700,400);
		setVisible(true);
		
		startClient();
	}
	
	public void startClient(){
		// ����Client �����
		try{
			connectToServer();
			getStreams();
			processName();
			processMessage();
		}catch(EOFException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			displayMessage( "��J�ʺٹL�{���쥼��������\n" );
		}catch( IOException e )
		{
			displayMessage( "�s�u����, �Х��}��Server\n" );
		} 
		finally
		{
			closeConnection();    // �����s�u
		}
	}
	
	// �إ߳s�uSocket
		private void connectToServer() throws ConnectException, IOException
		{	
			try{
				connection = new Socket( InetAddress.getByName( clientHost ),32768 );
			}catch(IOException ioException){
				JOptionPane.showMessageDialog(null,"Can not connect to Chat Server. ","Chat room closed",JOptionPane.ERROR_MESSAGE);
				System.exit(1); //���}
			}
				
		}
		
		// �إߦ�y
		private void getStreams() throws IOException
		{
			output = new ObjectOutputStream( connection.getOutputStream() );
			output.flush();
			input = new ObjectInputStream( connection.getInputStream() );
		}
		
		// �B�z�ʺ�
		private void processName() throws IOException, ClassNotFoundException
		{
			message = (String) input.readObject();
			//displayMessage("message:"+message+"\n");
			//if(message.contains("tellMeNumber")){displayMessage("contain\n");}
			/*while(message.contains("<Server>")||message.contains("tellMeNumber")){
				message = (String) input.readObject(); //��Ū�@��
			}*/
			while(message.equals("again")) 
			{
				//b.setText(String.format("���ʺ٤w���H�ϥ�"));
				showPlayerState.setVisible(true); //��ܼʺٿ��~
				nickNameJTextField.setText(""); //����
				message = (String) input.readObject(); //��Ū�@��
			}
			
			 //�e���}�l���ܰt�m
	    	 JOptionPane.showMessageDialog(null,"�i�J��ѫ�");
	    	 nickNameJLabel.setText("�ڪ��ʺ�:");
	    	 showPlayerState.setVisible(false); //�w�]�O"�ʺ٭���"�A����
	    	 nickNameJTextField.setEditable(false); //����A��J�W��
	    	 showCenterInfoJPanel.setVisible(true);
	    	 sendMsgJPanel.setVisible(true);
	    	 sendMsgJPanel.setVisible(true);
	    	 
	    	 //�P�_���X�ӨϥΪ̦b�u�W
	    	 if(memberNum <2){ //�p�G�����H�H�W
	    		 showPlayerState.setText("���ݪ��a�[�J!");
	    		 showPlayerState.setVisible(true);
	    		 diceNumJComboBox.setEditable(false);
	    		 diceJComboBox.setEditable(false);
	    	 }
			
		}
		// �B�z�T��
		private void processMessage() throws IOException, ClassNotFoundException
		{
			
			addWindowListener(new handler());	// �Y���������h�B�@
			
			
			while(close == false)
			{
				try{	// �B�z�Ӧ�server���T��
					message = (String) input.readObject();
					Scanner s = new Scanner(message);
					String command = s.next();
					String a[] = command.split("r");
					
					if(message.equals("Server Close")){
						JOptionPane.showMessageDialog(null, "Can not connect to Chat Server","Chat room closed",JOptionPane.WARNING_MESSAGE);
						System.exit(1); //�����s�u
					}
					else if(a[0].equals("tellMeNumbe")) //��ܽu�W�H��
					{
						memberNum = Integer.parseInt(a[1]); //String to int
						//displayMessage("memberNum:"+memberNum);
						if(memberNum >= 2){ //����ӥH�W�����a�F
							showPlayerState.setText("���ݹ��۩Χ�");
							//diceJComboBox.setEditable(true);
							//diceNumJComboBox.setEditable(true);
							diceJLabel1.setIcon(diceImg[generator.nextInt(6)]);
							diceJLabel2.setIcon(diceImg[generator.nextInt(6)]);
							diceJLabel3.setIcon(diceImg[generator.nextInt(6)]);
							diceJLabel4.setIcon(diceImg[generator.nextInt(6)]);
							diceJLabel5.setIcon(diceImg[generator.nextInt(6)]);
							diceJLabel6.setIcon(diceImg[generator.nextInt(6)]);
						}else{
							showPlayerState.setText("���ݪ��a�[�J!");
				    		//showPlayerState.setVisible(true);
				    		diceNumJComboBox.setEditable(false);
				    		diceJComboBox.setEditable(false);
						}
						//memberNumJTextField.setText(a[1]);
					}
					else if(message.equals("Your Turn")){
						showPlayerState.setText("����A�F");
						diceJComboBox.setEditable(true);
						diceNumJComboBox.setEditable(true);
						//diceJLabel1.setIcon(diceImg[]);
					}
					else //���`��ܰT��
					{
						displayMessage(message + "\n");
					}
					
					s.close();
				}	// end try
				catch( ClassNotFoundException e )
				{
					displayMessage("<<System>> Client�� ���쥼�������󫬺A");
				} // end catch
			}
		} // end method processConnection
		private void sendData( String message )
		{
			try	// �ǿ��ƥh��Server
			{
				output.writeObject( message );
				output.flush();
			}
			catch(IOException e)
			{
				showChatRoomJTextArea.append("��ƶǿ���~");
			} // end catch
		} // end method sendData
		
		private void closeConnection()
		{
			displayMessage( "�P���A���_�u\n");
			try{
				output.writeObject("exit");
				output.flush();
				try
				{
					output.close();
					input.close();
					connection.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				} // end catch
			}
			catch(IOException e)
			{
				showChatRoomJTextArea.append("\n��ƶǿ���~");
			}
		} // end method closeConnection
		private void displayMessage( final String messageToDisplay )
		{
			SwingUtilities.invokeLater(
					new Runnable()
					{
						public void run()
						{
							showChatRoomJTextArea.append(messageToDisplay);
						}	// end method run
					} // end inner class
			);	// end call to SwingUtilities.invokeLater
		}	// end method displayMessage	
		
		public class handler extends WindowAdapter{
			public void windowClosing(WindowEvent event)
			{
				close=true; 
				JOptionPane.showMessageDialog(null, "���}��ѫ�");
				closeConnection();
			}
		}
		
		public static void main(String[] args){ //Client�D�{��
			  Client application; // declare client application

		      // if no command line args
		      if ( args.length == 0 )
		         application = new Client( "127.0.0.1" ); // localhost
		      else
		         application = new Client( args[ 0 ] ); // use args

		      application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		}
}