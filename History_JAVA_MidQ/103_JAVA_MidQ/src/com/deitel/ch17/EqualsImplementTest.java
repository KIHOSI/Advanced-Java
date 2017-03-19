package com.deitel.ch17;
import java.util.LinkedHashSet;

public class EqualsImplementTest {
	/**
	 * Only for Testing your equals are correctly implements. 
	 * DO NOT CHANGE THIS CODE, or you will get ZERO point.
	 */
	public static void main(String[] args) throws Exception{
		LinkedHashSet<AccountRecordSerializable> r = new LinkedHashSet<AccountRecordSerializable>();
		String name = new String("dean");
		
		AccountRecordSerializable x = new AccountRecordSerializable(name,60,60);
		name = new String("dean");
		AccountRecordSerializable y = new AccountRecordSerializable(name,60,60);
		
		r.add(x);
		if(r.contains(y)){
			System.out.println("Override result:Correct!");
		}else{
			System.out.println("Override result:Error!");
		}
			
		
	}

}
