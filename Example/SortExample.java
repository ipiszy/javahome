import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


class Pair implements Comparable<Pair>
{
	public int a,b;
	Pair(int a,int b)
	{
		this.a=a;
		this.b=b;
	}
	@Override
	public int compareTo(Pair o) {
		if (a==o.a)return 0;
		else if (a<o.a)return -1;
		return 1;
	}
	
	public String toString(){
		return a+"";
	}
}
class PairComparator implements Comparator<Pair>
{

	@Override
	public int compare(Pair o1, Pair o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}

}
public class SortExample {
	public static void func(int i)throws FileNotFoundException,IOException
	{
		if(i==1)
			throw new FileNotFoundException();
		else
			throw new IOException();
		
	}
	public static void caller()
	{
		func(1);
	}
	public static void main(String[] args)
	{
		Pair pair1=new Pair(1,2);
		Pair pair2=new Pair(2,1);
		Pair pair3=new Pair(-1,3);
		Pair[] array={pair1,pair2,pair3};
		Arrays.sort(array,new PairComparator());
		ArrayList list=new ArrayList();
		list.add(pair1);
		list.add(1);
		System.out.println(list);
	}
}
