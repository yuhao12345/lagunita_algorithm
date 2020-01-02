package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mediansum {
	public static void main(String[] args) {
		Scanner input=null;
		maxHeap left=new maxHeap();  //  size: n+1 or n
		minHeap right=new minHeap();  // size: n
		
		try {
			input = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\Median.txt"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		int mid=10000;
		int sum=0;
		while(input.hasNextLine()) {
			count++;
			
			String s=input.nextLine();
			int m=Integer.parseInt(s);
			if (m<=mid)  left.insert(m);
			else  right.insert(m);

			if(right.a.size()>(count/2)) {
				int t=right.a.get(0);
				right.deleteMin(); 
				left.insert(t); 
			}

			if(left.a.size()>((count+1)/2)) {
				int t=left.a.get(0);
				left.deleteMax();
				right.insert(t);
			}
			
			mid=left.a.get(0);
			sum=(sum+mid)%10000;
			//System.out.println(count+" "+m+ input.hasNextLine()); 
		}
		System.out.println("total: "+sum); 
	}
}


