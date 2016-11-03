import java.util.*;

class GlassContainer
{
  int currentValue;
  int size;

  public GlassContainer(int size)
  {
	  this.size=size;
	  currentValue=0;
  }
};

class TwoContainersFinal
{
	public static void main(String[] args) 
	{
		int t,x,y,z;
		Scanner in=new Scanner(System.in);
		int T;
		T=in.nextInt();

	   for(t=0;t<T;t++)
	   {
        x=in.nextInt();
		y=in.nextInt();
		z=in.nextInt();
        GlassContainer a,b;
     
		a=new GlassContainer(x);
		b=new GlassContainer(y);

        int abCount=countSteps(a,b,z);
		int baCount=countSteps(b,a,z);

       if(abCount<=baCount && abCount!=-1)
	   {
		   System.out.println(abCount);
		   
	   }
	   else if(abCount>baCount && baCount!=-1)
	   {   		  
		   System.out.println(baCount);
	   }
	   else if(abCount==-1 && baCount==-1)
	   {
		   System.out.println(-1);
	   }

	 }

	}

	public static int countSteps(GlassContainer a,GlassContainer b,int z)
	{
		int count=0;
		a.currentValue=0; 
		b.currentValue=0;
	//	System.out.println("\nStart with a("+a.size+") = "+a.currentValue+"  b("+b.size+") = "+b.currentValue);
		if(a.size==2*(b.size) || b.size==2*a.size)
		{
			if(z==a.size)
			{
				count=1;
				return count;
			}
		    else if(z==b.size)
			{
				count=1;
				return count;
			}
			else
			{
				//System.out.println("Cannot be obtained");
			    return -1;
			}
		  
		}//eo if
		
       if(z>a.size && z>b.size)
	   {
          //System.out.println("Cannot be obtained");
	      return -1;
       }
	   
	    //fill b
        b.currentValue=b.size;
		//System.out.print("Fill b("+b.size+") : ");
		//System.out.println("  a("+a.size+") = "+a.currentValue+"  b("+b.size+") = "+b.currentValue); 
		count++;
		 if(a.currentValue==z || b.currentValue==z)
		  {
			return count;
	      }
		//trans(b,a)
	
		do
		{	
			//System.out.println("Came into do");

         /* while(a.currentValue<a.size && b.currentValue>0)
		  {
			a.currentValue++;
            b.currentValue--;
		  }                  
		  */

          if(b.currentValue>(a.size-a.currentValue))
		  {			  
			  b.currentValue=b.currentValue-(a.size-a.currentValue);
			  a.currentValue=a.size;
		  }
		  else if(b.currentValue<=(a.size-a.currentValue))
		  {
			  a.currentValue=a.currentValue+b.currentValue;
			  b.currentValue=0;
		  }
		  count++;
		 // System.out.println("Transf(b("+b.size+") to a("+a.size+"))   a("+a.size+") = "+a.currentValue+"  b("+b.size+") = "+b.currentValue); 
		  if(a.currentValue==z || b.currentValue==z)
		  {
			return count;
	      }
		  //if(a is full) empty it
          if(a.currentValue==a.size)
		  {
			a.currentValue=0;
			count++;
			//System.out.print("Fill a("+a.size+") : ");
			//System.out.println("   a("+a.size+") = "+a.currentValue+"  b("+b.size+") = "+b.currentValue); 
		  }
		  //if(b is empty) fill it full
          if(b.currentValue==0)
		  {
			b.currentValue=b.size;
			count++;
		//	System.out.print("Empty b("+b.size+") : ");
		//	System.out.println("   a("+a.size+") = "+a.currentValue+"  b("+b.size+") = "+b.currentValue); 

		  }

		}while(!(a.currentValue==0 && b.currentValue==b.size));   //!(a.currentValue==0 && b.currentValue==b.size)

     //   System.out.println("Cannot be obtained");
		return -1;
	}//eo method
}
