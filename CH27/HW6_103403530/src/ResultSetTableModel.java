// Fig. 28.25: ResultSetTableModel.java
// A TableModel that supplies ResultSet data to a JTable.
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ResultSetTableModel extends AbstractTableModel 
{
   
   private Statement statement;
   private ResultSet resultSet;
   private List< Person > results = new ArrayList<Person>();
   private ResultSetMetaData metaData;
   private int numberOfRows;
   
   private Connection connection = null;
   private PreparedStatement browseAllEntries = null; //瀏覽全部
   private PreparedStatement insertNewEntry = null; //插入新的表格
   private PreparedStatement update = null; //更新table
   private PreparedStatement delete = null; //刪除表格
   private PreparedStatement searchKeyWord = null; //搜尋特定內容
   private PreparedStatement sort = null;//排序

   // keep track of database connection status
   private boolean connectedToDatabase = false;
   
   // constructor initializes resultSet and obtains its meta data object;
   // determines number of rows
   public ResultSetTableModel( String url, String username,
      String password, String query ) throws SQLException
   {         
      // connect to database
      connection = DriverManager.getConnection( url, username, password );
      
      //建立指令
      browseAllEntries = connection.prepareStatement("SELECT * FROM people");
      insertNewEntry = connection.prepareStatement(
    		  "INSERT INTO people " + 
              "( name, phone, e_mail,sex ) " + 
              "VALUES ( ?, ?, ?, ? )" );
      //update = connection.prepareStatement(sql)
      searchKeyWord = connection.prepareStatement("SELECT * FROM people WHERE ? = ?" );
      
      // create Statement to query database
      statement = connection.createStatement( 
         ResultSet.TYPE_SCROLL_INSENSITIVE,
         ResultSet.CONCUR_UPDATABLE ); //可上下，可直接改database

      // update database connection status
      connectedToDatabase = true;

      // set query and execute it
      setQuery( query );
   } // end constructor ResultSetTableModel

   // get class that represents column type
   public Class getColumnClass( int column ) throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine Java class of column
      try 
      {
         String className = metaData.getColumnClassName( column + 1 );
         
         // return Class object that represents className
         return Class.forName( className );
      } // end try
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } // end catch
      
      return Object.class; // if problems occur above, assume type Object
   } // end method getColumnClass

   // get number of columns in ResultSet
   public int getColumnCount() throws IllegalStateException
   {   
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine number of columns
      try 
      {
         return metaData.getColumnCount(); 
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return 0; // if problems occur above, return 0 for number of columns
   } // end method getColumnCount

   // get name of a particular column in ResultSet
   public String getColumnName( int column ) throws IllegalStateException
   {    
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine column name
      try 
      {
         return metaData.getColumnName( column + 1 );  
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string for column name
   } // end method getColumnName

   // return number of rows in ResultSet
   public int getRowCount() throws IllegalStateException
   {      
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );
 
      return numberOfRows;
   } // end method getRowCount

   // obtain value in particular row and column
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try 
      {
         resultSet.absolute( row + 1 );
         return resultSet.getObject( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string object
   } // end method getValueAt
   
   // set new database query string
   public void setQuery( String query ) 
      throws SQLException, IllegalStateException 
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );
      
      // specify query and execute it
      resultSet = statement.executeQuery( query );
      
      // obtain meta data for ResultSet
      metaData = resultSet.getMetaData();

      // determine number of rows in ResultSet
      resultSet.last();                   // move to last row
      numberOfRows = resultSet.getRow();  // get row number      
      
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setQuery

   // close Statement and Connection               
   public void disconnectFromDatabase()            
   {              
      if ( connectedToDatabase )                  
      {
         // close Statement and Connection            
         try                                          
         {                                            
            resultSet.close();                        
            statement.close();                        
            connection.close();                       
         } // end try                                 
         catch ( SQLException sqlException )          
         {                                            
            sqlException.printStackTrace();           
         } // end catch                               
         finally  // update database connection status
         {                                            
            connectedToDatabase = false;              
         } // end finally                             
      } // end if
   } // end method disconnectFromDatabase 
   
// select all of the addresses in the database
   public List< Person > getAllPeople() //沒用
   {
      results = null;
      resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = browseAllEntries.executeQuery(); 
         results = new ArrayList< Person >();
         
         // obtain meta data for ResultSet
         metaData = resultSet.getMetaData();
         
        while ( resultSet.next() )
         {
            results.add( new Person(
               resultSet.getInt( "MemberID" ),
               resultSet.getString( "name" ),
               resultSet.getString( "phone" ),
               resultSet.getString( "e_mail" ),
               resultSet.getString("sex") ) );
         } // end while
         
         // determine number of rows in ResultSet
         //resultSet.last();                   // move to last row
         numberOfRows = resultSet.getRow();  // get row number 
         
         
         // notify JTable that model has changed
         fireTableStructureChanged();
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getAllPeople

   
   // select person by keyword   
   public List< Person > getKeyword( String keywordType,String keyword ) //沒用
   {
      List< Person > results = null;
      ResultSet resultSet = null;

      try 
      {
         searchKeyWord.setString( 1, keywordType ); // 搜尋的欄位名稱
         searchKeyWord.setString(2, keyword); //搜尋名稱

         // executeQuery returns ResultSet containing matching entries
         resultSet = searchKeyWord.executeQuery(); 

         results = new ArrayList< Person >();

         while ( resultSet.next() )
         {
            results.add( new Person( resultSet.getInt( "MemberID" ),
            resultSet.getString( "name" ),
            resultSet.getString( "phone" ),
            resultSet.getString( "e_mail" ),
            resultSet.getString( "sex" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getPeopleByName
   
   // add an entry
   public void addPerson( String query) 
		   throws SQLException, IllegalStateException 
   {
	 // results.add(new Person(id, userName, phoneNum, emailAddress, userSex)) 
	// ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	      int result = statement.executeUpdate(query);
	      if(result == 1){
	    	  JOptionPane.showMessageDialog(null, "Member added!","Member added",JOptionPane.INFORMATION_MESSAGE);
	      }
	      else{
	    	  JOptionPane.showMessageDialog(null, "Member not added!","Member added error",JOptionPane.ERROR_MESSAGE);
	      }
	      resultSet = statement.executeQuery("SELECT * FROM people");
	      metaData = resultSet.getMetaData();
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number
	      fireTableDataChanged();
	      
   } // end method addPerson
   
   public void updatePerson(String query)
		   throws SQLException, IllegalStateException 
   {
	   if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	      int result = statement.executeUpdate(query);
	      if(result == 1){
	    	  JOptionPane.showMessageDialog(null, "Member updated!","Member update",JOptionPane.INFORMATION_MESSAGE);
	      }
	      else{
	    	  JOptionPane.showMessageDialog(null, "Member not updated!","Member update error",JOptionPane.ERROR_MESSAGE);
	      }
	      
	      
	      resultSet = statement.executeQuery("SELECT * FROM people");
	      metaData = resultSet.getMetaData();
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number
	      fireTableDataChanged();
   }
   
   public void deletePerson(String query)
		   throws SQLException, IllegalStateException 
   {
	   if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	      int result = statement.executeUpdate(query);
	      if(result == 1){
	    	  JOptionPane.showMessageDialog(null, "Member updated!","Member update",JOptionPane.INFORMATION_MESSAGE);
	      }
	      else{
	    	  JOptionPane.showMessageDialog(null, "Member not updated!","Member update error",JOptionPane.ERROR_MESSAGE);
	      }
	      
	      resultSet = statement.executeQuery("SELECT * FROM people");
	      metaData = resultSet.getMetaData();
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number
	      fireTableDataChanged();
   }
   
   // close the database connection
   public void close()
   {
      try 
      {
         connection.close();
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
   } // end method close
}  // end class ResultSetTableModel




/**************************************************************************
 * (C) Copyright 1992-2012  by Deitel & Associates, Inc. and               *
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
