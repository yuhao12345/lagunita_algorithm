package lagunita;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Dijkstra's shortest path
public class DijShortest {
	public static int[] shortest(ArrayList<ArrayList<int[]>> a,int s) {  // starting node
		// gradually move node to the known set
		int[] d=new int[200];
		boolean[] mark=new boolean[200];
		ArrayList<Integer> k=new ArrayList<>();
		for(int i=0;i<200;i++) d[i]=1000000; 
		
		mark[s-1]=true; // add starting node to the known set
		d[s-1]=0;
		k.add(s);
		
		for (int i=1; i<200; i++) {
			ArrayList<int[]> compareList=new ArrayList<>();  // 1st: node #  2nd: distance
			for (int j=0;j<k.size();j++) { // scan all known set
				int node=k.get(j);   // start node
				ArrayList<int[]> nodelist=a.get(node-1);
				for (int m=0;m<nodelist.size();m++) {   // scan all connected node
					int node2=nodelist.get(m)[0]; // end node
					if (!mark[node2-1]) {  // this node is not marked
						compareList.add(new int[] {node2,nodelist.get(m)[1]+d[node-1]});
					}
				}
			}
			int[] pick=smallest(compareList); // return the chosen node and distance
			mark[pick[0]-1]=true;
			d[pick[0]-1]=pick[1];
			k.add(pick[0]);
		}
		return d;
	}
	public static int[] smallest(ArrayList<int[]> c) {  // choose node with min distance
		int min=c.get(0)[1];
		int minNode=c.get(0)[0];
		for (int i=1;i<c.size();i++) {
			if (c.get(i)[1]<min) {
				minNode=c.get(i)[0];
				min=c.get(i)[1];
			}
		}
		//  1st: node #  2nd: distance
		return new int[] {minNode,min};
	}
	public static void main(String[] args) {
		Scanner input=null;
		ArrayList<ArrayList<int[]>> a=new ArrayList<>();
		try {
			input = new Scanner(new File("C:\\Users\\ykang\\Documents\\lagunita\\dijkstraData.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(input.hasNextLine()) {
			a.add(new ArrayList<int[]>());
		
			String s=input.nextLine();
			String[] s1=s.split("\\s+");
			int vertex=Integer.parseInt(s1[0]);
			for (int i=1;i<s1.length;i++) {
				String[] l=s1[i].split(",");
				int l0=Integer.parseInt(l[0]);
				int l1=Integer.parseInt(l[1]);
				a.get(vertex-1).add(new int[] {l0,l1});
			}
		}
		

		System.out.println(a.size());
		//System.out.println(a.get(0).get(3)[0]+" "+a.get(0).get(3)[1]);
		int[] result=shortest(a,1);
		for (int i=0;i<200;i++) {
			System.out.println(i+1+" : "+result[i]);
		}
	}
}
