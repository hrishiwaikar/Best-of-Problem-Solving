import java.util.*;

class State
{
	static int stateCount;
	int stateName;
	String [][] board;
	State parent;
	ArrayList<State> childList;
	int validQueenCount;
	Queen stateQueen; //the queen that the state is trying to install in itself and is later successful with it
    //Stack<Queen> queenStack;
    boolean isValid;
    
    public State(Queen q)
	{
        board=new String[8][8];
		parent=null;
		stateCount=stateCount+1;
		stateName=stateCount;
		stateQueen=q;
        
		validQueenCount=0;
        
		for (int i=0;i<8 ;i++ )
		{
			for( int j=0;j<8;j++)
			{
				board[i][j]=" ";
			}
		}//eo of board creation

		childList=new ArrayList<State>();
		isValid=true;
	}//eo start space constructor


	public State(State parent,int x,int y,Queen q)
	{
		this.parent=parent;
		stateCount++;
		stateName=stateCount;
		board=new String[8][8];
		childList=new ArrayList<State>();
		stateQueen=q;

	   	//1. Copy the parent state's board into that of current state
        for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				board[i][j]=parent.board[i][j];
			}
		}

	//	System.out.println("After copying the board , before calling consolidation");

		//2. Add the new queen and consolidate her and update queenCount from that of parent's 
		//3. Store if the state is valid or invalid based on the result of consolidation method
		validQueenCount=parent.validQueenCount;
     //   System.out.println("ValidQueen count of parent and this state before consolidation "+validQueenCount);

		isValid=consolidateQueen(x,y,q);
		
	}

	public boolean consolidateQueen(int x,int y,Queen q)
	{
		int i,j;
		//install queen at position x and y
        
        if(board[x][y].equals(" "))
		{
			board[x][y]=q.name;
		}else{
			return false; //will never come here as no child will be formed for this position which is already filled
		}

	//	System.out.println("Inside consolidate Queen");
    //    System.out.println("Position of queen "+q.name+" is at "+x+" "+y);
		//then mark her path with her crosses while checking that there is no intersection

        //we have to check horizontal , vertical and diagonal ( two diagonal) paths that emenate from the queen
		
		//vertical check
        for(i=0;i<8;i++)
		{
		  if(i!=x)
		  {
            if(board[i][y].equals(" "))
		    {
			  board[i][y]=q.xName;
		     }else if(board[i][y].equals(parent.stateQueen.name))	 
			 {
              return false;
		     }
		   }
		}//eo of for horizontal

	   //horizontal check
	   for(j=0;j<8;j++)
		{
          if(j!=y)
		  {
		   if(board[x][j].equals(" "))
		   {
			   board[x][j]=q.xName;
		   }else if(board[x][j].equals(parent.stateQueen.name))
		   {
			   return false;
		   }
		  }
	   }

   //   System.out.println("Completed horizontal and vertical checks");
	   //diagonal checks
	   //upward right:
       i=x-1;
	   j=y+1;

	   while(i>=0 && j>=0 && i<=7 && j<=7)
	   {
		  if(board[i][j].equals(" "))
		  {
              board[i][j]=q.xName;
		  }else if(board[i][j].equals(parent.stateQueen.name)){
			  return false;
		  }
		  i=i-1;
		  j=j+1;
	   }

	   //upward left
	   i=x-1;
	   j=y-1;
	   
	    while(i>=0 && j>=0 && i<=7 && j<=7)
		{
			if(board[i][j].equals(" "))
			{
				board[i][j]=q.xName;
			}else if(board[i][j].equals(parent.stateQueen.name)){
				return false;
			}
			i=i-1;
			j=j-1;
		}

		//downward right
       i=x+1;
	   j=y+1;
	   
	    while(i>=0 && j>=0 && i<=7 && j<=7)
		{
			if(board[i][j].equals(" "))
			{
				board[i][j]=q.xName;
			}else if(board[i][j].equals(parent.stateQueen.name)){
				return false;
			}
			i=i+1;
			j=j+1;
		}

		//downward left
	   i=x+1;
	   j=y-1;
	   
	    while(i>=0 && j>=0 && i<=7 && j<=7)
		{
			if(board[i][j].equals(" "))
			{
				board[i][j]=q.xName;
			}else if(board[i][j].equals(parent.stateQueen.name)){
				return false;
			}
			i=i+1;
			j=j-1;
		}

	//	System.out.println("Completed diagonal checks");
		if(validQueenCount<7)
		{
        validQueenCount++;
		}

	//	System.out.println("ValidQueen count this state after consolidation "+validQueenCount);
		return true;
	}

}

class Queen
{
  String name;
  String xName;

  public Queen(String name,String xName)
  {
	  this.name=name;
	  this.xName=xName;
  }
}

class EightQueenUltimate 
{
	static Queen[] queenArray;
	static boolean foundsolution;
	static ArrayList<State> solutionStates;
	static int solutionCount;
	public static void main(String[] args) 
	{
		solutionCount=0;
		Scanner in=new Scanner(System.in);
		//create the queen array and the four queens
		 queenArray=new Queen[8];
        queenArray[0]=new Queen("Q1","X");
		queenArray[1]=new Queen("Q2","X");
		queenArray[2]=new Queen("Q3","X");
		queenArray[3]=new Queen("Q4","X");
		queenArray[4]=new Queen("Q5","X");
		queenArray[5]=new Queen("Q6","X");
		queenArray[6]=new Queen("Q7","X");
		queenArray[7]=new Queen("Q8","X");
        
		solutionStates=new ArrayList<State>();
		//Create the initial state
		State initialState=new State(queenArray[0]);

	//	System.out.println("Initial State is formed and calling recQueen");
        
		//a function where branch and bound on 4 queen will take place

        foundsolution=false;
		recursiveQueen(initialState);

		System.out.println("No of solution states : "+solutionStates.size() );

        

       for(State solution: solutionStates)
		{
		   System.out.println();
		   displayState(solution);
		   System.out.println("Solution state : "+solutionStates.indexOf(solution));
		   int k=in.nextInt();

		}

	}

	//System.out.println("");

	public static void recursiveQueen(State s)
	{
		 
        //create children of this state :
		//create one child at a time . After child is created , if the state is valid then 
		// call recursiveQueen on that child 
//		System.out.println("Inside recQueen method of state "+s.stateName);
        Queen nextQueen=queenArray[s.validQueenCount];
		//childcreation
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
             
			   if (solutionCount>90)
			   { 
			     break;
			   }
			  
				
				if(s.board[i][j].equals(" "))
				{
		//			System.out.println("Inside if s.board...equals space");
					//create a child at this location
					State aNewChild=new State(s,i,j,nextQueen);
					
					// if the child is valid ,add it to the childList of this state, and proceed for recursive call

                    if(s.validQueenCount==7 && aNewChild.isValid==true)
					{
						//display this solution 
						State solutionState=aNewChild;
						if(solutionStates.size()>0)
						{
						  boolean isNewSolution=checkSolution(solutionState);
						  if(isNewSolution==true)
						  {  
						   solutionStates.add(solutionState);
						   solutionCount++;
						   System.out.println("Solution Count = "+solutionCount);
						  }
						}else{
							solutionStates.add(solutionState);
						   solutionCount++;
						   System.out.println("Solution Count = "+solutionCount);
						}
						
		    		//	displayState(solutionState);
		//				System.out.println("Came at the solution if in state "+s.stateName);
                        foundsolution=true;
					//	break;
					}else if(aNewChild.isValid==true)
					{
					   recursiveQueen(aNewChild);
					  
					}					
				}
			}//eo j for
		}//eo i for


	}//eo recursive queen
   
   public static void displayState(State s)
   {
	   for(int i=0;i<8;i++)
	   {
		   System.out.println();
		   System.out.println();
		   for (int j=0;j<8 ;j++)
		   {
			   if(!(s.board[i][j].equals("Q1") || s.board[i][j].equals("Q2") || s.board[i][j].equals("Q3")
				   || s.board[i][j].equals("Q4") || s.board[i][j].equals("Q5") || s.board[i][j].equals("Q6")
				   || s.board[i][j].equals("Q7") || s.board[i][j].equals("Q8")))
			   {
				   s.board[i][j]=" X ";
				    System.out.print(s.board[i][j]+" ");
			   }else{

                    System.out.print(" "+s.board[i][j]+" ");
			   }
		   }
	   }
	   System.out.println("\n");

   }

   public static boolean checkSolution(State asolution)
   {
	  boolean isSame;
	  for(State existingSolution : solutionStates)
	  {
		 isSame=true;
	     for(int i=0;i<8;i++)
	     {
		   for(int j=0;j<8;j++)
		   {
			   if(!existingSolution.board[i][j].equals(asolution.board[i][j]))
			   {
				   if(asolution.board[i][j].equals("X") || existingSolution.board[i][j].equals("X"))
				   {
				     isSame=false;
				   }
	           }
		    }//eo for j
	     }//eo for i
         if(isSame==true)
		 {
			 return false;
		 }
	  }//eo outer for

	  return true;
   }// eo method checkSolution
}
