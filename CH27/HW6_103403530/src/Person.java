//Memberªº¸ê®Æ
public class Person
{
   private int memberID;
   private String name;
   private String phoneNumber;
   private String email;
   private String sex;

   /*// no-argument constructor
   public Person()
   {
   } // end no-argument Person constructor
	*/
   // constructor
   public Person( int id, String userName, String phoneNum, 
      String emailAddress, String userSex )
   {
      setMemberID( id );
      setName( userName );
      setPhoneNumber( phoneNum );
      setEmail( emailAddress );
      setSex( userSex );
   } // end five-argument Person constructor 

   // sets the addressID
   public void setMemberID( int id )
   {
      memberID = id;
   } // end method setAddressID

   // returns the addressID 
   public int getMemberID()
   {
      return memberID;
   } // end method getAddressID
   
   // sets the firstName
   public void setName( String userName )
   {
      name = userName;
   } // end method setFirstName

   // returns the first name 
   public String getName()
   {
      return name;
   } // end method getFirstName
   
   // sets the lastName
   public void setPhoneNumber( String phoneNum )
   {
      phoneNumber = phoneNum;
   } // end method setLastName

   // returns the last name 
   public String getPhoneNumber()
   {
      return phoneNumber;
   } // end method getLastName
   
   // sets the email address
   public void setEmail( String emailAddress )
   {
      email = emailAddress;
   } // end method setEmail

   // returns the email address
   public String getEmail()
   {
      return email;
   } // end method getEmail
   
   // sets the phone number
   public void setSex( String userSex )
   {
      sex = userSex;
   } // end method setPhoneNumber

   // returns the phone number
   public String getSex()
   {
      return sex;
   } // end method getPhoneNumber
} // end class Person


/**************************************************************************
 * (C) Copyright 1992-2012 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/

 