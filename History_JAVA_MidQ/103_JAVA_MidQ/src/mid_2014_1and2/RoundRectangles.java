package mid_2014_1and2;
import java.io.Serializable;

	public class RoundRectangles implements Serializable
	{
	   private int x,y,width,height,Rwidth,Rheight;
	   public RoundRectangles() 
	   {
	      this(0,0,0,0,0,0);
	   }

	   public RoundRectangles(
	       int x,int y,int width,int height,int Rwidth,int Rheight )
	   {
	      setX( x);
	      setY( y);
	      setWidth( width);
	      setHeight( height);
	      setRwidth( Rwidth);
	      setRheight( Rheight);
	   } 
	  
	   public void setX( int x1)
	   {
	      x = x1;
	   }   
	   public void setY( int y1)
	   {
	      y = y1;
	   }
	   public void setWidth( int width1)
	   {
	      width = width1;
	   }
	   public void setHeight( int height1)
	   {
	      height = height1;
	   }
	   public void setRwidth( int Rwidth1)
	   {
	      Rwidth = Rwidth1;
	   }
	   public void setRheight( int Rheight1)
	   {
	      Rheight = Rheight1;
	   }
	   
	   public int getX() 
	   {
	      return x; 
	   } 
	   public int getY() 
	   {
	      return y; 
	   } 
	   public int getWidth() 
	   {
	      return width; 
	   } 
	   public int getHeight() 
	   {
	      return height; 
	   } 
	   public int getRwidth() 
	   {
	      return Rwidth; 
	   } 
	   public int getRheight() 
	   {
	      return Rheight; 
	   } 
}
	
