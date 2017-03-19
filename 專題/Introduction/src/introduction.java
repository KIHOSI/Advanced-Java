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
    JLabel name = new JLabel(" �x�t��");
    JLabel grade = new JLabel(" �� �t��:��ޤTB");
    JTextArea characteristic = new JTextArea(" �� �ӤH�S��:"+"\n"+" ���w�s�A���ƪ��A�@�N�ǲ߷s���A���ե��`�H���������ơA��ƪ�������"+"\n"+" �j�A���d���ߡA�P���O�j");
    JTextArea goodat = new JTextArea(" �� �M��:"+"\n"+" 1.�{����O(�w�I�L�y���GC,Java,HTML5,CSS,JS,PHP)"+"\n"+" 2.�I�L���Ǩ�U�}�o�u��(ex.Laravel�BphpMyAdmin�Bgithub)�A��MVC�[�c������");
    JTextArea expeirience = new JTextArea(" �� �g��: 1.�ѥ[�L�Ǯժ��s�ͪ��T���{�]�աA�������}�o�����g��"+"\n"+" 2.���Τ譱���ѥ[�йC���A�ǲ߾ާ@�C������(NVL,RPG Maker,C2,Unity)�A"+"\n"+"    ��C���}�o���۷�{�ת��{��"+"\n"+" 3.�ثe���j��t���ޡA�޲z��x�{��"+"\n");
    JTextArea future = new JTextArea(" �� ���ӳW��:"+"\n"+" �ثe����ѥ[�t�W�s�줧Android�ǥ͹ζ��A�ǲ�App�}�o�����g��A�j�T�U"+"\n"+" ��Q�פ@�Ǹ�u�ҵ{�F�j�|�W���h�������q��ߡA�P�ɤ]�|�ǳƱ��¬�s��");
    JTextArea classcondi = new JTextArea(" �� �׽ұ��p:���t��u");
    
    Box buttonbox;
    JButton HOSIButton = new JButton("�x�t��");
    JButton CherButton = new JButton("����ޱ");
    JButton HeineButton = new JButton("���C��");
    JButton CathyButton = new JButton("�����o");
    JButton SurpriseButton = new JButton(image5);
    
    
	public introduction()
	{
		super("����ڭ� About Us");
		setLayout(new BorderLayout());
		
		leftPanel.setLayout(new GridLayout(1, 1));
		leftPanel.setPreferredSize(new Dimension(400, 160));           
		leftPanel.setBackground(Color.WHITE);
		rightPanel.setLayout(new GridLayout(8, 1));
		rightPanel.setBackground(Color.decode("#FFEBCD")); 
		
		add(leftPanel,BorderLayout.WEST);  
		add(rightPanel,BorderLayout.CENTER); 
		
		name.setFont(new Font("�s�ө���", Font.BOLD, 40));
		grade.setFont(new Font("�s�ө���", Font.BOLD, 20));
		characteristic.setFont(new Font("�s�ө���", Font.BOLD, 20));
		goodat.setFont(new Font("�s�ө���", Font.BOLD, 20));
		expeirience.setFont(new Font("�s�ө���", Font.BOLD, 20));
		future.setFont(new Font("�s�ө���", Font.BOLD, 20));
		classcondi.setFont(new Font("�s�ө���", Font.BOLD, 20));
		

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
	    
		HOSIButton.setFont(new Font("�s�ө���", Font.BOLD, 23));
		CherButton.setFont(new Font("�s�ө���", Font.BOLD, 23));;
		HeineButton.setFont(new Font("�s�ө���", Font.BOLD, 23));
		CathyButton.setFont(new Font("�s�ө���", Font.BOLD, 23));
		SurpriseButton.setFont(new Font("�s�ө���", Font.BOLD, 23));
		SurpriseButton.setToolTipText("�ۤ����ݧ��A�I�ڳ�: )");
		
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
			
			if(event.getActionCommand() == "�x�t��")
			{
				picture.setIcon(image1);
                name.setText(" �x�t��");
                grade.setText(" �� �t��:��ޤTB");
                characteristic.setText(" �� �ӤH�S��:"+"\n"+" ���w�s�A���ƪ��A�@�N�ǲ߷s���A���ե��`�H���������ơA��ƪ�������"+"\n"+" �j�A���d���ߡA�P���O�j");
                goodat.setText(" �� �M��:"+"\n"+" 1.�{����O(�w�I�L�y���GC,Java,HTML5,CSS,JS,PHP)"+"\n"+" 2.�I�L���Ǩ�U�}�o�u��(ex.Laravel�BphpMyAdmin�Bgithub)�A��MVC�[�c������");
                expeirience.setText(" �� �g��: 1.�ѥ[�L�Ǯժ��s�ͪ��T���{�]�աA�������}�o�����g��"+"\n"+" 2.���Τ譱���ѥ[�йC���A�ǲ߾ާ@�C������(NVL,RPG Maker,C2,Unity)�A"+"\n"+"    ��C���}�o���۷�{�ת��{��"+"\n"+" 3.�ثe���j��t���ޡA�޲z��x�{��"+"\n");
                future.setText(" �� ���ӳW��:"+"\n"+" �ثe����ѥ[�t�W�s�줧Android�ǥ͹ζ��A�ǲ�App�}�o�����g��A�j�T�U"+"\n"+" ��Q�פ@�Ǹ�u�ҵ{�F�j�|�W���h�������q��ߡA�P�ɤ]�|�ǳƱ��¬�s��");
                classcondi.setText(" �� �׽ұ��p:���t��u");
			}
			
			if(event.getActionCommand() == "����ޱ")
			{
				flag++;
				picture.setIcon(image2);
				name.setText(" ����ޱ");
                grade.setText(" �� �t��:��ޤTA");
                characteristic.setText(" �� �ӤH�S��:"+"\n"+" Open-minded�A�ַN�ǲ߷s�ޯ�A���Q�������Ʀ��@�w�����ۦ����|"+"\n"+" �ӹL�A�{�u�t�d�A���w�M�@�s�H��ѵM���ۥX�s�Q�k���Pı�I");
                goodat.setText(" �� �M��:"+"\n"+" ���������B�v���ſ�B Photoshop�Billustrator"+"\n"+" ���C,Java,HTML5,CSS,JS,PHP����¦���F��");
                expeirience.setText(" �� �g��:  1.�ް|�q�⤤�ߤuŪ�͡A��U�B�z�������D�P���A���޲z"+"\n"+" 2.��|�Ǻ��ޡA��U���@�����H�κ޲z��s��|�Ǻ����B�չB�|����"+"\n"+" 3.���Ʈդͷ|���ηF��-��v���A�ժ���v�P�v���s�@"+"\n"+" 4.��������Ʈդͷ|��m�A�ȱ߷|�ղժ��A�ժ��i�׳W���P���q�ξ�");
                future.setText(" �� ���ӳW��:"+"\n"+" �ثe������Ҹ�ީҡA�N�ӥH��T���D�]�������A�ª��Ĭ�޳o�ӻ���ڶi�C"+"\n"+" �j�T�U��Q�ײߤ@�Ǹ�u���ҵ{�ɨ��ڥثe�{����O�������A�]���ݦۤv��b"+"\n"+ "�M�D����i�{����O");
                classcondi.setText(" �� �׽ұ��p:���D�װ]���t");
				
			}
			
			
			if(event.getActionCommand() == "���C��")
			{
				flag++;
				picture.setIcon(image3);
				name.setText(" ���C��");
                grade.setText(" �� �t��:��ޤTA");
                characteristic.setText(" �� �ӤH�S��:"+"\n"+" ���H�M�A�����|����ۤv����h�C���w�P�����Ϋe���ͽר�u�@�g��A"+"\n"+" �ñq���o���D�z�P�ǲ�");
                goodat.setText(" �� �M��:"+"\n"+" 1.�[��ӤH�P�s�骺�欰�A�å[�H�w���B���R"+"\n"+" 2.�v���s��BSAI�BJava�BJavaScript");
                expeirience.setText(" �� �g��:"+"\n"+" 1.��Ǧ�(�컲�j���)"+"\n"+" 2.��줤���q�v����(�����B��Ⱶ��...��)"+"\n"+" 3.�ѻP�����A�~���"+"\n");
                future.setText(" �� ���ӳW��:"+"\n"+" �b�j�T�������P����۲Ū����o�i(�j�ƾڡB�϶���B������T...)�A"+"\n"+" �j�|�w�p�Ҩ���s��");
                classcondi.setText("");
			}
			
			if(event.getActionCommand() == "�����o")
			{
				flag++;
				picture.setIcon(image4);
				name.setText(" �����o");
                grade.setText(" �� �t��:��ޤTB");
                characteristic.setText(" �� �ӤH�S��:"+"\n"+" �өʸ����R�H�M�A���w���U�O�H�C�֩�ǲߡA�{�u�t�d�A���w���D�ԩʪ���"+"\n"+" �ȡC�t�~�p�G�Ʊ��S���n���|�������");
                goodat.setText(" �� �M��:"+"\n"+" 1.����ERP�����ҵ{�B��ھާ@�LERP�n��A���Ӻޤ譱���Ѧ��@�w�F��"+"\n"+" 2.C�BJAVA�BSQL�y���BASP�BPhotoshop�Billustrator");
                expeirience.setText(" �� �g��:"+"\n"+" 1.�����ҵ{�ղխ��A�۾�3D�ʵe�n��A���۽s�Ч��B�W�x�нҪ��g��"+"\n"+" 2.��ު�s�s�Ųղխ��A �]�p���������B�వ��¦���u"+"\n"+" 3.�Ŧ~�ؾ����ղխ��A���[�]�B�޲z�p���ϰ�������g��"+"\n");
                future.setText(" �� ���ӳW��:"+"\n"+" ���~��W�������N�~�A�j�|�|�w�ƹ�ߡA�ֿn¾���g��C"+"\n"+" �ثe���[�J�t�W�覨�ߪ�APP�ǥ͹ζ��A�Ʊ楼�ӥi�H�q���Ǩ�t�ε{���]�p"+"\n"+" ���ޯ�A�H�Φp�󦳮ĸg��@��team�A�åB�N�Ҿ����Φb�M�D�W");
                classcondi.setText(" �� �׽ұ��p:���t��u�t�BERP�Ǥ��ǵ{");
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
					JOptionPane.showMessageDialog(introduction.this,"�n���ݧ��C�ӤH���ۤ��~���surprise��!");
					
				}
                
			}
			
		}
		
		
	}
	
	public class surprise extends JFrame
	{
		JPanel northPanel = new JPanel();                                   
	    JPanel southPanel = new JPanel();
	    JLabel word1 = new JLabel("                                         �P�¦Ѯv�@�߬ݧ�!");
	    JLabel word2 = new JLabel("             �۫H�Ѯv��ڭ̦��@�w���F�ѤF~�Ѯv�@�w�n��ڭ̳�!! ^_^");
	    private ImageIcon image6 = new ImageIcon("src/AllofUs.png");
	    JLabel ourpicture = new JLabel(image6);
	    
		public surprise()
		{
			super("���!!!");
			setLayout(new BorderLayout());
			
			northPanel.setBackground(Color.WHITE);
			northPanel.setPreferredSize(new Dimension(400, 700)); 
			southPanel.setLayout(new GridLayout(3, 1));
			southPanel.setBackground(Color.decode("#FFEBCD")); 
			
			word1.setFont(new Font("�s�ө���", Font.BOLD, 30));
			word2.setFont(new Font("�s�ө���", Font.BOLD, 30));
			
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
