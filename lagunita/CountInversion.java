package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CountInversion {
	public static void main(String[] args) {
		//load file

		ArrayList<Integer> a=new ArrayList<>();
		try {
			Scanner inFile = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\integerArray.txt"));
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
		//int[] b=new int[] {3,4,1,5,9,10,8,90,20,30,40,50,35};
		System.out.println(CountInver(b).n);
	}
	public static Pair CountInver(int[] b) {
		assert(b!=null);
		int l=b.length;
		if (l==1) return new Pair(b,0);
		
		int[] b1=Arrays.copyOfRange(b, 0, l/2);
		int[] b2=Arrays.copyOfRange(b, l/2, l);

		Pair c1=CountInver(b1);
		Pair c2=CountInver(b2);
		Pair c3=CountSplit(c1.array,c2.array);
		long count= c1.n+ c2.n + c3.n;
		int[] sortedArray=(int[]) c3.array;
		return new Pair(sortedArray,count);
	}
	public static Pair CountSplit(int[] b1,int[] b2) { //b1,b2 are sorted array
		long l1=b1.length;
		long l2=b2.length;
		int[] array=new int[(int) (l1+l2)];
		long i = 0,j=0;
		long count=0;
		for (int k=0;k<l1+l2;k++) {
			if(i==l1) {
				array[k]=b2[(int) j];
				j++;
			}else if(j==l2) {
				array[k]=b1[(int) i];
				i++;
			}else if(b1[(int) i]<b2[(int) j]) {
				assert(i<l1 && j<l2);
				array[k]=b1[(int) i];
				i++;
			}else {
				assert(i<l1 && j<l2);
				array[k]=b2[(int) j];
				j++;
				count=count+(l1-i);
			}
		}
		return new Pair(array,count);
	}
	
	public static int[] convert(ArrayList<Integer> a) {
		int[] b=new int[a.size()];
		for (int i=0;i<a.size();i++) {
			b[i]=a.get(i);
		}
		return b;
	}
}
