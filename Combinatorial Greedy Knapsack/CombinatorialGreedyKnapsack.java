package BE;
import java.lang.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

class Objects
{
	String name;
	float weight,value;
	float Xi;
	
	public Objects(String name,float weight,float value)
	{
		this.name=name;
		this.weight=weight;
		this.value=value;
	}

	
};

class FSO //FSO -Feasible solution object 
{
   float xi,wixi,vixi;
   Objects o;

   public FSO(Objects o,float xi)
	{
        this.o=o;
		this.xi=xi;
		wixi=o.weight*xi;
	    vixi=o.value*xi;

	}

	

	

};

class FS //FS -Feasible solution
{
	FSO[] fso;
	float wSum,vSum;
	int size;

	public FS(int size)
	{
      this.size=size;
	  fso =new FSO[size];
	  wSum=0;
	  vSum=0;
	}
};





class CombinatorialGreedyKnapsack 
{
	public static void main(String[] args) 
	{
		
	   int i,j,number;
	   float weight,value;

	   String name;
	   Scanner in=new Scanner(System.in);

	   //Accept no of objects in Sample Space
       System.out.println("Enter the total no. of objects in Sample space");
	   number=in.nextInt();

	   //Create array of Objects
	   
	   Objects o[]=new Objects[number];


	   //Accept name,weight and value of each object

       System.out.println("Enter the name,weight and value of objects");
       
       for(i=0;i<number;i++)
	   {
		  System.out.println("Object "+i);
		  System.out.println("name =");
		  name=in.next();
		  System.out.println("weight =");
		  weight=in.nextFloat();
		  System.out.println("value =");
		  value=in.nextFloat();
          o[i]=new Objects(name,weight,value);
	    }//end of for
        
		//Accept knapsack capacity
        int W;
		System.out.println("Enter Knapsack capacity");
        W=in.nextInt();
         
		// Calculate Xi of each object

		for(i=0;i<number;i++)
		{
			o[i].Xi=o[i].weight/W;
		}
		//Display objects list 

        System.out.println("List of objects ");
		System.out.println("\nname\tweight\tvalue");
		for(i=0;i<number;i++)
		{
		System.out.println("  "+o[i].name+"\t"+o[i].weight+"\t"+o[i].value);        
			
		}//end of for
      
	   boolean done=false;
	   //Main problem sol starts here
       while(done==false)
	  {
       System.out.println("Enter the no of feasible solutions to find ");
       int no=in.nextInt();
      //generate y1,y2,y3

	   FS[] fs=new FS[no]; //declaring array of 4 feasible solutions 
       float y[]=new float[number];//will hold all yis'
	  

	   boolean valid;//valid to check if yi is less than Xi for that o[i]
	   float ySum;
        
	   Random rand=new Random();
	   float randomno;
 
	   for(int l=0;l<no;l++) //loop for fss'
	   { 
           fs[l]=new FS(number);
		   
		   for(i=0;i<number;i++)
	       {
		      y[i]=0;
	       }
		  ySum=0;
          for(i=0;i<number && ySum<1;i++)// y[i]s for each feasible solution
          {
			  
			  valid=false;
              while(valid==false)
              {
			    if(i<(number-1))
			    {
					 randomno=rand.nextInt(Math.round(((1-ySum)*100))+10);
					 y[i]=randomno/100;

					 if(y[i]<=o[i].Xi)
					 {
						 valid=true;
						 ySum=ySum+y[i];
					 }

			    }else if(i==(number-1))
			    {
					do{
						y[i]=1-ySum;
						if(y[i]<=o[i].Xi)
						{
							valid=true;
							ySum=ySum+y[i];
						}
						else
						{
						   randomno=rand.nextInt(Math.round(((1-ySum)*100))+10);
					       y[i]=randomno/100;

					      if(y[i]<=o[i].Xi)
					      {
					        valid=true;
						    ySum=ySum+y[i];
					      }
						}
					}while(valid==false);
			    }
              }//end of while
          }//end of for with i ,now we have got y[] populated

          //display y[]
		   System.out.println("\nFor Feasible Solution "+l+" values of y[i]");
		  System.out.println("Object     y[i]");
		  for(i=0;i<number;i++)
		  {
			  System.out.println(" "+o[i].name+"\t   "+y[i]);        
		  }


         //use yi to find xi for each object of feasible soln
		 float xi;
		 
		 for(i=0;i<number;i++)
		 {
			 xi=(y[i]*W)/o[i].weight;//calculate xi from yi
			 fs[l].fso[i]=new FSO(o[i],xi);//create fso object
			 fs[l].wSum=fs[l].fso[i].wixi+fs[l].wSum;
			 fs[l].vSum=fs[l].fso[i].vixi+fs[l].vSum;
		 }
         //now we got wSum(not imp) and vSum(imp) of the feasible solution fs[l]
          
		  System.out.println("\nFeasible Solution "+l);
		  System.out.println("\nObjects  xi");
		  for(i=0;i<number;i++)
		  {
			  System.out.println(" "+fs[l].fso[i].o.name+"\t "+fs[l].fso[i].xi);

		  }
		  System.out.println("wSum= "+fs[l].wSum);
		  System.out.println("vSum= "+fs[l].vSum);


	   }//end of for with l

  //Find Optimum Solution
   FS temp;
   for(i=0;i<no;i++)
	{
	   for(j=i+1;j<no;j++)
	   {
		  if(fs[i].vSum<fs[j].vSum)
		  {
			temp=fs[i];
			fs[i]=fs[j];
			fs[j]=temp;
		  }
	   }
	}
   valid=false;
   int l=0;
   while(valid==false)
	{
	   if(fs[l].wSum<=W)
	   {
		   valid=true;
	   }
	   else
	   {   l++;
	   }
   }

   System.out.println("Optimum Solution is ");  
		  System.out.println("\nObjects  xi");
		  for(i=0;i<number;i++)
		  {
			  System.out.println(" "+fs[l].fso[i].o.name+"\t "+fs[l].fso[i].xi);

		  }
		  System.out.println("wSum= "+fs[l].wSum);
		  System.out.println("vSum= "+fs[l].vSum);
	
	System.out.println("\nWant another try with different no of feasible solutions?(y/n)");
    String ans=in.next();
	if(ans.equals("y"))
	{
       done=false;
	}
	else
	{
		done=true;
	}

    
  }//end of while done 
   
 }//end of main

}//end of GFK class 
