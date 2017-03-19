package data_structures;

public class Mainclass {
	/**
	 *
	 * @author Michael
	 */

	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
	        // TODO code application logic here
	        char[] str = {'H', 'e', 'y', ',', ' ', 't', 'h', 'e', 'r', 'e'};
	        Arraycopy AC = new Arraycopy();
	        AC.setName(str);
	        System.out.println(AC.getName());
	        System.out.println("String : "+AC.getName()+", Length : "+AC.getLength());
	        System.out.println(AC.getInfo());
	    }
	    
	}

	class Arraycopy{
	    private char[] Astr= new char[20];
	    private int length;
	    
	    public void setName(char[] str){
	        System.arraycopy(str, 0, Astr, 0, str.length);
	        this.length = str.length;
	    }
	    
	    public char[] getName(){
	        return Astr;
	    }
	    
	    public int getLength(){
	        return length;
	    }
	    
	    public String getInfo(){
	        return String.format("String : %s, Length : %d", Astr, length);
	    }
}

