import java.util.*;


class KNode
{
	float psum,wsum;
	float upperbound;
	int[] considered;
	int generation;
	boolean valid=true;
	KNode leftChild,rightChild;

	//the constructor gets an array describing items it can take limited by 
	//current generation
	public KNode(int[] considered,int generation)
	{
		this.considered=considered;
		this.generation=generation;

		//calculate the wsum and psum of this node
		int i=0;
		float wsum=0;
		float psum=0;
		//System.out.println("Inside Constructor");
		
		// get the w and p arrays
		float[] w=KnapsackBranchAndBound.w;
		float[] p=KnapsackBranchAndBound.p;
		int max=KnapsackBranchAndBound.M;
		
		while(i<generation)
		{
			if(considered[i]==1)
			{
				wsum=wsum+w[i];
				psum=psum+p[i];
			}
			i++;

		}//eo while


		this.wsum=wsum;
		this.psum=psum;
		//System.out.println("Wsum= "+wsum+" psum= "+psum);


		if(wsum>max)
		{
			valid=false;
		}

		if(valid==true)
		{
			if(psum>KnapsackBranchAndBound.bestprofit)
			{
				//System.out.println("Got a better node");
				KnapsackBranchAndBound.bestprofit=psum;
				KnapsackBranchAndBound.bestsofar=this;
			}
		}
		//calculate the upperBound
		
		this.upperbound=calculateUB(w,p,max);
		//System.out.println("Upperbound"+this.upperbound);


	}//eo constructor

	public float calculateUB(float[] w,float[] p,int max)
	{
		float wSum=0;
		float upperbound=0;
		int i=0;

		while(wSum+w[i]<=max)
		{
			wSum=wSum+w[i];
			upperbound=upperbound+p[i];
			i++;
		}

		float remaining=max-wSum;
		upperbound=upperbound+((remaining/w[i])*p[i]);

		return upperbound;
	}
}


public class KnapsackBranchAndBound
{
	public static float[] p;  // Profits
	public static float[] w;  // Weights
	public static KNode bestsofar; // Will store the node with highest profit 
	public static float bestprofit; //Value of highest profit
	public static int N; // No. of Items
	public static ArrayList<KNode> liveNodes; // List of open/live nodes
	public static int M; // Max capacity
	
	public static void main(String[] args)
	{
		
		Scanner in=new Scanner(System.in);
		System.out.println("\nNo of Items?");
		N=in.nextInt();
		
		p=new float[N];
		w=new float[N];

		System.out.println("Enter the weights and profits of the items : ");
		
		for(int i=0;i<N;i++)
		{
			w[i]=in.nextFloat();
			p[i]=in.nextFloat();
		}
	
		System.out.println("Enter the Knapsack Capacity?");
		M=in.nextInt();
		//print items p and w
		System.out.println("\nThe items are ");
		for(int i=0;i<N;i++)
		{
			System.out.println("w = "+w[i]+" p = "+p[i]);
		}
		

		
		//In branch and bound the state space tree's root node should start with the item with highest p/w value
		//and consecutive items in each level should be taken in decreasing value of their p/w value

		//sort and store items by p/w


		for(int i=0;i<N;i++)
		{
			for (int j=i+1;j<N ;j++ )
			{
				if ((p[j]/w[j])>(p[i]/w[i]))
				{
					float tempp=p[i];
					float tempw=w[i];
					p[i]=p[j];
					w[i]=w[j];
					p[j]=tempp;
					w[j]=tempw;
		
				}
			}//eo for j
		}//eo for i

		System.out.println("\nSorted based on p/w :\n");
		//print items p and w
		for(int i=0;i<N;i++)
		{
			System.out.println("w = "+w[i]+"  p = "+p[i]);
		}

	
		//Begining of the core logic of branch and bound

		//We shall mainitain a list of live nodes.
		//In each iteration obtain the best node based on highest upperbound is obtained
	//Create children for that node and if the children are valid ,and add them to the livenodes list
		//terminate the loop if we obtain maximumprofit so fa higher than highest upperbound value in livenodes
		//that node associated with the maxprofit so far is the best node and therefore the solution vector


		liveNodes=new ArrayList<KNode>(); 

		int[] considered=new int[N]; //this is the array that stores the items that will be considered in a particular node

		for(int i=0;i<N;i++)
		{
			considered[i]=1;
		}
		
		KNode root=new KNode(considered,0);

		liveNodes.add(root);

		//Core logic of branch and bound

		KNode currentBest=root;

		//run the loop as long as liveNodes is not empty but mainly 
		//as long as we dont get profit value higher than all the upperbounds
		bestprofit=0;
		
		while(liveNodes.size()>0 && currentBest.upperbound>bestprofit)
		{
		   
		   if(currentBest.generation<N)
		   {
			   createChildren(currentBest);
		   }
		  // System.out.println("createdChildren now getting best");
		   currentBest=getCurrentBest();
		}

		
		
		System.out.println("\n\nOptimum solution");
		for(int i=0;i<N;i++)
		{
			if(i<bestsofar.generation)
			{
				System.out.print(bestsofar.considered[i]+" ");
			}else
			{
				int j=0;
				System.out.print(j+" ");
			}
		}

		System.out.println("\n\nOptimum Solution elements");
		for(int i=0;i<N;i++)
		{
			if(bestsofar.considered[i]==1 && i<bestsofar.generation)
			{
				System.out.println("W= "+w[i]+" P= "+p[i]);
			}
		}

		System.out.println("\nBestprofit "+bestprofit);

		

		//now we have got the bestNode
		
	
	}//eo main

	public static KNode getCurrentBest()
	{
		//basically in this method we pop the node with highest upperbound
		KNode best=liveNodes.get(0);
		for (KNode anode:liveNodes)
		{
			if(anode.upperbound>best.upperbound)
			{
				best=anode;
			}
		}
		int index=liveNodes.indexOf(best);
		liveNodes.remove(index);

		return best;
	}//eo getCurrentBest


	//Method to create children of a node 

	public static void createChildren(KNode currentBest)
	{
		int[] considered=currentBest.considered; 
		int gen=currentBest.generation;
		int[] leftconsidered=new int[N]; //will store the index of items that are to be considered by left node
		int[] rightconsidered=new int[N];// store the index of items to be considered in right noode

		//System.out.println("Inside createChildren for child of generation "+gen);

		//make copy of considered in left and right
		for (int i=0;i<N ;i++)
		{
			leftconsidered[i]=considered[i];
			rightconsidered[i]=considered[i];
		}

		
		//create left child

		KNode leftChild=new KNode(leftconsidered,gen+1);

		//create right child
		//exclude object indexed by gen from right child
		//for example , root will exclude object 0 from right child

		rightconsidered[gen]=0;

		KNode rightChild=new KNode(rightconsidered,gen+1);

		//add the children to the parent
		currentBest.leftChild=leftChild;
		currentBest.rightChild=rightChild;
		
		//If the children are valid , add them to the list of live nodes
		if(leftChild.valid)
		{
			liveNodes.add(leftChild);
		}

		if(rightChild.valid)
		{
			liveNodes.add(rightChild);
		}
		
		
	}//eo createchildren
}