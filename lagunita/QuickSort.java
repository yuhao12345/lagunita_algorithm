package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuickSort {
	public long count=0;   // # of comparison 
	private int[] a;
	public QuickSort(int[] a) {
		this.a=a;
	}
	public int[] getArray() {
		return a;
	}
	public long quickSort(int s,int f) {  //starting index, ending index
		assert(s<f);
		if (f==s || f==s+1) return 0;
		
		int l=f-s;
		int i=s+1;
		// set  first element of the array as the pivot element
		//int p=a[s]; 
	    //  set final element of the given array as the pivot element
		//int p=a[f-1]; swap(s,f-1);   
		//  choose the pivot as median of the first, middle, and final elements of the given array.
		int[] m=median(a[s],a[f-1],a[(l-1)/2+s]); 
		int p=m[0];
		int position=new int[] {s,f-1,(l-1)/2+s}[m[1]];
		swap(position,s);
		
		for (int j=s+1;j<f;j++) {
			if (a[j]<p) {
				if (i<j)
					swap(j,i);
				i++;
			}

		}
		if (i!=s+1) swap(s,i-1);
		count= l-1 + quickSort(s,i-1) + quickSort(i,f);
		return count;
	}
	public void swap(int i,int j) {
		int tmp=a[i];
		a[i]=a[j];
		a[j]=tmp;
	}
	public static int[] convert(ArrayList<Integer> a) {
		int[] b=new int[a.size()];
		for (int i=0;i<a.size();i++) {
			b[i]=a.get(i);
		}
		return b;
	}
	public static int[] median(int a,int b,int c) {
		if ((a-b)*(a-c)<0) return new int[] {a,0};
		if ((b-a)*(b-c)<0) return new int[] {b,1};
		return new int[] {c,2};
	}
	public static void main(String[] args) {
		//load file

		ArrayList<Integer> a=new ArrayList<>();
		try {
			Scanner inFile = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\QuickSort.txt"));
		    while (inFile.hasNext()) {
		        // find next line
		        String token1 = inFile.next();
		        a.add(Integer.valueOf(token1));
		      }
		      inFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] b = convert(a);
		int l=b.length;
		System.out.println(b[10]+" "+l);

		//test
		//int[] b=new int[] {3,4,1,5};
		QuickSort q=new QuickSort(b);
		q.quickSort(0, b.length);
		System.out.println(q.count);
		System.out.println(q.getArray()[0]);
	}
}
