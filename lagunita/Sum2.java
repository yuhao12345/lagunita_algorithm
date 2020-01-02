package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sum2 {
	public static int hash(long n) {
		int r=(int)(n % 1000000);
		if (r<0) r+=1000000;
		return r;
	}
	public static boolean hasTarget(int t,ArrayList<ArrayList<Long>> a,ArrayList<Long> list) {
		// t is the sum of two number, a is hash table of data, list is data
		for (int i=0;i<list.size();i++) {
			long m=list.get(i);	
			if (m!=(t-m))
				if (hasNumber(t-m,a)) return true;
		}
		return false;
	}
	public static boolean hasNumber(long n,ArrayList<ArrayList<Long>> a) {
		int h=hash(n);
		if (h>=a.size()) return false;
		ArrayList<Long> b=a.get(h);
		for(int i=0;i<b.size();i++) {
			if (b.get(i)==n) return true;
		}
		return false;
	}
	public static void main(String[] args) {
		Scanner input=null;
		ArrayList<ArrayList<Long>> a=new ArrayList<>();
		ArrayList<Long> list=new ArrayList<>();
		
		try {
			input = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\algo1-programming_prob-2sum.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(input.hasNextLine()) {
			String s=input.nextLine();
			long m=Long.parseLong(s);
			list.add(m);
			int n=hash(m);
			while (n>=a.size()) a.add(new ArrayList<Long>());
			//System.out.println(m+" "+n);
			a.get(n).add(m);
		}
		//System.out.println(a.size());  // get adjacent list
		//System.out.println(list.size()); 
		int count=0;
		
		for (int t=-10000; t<=10000; t++) {
			if (hasTarget(t,a,list)) count++;
		}
		System.out.println(count); 
		//System.out.println(hasTarget(2000,a,list));

	}
}

