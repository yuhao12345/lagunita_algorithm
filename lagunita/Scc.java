package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//run configurations: VM arguments: -Xss64m
public class Scc {
	private int currentLabel=0;
	static int number_node=875714;
	// already know there are 875714 vertices
	private boolean[] mark=new boolean[number_node];  // default is false, whether the node is touched
	private ArrayList<int[]> ftime=new ArrayList<int[]>();
	public ArrayList<ArrayList<Integer>> b;
	public Scc(ArrayList<ArrayList<Integer>> b) {
		this.b=b;
	}
	public void DFS(int s){ // start point
		mark[s-1]=true; // mark node first
		if (s>b.size()) { // the node has no out-edge
			ftime.add(new int[] {s,++currentLabel}); 
			return;
		}
		if (b.get(s-1).size()==0) {  // the node has no out-edge
			ftime.add(new int[] {s,++currentLabel}); 
			return;
		}
		for (int i=0;i<b.get(s-1).size();i++) {  // scan the out edge
			int n=b.get(s-1).get(i);
			if(mark[n-1]==false) { // the node i will be the new node
				DFS(n);
			}
		}
		// all out nodes are marked
		ftime.add(new int[] {s,++currentLabel}); 
		return;
	}
	public void DFS_loop() {  
		// graph_rev, return finishing time for each node, 1st column is node index, 2nd column is f-time
		// start from the largest index
		for (int i=number_node; i>0; i--) {
			if (mark[i-1]==false) {  // if not visited before, do DFS
				DFS(i);
			}
		}
	}
	public int DFS_scc(int s) {
		int count=1;  // s itself
		mark[s-1]=true;
		if (s>b.size()) return 1; // the node has no out-edge
		if (b.get(s-1).size()==0) return 1;	
		for (int i=0;i<b.get(s-1).size();i++) {  // scan the out edge
			int n=b.get(s-1).get(i);
			if(mark[n-1]==false) { // the node i will be the new node
				count+=DFS_scc(n);
			}
		}
		// all out nodes are marked
		return count;
	}
	public ArrayList<Integer> DFS_loop_scc(int[] ft) {   // 2nd step, original graph
		ArrayList<Integer> cluster=new ArrayList<>();
		// start from largest ft
		for (int i=number_node;i>0;i--) {
			int j=ft[i-1];    // node index in graph
			if (mark[j-1]==false) {
				cluster.add(DFS_scc(j));
			}
		}
		return cluster;
	}
	public static int[] convertFtime(ArrayList<int[]> f) {   // finishing time -> node 
		int[] ft=new int[number_node];
		for (int i=0;i<number_node;i++) {
			ft[f.get(i)[1]-1]=f.get(i)[0];
		}
		return ft;
	}
	public static ArrayList<Integer> rank(ArrayList<Integer> c) {
		for (int j=c.size()-2;j>=0;j--) {
			for (int i=0;i<=j;i++) {
				if(c.get(i)<c.get(i+1)) {
					int tmp=c.get(i);
					c.set(i, c.get(i+1));
					c.set(i+1, tmp);
				}
			}
		}
		return c;
	}

	public static void main(String[] args) {
		Scanner input=null;
		ArrayList<ArrayList<Integer>> a=new ArrayList<>();
		
		ArrayList<ArrayList<Integer>> b=new ArrayList<>();  // reversed graph
		try {
			input = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\SCC.txt"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(input.hasNextLine()) {
			String s=input.nextLine();
			int[] s1=Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).toArray();
			while (s1[0]>a.size()) {  // the new vertex is not added
				a.add(new ArrayList<Integer>());
			}
			a.get(s1[0]-1).add(s1[1]);   //this new vertex add a new out edge
			while (s1[1]>b.size()) {  // the new vertex is not added
				b.add(new ArrayList<Integer>());
			}
			b.get(s1[1]-1).add(s1[0]);   //this new vertex add a new out edge
		}
		//System.out.println(a.size());  // get adjacent list
		//System.out.println(b.size());  // the b does not contain whole vertices
		Scc g_rev=new Scc(b);
		g_rev.DFS_loop();
		//System.out.println(g_rev.currentLabel);
		int[] ft=convertFtime(g_rev.ftime);  // finishing time -> node 
		Scc g=new Scc(a);
		ArrayList<Integer> cluster=g.DFS_loop_scc(ft);
		ArrayList<Integer> r=rank(cluster);
		System.out.println("cluster size");
		int n_node=0;
		for (int i=0;i<r.size();i++) {
			n_node+=r.get(i);
		}
		System.out.println("total node: "+n_node);
		for (int i=0;i<10;i++) {
			System.out.println(r.get(i));
		}
	}
}

