package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class KargerMinCut {
	private ArrayList<int[]> a;   // adjacent matrix
	private int nedge;  // number of edge*2
	private int npoint;
	public KargerMinCut(ArrayList<int[]> a) {
		this.a=a;
		npoint=a.size();
		updateNedge();
	}
	public void updateNedge() {
		nedge=0;
		for (int i=0;i<a.size();i++) {
			nedge+=(a.get(i).length-1);   // a include the node itself
		}
	}
	public int[] randomPick() {
		Random rnd=new Random();
		//System.out.println(nedge);
		int r=rnd.nextInt(nedge);
		int node1=0;
		while(r>=(a.get(node1).length-1)) {
			r-=(a.get(node1).length-1);
			node1++;
		}
		int node2=a.get(node1)[r];  // not r-1
		return new int[] {node1+1,node2};   // node start from 1
	}
	public static int[] convertArrayList(ArrayList<Integer> a) {
		int[] b=new int[a.size()];
		for (int i=0;i<a.size();i++) {
			b[i]=a.get(i);
		}
		return b;
	}
	public boolean mergeTwoNode(int[] pair) {
		int n1=pair[0];
		int n2=pair[1];
		ArrayList<Integer> update=new ArrayList<>();
		update.add(n1);
		int[] l1=a.get(n1-1);   // linked node list of node n1
		int[] l2=a.get(n2-1);  
		if (n1==n2) {
			for (int i=1;i<l1.length;i++) {
				if (l1[i]!=n2) update.add(l1[i]);
			}
			a.set(n1-1, convertArrayList(update));
			updateNedge();
			return true;
		}else {
			for (int i=1;i<l1.length;i++) {
				if (l1[i]!=n2 && l1[i]!=n1) update.add(l1[i]);
			}
			for (int i=1;i<l2.length;i++) {
				if (l2[i]!=n2 && l2[i]!=n1) update.add(l2[i]);
			}
			a.set(n1-1, convertArrayList(update));
			a.set(n2-1,new int[] {n2});
			
			npoint--;
			replaceNode(n1,n2);  // replace n2 by n1
			updateNedge();
			return true;
		}
	}
	public void replaceNode(int n1,int n2) {  // merge n1 and n2, so that n2 are replaced by n1
		for (int i=0;i<a.size();i++) {  
			int[] b=a.get(i);
			ArrayList<Integer> tmp=new ArrayList<>();
			tmp.add(b[0]);
			for (int j=1;j<b.length;j++) {
				if (b[j]==n2) tmp.add(n1);
				else tmp.add(b[j]);
			}
			a.set(i, convertArrayList(tmp));
		}
	}
	/*
	public void simplify() {
		a.removeIf(Objects::isNull);
	}*/
	public static int minCut(ArrayList<int[]> a_input) {
		ArrayList<int[]> a=new ArrayList<>(a_input);  // must create a new one, or will influence input
		KargerMinCut f=new KargerMinCut(a);
		while (f.npoint>2) {
			f.mergeTwoNode(f.randomPick());	
		}
		/*
		ArrayList<Integer> node=new ArrayList<>();
		for (int i=0;i<a.size();i++) {
			if (a.get(i).length>1) node.add(i+1);
		}
		*/
		//System.out.println("remaining # of node: "+node.size());
		//int n1=f.a.get(node.get(0)-1).length;
		//int n2=f.a.get(node.get(1)-1).length;

		//System.out.println("n1:" +n1);
		//System.out.println("n2:" +n2);
		//System.out.println("edge:"+ f.nedge);
		//for (int i=0;i<n1;i++)
			//System.out.print(f.a.get(node.get(0)-1)[i]+" ");
		//System.out.println();
		//for (int i=0;i<n2;i++)
			//System.out.print(f.a.get(node.get(0)-1)[i]+" ");
		//System.out.println();
		return f.nedge/2;
	}
	public static int min(int[] p) {
		int m=p[0];
		for (int i=1;i<p.length;i++)
			if (p[i]<m) m=p[i];
		return m;
	}
	public static void main(String[] args) {
		Scanner input=null;
		ArrayList<int[]> input_array=new ArrayList<>();
		try {
			input = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\kargerMinCut.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(input.hasNextLine()) {
			String s=input.nextLine();
			int[] s1=Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).toArray();
			input_array.add(s1);	
		}
		//System.out.println(a.size());

		//System.out.println(minCut(input_array));
		
		int npoint=input_array.size();
		int ntest=10000;//(int) (Math.pow(npoint, 2)*Math.log(npoint));  // failing rate: 1/n
		int[] result=new int[ntest];
		for (int i=0;i<ntest;i++) {
			result[i]=minCut(input_array);
		}
		System.out.print(min(result));
	}
}
