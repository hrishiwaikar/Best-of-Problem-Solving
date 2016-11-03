import java.util.*;

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

class State{

 static int stateCount;
 int[][] puzzle;
 int fn,gn,hn;
 int stateName;
 State[] children;
 State parent;
 boolean isRedundantState;
 int spacePositionI;
 int spacePositionJ;

  public State(State parent,int tiletoswapI,int tiletoswapJ)
  {
	  puzzle=new int[2][2];
	  stateCount=stateCount+1;
	  stateName=stateCount;
	  this.parent=parent;
	  children=new State[2];
	  
      //make this state by modifying the parent state by swapping space postion with tiletoswap
      
      for(int i=0;i<2;i++)
	  {
		  for(int j=0;j<2;j++)
		  {
			  this.puzzle[i][j]=parent.puzzle[i][j];
		  }
	  }

	  //swap tiletoswap with current space position which is still parent's space position

      //spacePositionI=parent.spacePositionI;
	 // spacePositionJ=parent.spacePositionJ;
	 //int[][] temp;
	// temp=puzzle[parent.spacePositionI];
	
	 puzzle[parent.spacePositionI][parent.spacePositionJ]=puzzle[tiletoswapI][tiletoswapJ];
     puzzle[tiletoswapI][tiletoswapJ]=parent.puzzle[parent.spacePositionI][parent.spacePositionJ];

	 this.spacePositionI=tiletoswapI;
	 this.spacePositionJ=tiletoswapJ;


      //calculate gn ,hn and fn
	  calculategn();
	  calculatehn();
	  calculatefn();
      
  }
  public State()
  {
	  puzzle=new int[2][2];
	  this.parent=null;
	  this.gn=0;
	  children=new State[2];
      
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
	  int iCount=1;
     for(int i=0;i<2;i++)
	  {
		 for(int j=0;j<2;j++)
		 {
			 //System.out.println("iCount : "+iCount);
			 //System.out.println("puzzle"+i+j+"="+puzzle[i][j]);
			 if(!(i==1 && j==1) && this.puzzle[i][j]!=iCount)
			 {
				 
				//System.out.println("Came here ,puzzle"+i+j+"= "+this.puzzle[i][j]+" iCount= "+iCount);
				 hn=hn+1;
			 }else if((i==1 && j==1) && this.puzzle[i][j]!=0)
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
	  for(int i=0;i<2;i++)
	  {
		  for(int j=0;j<2;j++)
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

public class FourPuzzle{

  //static State goalState;
  public static void main(String[] args)
  {
    //input start state and goal state and store them in start and goal objects of class State
    
	/*goalState=new State();
	goalState.stateName=99;
    goalState.puzzle[0][0]=1;
	goalState.puzzle[0][1]=2;
	goalState.puzzle[1][0]=3;
	goalState.puzzle[1][1]=0;
	*/
    int i,j;

	State startState=new State();

	//Accept Start State
	System.out.println("Enter the Start State");
    Scanner in=new Scanner(System.in);
	for(i=0;i<2;i++)
	{
		System.out.println();
		for(j=0;j<2;j++)
		{
			System.out.println("p["+i+"]["+j+"]=");
            startState.puzzle[i][j]=in.nextInt();
			if(startState.puzzle[i][j]==0)
			{
				startState.spacePositionI=i;
				startState.spacePositionJ=j;
			}
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

	 //  System.out.println("Displaying currentState in while loop");
	 //  displayState(currentState);

	   if(currentState.hn==0)
	   {
		   solutionState=currentState;
		   closedList.add(currentState);
		   
		   //break out of this loop and do something to find it's parents.
		   break;
	   }else{

		  // System.out.println("came inside else");
           
		   createChildren(currentState);

           for(i=0;i<2;i++)
		   {
			   if(currentState.equals(startState))
			   {
				   openList.add(currentState.children[i]);
			   }else if(!currentState.parent.equals(currentState.children[i]))
			   {
				   openList.add(currentState.children[i]);
			   }
		   }
		   closedList.add(currentState);
	   }   
	   
	}//eo while
    
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

  public static void createChildren(State parent)
  {
	  int spaceI=parent.spacePositionI;
	  int spaceJ=parent.spacePositionJ;
      
	  parent.children[0]=new State(parent,not(spaceI),spaceJ);
	  //displayState(parent.children[0]);
	  parent.children[1]=new State(parent,spaceI,not(spaceJ));	
	 // displayState(parent.children[1]);
	  
  }

  public static void displayState(State aState)
  {
	  System.out.print("\n\nState Name : "+aState.stateName);
	  
	  for(int i=0;i<2;i++)
	  {
		  System.out.println();
		  for(int j=0;j<2;j++)
		  {
			  System.out.print(aState.puzzle[i][j]+" ");
		  }
	  }
	  
  }

 
 public static int not(int number)
 {
	 if(number==0)
	 {
		 return 1;
	 }
	 return 0;
 }
}


 
