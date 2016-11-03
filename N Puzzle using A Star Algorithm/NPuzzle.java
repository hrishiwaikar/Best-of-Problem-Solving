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

 static long stateCount;
 static int N;
 static HomePosition[] homePosition;
 int[][] puzzle;
 int fn,gn,hn;
 long stateName;
 //State[] children;
 ArrayList<State> children;
 State parent;
 boolean isRedundantState;
 int spacePositionI;
 int spacePositionJ;

  public State(State parent,int tiletoswapI,int tiletoswapJ)
  {
	  puzzle=new int[N][N];
	  if(stateCount<1000000)
	  {
	  stateCount=stateCount+1;
	  }else
	  {
		  stateCount=0;
	  }
	  stateName=stateCount;
	  this.parent=parent;
	  //children=new State[2];
          children=new ArrayList<State>();
	  
      //make this state by modifying the parent state by swapping space postion with tiletoswap
      
      for(int i=0;i<N;i++)
	  {
		  for(int j=0;j<N;j++)
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
  public State()
  {
	  puzzle=new int[N][N];
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

	  for(int i=0;i<N;i++)
	  {
		  for(int j=0;j<N;j++)
		  {
			  no=this.puzzle[i][j];
			  distance=Math.abs(i-homePosition[no].I)+Math.abs(j-homePosition[no].J);
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
	  for(int i=0;i<N;i++)
	  {
		  for(int j=0;j<N;j++)
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



public class NPuzzle{


  public static void main(String[] args)
  {
    //input start state and goal state and store them in start and goal objects of class State
    

    int i,j,puzzleDimension;
    Scanner in=new Scanner(System.in);
	
   
    System.out.println("Enter the dimension of the Puzzle (eg. for 8 puzzle put 3 as it's 3x3 puzzle) :");
	puzzleDimension=in.nextInt();
	State.N=puzzleDimension;
	int tileCount=puzzleDimension*puzzleDimension;

	//Accept Start State
	State startState=new State();
	System.out.println("Enter the Start State");
    
	int iCount=1;
	State.homePosition=new HomePosition[tileCount];
	for(i=0;i<puzzleDimension;i++)
	{
		//System.out.println();
		for(j=0;j<puzzleDimension;j++)
		{
			//System.out.println("p["+i+"]["+j+"]=");
            startState.puzzle[i][j]=in.nextInt();
			if(startState.puzzle[i][j]==0)
			{
				startState.spacePositionI=i;
				startState.spacePositionJ=j;
			}
			if(iCount<tileCount)
			{
				State.homePosition[iCount]=new HomePosition(i,j);

			}else if(iCount==tileCount)
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
	long closedChildrenCount=0;
	long totalOpenChildren=1;
	long openChildrenCount=1;
	
	
	while(true)
	{
	   State currentState=openList.poll();
	   openChildrenCount--;
      // System.out.println("state name "+currentState.stateName);
	  // System.out.println("Displaying currentState in while loop");
	 //  displayState(currentState);

	   if(currentState.hn==0)
	   {
		   solutionState=currentState;
		   closedList.add(currentState);
		   closedChildrenCount++;
		   
		   //break out of this loop and do something to find it's parents.
		   break;
	   }else{

		  // System.out.println("came inside else");
           
		  int childCount= createChildren(currentState);
          //System.out.println("Child Count"+childCount);
           for(i=0;i<childCount;i++)
		   {
			   //if(currentState.equals(startState))
			  // {
				//   openList.add(currentState.children.get(i));
			  // }else if(!currentState.parent.equals(currentState.children.get(i)))
			 //  {
				  // System.out.println();
				  // displayState(currentState.children.get(i));
				   openList.add(currentState.children.get(i));
				   totalOpenChildren++;
				   openChildrenCount++;
			 //  }
		   }
		   closedList.add(currentState);
		   closedChildrenCount++;
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

    //System.out.println("\n\nSolution Steps :");

	int step=1;
	while(solution.size()>0)
	{
	  System.out.println("\n\nStep "+step);	  
      State aState=solution.removeLast();

      displayState(aState);
	  step++;

	}

	//System.out.println("\n\nTotal Open Children ever : "+totalOpenChildren);
	//System.out.println("Open Children Count : "+openChildrenCount);
	//System.out.println("Closed Children Count :"+closedChildrenCount);

  }//eo main

  public static int createChildren(State parent)
  {
	  int spaceI=parent.spacePositionI;
	  int spaceJ=parent.spacePositionJ;
	  int grandParentI=99;
	  int grandParentJ=99;
	  if(parent.parent!=null)
	  {
	   grandParentI=parent.parent.spacePositionI;
	   grandParentJ=parent.parent.spacePositionJ;
	  }

          int childCount=0;
          /*
	  parent.children[0]=new State(parent,not(spaceI),spaceJ);
	  //displayState(parent.children[0]);
	  parent.children[1]=new State(parent,spaceI,not(spaceJ));	
	 // displayState(parent.children[1]);
	  */

         //if i-1,j
           if(spaceI-1>=0 && spaceJ>=0 && !(spaceI-1==grandParentI && spaceJ==grandParentJ))
           {
              parent.children.add(new State(parent,spaceI-1,spaceJ));
              childCount++;
	   }         
	//if i , j-1
           if(spaceI>=0 && spaceJ-1>=0 && !(spaceI==grandParentI && spaceJ-1==grandParentJ))
           {
              parent.children.add(new State(parent,spaceI,spaceJ-1));
	      childCount++;
	   }
	 //if  i,j+1
           if(spaceI>=0 && spaceJ+1<=State.N-1 && !(spaceI==grandParentI && spaceJ+1==grandParentJ))
           {
              parent.children.add(new State(parent,spaceI,spaceJ+1));
	      childCount++;
	   }
	 //if i+1,j	
	  if(spaceI+1<=State.N-1 && spaceJ>=0 && !(spaceI+1==grandParentI && spaceJ==grandParentJ))
          {
	      parent.children.add(new State(parent,spaceI+1,spaceJ));
	      childCount++;
	  }
         
         
    return childCount;
  }

  public static void displayState(State aState)
  {
	 // System.out.print("\nState Name : "+aState.stateName);
	  
	  for(int i=0;i<State.N;i++)
	  {
		  System.out.println();
		  for(int j=0;j<State.N;j++)
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


 
