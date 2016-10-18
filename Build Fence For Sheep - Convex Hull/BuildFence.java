import java.util.*;
import java.lang.Math;
import java.text.DecimalFormat;

class Sheep
{
   int x;
   int y;
   int index;
   boolean posted;

   public Sheep(int x,int y,int index)
	{
	   this.x=x;
	   this.y=y;
	   this.index=index;
	   posted=false;
	}

	public Sheep(int x,int y)
	{
		this.x=x;
       	this.y=y;
		posted=false;
	}

// @Override
 public boolean equals(Sheep s)
 {
	 if(this.x==s.x && this.y==s.y)
	 {
		 return true;
	 }
	 else
		 return false;
 }

}

 class Fence
{
	public static boolean rightMostPointCovered=false;
	public static boolean leftMostPointCovered=false;
	public static boolean topMostPointCovered=false;
	public static boolean bottomMostPointCovered=false;
	public static Sheep bottomMostPoint,topMostPoint,rightMostPoint,leftMostPoint;
	public static float totalLength=0;
}

public class BuildFence 
{
	
	public static void main(String[] args) 
	{

        int i;

        Scanner in=new Scanner(System.in);

		//Take no of tests (<=100)
        int T;
		T=in.nextInt();
		ArrayList<Sheep> sheepList=new ArrayList<>();
		   LinkedList boundary=new LinkedList();
        //Put in a loop T times
    System.out.println();   
	  for(int k=0;k<T;k++)
	  {
		//System.out.println();
		//Take no of sheeps as input
		int N;
        N=in.nextInt();
        
        Fence.rightMostPointCovered=false;
	    Fence.leftMostPointCovered=false;
	    Fence.topMostPointCovered=false;
	    Fence.bottomMostPointCovered=false;

		Sheep lowestLeft=new Sheep(10001,10001);
        Sheep rightMost=new Sheep(-10001,-10001);
		Sheep topMost=new Sheep(-10001,-10001);
		Sheep leftMost=new Sheep(10001,10001);
        int xCor,yCor;
 		//Take the sheeps coordinates as input and generate the objects of sheep
         for(i=0;i<N;i++)
		 {
			 
			 xCor=in.nextInt();
			 yCor=in.nextInt();
			 //System.out.println();
             Sheep aSheep=new Sheep(xCor,yCor,i+1);
			 
		    //Find the lowest and left most point, RMP ,TMP AND LMP to begin with
			 
             sheepList.add(aSheep);
        
		     // for lowest left
		     if(lowestLeft.y>aSheep.y)
			 {
				  lowestLeft=aSheep;
			 }			
			 else if(lowestLeft.y==aSheep.y)
			 {
					if(lowestLeft.x>aSheep.x)
					 {
                        lowestLeft=aSheep;
					 }
				 	 
			 }

			 //for rightMost
			 if(aSheep.x>=rightMost.x)
			 {
				 if(aSheep.x==rightMost.x)
				 {
                     if(aSheep.y<rightMost.y)
					 {
					  rightMost=aSheep;
					 }
				 }
				 else
				   rightMost=aSheep;
			 }
			  
			 //for topMost
			 if(aSheep.y>=topMost.y)
			 {
				 if(aSheep.y==topMost.y)
				 { 
                     if(aSheep.x>topMost.x)
					 {
						 topMost=aSheep;
					 }
				 }else
				    topMost=aSheep;
			 }

			 //for leftMost
			 if(aSheep.x<=leftMost.x)
			 {
				 if(aSheep.x==leftMost.x)
				 {
					 if(aSheep.y>leftMost.y)
					 {
						 leftMost=aSheep;
					 }
				 }else
				  leftMost=aSheep;
			 }
 		 }//eo for

         System.out.println("SHEEP LIST:");
         for(int l=0;l<sheepList.size();l++)
		  {
			 System.out.println(sheepList.get(l).x+", "+sheepList.get(l).y);
		  }

		 Fence.bottomMostPoint=lowestLeft;
		 Fence.topMostPoint=topMost;
		 Fence.rightMostPoint=rightMost;
		 Fence.leftMostPoint=leftMost;
         

		 System.out.println();
		 System.out.println("bottomMost = "+Fence.bottomMostPoint.x+", "+Fence.bottomMostPoint.y);
		 System.out.println("rightMost = "+Fence.rightMostPoint.x+", "+Fence.rightMostPoint.y);
		 System.out.println("topMost = "+Fence.topMostPoint.x+", "+Fence.topMostPoint.y);
		 System.out.println("leftMost = "+Fence.leftMostPoint.x+", "+Fence.leftMostPoint.y);
     
		//Start the boundary joining process

		//ArrayList<Sheep> boundary=new ArrayList<>();
        //boundary.add(lowestLeft);
     //   int k=in.nextInt();
	    
       // System.out.println();
	 
	//	lowestLeft.posted=true;
		boundary.addFirst(lowestLeft);
        System.out.println("\nBoundary posts:\n"+Fence.bottomMostPoint.x+", "+Fence.bottomMostPoint.y);
        Sheep isNextPost=new Sheep(100001,20001);
		float totalDistance=0;
        while(lowestLeft.equals(isNextPost)==false)
		{
		
		  System.out.println("Came inside while isNextPost not equals lowestLeft");
		  Sheep lastSheep=(Sheep)boundary.getLast();
	      isNextPost=findNextPost(lastSheep,sheepList);
       
	     

	   	// k=in.nextInt(); 
		  if(isNextPost.equals(lastSheep)!=true)
		  {
			 System.out.println("inside if isNextPost equals LastSheep is not true");
			if(isNextPost.equals(lowestLeft)==true)
			  {
				System.out.println("inside if isNextPost equals lowestLeft is true");
				//add only distance , but dont add to the linked list boundary
				totalDistance=totalDistance+(float)Math.sqrt((Math.pow((isNextPost.x-lastSheep.x),2))+(Math.pow(isNextPost.y-lastSheep.y,2)));
                System.out.println("totalDistance = "+totalDistance);
				//break;
			  }else
			  {
				  System.out.println("inside if isNextPost equals lowestLeft is false");
		       isNextPost.posted=true;
			  System.out.println(isNextPost.x+" , "+isNextPost.y);	
		  
		        boundary.addLast(isNextPost);	 
                totalDistance=totalDistance+(float)Math.sqrt((Math.pow((isNextPost.x-lastSheep.x),2))+(Math.pow(isNextPost.y-lastSheep.y,2)));
				System.out.println("totalDistance = "+totalDistance);
			  }

		  }else
		  {
			  System.out.println("inside if isNextPost equals LastSheep is true");
			 
		  }
		
	    }
        
	    //print the posts on boundary
		DecimalFormat df=new DecimalFormat("0.00");
	    System.out.println(df.format(totalDistance));
		for(int j=0;j<boundary.size();j++)
		{
			Sheep jSheep=(Sheep)boundary.get(j);
			System.out.print(jSheep.index+" ");
			
		}
		System.out.println("\n");
		sheepList.clear();
		boundary.clear();
		isNextPost.posted=false;
		lowestLeft.posted=false;
	 }//eo for T
	}//eo main
    
	public static Sheep findNextPost(Sheep currentPost,ArrayList sheepList)
	{
       
       //Here we are to find the next post . It shall be the sheep positioned at point with 
	   //lowest slope value when line is drawn from currentPost to that point.
      
	  int i;

      float x1=(float)currentPost.x;
	  float y1=(float)currentPost.y;
      Sheep iSheep;
	  Sheep bestNext=currentPost;
	  float lowestSlope=1000000;
	  float xdif,dist1,dist2;
	  float iSlope;
	  float bestDistance=0;
      if(Fence.rightMostPointCovered==false)
	  {
		  //enter here if right most point is yet not been made a post. Here we seek a point from points to right of current post
		  // and have lowest slope.
          
		  for(i=0;i<sheepList.size();i++)
		  {
			 iSheep=(Sheep)sheepList.get(i);
			 
			 if(iSheep.posted==false)
		 	 {
			   float x2=(float)iSheep.x;
			   float y2=(float)iSheep.y;

               
               
			   if(x2>x1 && (y2>y1 || y2==y1))
				 {
				    //calculate slope between currentPost and iSheep
					
					xdif=x2-x1;
					if(xdif==0)
					{
						iSlope=10000001;
					}else
					 { iSlope=(y2-y1)/xdif;
					 }
					//if slope is lower than the current lowest	slope
					 	if(iSlope<lowestSlope)
						 {
						   lowestSlope=iSlope;
						   bestNext=iSheep;
						 }else if(iSlope==lowestSlope)
					      {
							 //if slopes are equal , then select the best next post as post with larger distance from current post
							 //dist1= dist betw. current and latest bestnext ,,, dist2= dist. betw current and new point with same slope
							 dist1=(float)Math.sqrt(Math.pow(bestNext.x-x1,2)+Math.pow(bestNext.y-y1,2));
                             dist2=(float)Math.sqrt((Math.pow(xdif,2))+(Math.pow(y2-y1,2)));
                             if(dist2>dist1)
							 {
								 bestNext=iSheep;
							 }

						  }

				}//eo if(x2>x1)
              
			 }//eo posted if
		 
		   }//eo for
       
	                 if(bestNext.x==Fence.rightMostPoint.x)
					   {
						 Fence.rightMostPointCovered=true;
					     return bestNext;
					   }
	   
	   /*
		if(bestNext.equals(currentPost))
		  {
		   return null;
		  }
		  */
			

	   }//eo if rightcovered
       else if(Fence.topMostPointCovered==false)
		{
             //enter here if top most point has not yet been made a post. Here we seek a point from points 
		  //above and left of current post having the lowest slope.
		  for(i=0;i<sheepList.size();i++)
		  {
			 iSheep=(Sheep)sheepList.get(i);
			 if(iSheep.posted==false)
		 	 {
			   float x2=(float)iSheep.x;
			   float y2=(float)iSheep.y;

               
               
   			     if(y2>y1 && (x2<x1 || x2==x1))//points only above and to left
				 {
				    //calculate slope between currentPost and iSheep
					xdif=x2-x1;
					if(xdif==0)
					{
						iSlope=-10000001;
					}else
					 { iSlope=(y2-y1)/xdif;
					 }
					
					//if slope is lower than the current lowest	slope
					 if(iSlope<lowestSlope)
					 {
						lowestSlope=iSlope;
						bestNext=iSheep;
					 
					  }else if(iSlope==lowestSlope)
					    {
							 //if slopes are equal , then select the best next post as post with larger distance from current post
							 //dist1= dist betw. current and latest bestnext ,,, dist2= dist. betw current and new point with same slope
							 dist1=(float)Math.sqrt(Math.pow(bestNext.x-x1,2)+Math.pow(bestNext.y-y1,2));
                             dist2=(float)Math.sqrt((Math.pow(xdif,2))+(Math.pow(y2-y1,2)));
							 
                             if(dist2>dist1)
							 {
								 bestDistance=dist2;
								 bestNext=iSheep;
							 }
							 else
							  {
								 bestDistance=dist1;
							  }

						}
 
  				}//eo if(y2>y1)
              
			 }//eo posted if
		 
		   }//eo for
       
	       if(bestNext.y==Fence.topMostPoint.y)
		   {
				 Fence.topMostPointCovered=true;
			     return bestNext;
		   }

		}//eo if topCovered 
        else if(Fence.leftMostPointCovered==false)
		{
             //enter here if left most point has not yet been made a post going from top to left. Here we seek a point 
		  // from points to left and below the current post having the lowest slope.
		  for(i=0;i<sheepList.size();i++)
		  {
			 iSheep=(Sheep)sheepList.get(i);
			 if(iSheep.posted==false)
		 	 {
			   float x2=(float)iSheep.x;
			   float y2=(float)iSheep.y;

   			     if((y1>y2 || y1==y2) && x2<x1)//points only below and to left
				 {
				    //calculate slope between currentPost and iSheep
					xdif=x2-x1;
					if(xdif==0)
					{
						iSlope=10000001;
					}else
					 { iSlope=(y2-y1)/xdif;
					 }
					

					//if slope is lower than the current lowest	slope
					 if(iSlope<lowestSlope)
					 {
						lowestSlope=iSlope;
						bestNext=iSheep;
					  }else if(iSlope==lowestSlope)
					      {
							 //if slopes are equal , then select the best next post as post with larger distance from current post
							 //dist1= dist betw. current and latest bestnext ,,, dist2= dist. betw current and new point with same slope
							 dist1=(float)Math.sqrt(Math.pow(bestNext.x-x1,2)+Math.pow(bestNext.y-y1,2));
                             dist2=(float)Math.sqrt((Math.pow(xdif,2))+(Math.pow(y2-y1,2)));
                             if(dist2>dist1)
							 {
								 bestNext=iSheep;
								 bestDistance=dist2;
							 }else
								 bestDistance=dist1;

						  }
 
  				}//eo if(y1>y2)
              
			 }//eo posted if
		 
		   }//eo for
       
	       if(bestNext.y==Fence.leftMostPoint.y)
		   {
				 Fence.leftMostPointCovered=true;
			     return bestNext;
		   }

		}//eo if leftCovered 
        else if(Fence.bottomMostPointCovered==false)
		{
             //enter here if bottom most point has not yet been made a post going from left most to bottom most. Here we seek a point 
		  // from points to bottom and right of current post having the lowest slope.
		  for(i=0;i<sheepList.size();i++)
		  {
			 iSheep=(Sheep)sheepList.get(i);
			 if(iSheep.posted==false)
		 	 {
			   float x2=(float)iSheep.x;
			   float y2=(float)iSheep.y;

   			     if(y1>y2 && (x2>x1 || x2==x1))//points only below and to right
				 {
				    //calculate slope between currentPost and iSheep
				
					xdif=x2-x1;
					if(xdif==0)
					{
						iSlope=-10000001;
					}else
					 { iSlope=(y2-y1)/xdif;
					 }
					

					//if slope is lower than the current lowest	slope
					 if(iSlope<lowestSlope)
					 {
						lowestSlope=iSlope;
						bestNext=iSheep;
					  }else if(iSlope==lowestSlope)
					      {
							 //if slopes are equal , then select the best next post as post with larger distance from current post
							 //dist1= dist betw. current and latest bestnext ,,, dist2= dist. betw current and new point with same slope
							 dist1=(float)Math.sqrt(Math.pow(bestNext.x-x1,2)+Math.pow(bestNext.y-y1,2));
                             dist2=(float)Math.sqrt((Math.pow(xdif,2))+(Math.pow(y2-y1,2)));
                             if(dist2>dist1)
							 {
								 bestNext=iSheep;
							     bestDistance=dist2;
							 }else
								 bestDistance=dist1;

						  }
 
  				}//eo if(y1>y2)
              
			 }//eo posted if
		 
		   }//eo for
       
	       if(bestNext.y==Fence.bottomMostPoint.y)
		   {
				 Fence.bottomMostPointCovered=true;
			     return bestNext;
		   }

		}//eo if leftCovered 


        Fence.totalLength=bestDistance;
        return bestNext;

	}//end of findNextPost() method

}//eo BuildFence class
