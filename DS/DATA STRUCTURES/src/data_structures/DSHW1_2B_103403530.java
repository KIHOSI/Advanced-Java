package data_structures;
import java.util.*;
public class DSHW1_2B_103403530 {
	public static int Priority(char operator){
		if((operator == '*')||(operator == '/')) //�p�G��*��/
		{
			return 2; //�v�Ȭ�2
		}
		else if((operator == '+')||(operator == '-')) //�p�G��+��-
		{
			return 1; //�v�Ȭ�1
		}
		else //�p�G�����A��
		{
			return 0; //�v�Ȭ�0
		}
	}
	public static int Calculate(int num1,char operator,int num2){ //�ƾǭp��
		int result = 0;
		if(operator == '+') //�[�k
		{
			result = num1 + num2;
		}
		else if(operator == '-') //��k
		{
			result = num1 - num2;
		}
		else if(operator == '*') //���k
		{
			result = num1 * num2;
		}
		else if(operator == '/') //���k
		{
			result = num1 / num2;
		}
		return result;
	}
	public static void main(String[] args) {
		/*****��J�r��*****/
		Scanner input = new Scanner(System.in); //input�x�sScanner�̪�System.in��k
		Stack<Character> st = new Stack<Character>(); //�إߤ@�ӪŪ�stack���r���榡�A�W�r�sst
		int check; //�ˬd���k�A���O�_���~
		int space; //�p�⥪�A���k�A����
		char[] res; //�ŧi�r���}�Cres�x�s��J������
		do{
			space = 0;
			check = 0;
			System.out.println("�п�J���ǹB�⦡(�B�⤸�Ҭ����):");
			String preRes = input.nextLine(); //�x�s��J���r���preRes
			res = new char[preRes.length()]; //�r���}�Cres���׬��r��preRes�x�s������
			for(int i=0;i<res.length;i++)
			{
				res[i] = preRes.charAt(i); //�NpreRes�x�s���r��(��r��)�s�J�r���}�Cres��
	/***********************************���ҥ��A���B�k�A��********************************/
				if(res[i]=='(') //�p�G�O���A��
				{
					st.push(res[i]); //�x�s�ist��(st�M�x�s���r���榡�����ۦP)
					space += 1;
				}
				else if(res[i]==')') //�p�G�O�k�A��
				{
					if(st.empty()) //�p�Gst�O�Ū�
					{
						System.out.println("���~:�k�A���S�����!"); //st�̭��S�����A���A�k�A���L�k���
						check = 1; //���~
						System.out.println("���s��J!");
					}
					else //�N���A���qst�R��
					{
						st.pop(); //�qst���X���A��
						space += 1;
					}
				}
			}
			if(!st.empty()) //����st�٦��S���F��
			{
				System.out.println("���~:���A���S�����!"); //st�̭��٦s�����A���A��ܤ֤F�k�A��
				check = 1; //���~
				System.out.println("���s��J!");
			}
			while(!st.empty()) //�M��stack
			{
				st.pop();
			}
		}while(check!=0); //check��0��ܥ��T�A�_�h�����~�A�������s��J
/********************************��������*****************************************/
		char posfix[] = new char[res.length-space]; //�ŧipostfix�x�s��ǡA�N�h�l���Ů�h��
		char j = 0; //j��posfix��Index
		char i = 0; //i��res��index(�]��char�}�C�L�k�ϥ�remove��k�A�u��N�°}�C�ƻs��s�}�C�A�G�N�C�@����i���O�H0�}�l)
		String transRes;
		String transPos;
		
		System.out.printf("%-30s %-20s %-30s","Input Buffer","Operator Stack","Output String");
		System.out.println();
		
		
		
		while(res.length != 0) //����res�̨S�������~����
		{
			
			transRes = String.valueOf(res);
			transPos = String.valueOf(posfix);
			
			//�L�X���e
			System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
			System.out.println();
			
			
			i = 0; //�C�]�^�@���Ai�N���]��0
			char looker = res[i]; //looker�x�s�{�b�j�M�쪺���Ǥ���
			if(looker == '(') //�p�G�����A��
			{
				st.push(res[i]); //��ist��
			}
			else if(looker == ')') //�p�G���k�A��
			{
				char popToken = st.pop(); //popToken�x�s�qst���X�Ӫ���
				while(popToken != '(') //�p�G���X�Ӫ����O���A��
				{
					posfix[j]=popToken; //�N�Ȧs��posfix
					j += 1; //posfix����U�@��Index
					popToken = st.pop(); //�A���X�@���A�����쥪�A��
				}
			}
			else if((looker == '+')||(looker == '-')||(looker == '*')||(looker == '/')) //�p�G��+-*/
			{
				if(!st.empty()) //�p�Gst���O�Ū�
				{
					char top = st.peek(); //top�x�s�{�b�bstack�̤W������
					while((!st.empty())&&(Priority(looker)<=Priority(top))) //�P�_st�O�_���šA�ΧP�_looker�Ptop���v��
					{
						char popOperator = st.pop(); //popOperator�x�s���X�Ӫ���
						posfix[j] = popOperator; //��iposfix��
						j += 1; //posfix����U�@�Ӧ�m
						if(!st.empty()) //�p�Gst���O�Ū�
						{
							top = st.peek(); //�A�@���x�sstack�̤W������
						}
					}
					if(st.empty()) //�p�Gst�ܦ��Ū��ܡA�N�L�X���G
					{
						System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
						System.out.println();
					}
				}
				st.push(looker); //�Nlooker��ist
			}
			else //looker���B�⤸
			{
				posfix[j] = looker; //��iposfix
				j += 1; //�U�@�Ӧ�m
			}
			res = Arrays.copyOfRange(res,i+1,res.length); //�u�x�s�һݭn������(����R��looker�ҫ�����)
			
			//System.out.println(res);
		}
		int status; //�P�_st�O�_���šA���O�Ū��ܭn�A�L�@�����G
		do{
			
			transRes = String.valueOf(res);
			transPos = String.valueOf(posfix);
			
			System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
			System.out.println();
			//�C�L���e
			
			
			status = 0;
			while(!st.empty()) //�M��st
			{
				char popRemainder = st.pop(); //�N�Ѿl���������X��
				posfix[j] = popRemainder; //��Jposfix
				j += 1;
				status += 1;
			}
		}while(status != 0);
/**********************************�p���ǥH�D��***********************************/
		Stack<Integer> st2 = new Stack<Integer>(); //�гy�@�ӷs��int�榡��stack�A�~�i�s���ƥH�W���Ʀr
		for(int k=0;k<posfix.length;k++)
		{
			if((posfix[k] == '+')||(posfix[k] == '-') || (posfix[k] == '*') || (posfix[k] == '/')) //�p�G���B��l
			{
				int operand2 = st2.pop(); //operand2�x�sst�̤W������
				int operand1 = st2.pop(); //operand1�x�s�ĤG�ӭ�
				char operator = posfix[k]; //operator�x�s�B��l
				int value = Calculate(operand1,operator,operand2); //value�x�s�p�⵲�G(char�榡)
				st2.push(value); //�N���G��ist2��
			}
			else //�p�G���B�⤸
			{
				int operand = posfix[k]-48; //posfix[i]��char�榡�A�নint�|�ܦ�ASCII�A�48�N�|�o���T����
				st2.push(operand); //�N�ȥ�Jst2
			}
		}
		int answer = st2.pop(); //answer�x�s�̲׵���
		System.out.println("�p�⪺�Ȭ�:"+answer);		
	}
}
