package lagunita;

import java.util.ArrayList;

public class minHeap{
	public ArrayList<Integer> a;
	public minHeap() {
		a=new ArrayList<Integer>();
	}
	public void insert(int n) {
		a.add(n);
		int s=a.size();
		if (s==1) return;
		while(a.get(s-1)<a.get(s/2-1)) {
			swap(s-1,s/2-1);
			s=s/2;
			if (s==1) return;
		}
		
	}
	public void deleteMin() {
		int s=a.size();
		if(s==1) a=null;
		else {
			swap(0,s-1); 
			a.remove(s-1);   // size:s-1
			int t=1;
			while(2*t<=s-2) {
				int l=a.get(2*t-1);
				int r=a.get(2*t);
				if (a.get(t-1)>Math.min(l, r)) {
					if (a.get(2*t-1)>=a.get(2*t)) {swap(2*t,t-1); t=2*t+1;}
					else {swap(2*t-1,t-1); t=2*t;}
				}else
					break;
			}
			if(2*t-1==s-2)
				if (a.get(t-1)>a.get(2*t-1))
					swap(t-1,2*t-1);
		}
	}
	public void swap(int i,int j) {
		int tmp=a.get(i);
		a.set(i, a.get(j));
		a.set(j, tmp);
	}
}