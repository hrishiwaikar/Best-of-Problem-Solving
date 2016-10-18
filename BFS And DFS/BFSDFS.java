
import java.io.*;
import java.util.Scanner;

class Node
{
  int data;
  Node leftChild;
  Node rightChild;
  Node next;

  public Node(int data)
  {
    this.data=data;
    leftChild=null;
    rightChild=null;
    next=null;
  
  }
  
}
class Queue
{
   Node head,tail;
   
   public Queue(Node head)
   {
      this.head=head;
      this.tail=head; 
   }
   
   public void enQueue(Node node)
   {
      if(head==null)
      { 
        head=node;
        tail=node;
      }
      else
      {
		  if(head==tail)//condition if only one node in the queue at head
		  {
			  head.next=node;
			  tail=node;
		  }
		  else
		  {
            tail.next=node;
            tail=node;
          }
         /* Node current;
          current=head;
          while(current.next!=null)
          {
             current=current.next;
          }
          */
      }
   }//eo enqueue
    
   public Node deQueue()
   {
       if(head==null)
       {
          return null;
       }
      else
       { 
           Node temp;
           temp=head;   
           head=head.next;
           temp.next=null;
           return temp;   
       }
   }//eo of dequeue
   
   public Node ifEmpty()
   { 
       return head;        
   }
}//eo Queue class

                          

class Tree
{
   Node root;
   Queue q;
     public Tree(int data)
     {
       root=new Node(data);
        q=new Queue(root);
       addNodes(root);
     }
   public void addNodes(Node node)
   {
     Scanner in=new Scanner(System.in);
     int b;
        
		 if(node!=null)
		 {
		   if(node.leftChild==null)
           {
            System.out.println("\nEnter leftChild of "+node.data);
            
            b=in.nextInt();
            if(b!=666)
            {
			 Node temp1=new Node(b);
             node.leftChild=temp1;
            }
           }//eo leftchild condition
           if(node.rightChild==null)
           {
            System.out.println("\nEnter rightChild of "+node.data);
            
            b=in.nextInt();
            if(b!=666)
            {
			 Node temp2=new Node(b);
             node.rightChild=temp2;
            }
           }// eo right child cond.
	     }//eo if
    
          if(node.leftChild!=null)
          {
             addNodes(node.leftChild);
          }
          if(node.rightChild!=null)
          {
             addNodes(node.rightChild);
          }
   }//eo addNodes
        
  public void bfs(Node node)
  {
    if(q.ifEmpty()==null)// add root to queue if q is empty
    {
       q.enQueue(node);
    }    
    else 
    { //add children if any to q
	   if(node!=null)
	   {
        if(node.leftChild!=null)
        {
           q.enQueue(node.leftChild);
        }
        if(node.rightChild!=null)
        {
          q.enQueue(node.rightChild);
        }
	  }//eo if
	 }//eo node
   //display Node and dequeue it
    System.out.println("\t"+q.deQueue().data);
    
	//BFS on children
    if(node.leftChild!=null)
    {
       bfs(node.leftChild);
    }
    if(node.rightChild!=null)
    {
       bfs(node.rightChild);
    }
   
  }//eo bfs     
      
}
public class BFSDFS
{
   public static void main(String[] args)
   {
	   Scanner in=new Scanner(System.in);
	   int val;
       System.out.println("BFS");
	   System.out.println("\nEnter the root node ");
	   val=in.nextInt();
       Tree t=new Tree(val);
	   t.bfs(t.root);
    }// end of main
}// end of bfsdfs class
