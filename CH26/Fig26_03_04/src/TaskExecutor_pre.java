// Fig. 26.4: TaskExecutor.java
// Using an ExecutorService to execute Runnables.
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class TaskExecutor_pre
{
   public static void main( String[] args )
   {
      // create and name each runnable
	  System.out.println("Create threads");
	  Thread thread1 = new Thread(new PrintTask("task1"));
	  Thread thread2 = new Thread(new PrintTask("task2"));
	  Thread thread3 = new Thread(new PrintTask("task3"));
	  
      

      // start threads and place in runnable state
      System.out.println("Threads created,starting tasks");
      thread1.start();
      thread2.start();
      thread3.start();

      
      System.out.println( "Tasks started, main ends.\n" );
   } // end main
} // end class TaskExecutor


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