import java.util.Scanner;
import java.io.*;
import java.lang.Thread;
import java.util.Random;
class Cell 
{
	int v; //value
	//boolean visited;

	public Cell(int v)
	{
      this.v=v;
	 // visited=false;
	}
}


public class SnakeThreadManual extends Thread 
{
	static int m,n;
	static int solution=0;
    static Cell[][] c;
	int fv,i,j;
	int[][] currentState;
    public SnakeThreadManual(int i,int j,int fv,int[][] oldState)
	{
		
		this.i=i;
		this.j=j;
		this.fv=fv;
		currentState=new int[n][m];
		for(int k=0;k<n;k++)
		{
			for(int l=0;l<m;l++)
			{
    		   currentState[l][k]=oldState[l][k];
			}
	    }
		
	}//end of constructor

	public void run()
	{
		 SnakeThreadManual stUp,stDown,stRight;
		 stUp=null;
		 stDown=null;
		 stRight=null;
		// c[i][j].visited=true;
		currentState[i][j]=1;
		fv=fv+c[i][j].v;
        System.out.println("At cell "+i+","+j+" = "+c[i][j].v+" and fv = "+fv);
		
     try{
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
        if(currentState[r][j]==0)//if(c[r][j].visited==false)
		{
			if(c[r][j].v!=-1)
			{
				//gotoNext(r,j,fr);

				stUp=new SnakeThreadManual(r,j,fr,currentState);
	            stUp.start();
	           

			}else
			{
				//c[r][j].visited=true;
				currentState[r][j]=1;
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
		   if(currentState[i][j+1]==0)//if(c[i][j+1].visited==false)
		  {
			if(c[i][j+1].v!=-1)
			{
				 stRight=new SnakeThreadManual(i,j+1,fv,currentState);
	            stRight.start();
	          

				//gotoNext(i,j+1,fv);
			}else
			{
				//c[i][j+1].visited=true;
				currentState[i][j+1]=1;
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
		if(currentState[r][j]==0)//if(c[r][j].visited==false)
		{
			if(c[r][j].v!=-1)
			{
				//gotoNext(r,j,fr);
				 stDown=new SnakeThreadManual(r,j,fr,currentState);
	            stDown.start();
	           
			}else
			{
				//c[r][j].visited=true;
				currentState[r][j]=1;
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
	
	   
		System.out.println("Coming back from cell "+i+","+j+" = "+c[i][j].v+" and fv = "+fv);
		//System.out.println();
        if(stUp!=null)
		{
        stUp.join();
		}
		if(stRight!=null)
		{
		stRight.join();
		}
		if(stDown!=null)
		{
		stDown.join();
		}
		 //c[i][j].visited=false;
		return;
	 }catch(InterruptedException ex)
		{
		 System.out.println(ex);
		}
	}//end of run


	public static void main(String[] args) 
	{
		try{
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

      int visited[][]=new int[n][m];
	 for(int k=0;k<n;k++)
	 {
		 for(int l=0;l<m;l++)
		 {
			 visited[k][l]=0;
		 }
	 }

     int fv=0;
     i=0;
	 j=0;
    
	 SnakeThreadManual st=new SnakeThreadManual(i,j,fv,visited);
	 st.start();
	 st.join();
	 // gotoNext(i,j,fv);

	 System.out.println("\nBest solution = "+solution);
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}//end of main

}//end of class
