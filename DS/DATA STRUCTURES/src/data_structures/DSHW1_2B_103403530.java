package data_structures;
import java.util.*;
public class DSHW1_2B_103403530 {
	public static int Priority(char operator){
		if((operator == '*')||(operator == '/')) //如果為*或/
		{
			return 2; //權值為2
		}
		else if((operator == '+')||(operator == '-')) //如果為+或-
		{
			return 1; //權值為1
		}
		else //如果為左括號
		{
			return 0; //權值為0
		}
	}
	public static int Calculate(int num1,char operator,int num2){ //數學計算
		int result = 0;
		if(operator == '+') //加法
		{
			result = num1 + num2;
		}
		else if(operator == '-') //減法
		{
			result = num1 - num2;
		}
		else if(operator == '*') //乘法
		{
			result = num1 * num2;
		}
		else if(operator == '/') //除法
		{
			result = num1 / num2;
		}
		return result;
	}
	public static void main(String[] args) {
		/*****輸入字串*****/
		Scanner input = new Scanner(System.in); //input儲存Scanner裡的System.in方法
		Stack<Character> st = new Stack<Character>(); //建立一個空的stack為字元格式，名字叫st
		int check; //檢查左右括號是否有誤
		int space; //計算左括號右括號數
		char[] res; //宣告字元陣列res儲存輸入的中序
		do{
			space = 0;
			check = 0;
			System.out.println("請輸入中序運算式(運算元皆為整數):");
			String preRes = input.nextLine(); //儲存輸入的字串到preRes
			res = new char[preRes.length()]; //字元陣列res長度為字串preRes儲存的長度
			for(int i=0;i<res.length;i++)
			{
				res[i] = preRes.charAt(i); //將preRes儲存的字串(轉字元)存入字元陣列res裡
	/***********************************驗證左括號、右括號********************************/
				if(res[i]=='(') //如果是左括號
				{
					st.push(res[i]); //儲存進st裡(st和儲存的字元格式必須相同)
					space += 1;
				}
				else if(res[i]==')') //如果是右括號
				{
					if(st.empty()) //如果st是空的
					{
						System.out.println("錯誤:右括號沒有對稱!"); //st裡面沒有左括號，右括號無法對稱
						check = 1; //錯誤
						System.out.println("重新輸入!");
					}
					else //將左括號從st刪除
					{
						st.pop(); //從st取出左括號
						space += 1;
					}
				}
			}
			if(!st.empty()) //驗證st還有沒有東西
			{
				System.out.println("錯誤:左括號沒有對稱!"); //st裡面還存有左括號，表示少了右括號
				check = 1; //錯誤
				System.out.println("重新輸入!");
			}
			while(!st.empty()) //清空stack
			{
				st.pop();
			}
		}while(check!=0); //check為0表示正確，否則為錯誤，必須重新輸入
/********************************中序轉後序*****************************************/
		char posfix[] = new char[res.length-space]; //宣告postfix儲存後序，將多餘的空格去掉
		char j = 0; //j為posfix的Index
		char i = 0; //i為res的index(因為char陣列無法使用remove方法，只能將舊陣列複製到新陣列，故將每一次的i都是以0開始)
		String transRes;
		String transPos;
		
		System.out.printf("%-30s %-20s %-30s","Input Buffer","Operator Stack","Output String");
		System.out.println();
		
		
		
		while(res.length != 0) //直到res裡沒有元素才停止
		{
			
			transRes = String.valueOf(res);
			transPos = String.valueOf(posfix);
			
			//印出內容
			System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
			System.out.println();
			
			
			i = 0; //每跑回一次，i就重設為0
			char looker = res[i]; //looker儲存現在搜尋到的中序元素
			if(looker == '(') //如果為左括號
			{
				st.push(res[i]); //放進st裡
			}
			else if(looker == ')') //如果為右括號
			{
				char popToken = st.pop(); //popToken儲存從st取出來的值
				while(popToken != '(') //如果取出來的不是左括號
				{
					posfix[j]=popToken; //將值存到posfix
					j += 1; //posfix換到下一個Index
					popToken = st.pop(); //再取出一次，直到找到左括號
				}
			}
			else if((looker == '+')||(looker == '-')||(looker == '*')||(looker == '/')) //如果為+-*/
			{
				if(!st.empty()) //如果st不是空的
				{
					char top = st.peek(); //top儲存現在在stack最上面的值
					while((!st.empty())&&(Priority(looker)<=Priority(top))) //判斷st是否為空，及判斷looker與top的權限
					{
						char popOperator = st.pop(); //popOperator儲存取出來的值
						posfix[j] = popOperator; //丟進posfix裡
						j += 1; //posfix換到下一個位置
						if(!st.empty()) //如果st不是空的
						{
							top = st.peek(); //再一次儲存stack最上面的值
						}
					}
					if(st.empty()) //如果st變成空的話，就印出結果
					{
						System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
						System.out.println();
					}
				}
				st.push(looker); //將looker放進st
			}
			else //looker為運算元
			{
				posfix[j] = looker; //放進posfix
				j += 1; //下一個位置
			}
			res = Arrays.copyOfRange(res,i+1,res.length); //只儲存所需要的元素(等於刪除looker所指元素)
			
			//System.out.println(res);
		}
		int status; //判斷st是否為空，不是空的話要再印一次結果
		do{
			
			transRes = String.valueOf(res);
			transPos = String.valueOf(posfix);
			
			System.out.printf("%-30s %-20s %-30s",transRes,st,transPos);
			System.out.println();
			//列印內容
			
			
			status = 0;
			while(!st.empty()) //清空st
			{
				char popRemainder = st.pop(); //將剩餘的元素取出來
				posfix[j] = popRemainder; //丟入posfix
				j += 1;
				status += 1;
			}
		}while(status != 0);
/**********************************計算後序以求值***********************************/
		Stack<Integer> st2 = new Stack<Integer>(); //創造一個新的int格式的stack，才可存兩位數以上的數字
		for(int k=0;k<posfix.length;k++)
		{
			if((posfix[k] == '+')||(posfix[k] == '-') || (posfix[k] == '*') || (posfix[k] == '/')) //如果為運算子
			{
				int operand2 = st2.pop(); //operand2儲存st最上面的值
				int operand1 = st2.pop(); //operand1儲存第二個值
				char operator = posfix[k]; //operator儲存運算子
				int value = Calculate(operand1,operator,operand2); //value儲存計算結果(char格式)
				st2.push(value); //將結果丟進st2裡
			}
			else //如果為運算元
			{
				int operand = posfix[k]-48; //posfix[i]為char格式，轉成int會變成ASCII，減掉48就會得正確的值
				st2.push(operand); //將值丟入st2
			}
		}
		int answer = st2.pop(); //answer儲存最終答案
		System.out.println("計算的值為:"+answer);		
	}
}
