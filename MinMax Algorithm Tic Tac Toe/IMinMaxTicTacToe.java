import java.util.*;

class State
{
	static int nameCount;
	int name;
    char[][] board;
    State parent;
	State successor;
	char stateActor;
	ArrayList<State> childList;
    int minimaxValue;
    boolean isTerminal=false;
	char sameChar;
	int spaces;
	int charPositionX;
	int charPositionY;
	public State()
	{
		nameCount=0;
		this.name=nameCount;
		board=new char[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				board[i][j]=' ';
			}
		}
		this.parent=null;
		childList=new ArrayList<State>();
        minimaxValue=0;
        isTerminal=false;
		successor=null;
		spaces=9;
		//stateActor='X';
	}//eo basic constructor

    public State(State parent, int x,int y, char stateActor)
    {
       this.parent=parent;
       nameCount=nameCount+1;
	   this.name=nameCount;
	   board=new char[3][3];
       this.stateActor=stateActor;
	   spaces=parent.spaces-1;
	   minimaxValue=0;

	   charPositionX=x;
	   charPositionY=y;
	   //copy this board's configuration from parent's configuration and later only add that single X or O
              
       for(int i=0;i<3;i++)
	   {
			for(int j=0;j<3;j++)
			{
				board[i][j]=parent.board[i][j];
			}
	   }

		board[x][y]=stateActor;
	    isTerminal=checkTerminality();
		if(!isTerminal)
		{
			childList=new ArrayList<State>();
		}

		//minimaxValue=0;
		successor = null;

	}

    
	//method to check if this state is terminal;
	public boolean checkTerminality()
	{

		for(int k=0;k<3;k++)
		{
			if(areSame(board[k][0],board[k][1],board[k][2],stateActor) || areSame(board[0][k],board[1][k],board[2][k],stateActor))
			{
                if(stateActor=='X')
				{
					minimaxValue=10;
				}else{
					minimaxValue=-10;
				}

				//System.out.println("Game over with minimax value "+minimaxValue);
			    return true;
			}
		}
		
		if(areSame(board[0][0],board[1][1],board[2][2],stateActor) || areSame(board[0][2],board[1][1],board[2][0],stateActor))
		{
			   if(stateActor=='X')
				{
					minimaxValue=10;
				}else{
					minimaxValue=-10;
				}

				//System.out.println("Game over with minimax value "+minimaxValue);
              return true;
		}

		//if the board is full ,then it terminates even if no one has won .So its a tie and minimaxValue=0;
		if(spaces==0)
		{
			minimaxValue=0;
			//System.out.println("Game over with minimax value "+minimaxValue);
			return true;
		}

		return false;
	}//

	private boolean areSame(char a,char b,char c,char d)
	{
		//char checkValue=d;
		if(a==d && b==d && c==d)
		{
			sameChar=d;
			return true;
		}
		return false;
	}
}//eo class State


public class IMinMaxTicTacToe
{
	static Scanner in;
	public static void main(String[] args)
    {
	   in=new Scanner(System.in);
	   State initialState=new State();

       //start the min max algo on initial state
	   initialState.minimaxValue=recursiveMax(initialState);
       
	   System.out.println("Choose Tic Tac Toe game :");
       System.out.println("1. Computer Vs Computer :Press 1");
	   System.out.println("2. You Vs Computer : Press 2");
       int choice=in.nextInt();
	   if(choice==1)
		{
	      compVsComp(initialState);
		}else if(choice==2)
		{
			youVsComp(initialState);

		}


       

	}// eo of main

	public static void youVsComp(State initialState)
	{
		System.out.println("Computer is X , you are O");
		System.out.println("Enter i and j coordinates of your move , eg: 0 1\n");
		//Here we allow the first action to be taken by Computer who is X

		State computerAction, userAction;
        State lastAction=null;
		computerAction=initialState.successor;
		while(true)
		{
		  
          displayMove(computerAction);
		  if(computerAction.isTerminal)
		  {
			lastAction=computerAction;
			break;
		  }

		  userAction=getUserAction(computerAction);
          displayMove(userAction);
		  if(userAction.isTerminal)
		  {
			  lastAction=userAction;
			  break;
		  }

		  computerAction=userAction.successor;
		}//eo while  
       
	    System.out.println();
	    if(lastAction.minimaxValue>0)
		{
			System.out.println("X has won");
		}else if(lastAction.minimaxValue<0)
		{
			System.out.println("O has won");
		}else if(lastAction.minimaxValue==0)
		{
			System.out.println("It's a tie");
		}
		
	}

	public static State getUserAction(State s)
	{
		System.out.println("\nEnter the location where you want to put O");
		int x=in.nextInt();
		int y=in.nextInt();

        State userChoiceState=findChild(s,x,y);
		return userChoiceState;
	}

	public static State findChild(State s,int x,int y)
	{
	  
       for(State child: s.childList)
	   {
		   if(child.charPositionX==x && child.charPositionY==y)
		   {
			   return child;
		   }
	   }
	   return null;
	}

	public static void compVsComp(State initialState)
	{
		 System.out.println("Computer Vs Computer , printing sequence of steps");
       //display the sequence of steps
	   State currentState=initialState;
	   while(currentState!=null)
	   {
           displayMove(currentState);
		   currentState=currentState.successor;
	   }
       if(initialState.minimaxValue==0)
		{
		   System.out.println("\nIt's a tie!!!"); 
		}

	}

	public static int recursiveMax(State s)
    {
		//displayMove(s);
        if(s.isTerminal)
		{
			return s.minimaxValue;
		}
        
		// if s is non terminal , create children
		createChildren(s,'X');
        s.successor=s.childList.get(0);
		for(State child: s.childList)
		{
			child.minimaxValue=recursiveMin(child);
			
			if(child.minimaxValue>s.successor.minimaxValue)
			{
				s.successor=child;
			}
		}
        
		return s.successor.minimaxValue;
	}

	public static int recursiveMin(State s)
    {
        //displayMove(s);
        if(s.isTerminal)
		{
			return s.minimaxValue;
		}
        
		// if s is non terminal , create children
		createChildren(s,'O');
        s.successor=s.childList.get(0);
		for(State child: s.childList)
		{
			child.minimaxValue=recursiveMax(child);
			if(child.minimaxValue<s.successor.minimaxValue)
			{
				s.successor=child;
			}
		}

		return s.successor.minimaxValue;
	}

	public static void createChildren(State s,char stateActor)
	{
        int childCount=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(s.board[i][j]==' ')
				{
                   s.childList.add(new State(s,i,j,stateActor));
				   childCount++;
				}
			}
		}
		//System.out.println(childCount);
	}//eo createChildren

	public static void displayMove(State s)
	{
		if(s.name==0)
		{
		  System.out.println("\nBlank Board : ");
		}else
		{
		  System.out.println("Move by "+s.stateActor+" : ");
		}
		//System.out.println("State name :"+s.name+" , state's minimaxValue : "+s.minimaxValue+" Terminal State: "+s.isTerminal);
       for (int i=0;i<3 ; i++)
       {
		   System.out.print("\n");
		   for (int j=0;j<3 ;j++ )
		   {
             System.out.print(s.board[i][j]+" ");
		   }
       }
	   System.out.println();
	}
}//eo class  

