import java.util.*;
import java.lang.*;

//This code is designed and written by Hrishikesh Waikar 

class MyComparator implements Comparator<State>
{
   public int compare(State firstState,State secondState)
   {
	   if(firstState.fn>secondState.fn)
	   {
    	   return 1;
	   }else if(firstState.fn<secondState.fn)
	   {
		   return -1;
	   }
	   return 0;
   }
}

class HomePosition
{
	int I;
	int J;

	public HomePosition(int I,int J)
	{
		this.I=I;
		this.J=J;
	}
}
class State{

 static int stateCount;
 static HomePosition[] homePosition;
 int[][] puzzle;
 int fn,gn,hn;
 int stateName;
 //State[] children;
 ArrayList<State> children;
 State parent;
 boolean isRedundantState;
 int spacePositionI;
 int spacePositionJ;

  public State(State parent,int tiletoswapI,int tiletoswapJ)
  {
	  puzzle=new int[3][3];
	  stateCount=stateCount+1;
	  stateName=stateCount;
	  this.parent=parent;
	  //children=new State[2];
          children=new ArrayList<State>();
	  
      //make this state by modifying the parent state by swapping space postion with tiletoswap
      
      for(int i=0;i<3;i++)
	  {
		  for(int j=0;j<3;j++)
		  {
			  this.puzzle[i][j]=parent.puzzle[i][j];
		  }
	  }

	  //swap tiletoswap with current space position which is still parent's space position
 

	
	 puzzle[parent.spacePositionI][parent.spacePositionJ]=puzzle[tiletoswapI][tiletoswapJ];
     puzzle[tiletoswapI][tiletoswapJ]=parent.puzzle[parent.spacePositionI][parent.spacePositionJ];

	 this.spacePositionI=tiletoswapI;
	 this.spacePositionJ=tiletoswapJ;


      //calculate gn ,hn and fn
	  calculategn();
	  calculatehn();
	  calculatefn();
      
  }

  
//This code is designed and written by Hrishikesh Waikar 
  public State()
  {
	  puzzle=new int[3][3];
	  this.parent=null;
	  this.gn=0;
	  //children=new State[2];
          children=new ArrayList<State>();
      
	  //calculatehn();
	  //calculatefn();     
  }

  public void calculategn()
  {
	  if(parent!=null)
	  {
	   this.gn=parent.gn+1;
	  }
  }
  

  public void calculatehn()
  {
	  this.hn=0;
	  int iCount=0;
	  int no;
	  int distance;

	  for(int i=0;i<3;i++)
	  {
		  for(int j=0;j<3;j++)
		  {
			  no=this.puzzle[i][j];
			  distance=Math.abs(i-homePosition[no].I) + Math.abs(j-homePosition[no].J);
			  hn=hn+distance;

		  }
	  }
  }

  

  public void calculateHn()
  {
	  this.hn=0;
	  int iCount=1;
     for(int i=0;i<3;i++)
	  {
		 for(int j=0;j<3;j++)
		 {
			 if(!(i==2 && j==2) && this.puzzle[i][j]!=iCount)
			 {
				 hn=hn+1;
			 }else if((i==2 && j==2) && this.puzzle[i][j]!=0)
			 {
				  //System.out.println("and Came here");
                                  hn=hn+1;  
			 }
			 //System.out.println(hn);
			 iCount++;
		 }
	  }
  }//eo calculate hn

  public void calculatefn()
  {
	  this.fn=this.gn+this.hn;
  }

  public boolean equals(State otherState)
  {
	 // System.out.println("Came here in equals");
	  for(int i=0;i<3;i++)
	  {
		  for(int j=0;j<3;j++)
		  {
			  if(this.puzzle[i][j]!=otherState.puzzle[i][j])
			  {
				  return false;
			  }
		  }
	  }

	  return true;
  }
 
}

public class EightPuzzle{

  //static State goalState;
  public static void main(String[] args)
  {
    //input start state and goal state and store them in start and goal objects of class State
    

    int i,j;

	State startState=new State();

	//Accept Start State
	System.out.println("Enter the Start State");
    Scanner in=new Scanner(System.in);
	int iCount=1;
	State.homePosition=new HomePosition[9];
	for(i=0;i<3;i++)
	{
		//System.out.println();
		for(j=0;j<3;j++)
		{
			//System.out.println("p["+i+"]["+j+"]=");
            startState.puzzle[i][j]=in.nextInt();
			if(startState.puzzle[i][j]==0)
			{
				startState.spacePositionI=i;
				startState.spacePositionJ=j;
			}
			if(iCount<9)
			{
				State.homePosition[iCount]=new HomePosition(i,j);

			}else if(iCount==9)
			{
				State.homePosition[0]=new HomePosition(i,j);
			}
			iCount++;
		}
	}
	
	startState.stateName=1;
        State.stateCount=1;
	startState.calculategn();
	startState.calculatehn();
	startState.calculatefn();


    //create open and closed lists
    PriorityQueue<State> openList=new PriorityQueue<State>(new MyComparator());

	ArrayList<State> closedList=new ArrayList<State>();

    openList.add(startState);

    //main program to find the path to final state    
	
	State solutionState=null;
	while(true)
	{
	   State currentState=openList.poll();
       System.out.println("state name "+currentState.stateName);
	   System.out.println("Displaying currentState in while loop");
	 //  displayState(currentState);

	   if(currentState.hn==0)
	   {
		   solutionState=currentState;
		   closedList.add(currentState);
		   
		   //break out of this loop and do something to find it's parents.
		   break;
	   }else{

		  // System.out.println("came inside else");
           
		  int childCount= createChildren(currentState);
          System.out.println("Child Count"+childCount);
           for(i=0;i<childCount;i++)
		   {
			   if(currentState.equals(startState))
			   {
				   openList.add(currentState.children.get(i));
			   }else if(!currentState.parent.equals(currentState.children.get(i)))
			   {
				   openList.add(currentState.children.get(i));
			   }
		   }
		   closedList.add(currentState);
	   }   
	   
	}//eo while
    
	
 //This code is designed and written by Hrishikesh Waikar 

	ArrayDeque<State> solution=new ArrayDeque<State>();
	if(solutionState!=null)
	{
        State thisState=solutionState;
		while(thisState.parent!=null)
		{
			solution.add(thisState);
			thisState=thisState.parent;
		}
		solution.add(thisState);
	}

    System.out.println("\n\nSolution State :");

	
	while(solution.size()>0)
	{
      State aState=solution.removeLast();
      displayState(aState);

	}

  }//eo main

  public static int createChildren(State parent)
  {
	  int spaceI=parent.spacePositionI;
	  int spaceJ=parent.spacePositionJ;
          int childCount=0;
          /*
	  parent.children[0]=new State(parent,not(spaceI),spaceJ);
	  //displayState(parent.children[0]);
	  parent.children[1]=new State(parent,spaceI,not(spaceJ));	
	 // displayState(parent.children[1]);
	  */

         //if i-1,j
           if(spaceI-1>=0 && spaceJ>=0)
           {
              parent.children.add(new State(parent,spaceI-1,spaceJ));
              childCount++;
	   }         
	//if i , j-1
           if(spaceI>=0 && spaceJ-1>=0)
           {
              parent.children.add(new State(parent,spaceI,spaceJ-1));
	      childCount++;
	   }
	 //if  i,j+1
           if(spaceI>=0 && spaceJ+1<=2)
           {
              parent.children.add(new State(parent,spaceI,spaceJ+1));
	      childCount++;
	   }
	 //if i+1,j	
	  if(spaceI+1<=2 && spaceJ>=0)
          {
	      parent.children.add(new State(parent,spaceI+1,spaceJ));
	      childCount++;
	  }
         
         
    return childCount;
  }

  public static void displayState(State aState)
  {
	  System.out.print("\n\nState Name : "+aState.stateName);
	  
	  for(int i=0;i<3;i++)
	  {
		  System.out.println();
		  for(int j=0;j<3;j++)
		  {
			  System.out.print(aState.puzzle[i][j]+" ");
		  }
	  }
	  
  }

 
//This code is designed and written by Hrishikesh Waikar 
 public static int not(int number)
 {
	 if(number==0)
	 {
		 return 1;
	 }
	 return 0;
 }
}


 
