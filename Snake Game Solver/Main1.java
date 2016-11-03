package BE;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

class Cell 
{
	int v; //value
	boolean visited;

	public Cell(int v)
	{
      this.v=v;
	  visited=false;
	}
}

class Main1
{ 
   static int m,n;
  static int solution=0;
	static Cell[][] c;
	public static void main(String[] args)
	{
		Scanner in=new Scanner(System.in);
			
	    int i,j,value;	
		Random random=new Random();
		//Accept no of rows n and coloumns m
         n=in.nextInt();
		System.out.print(" ");
		 m=in.nextInt();
        System.out.println("\n");
        c=new Cell[n][m];
       
	   //Enter the values of the cells of the grid from top left
	   for(i=n-1;i>=0;i--)
		{		   
		   for(j=0;j<m;j++)
			{
			  value=in.nextInt();
			   
			   System.out.print(" ");
			   c[i][j]=new Cell(value);
			}
			System.out.println();
			
		}//end of for
       System.out.println();		 
     for(i=n-1;i>=0;i--)
	 {
		 for(j=0;j<m;j++)
		 {   
			 System.out.print(c[i][j].v+" ");
		 }
		 System.out.print("\n");
	 }

     int fv=0;
     i=0;
	 j=0;
	 gotoNext(i,j,fv);

	 System.out.println("\n"+solution);

	
 	}//end of main method

	public static void gotoNext(int i,int j,int fv)
	{
	       
		c[i][j].visited=true;
		fv=fv+c[i][j].v;
        System.out.println("At cell "+i+","+j+" = "+c[i][j].v+" and fv = "+fv);
		

		//going to upper cell
		int r,fr;
		if((i+1)>(n-1))
		{
          r=0;
		  fr=0;
		}
		else
		{ 
			r=i+1;
			fr=fv;
		}
        if(c[r][j].visited==false)
		{
			if(c[r][j].v!=-1)
			{
				gotoNext(r,j,fr);
			}else
			{
				c[r][j].visited=true;
			}
		}
		else
		{
		  if(j==m-1)
		  {
		   if(fv>solution)
			{
			   solution=fv;
			}
		 }
		}
		
		
       //going to right cell
		if((j+1)<=(m-1))
	    {		
		  if(c[i][j+1].visited==false)
		  {
			if(c[i][j+1].v!=-1)
			{
				gotoNext(i,j+1,fv);
			}else
			{
				c[i][j+1].visited=true;
			}
		  }
		}

		//going to lower cell
		if((i-1)<0)
	    {
			r=n-1;
			fr=0;
		}
		else
	    {
			r=i-1;
			fr=fv;
		}
		if(c[r][j].visited==false)
		{
			if(c[r][j].v!=-1)
			{
				gotoNext(r,j,fr);
			}else
			{
				c[r][j].visited=true;
			}
		}
		else  //ie. its visited 
		{
		 if(j==m-1)
		 {
		   if(fv>solution)
			{
			   solution=fv;
			}
		 }
		}
	
	    c[i][j].visited=false;
		System.out.println("Coming back from cell "+i+","+j+" = "+c[i][j].v+" and fv = "+fv);
		//System.out.println();
     
		return;
	}
};//end of class Main







