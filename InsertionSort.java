import java.util.*;
class InsertionSort 
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);

		int[] a=new int[6];
		int i,j;
        
		//just scanning the array elements into the array
		for(i=0;i<6;i++)
		{
			a[i]=in.nextInt();
		}

		System.out.println();
		
        //Insertion sort 
         
		for(j=1;j<a.length;j++)
		{
            int key=a[j];
			i=j-1;
			
			while(i>=0 && a[i]>key)
			{
               a[i+1]=a[i];
			   i--;
			}
			a[i+1]=key;
		}
       
		for(i=0;i<6;i++)
		{
			System.out.print(a[i]+" ");
		}

	}
}
