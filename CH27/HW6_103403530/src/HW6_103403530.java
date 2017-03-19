//HW6:103403530 洪靖雯
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class HW6_103403530  extends JFrame{
	private JPanel upJPanel = new JPanel();
	private JPanel centerJPanel = new JPanel();
	private JPanel downJPanel = new JPanel();
	//上方搜尋列
	private JLabel fielednameJLabel = new JLabel("Fieled Name:");
	private String[] columnNameString = {"MemberID","name","phone","e_mail","sex"};
	private JComboBox<String> columnNameJComboBox = new JComboBox<String>(columnNameString);
	private JTextField searchKeywordJTextField = new JTextField(55);
	private JButton findJButton = new JButton("Find");
	//中間
	private JPanel columnNameJPanel = new JPanel();
	private JPanel tabelContentJPanel = new JPanel();
	private JLabel memberIDJLabel = new JLabel("Member ID:");
	private JLabel nameJLabel = new JLabel("Name:");
	private JLabel phoneNumberJLabel = new JLabel("Phone Number:");
	private JLabel emailJLabel = new JLabel("Email:");
	private JLabel sexJLabel = new JLabel("Sex:");
	private JTextField memberIDJTextField = new JTextField();
	private JTextField nameJTextField = new JTextField();
	private JTextField phoneNumberJTextField = new JTextField();
	private JTextField emailJTextField = new JTextField();
	private JTextField sexJTextField = new JTextField();
	private JTable resultJTable;
	//下方
	private JButton browseAllEntriesJButton = new JButton("Browse All Entries");
	private JButton insertNewEntryJButton = new JButton("Insert New Entry");
	private JButton updateJButton = new JButton("update");
	private JButton deleteJButton = new JButton("delete");
	//資料庫設定
	static final String DATABASE_URL = "jdbc:mysql://localhost/member";
	static final String USERNAME = "java";
	static final String PASSWORD = "java";
	
	private PreparedStatement browseAllEntries = null; //瀏覽全部
	private PreparedStatement insertNewEntry = null; //插入新的表格
	private PreparedStatement update = null; //更新table
	private PreparedStatement delete = null; //刪除表格
	private PreparedStatement searchKeyWord = null; //搜尋特定內容
	private PreparedStatement sort = null;//排序
	
	private ResultSetTableModel tableModel;
	private TableRowSorter<TableModel> sorter ;
	//指令
	static final String browseAllEntriesQuery = "SELECT * FROM people";
	//static final String insertNewEntryQuery;
	//static final String updateQuery;
	//static final String deleteQuery;
	//static final String searchQuery;
	//static final String sortQuery;
	
	
	public HW6_103403530(){ 
		super("Member");
		setLayout(new BorderLayout());
		//上方搜尋
		upJPanel.setLayout(new FlowLayout());
		Border titleBorder=BorderFactory.createTitledBorder("Find an entry by a field");
		upJPanel.setBorder(titleBorder);
		upJPanel.add(fielednameJLabel);
		upJPanel.add(columnNameJComboBox);
		upJPanel.add(searchKeywordJTextField);
		upJPanel.add(findJButton);
		//中間
		centerJPanel.setLayout(new BorderLayout());
		 //左
		columnNameJPanel.setLayout(new GridLayout(5, 2));
		columnNameJPanel.add(memberIDJLabel);
		memberIDJTextField.setEditable(false); 
		columnNameJPanel.add(memberIDJTextField);
		columnNameJPanel.add(nameJLabel);
		columnNameJPanel.add(nameJTextField);
		columnNameJPanel.add(phoneNumberJLabel);
		columnNameJPanel.add(phoneNumberJTextField);
		columnNameJPanel.add(emailJLabel);
		columnNameJPanel.add(emailJTextField);
		columnNameJPanel.add(sexJLabel);
		columnNameJPanel.add(sexJTextField);
		 //右
		try{
			//建立JTabel
        	tableModel = new ResultSetTableModel( DATABASE_URL,
        			USERNAME, PASSWORD, browseAllEntriesQuery);
        	//tableModel.getAllPeople();
        	resultJTable = new JTable(tableModel);
        	//可排序
        	sorter = new TableRowSorter<TableModel>(tableModel);
			resultJTable.setRowSorter(sorter);
        }catch(SQLException sqlException){
        	//錯誤訊息
        	JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
                    "Database error", JOptionPane.ERROR_MESSAGE );
        	 // ensure database connection is closed
            tableModel.disconnectFromDatabase();
            System.exit( 1 ); // terminate application
        }
		//resultJTable.setModel(tableModel);
		resultJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO 自動產生的方法 Stub
				try{
					memberIDJTextField.setText(resultJTable.getValueAt(resultJTable.getSelectedRow(),0).toString());
					nameJTextField.setText(resultJTable.getValueAt(resultJTable.getSelectedRow(), 1).toString());
					phoneNumberJTextField.setText(resultJTable.getValueAt(resultJTable.getSelectedRow(), 2).toString());
					emailJTextField.setText(resultJTable.getValueAt(resultJTable.getSelectedRow(), 3).toString());
					sexJTextField.setText(resultJTable.getValueAt(resultJTable.getSelectedRow(), 4).toString());
				}catch(IndexOutOfBoundsException indexOutOfBoundsException){
					
				}
				/*try{
					tableModel.setQuery(browseAllEntriesQuery);
				}catch(SQLException sqlException){
					JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
		                    "Database error", JOptionPane.ERROR_MESSAGE );
		        	 // ensure database connection is closed
		            tableModel.disconnectFromDatabase();
		            System.exit( 1 ); // terminate application
				}*/
			}
		});
		
		tabelContentJPanel.add( new JScrollPane( resultJTable ));
		resultJTable.setPreferredScrollableViewportSize(new Dimension(700,330));
		centerJPanel.add(columnNameJPanel,BorderLayout.WEST);
		centerJPanel.add(tabelContentJPanel,BorderLayout.CENTER);
		//下方
		downJPanel.setLayout(new GridLayout(1, 4));
		downJPanel.add(browseAllEntriesJButton);
		downJPanel.add(insertNewEntryJButton);
		downJPanel.add(updateJButton);
		downJPanel.add(deleteJButton);
		
		
		browseAllEntriesJButton.addActionListener(new ActionListener() {
			//顯示全部
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動產生的方法 Stub
				try{
					tableModel.setQuery(browseAllEntriesQuery);
					sorter = new TableRowSorter<TableModel>(tableModel);
					resultJTable.setRowSorter(sorter);
				}catch(SQLException sqlException){
					JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
		                    "Database error", JOptionPane.ERROR_MESSAGE );
		        	 // ensure database connection is closed
		            tableModel.disconnectFromDatabase();
		            System.exit( 1 ); // terminate application
				}
				
			}
		});
		//尋找資料
		findJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動產生的方法 Stub
				String keywordType = (String) columnNameJComboBox.getSelectedItem(); //搜尋欄位名稱
				String keyword = searchKeywordJTextField.getText(); //要搜尋的字
				//System.out.println(keywordType);
				//System.out.println(keyword);
				if(keyword.length() == 0){  //如果是空的
					sorter.setRowFilter(null);
				}else{
					try{ //找尋資料
						/*sorter.setRowFilter( 
		                     RowFilter.regexFilter( keyword ) );*/
						tableModel.setQuery("SELECT * FROM people WHERE "+keywordType+" = '" + keyword + "'");
						//tableModel.getKeyword(keywordType, keyword);
					}catch(SQLException sqlException){
						JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
			                    "Database error", JOptionPane.ERROR_MESSAGE );
						 tableModel.disconnectFromDatabase();
				            System.exit( 1 ); // terminate application
					}
					
				}
			}
		});
		
		//加入表格
		insertNewEntryJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動產生的方法 Stub
				//驗證是否是空白
				if((nameJTextField.getText().equals(""))||(sexJTextField.getText().equals(""))){
					JOptionPane.showMessageDialog(null, "不能留白!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//驗證手機號碼
				if(!phoneNumberJTextField.getText().matches("[0][9][0-9]{2}-[0-9]{6}")){
					JOptionPane.showMessageDialog(null, "手機號碼格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//驗證信箱
				if(!emailJTextField.getText().matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)+$")){
					JOptionPane.showMessageDialog(null, "信箱格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//驗證沒有問題，開始加入資料
				//String memberID = memberIDJTextField.getText();
				String userName = nameJTextField.getText();
				String phoneNum = phoneNumberJTextField.getText();
				String emailAddress = emailJTextField.getText();
				String userSex = sexJTextField.getText();
				
				try{
					tableModel.addPerson("INSERT INTO people( name, phone, e_mail,sex )VALUES ( '" + userName + "','" + phoneNum + "','" + emailAddress + "' ,'" + userSex + "'  )");
					//tableModel.setQuery(browseAllEntriesQuery);
				}catch(SQLException sqlException){
					JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
		                    "Database error", JOptionPane.ERROR_MESSAGE );
					 tableModel.disconnectFromDatabase();
			            System.exit( 1 ); // terminate application
				}
				
				
			}
		});
		
		//更新資料
		updateJButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自動產生的方法 Stub
				//驗證是否是空白
				if((nameJTextField.getText().equals(""))||(sexJTextField.getText().equals(""))){
					JOptionPane.showMessageDialog(null, "不能留白!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//驗證手機號碼
				if(!phoneNumberJTextField.getText().matches("[0][9][0-9]{2}-[0-9]{6}")){
					JOptionPane.showMessageDialog(null, "手機號碼格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//驗證信箱
				if(!emailJTextField.getText().matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)+$")){
					JOptionPane.showMessageDialog(null, "信箱格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try{
					//tableModel.addPerson("INSERT INTO people( name, phone, e_mail,sex )VALUES ( '" + userName + "','" + phoneNum + "','" + emailAddress + "' ,'" + userSex + "'  )");
					//驗證沒有問題，開始加入資料
					String memberID = memberIDJTextField.getText();
					String userName = nameJTextField.getText();
					String phoneNum = phoneNumberJTextField.getText();
					String emailAddress = emailJTextField.getText();
					String userSex = sexJTextField.getText();
					
					tableModel.updatePerson("UPDATE people SET name = '"+ userName +"',phone = '"+ phoneNum +"',e_mail = '"+ emailAddress +"',sex = '"+ userSex +"' WHERE MemberID = '"+ memberID +"'");
					//tableModel.setQuery(browseAllEntriesQuery);
				}catch(SQLException sqlException){
					JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
		                    "Database error", JOptionPane.ERROR_MESSAGE );
					 tableModel.disconnectFromDatabase();
			            System.exit( 1 ); // terminate application
				}
			}
		});
		
		//刪除資料
				deleteJButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自動產生的方法 Stub
						//驗證是否是空白
						if((nameJTextField.getText().equals(""))||(sexJTextField.getText().equals(""))){
							JOptionPane.showMessageDialog(null, "不能留白!","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
						//驗證手機號碼
						if(!phoneNumberJTextField.getText().matches("[0][9][0-9]{2}-[0-9]{6}")){
							JOptionPane.showMessageDialog(null, "手機號碼格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
						//驗證信箱
						if(!emailJTextField.getText().matches("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)+$")){
							JOptionPane.showMessageDialog(null, "信箱格式錯誤!","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						try{
							//驗證沒有問題，開始加入資料
							String memberID = memberIDJTextField.getText();
							//String userName = nameJTextField.getText();
							//String phoneNum = phoneNumberJTextField.getText();
							//String emailAddress = emailJTextField.getText();
							//String userSex = sexJTextField.getText();
							tableModel.deletePerson("DELETE FROM people WHERE MemberID = '"+ memberID +"'");
							//tableModel.setQuery(browseAllEntriesQuery);
						}catch(SQLException sqlException){
							JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
				                    "Database error", JOptionPane.ERROR_MESSAGE );
							 tableModel.disconnectFromDatabase();
					            System.exit( 1 ); // terminate application
						}
					}
				});
		
		//介面配置
		add(upJPanel,BorderLayout.NORTH);
		add(centerJPanel,BorderLayout.CENTER);
		add(downJPanel, BorderLayout.SOUTH);
		
		
	}
	public static void main(String[] args){
		HW6_103403530 h = new HW6_103403530();
		h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		h.setSize(900,500);
		h.setVisible(true);
		
	}
}
